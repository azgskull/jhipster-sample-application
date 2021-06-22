import dayjs from 'dayjs';
import { IModePaiement } from 'app/shared/model/mode-paiement.model';
import { IPaiementStatus } from 'app/shared/model/paiement-status.model';
import { IEcheance } from 'app/shared/model/echeance.model';

export interface IPaiement {
  id?: number;
  code?: string;
  date?: string;
  montant?: number;
  description?: string | null;
  modePaiement?: IModePaiement | null;
  status?: IPaiementStatus | null;
  echeances?: IEcheance[] | null;
}

export const defaultValue: Readonly<IPaiement> = {};
