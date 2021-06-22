import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IEcheance } from 'app/shared/model/echeance.model';
import { ICertificat } from 'app/shared/model/certificat.model';
import { IAdhesion } from 'app/shared/model/adhesion.model';
import { IPresence } from 'app/shared/model/presence.model';
import { IPays } from 'app/shared/model/pays.model';
import { IVille } from 'app/shared/model/ville.model';
import { ITypeIdentite } from 'app/shared/model/type-identite.model';
import { IAssurance } from 'app/shared/model/assurance.model';

export interface ISportif {
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
  echeances?: IEcheance[] | null;
  certificats?: ICertificat[] | null;
  adhesions?: IAdhesion[] | null;
  presences?: IPresence[] | null;
  pays?: IPays | null;
  ville?: IVille | null;
  typeIdentite?: ITypeIdentite | null;
  assurances?: IAssurance[] | null;
}

export const defaultValue: Readonly<ISportif> = {};
