import { ISeanceProgramme } from 'app/shared/model/seance-programme.model';
import { IEmploye } from 'app/shared/model/employe.model';
import { IPays } from 'app/shared/model/pays.model';
import { IVille } from 'app/shared/model/ville.model';
import { IStructure } from 'app/shared/model/structure.model';

export interface ISalle {
  id?: number;
  nom?: string;
  description?: string | null;
  siteWeb?: string | null;
  adresse?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  email?: string | null;
  telephone?: string | null;
  seanceProgrammes?: ISeanceProgramme[] | null;
  employes?: IEmploye[] | null;
  pays?: IPays | null;
  ville?: IVille | null;
  structure?: IStructure | null;
}

export const defaultValue: Readonly<ISalle> = {};
