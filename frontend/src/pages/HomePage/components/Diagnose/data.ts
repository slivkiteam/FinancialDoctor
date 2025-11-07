interface Step {
    id: number;
    bgColor: string;
    title: string;
}

export const steps: Step[] = [
    {
        id: 1,
        bgColor: 'rgba(142, 197, 255, 1)',
        title: 'Подключи свой банк',
    },
    {
        id: 2,
        bgColor: 'rgba(123, 241, 168, 1)',
        title: 'Получи свою аналитику'
    },
    {
        id: 3,
        bgColor: 'rgba(70, 237, 213, 1)',
        title: 'Следуй финансовым советам'
    }
]