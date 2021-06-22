import { IRole } from 'app/shared/model/role.model';
import { ISeance } from 'app/shared/model/seance.model';
import { ISportif } from 'app/shared/model/sportif.model';

export interface IPresence {
  id?: number;
  heureDebut?: string | null;
  heureFin?: string | null;
  description?: string | null;
  role?: IRole | null;
  seance?: ISeance | null;
  sportif?: ISportif | null;
}

export const defaultValue: Readonly<IPresence> = {};
