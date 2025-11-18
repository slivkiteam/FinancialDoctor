interface Step {
    id: number;
    bgColor: string;
    title: string;
}

export const steps: Step[] = [
    {
        id: 1,
        bgColor: 'rgba(21, 93, 252, 1)',
        title: 'Подключи свой банк',
    },
    {
        id: 2,
        bgColor: 'rgba(0, 166, 62, 1)',
        title: 'Получи свою аналитику'
    },
    {
        id: 3,
        bgColor: 'rgba(0, 150, 137, 1)',
        title: 'Следуй финансовым советам'
    }
]