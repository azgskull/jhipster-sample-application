import { IPays } from 'app/shared/model/pays.model';

export interface IVille {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
  pays?: IPays | null;
}

export const defaultValue: Readonly<IVille> = {};
