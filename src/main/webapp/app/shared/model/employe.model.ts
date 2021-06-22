import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITypeIdentite } from 'app/shared/model/type-identite.model';
import { ISalle } from 'app/shared/model/salle.model';

export interface IEmploye {
  id?: number;
  code?: string;
  nom?: string;
  prenom?: string;
  photo?: string | null;
  dateNaissance?: string | null;
  numeroIdentite?: string | null;
  adresse?: string | null;
  telephone?: string | null;
  email?: string | null;
  utilisateur?: IUser | null;
  typeIdentite?: ITypeIdentite | null;
  salle?: ISalle | null;
}

export const defaultValue: Readonly<IEmploye> = {};
