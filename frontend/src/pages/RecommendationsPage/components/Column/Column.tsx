import s from './Column.module.css'

interface ColumnProps {
  title: string;
  styles: Array<{
    background: string;
    border: string;
  }>;
  pointList: Array<string>;
}

export const Column = ({ pointList, title, styles }: ColumnProps) => {
  return (
    <div
      className={s.column}
      style={{
        background: styles[0].background,
        border: styles[0].border,
      }}
    >
      <h3 className={s.columnTitle}>{title}</h3>
      <ul>
        {pointList.map((item, index) => (
          <li key={index}>{item}</li>
        ))}
      </ul>
    </div>
  );
};
