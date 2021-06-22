export interface IRole {
  id?: number;
  code?: string;
  nom?: string;
  description?: string | null;
}

export const defaultValue: Readonly<IRole> = {};
