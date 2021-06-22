import { IVille } from 'app/shared/model/ville.model';

export interface IPays {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  villes?: IVille[] | null;
}

export const defaultValue: Readonly<IPays> = {};
