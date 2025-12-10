package ru.slivki.financial_doctor.service.impl;

import jakarta.transaction.Transactional;
import ru.slivki.financial_doctor.model.FinancialDiagnosis;
import ru.slivki.financial_doctor.model.TreatmentPlan;
import ru.slivki.financial_doctor.model.Transaction;
import org.springframework.stereotype.Service;
import ru.slivki.financial_doctor.repository.FinancialDiagnosisRepository;
import ru.slivki.financial_doctor.repository.TreatmentPlanRepository;
import ru.slivki.financial_doctor.repository.TransactionRepository;
import ru.slivki.financial_doctor.repository.UserRepository;
import ru.slivki.financial_doctor.service.FinancialDiagnosisService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FinancialDiagnosisServiceImpl implements FinancialDiagnosisService {

    private final FinancialDiagnosisRepository financialDiagnosisRepository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public FinancialDiagnosisServiceImpl(
            final FinancialDiagnosisRepository financialDiagnosisRepository,
            final TreatmentPlanRepository treatmentPlanRepository,
            final TransactionRepository transactionRepository,
            final UserRepository userRepository) {
        this.financialDiagnosisRepository = financialDiagnosisRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FinancialDiagnosis recalculateDiagnosis(final Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // анализируем последний месяц
        LocalDate today = LocalDate.now();
        LocalDateTime periodStart = today.minusMonths(1).atStartOfDay();
        LocalDateTime periodEnd = today.plusDays(1).atStartOfDay();

        List<Transaction> transactions =
                transactionRepository.findByUserIdAndTransactionDateBetween(userId, periodStart, periodEnd);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        BigDecimal creditPayments = BigDecimal.ZERO;
        BigDecimal cashWithdrawals = BigDecimal.ZERO;
        BigDecimal investmentOut = BigDecimal.ZERO;
        int cashPaymentsCount = 0;

        for (Transaction txn : transactions) {
            if (txn.getAmount() == null) {
                continue;
            }
            String type = safeLower(txn.getTransactionType());
            String desc = safeLower(txn.getDescription());
            String merchant = safeLower(txn.getMerchantCategory());

            int sign = txn.getAmount().signum();
            if (sign > 0) {
                totalIncome = totalIncome.add(txn.getAmount());
            } else if (sign < 0) {
                totalExpenses = totalExpenses.add(txn.getAmount().abs());
            }

            if (sign < 0 && (type.contains("credit") || desc.contains("кредит") || desc.contains("loan") || merchant.contains("loan"))) {
                creditPayments = creditPayments.add(txn.getAmount().abs());
            }
            if (sign < 0 && (type.contains("cash") || desc.contains("atm") || desc.contains("налич"))) {
                cashWithdrawals = cashWithdrawals.add(txn.getAmount().abs());
                cashPaymentsCount++;
            }
            if (sign < 0 && (type.contains("invest") || merchant.contains("invest") || desc.contains("инвест") || desc.contains("crypto") || merchant.contains("crypto"))) {
                investmentOut = investmentOut.add(txn.getAmount().abs());
            }
        }

        BigDecimal netCashFlow = totalIncome.subtract(totalExpenses);
        int spendingScore = calculateSpendingScore(totalIncome, totalExpenses);
        int savingScore = calculateSavingScore(totalIncome, totalExpenses);
        int borrowingScore = 70; // заглушка
        int planningScore = 70;  // заглушка
        int financialHealthScore = (spendingScore + savingScore + borrowingScore + planningScore) / 4;

        String diagnosisType = buildDiagnosisType(
                totalIncome,
                totalExpenses,
                netCashFlow,
                transactions.size(),
                spendingScore,
                savingScore,
                creditPayments,
                cashWithdrawals,
                investmentOut,
                cashPaymentsCount);

        // деактивируем прошлые диагнозы
        var existing = financialDiagnosisRepository.findByUserIdAndIsActiveTrue(userId);
        existing.forEach(d -> d.setIsActive(false));
        financialDiagnosisRepository.saveAll(existing);

        FinancialDiagnosis diagnosis = new FinancialDiagnosis();
        diagnosis.setUser(user);
        diagnosis.setDiagnosisType(diagnosisType);
        diagnosis.setAnalysisPeriodStart(periodStart.toLocalDate());
        diagnosis.setAnalysisPeriodEnd(periodEnd.toLocalDate());
        diagnosis.setFinancialHealthScore(financialHealthScore);
        diagnosis.setSpendingScore(spendingScore);
        diagnosis.setSavingScore(savingScore);
        diagnosis.setBorrowingScore(borrowingScore);
        diagnosis.setPlanningScore(planningScore);
        diagnosis.setIsActive(true);
        diagnosis.setDiagnosisDate(LocalDateTime.now());

        FinancialDiagnosis savedDiagnosis = financialDiagnosisRepository.save(diagnosis);

        // создаём базовый план лечения под диагноз
        createOrUpdateTreatmentPlansForDiagnosis(savedDiagnosis, diagnosisType, netCashFlow, totalIncome, totalExpenses);

        return savedDiagnosis;
    }

    @Override
    public FinancialDiagnosis getCurrentDiagnosis(final Long userId) {
        return financialDiagnosisRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<FinancialDiagnosis> getDiagnosisHistory(final Long userId) {
        return financialDiagnosisRepository.findByUserId(userId);
    }

    @Override
    public List<TreatmentPlan> getCurrentTreatmentPlans(final Long userId) {
        return treatmentPlanRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }

    @Override
    public TreatmentPlan updateTreatmentPlanProgress(final Long planId,
                                                     final Integer progressPercentage,
                                                     final String status,
                                                     final BigDecimal currentValue) {
        TreatmentPlan plan = treatmentPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Treatment plan not found: " + planId));

        if (progressPercentage != null) {
            plan.setProgressPercentage(progressPercentage);
        }
        if (status != null) {
            plan.setStatus(status);
        }
        if (currentValue != null) {
            plan.setCurrentValue(currentValue);
        }
        plan.setUpdatedAt(LocalDateTime.now());

        if ("COMPLETED".equalsIgnoreCase(plan.getStatus()) && plan.getCompletedAt() == null) {
            plan.setCompletedAt(LocalDateTime.now());
        }

        return treatmentPlanRepository.save(plan);
    }

    private int calculateSpendingScore(final BigDecimal totalIncome, final BigDecimal totalExpenses) {
        if (totalIncome == null || totalIncome.signum() <= 0) {
            return 50;
        }
        if (totalExpenses == null) {
            return 80;
        }
        BigDecimal ratio = safeDivide(totalExpenses, totalIncome);
        if (ratio.compareTo(new BigDecimal("0.6")) <= 0) {
            return 90;
        }
        if (ratio.compareTo(new BigDecimal("0.8")) <= 0) {
            return 70;
        }
        return 50;
    }

    private int calculateSavingScore(final BigDecimal totalIncome, final BigDecimal totalExpenses) {
        if (totalIncome == null || totalIncome.signum() <= 0) {
            return 50;
        }
        if (totalExpenses == null) {
            return 80;
        }
        BigDecimal savings = totalIncome.subtract(totalExpenses);
        if (savings.signum() <= 0) {
            return 40;
        }
        BigDecimal savingRate = safeDivide(savings, totalIncome);
        if (savingRate.compareTo(new BigDecimal("0.2")) >= 0) {
            return 90;
        }
        if (savingRate.compareTo(new BigDecimal("0.1")) >= 0) {
            return 70;
        }
        return 50;
    }

    private String buildDiagnosisType(final BigDecimal totalIncome,
                                      final BigDecimal totalExpenses,
                                      final BigDecimal netCashFlow,
                                      final int transactionsCount,
                                      final int spendingScore,
                                      final int savingScore,
                                      final BigDecimal creditPayments,
                                      final BigDecimal cashWithdrawals,
                                      final BigDecimal investmentOut,
                                      final int cashPaymentsCount) {
        BigDecimal expenseRate = safeDivide(totalExpenses, totalIncome);
        BigDecimal savingRate = safeDivide(totalIncome.subtract(totalExpenses), totalIncome);
        BigDecimal creditRate = safeDivide(creditPayments, totalIncome);
        BigDecimal cashRate = safeDivide(cashWithdrawals, totalExpenses);
        BigDecimal investRateFromSavings = safeDivide(investmentOut, totalIncome.max(BigDecimal.ONE));

        if (expenseRate.compareTo(new BigDecimal("0.90")) >= 0 && savingRate.compareTo(new BigDecimal("0.05")) < 0) {
            return "РАССЕЯННЫЙ СПЕНДЕР";
        }

        if (savingRate.compareTo(new BigDecimal("0.15")) >= 0 && netCashFlow.signum() > 0 && creditRate.compareTo(new BigDecimal("0.10")) < 0) {
            return "ЗДОРОВЫЙ БУХГАЛТЕР";
        }

        if (creditRate.compareTo(new BigDecimal("0.25")) >= 0) {
            return "КРЕДИТНЫЙ ЗАЛОЖНИК";
        }

        if (totalIncome.compareTo(new BigDecimal("50000")) < 0 && transactionsCount > 30 && savingRate.compareTo(new BigDecimal("0.05")) < 0) {
            return "БЮДЖЕТНЫЙ СТУДЕНТ";
        }

        if (transactionsCount <= 2 && netCashFlow.signum() <= 0) {
            return "ДЕНЕЖНЫЙ ЗАТВОРНИК";
        }

        if (savingRate.compareTo(new BigDecimal("0.15")) >= 0 && investRateFromSavings.compareTo(new BigDecimal("0.01")) < 0) {
            return "КОНСЕРВАТИВНЫЙ ВКЛАДЧИК";
        }

        if (investRateFromSavings.compareTo(new BigDecimal("0.50")) >= 0) {
            return "РИСКОВЫЙ ИГРОК";
        }

        if (cashRate.compareTo(new BigDecimal("0.80")) >= 0 || cashPaymentsCount >= (int) Math.max(5, transactionsCount * 0.8)) {
            return "ЦИФРОВОЙ ОТШЕЛЬНИК";
        }

        if (savingRate.compareTo(new BigDecimal("0.40")) >= 0) {
            return "ФИНАНСОВЫЙ АСКЕТ";
        }

        if (transactionsCount < 5 && netCashFlow.signum() <= 0) {
            return "НЕРАВНОМЕРНЫЙ ПОТОК";
        }

        if (spendingScore < 60 && savingScore < 60) {
            return "НЕУСТОЙЧИВЫЙ ПЛАНИРОВЩИК";
        }
        return "ЗДОРОВЫЙ БУХГАЛТЕР";
    }

    private void createOrUpdateTreatmentPlansForDiagnosis(final FinancialDiagnosis diagnosis,
                                                          final String diagnosisType,
                                                          final BigDecimal netCashFlow,
                                                          final BigDecimal totalIncome,
                                                          final BigDecimal totalExpenses) {
        // На старте — простое ветвление по типу диагноза из текстовой схемы
        switch (diagnosisType) {
            case "РАССЕЯННЫЙ СПЕНДЕР" -> createPlansRasseyannyySpender(diagnosis, totalIncome, totalExpenses);
            case "ЗДОРОВЫЙ БУХГАЛТЕР" -> createPlansZdorovyyBukhgalter(diagnosis, totalIncome, totalExpenses);
            case "ФИНАНСОВЫЙ АСКЕТ" -> createPlansFinansovyyAsket(diagnosis, totalIncome);
            case "БЮДЖЕТНЫЙ СТУДЕНТ" -> createPlansByudzhetnyyStudent(diagnosis);
            case "НЕРАВНОМЕРНЫЙ ПОТОК" -> createPlansNeravnomernyyPotok(diagnosis, totalIncome);
            case "КРЕДИТНЫЙ ЗАЛОЖНИК" -> createPlansKreditnyZalozhnik(diagnosis);
            case "ДЕНЕЖНЫЙ ЗАТВОРНИК" -> createPlansDeneznyyZatvornik(diagnosis);
            case "КОНСЕРВАТИВНЫЙ ВКЛАДЧИК" -> createPlansKonservativnyVkladchik(diagnosis);
            case "РИСКОВЫЙ ИГРОК" -> createPlansRiskovyyIgrok(diagnosis);
            case "ЦИФРОВОЙ ОТШЕЛЬНИК" -> createPlansCifrovoyOtshelnik(diagnosis);
            default -> createPlansRasseyannyySpender(diagnosis, totalIncome, totalExpenses);
        }
    }

    private void createPlansRasseyannyySpender(final FinancialDiagnosis diagnosis,
                                               final BigDecimal totalIncome,
                                               final BigDecimal totalExpenses) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "SAVINGS",
                "Настройте автосписание 10% от зарплаты на накопительный счёт",
                "Автосписание 10% от дохода на отдельный накопительный счёт.",
                "HIGH",
                "ACTIVE",
                "SAVING_RATE",
                safeMultiply(totalIncome, new BigDecimal("0.10")),
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "SPENDING",
                "Установите еженедельный лимит на категорию \"Развлечения\"",
                "Определите недельный лимит на развлечения и придерживайтесь его.",
                "MEDIUM",
                "ACTIVE",
                "ENTERTAINMENT_LIMIT",
                safeMultiply(totalExpenses, new BigDecimal("0.20")),
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "IMPULSE_CONTROL",
                "Правило 24 часов для незапланированных покупок > 2000р",
                "Перед покупкой дороже 2000р делайте паузу 24 часа.",
                "LOW",
                "ACTIVE",
                "IMPULSE_CONTROL",
                BigDecimal.valueOf(2000),
                BigDecimal.ZERO));
    }

    private void createPlansZdorovyyBukhgalter(final FinancialDiagnosis diagnosis,
                                               final BigDecimal totalIncome,
                                               final BigDecimal totalExpenses) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "SAVINGS",
                "Разделите накопления на подушку, цели и инвестиции",
                "Создайте три корзины: подушка, цели, инвестиции.",
                "MEDIUM",
                "ACTIVE",
                "BUCKETING",
                safeMultiply(totalIncome, new BigDecimal("0.05")),
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "INCOME",
                "Найдите вклад с процентной ставкой выше текущей",
                "Сравните ставки и выберите более доходный вклад.",
                "MEDIUM",
                "ACTIVE",
                "DEPOSIT_RATE",
                safeMultiply(totalIncome, new BigDecimal("0.05")),
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "INVEST",
                "Микроинвестиции 5% от дохода",
                "Направляйте 5% дохода в микроинвестиции после подушки.",
                "LOW",
                "ACTIVE",
                "MICRO_INVEST",
                safeMultiply(totalIncome, new BigDecimal("0.05")),
                BigDecimal.ZERO));
    }

    private void createPlansFinansovyyAsket(final FinancialDiagnosis diagnosis,
                                            final BigDecimal totalIncome) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "BALANCE",
                "Добавьте 5%-10% расходов «На радость себе»",
                "Запланируйте траты на хобби/отдых, чтобы избежать выгорания.",
                "MEDIUM",
                "ACTIVE",
                "JOY_EXPENSE",
                safeMultiply(totalIncome, new BigDecimal("0.05")),
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "GOAL",
                "Накопите на покупку, улучшающую жизнь/настроение",
                "Определите конкретную покупку и копите на неё.",
                "LOW",
                "ACTIVE",
                "POSITIVE_GOAL",
                safeMultiply(totalIncome, new BigDecimal("0.10")),
                BigDecimal.ZERO));
    }

    private void createPlansByudzhetnyyStudent(final FinancialDiagnosis diagnosis) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "TRACKING",
                "Ведите учёт всех трат 2 недели",
                "Каждую покупку фиксируйте для понимания картины расходов.",
                "HIGH",
                "ACTIVE",
                "EXPENSE_TRACKING",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "RULE",
                "Правило 50/30/20",
                "Нужды/хотелки/инвестиции: 50/30/20.",
                "MEDIUM",
                "ACTIVE",
                "BUDGET_RULE",
                BigDecimal.ZERO,
                BigDecimal.ZERO));
    }

    private void createPlansNeravnomernyyPotok(final FinancialDiagnosis diagnosis,
                                               final BigDecimal totalIncome) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "CASH_BUFFER",
                "Откладывайте 40%-50% в «жирные» месяцы",
                "Формируйте буфер для «тощих» месяцев.",
                "HIGH",
                "ACTIVE",
                "BUFFER",
                safeMultiply(totalIncome, new BigDecimal("0.40")),
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "SEPARATE_ACCOUNT",
                "Отдельный счёт для нерегулярных доходов",
                "Используйте отдельный счёт для сглаживания потока.",
                "MEDIUM",
                "ACTIVE",
                "SEPARATE_ACCOUNT",
                BigDecimal.ZERO,
                BigDecimal.ZERO));
    }

    private void createPlansKreditnyZalozhnik(final FinancialDiagnosis diagnosis) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "DEBT",
                "Метод снежного кома: гасите самый дорогой долг",
                "Направляйте свободные средства на долг с максимальной ставкой.",
                "HIGH",
                "ACTIVE",
                "DEBT_SNOWBALL",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "DEBT",
                "Изучите предложения по рефинансированию кредитов",
                "Сравните ставки и рассмотрите рефинансирование.",
                "MEDIUM",
                "ACTIVE",
                "REFINANCE",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "DEBT",
                "Воздержитесь от новых кредитов до закрытия текущих",
                "Не открывайте новые кредиты, пока не погашены действующие.",
                "LOW",
                "ACTIVE",
                "NEW_CREDIT_PAUSE",
                BigDecimal.ZERO,
                BigDecimal.ZERO));
    }

    private void createPlansDeneznyyZatvornik(final FinancialDiagnosis diagnosis) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "HABIT",
                "5 минут в день на проверку баланса и истории",
                "Ежедневно уделяйте 5 минут проверке счетов.",
                "HIGH",
                "ACTIVE",
                "DAILY_CHECK",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "AUTOPAY",
                "Настройте автооплату коммунальных услуг и кредитов",
                "Подключите автоплатёж по обязательным расходам.",
                "HIGH",
                "ACTIVE",
                "AUTOPAY",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "GOAL",
                "Поставьте маленькую приятную финансовую цель",
                "Определите простую цель и отслеживайте прогресс.",
                "LOW",
                "ACTIVE",
                "SMALL_GOAL",
                BigDecimal.ZERO,
                BigDecimal.ZERO));
    }

    private void createPlansKonservativnyVkladchik(final FinancialDiagnosis diagnosis) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "ALLOCATE",
                "Разделите сбережения на 3 корзины: ликвидность, безопасность, рост",
                "Распределите накопления по трем корзинам.",
                "MEDIUM",
                "ACTIVE",
                "THREE_BUCKETS",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "EDU",
                "Пройдите курс \"Первые шаги в инвестициях\"",
                "Обучение базовым принципам инвестиций.",
                "LOW",
                "ACTIVE",
                "INVEST_EDU",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "INVEST",
                "Соберите стартовый инвест-портфель",
                "Сформируйте базовый диверсифицированный портфель.",
                "MEDIUM",
                "ACTIVE",
                "START_PORTFOLIO",
                BigDecimal.ZERO,
                BigDecimal.ZERO));
    }

    private void createPlansRiskovyyIgrok(final FinancialDiagnosis diagnosis) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "REBALANCE",
                "Сделайте ребалансировку: риск не выше 10%-15%",
                "Снизьте долю рискованных активов до 10%-15%.",
                "HIGH",
                "ACTIVE",
                "REBALANCE",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "SHIFT",
                "Переведите основу портфеля в консервативные инструменты",
                "Увеличьте долю облигаций/депозитов/индексов.",
                "MEDIUM",
                "ACTIVE",
                "SHIFT_TO_SAFE",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "RULE24",
                "24 часа перед покупкой рискованной акции",
                "Делайте паузу перед покупкой высокорискованных активов.",
                "LOW",
                "ACTIVE",
                "RISK_PAUSE",
                BigDecimal.ZERO,
                BigDecimal.ZERO));
    }

    private void createPlansCifrovoyOtshelnik(final FinancialDiagnosis diagnosis) {
        treatmentPlanRepository.save(buildPlan(diagnosis,
                "ONE_SCENARIO",
                "Начните с одного онлайн-сценария (например, оплата ЖКУ)",
                "Попробуйте безопасный онлайн-платёж как пилот.",
                "HIGH",
                "ACTIVE",
                "ONLINE_PILOT",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "ASSIST",
                "Попросите кассира помочь настроить автоплатёж обязательных расходов",
                "Подключите автоплатёж с поддержкой банка.",
                "MEDIUM",
                "ACTIVE",
                "ASSISTED_AUTOPAY",
                BigDecimal.ZERO,
                BigDecimal.ZERO));

        treatmentPlanRepository.save(buildPlan(diagnosis,
                "SECURITY",
                "Заведите тетрадку/менеджер паролей для онлайн-банка",
                "Удобно и безопасно храните пароли/коды.",
                "LOW",
                "ACTIVE",
                "PASSWORD_HYGIENE",
                BigDecimal.ZERO,
                BigDecimal.ZERO));
    }

    private TreatmentPlan buildPlan(final FinancialDiagnosis diagnosis,
                                    final String planType,
                                    final String title,
                                    final String description,
                                    final String priority,
                                    final String status,
                                    final String targetMetric,
                                    final BigDecimal targetValue,
                                    final BigDecimal currentValue) {
        TreatmentPlan plan = new TreatmentPlan();
        plan.setDiagnosis(diagnosis);
        plan.setUser(diagnosis.getUser());
        plan.setPlanType(planType);
        plan.setTitle(title);
        plan.setDescription(description);
        plan.setPriority(priority);
        plan.setStatus(status);
        plan.setTargetMetric(targetMetric);
        plan.setTargetValue(targetValue == null ? BigDecimal.ZERO : targetValue);
        plan.setCurrentValue(currentValue == null ? BigDecimal.ZERO : currentValue);
        plan.setProgressPercentage(0);
        plan.setStartDate(LocalDate.now());
        plan.setUpdatedAt(LocalDateTime.now());
        return plan;
    }

    private BigDecimal safeDivide(final BigDecimal numerator, final BigDecimal denominator) {
        if (numerator == null || denominator == null || denominator.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return numerator.divide(denominator, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal safeMultiply(final BigDecimal value, final BigDecimal factor) {
        if (value == null || factor == null) {
            return BigDecimal.ZERO;
        }
        return value.multiply(factor);
    }

    private String safeLower(final String value) {
        return value == null ? "" : value.toLowerCase();
    }
}


