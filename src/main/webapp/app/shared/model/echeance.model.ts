import dayjs from 'dayjs';
import { IAssurance } from 'app/shared/model/assurance.model';
import { ICertificat } from 'app/shared/model/certificat.model';
import { ISeance } from 'app/shared/model/seance.model';
import { ISportif } from 'app/shared/model/sportif.model';
import { IPaiement } from 'app/shared/model/paiement.model';

export interface IEcheance {
  id?: number;
  code?: string;
  datePrevuPaiement?: string;
  montant?: number;
  payeTotalement?: boolean | null;
  assurance?: IAssurance | null;
  certificat?: ICertificat | null;
  seances?: ISeance[] | null;
  sportif?: ISportif | null;
  paiements?: IPaiement[] | null;
}

export const defaultValue: Readonly<IEcheance> = {
  payeTotalement: false,
};
