import dayjs from 'dayjs';
import { IOrganismeAssurance } from 'app/shared/model/organisme-assurance.model';
import { ISportif } from 'app/shared/model/sportif.model';

export interface IAssurance {
  id?: number;
  code?: string;
  dateDebut?: string;
  dateFin?: string;
  details?: string | null;
  organismeAssurance?: IOrganismeAssurance | null;
  sportifs?: ISportif[] | null;
}

export const defaultValue: Readonly<IAssurance> = {};
