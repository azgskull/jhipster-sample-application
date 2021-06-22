import { ISeance } from 'app/shared/model/seance.model';
import { IAdhesion } from 'app/shared/model/adhesion.model';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { ISalle } from 'app/shared/model/salle.model';

export interface ISeanceProgramme {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
  programmeExpression?: string | null;
  duree?: number | null;
  seances?: ISeance[] | null;
  adhesions?: IAdhesion[] | null;
  discipline?: IDiscipline | null;
  salle?: ISalle | null;
}

export const defaultValue: Readonly<ISeanceProgramme> = {};
