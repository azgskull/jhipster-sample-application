export interface ITypeIdentite {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
}

export const defaultValue: Readonly<ITypeIdentite> = {};
