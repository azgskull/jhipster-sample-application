import { IStructure } from 'app/shared/model/structure.model';

export interface IDiscipline {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
  photo?: string | null;
  disciplines?: IStructure[] | null;
}

export const defaultValue: Readonly<IDiscipline> = {};
