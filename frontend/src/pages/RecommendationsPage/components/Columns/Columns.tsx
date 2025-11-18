import { Column } from "../Column/Column";
import { columnData } from "../Column/data";
import s from './Columns.module.css'

export const Columns = () => {
  return (
    <div className={s.columns}>
      {columnData.map((item, index) => (
        <Column
          key={index}
          title={item.title}
          styles={[{ background: item.background, border: item.border }]}
          pointList={[]}
        />
      ))}
    </div>
  );
};
