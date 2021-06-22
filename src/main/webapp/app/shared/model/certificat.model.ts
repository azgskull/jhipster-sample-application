import dayjs from 'dayjs';
import { ITemplateCertificat } from 'app/shared/model/template-certificat.model';
import { ISeance } from 'app/shared/model/seance.model';
import { ISportif } from 'app/shared/model/sportif.model';

export interface ICertificat {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
  date?: string;
  dateFin?: string | null;
  fichierContentType?: string | null;
  fichier?: string | null;
  templateCertificat?: ITemplateCertificat | null;
  seance?: ISeance | null;
  sportif?: ISportif | null;
}

export const defaultValue: Readonly<ICertificat> = {};
