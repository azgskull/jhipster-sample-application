export interface ITypeSeance {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
}

export const defaultValue: Readonly<ITypeSeance> = {};
