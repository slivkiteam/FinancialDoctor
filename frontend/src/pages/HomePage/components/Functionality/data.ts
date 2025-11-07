import pig from '@assets/functionality/piggy-bank.svg'
import goal from '@assets/functionality/goal.svg'
import chart from '@assets/functionality/chart-column-big.svg'
import shield from '@assets/functionality/shield.svg'

interface Card {
    id: number;
    image: string;
    title: string;
    description: string;
}

export const cardsData: Card[] = [
    {
        id: 1,
        image: pig,
        title: 'Персоанализированные советы',
        description: 'Персоанализированные советы'
    },
    {
        id: 2,
        image: chart,
        title: 'Оценка финансового здоровья',
        description: 'Оценка финансового здоровья'
    },
    {
        id: 3,
        image: goal,
        title: 'Планы трат',
        description: 'Планы трат'
    },
    {
        id: 4,
        image: shield,
        title: 'Безопасность на уровне банка',
        description: 'Безопасность на уровне банка'
    }
]