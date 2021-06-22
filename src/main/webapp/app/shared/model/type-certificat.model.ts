export interface ITypeCertificat {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
}

export const defaultValue: Readonly<ITypeCertificat> = {};
