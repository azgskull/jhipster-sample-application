import { IDiscipline } from 'app/shared/model/discipline.model';
import { ITypeCertificat } from 'app/shared/model/type-certificat.model';

export interface ITemplateCertificat {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
  fichierContentType?: string | null;
  fichier?: string | null;
  discipline?: IDiscipline | null;
  typeCertificat?: ITypeCertificat | null;
}

export const defaultValue: Readonly<ITemplateCertificat> = {};
