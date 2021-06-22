import dayjs from 'dayjs';
import { IPresence } from 'app/shared/model/presence.model';
import { ITypeSeance } from 'app/shared/model/type-seance.model';
import { ISeanceProgramme } from 'app/shared/model/seance-programme.model';
import { IEcheance } from 'app/shared/model/echeance.model';

export interface ISeance {
  id?: number;
  nom?: string;
  description?: string | null;
  date?: string;
  heureDebut?: string | null;
  heureFin?: string | null;
  presences?: IPresence[] | null;
  typeSeance?: ITypeSeance | null;
  seanceProgramme?: ISeanceProgramme | null;
  echeances?: IEcheance[] | null;
}

export const defaultValue: Readonly<ISeance> = {};
