import { AuthenticatedLayout } from "@/components/layout/AuthenticatedLayout/AuthenticatedLayout";
import s from './CardPage.module.css'

export const CardPage = () => {
  return (
    <AuthenticatedLayout>
      <div className={s.cardPage}>CardPage</div>
    </AuthenticatedLayout>
  );
};
