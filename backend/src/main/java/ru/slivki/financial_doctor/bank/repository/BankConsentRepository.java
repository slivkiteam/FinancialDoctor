package ru.slivki.financial_doctor.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slivki.financial_doctor.bank.model.BankConsent;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankConsentRepository extends JpaRepository<BankConsent, String> {

    Optional<BankConsent> findByConsentIdAndUserId(String consentId, Long userId);

    List<BankConsent> findByUserId(Long userId);
}



