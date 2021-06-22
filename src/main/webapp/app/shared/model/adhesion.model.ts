import dayjs from 'dayjs';
import { IRole } from 'app/shared/model/role.model';
import { ISeanceProgramme } from 'app/shared/model/seance-programme.model';
import { ISportif } from 'app/shared/model/sportif.model';

export interface IAdhesion {
  id?: number;
  dateDebut?: string;
  dateFin?: string | null;
  role?: IRole | null;
  seanceProgramme?: ISeanceProgramme | null;
  sportif?: ISportif | null;
}

export const defaultValue: Readonly<IAdhesion> = {};
