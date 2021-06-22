import { ISalle } from 'app/shared/model/salle.model';
import { IPays } from 'app/shared/model/pays.model';
import { IVille } from 'app/shared/model/ville.model';
import { IDiscipline } from 'app/shared/model/discipline.model';

export interface IStructure {
  id?: number;
  nom?: string;
  description?: string | null;
  siteWeb?: string | null;
  adresse?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  email?: string | null;
  telephone?: string | null;
  salles?: ISalle[] | null;
  pays?: IPays | null;
  ville?: IVille | null;
  disciplines?: IDiscipline[] | null;
}

export const defaultValue: Readonly<IStructure> = {};
