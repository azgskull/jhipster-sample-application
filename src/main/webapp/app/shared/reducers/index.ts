import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import pays, {
  PaysState
} from 'app/entities/pays/pays.reducer';
// prettier-ignore
import ville, {
  VilleState
} from 'app/entities/ville/ville.reducer';
// prettier-ignore
import structure, {
  StructureState
} from 'app/entities/structure/structure.reducer';
// prettier-ignore
import salle, {
  SalleState
} from 'app/entities/salle/salle.reducer';
// prettier-ignore
import typeSeance, {
  TypeSeanceState
} from 'app/entities/type-seance/type-seance.reducer';
// prettier-ignore
import seance, {
  SeanceState
} from 'app/entities/seance/seance.reducer';
// prettier-ignore
import seanceProgramme, {
  SeanceProgrammeState
} from 'app/entities/seance-programme/seance-programme.reducer';
// prettier-ignore
import sportif, {
  SportifState
} from 'app/entities/sportif/sportif.reducer';
// prettier-ignore
import employe, {
  EmployeState
} from 'app/entities/employe/employe.reducer';
// prettier-ignore
import typeIdentite, {
  TypeIdentiteState
} from 'app/entities/type-identite/type-identite.reducer';
// prettier-ignore
import adhesion, {
  AdhesionState
} from 'app/entities/adhesion/adhesion.reducer';
// prettier-ignore
import organismeAssurance, {
  OrganismeAssuranceState
} from 'app/entities/organisme-assurance/organisme-assurance.reducer';
// prettier-ignore
import assurance, {
  AssuranceState
} from 'app/entities/assurance/assurance.reducer';
// prettier-ignore
import echeance, {
  EcheanceState
} from 'app/entities/echeance/echeance.reducer';
// prettier-ignore
import paiementStatus, {
  PaiementStatusState
} from 'app/entities/paiement-status/paiement-status.reducer';
// prettier-ignore
import paiement, {
  PaiementState
} from 'app/entities/paiement/paiement.reducer';
// prettier-ignore
import modePaiement, {
  ModePaiementState
} from 'app/entities/mode-paiement/mode-paiement.reducer';
// prettier-ignore
import presence, {
  PresenceState
} from 'app/entities/presence/presence.reducer';
// prettier-ignore
import typeCertificat, {
  TypeCertificatState
} from 'app/entities/type-certificat/type-certificat.reducer';
// prettier-ignore
import certificat, {
  CertificatState
} from 'app/entities/certificat/certificat.reducer';
// prettier-ignore
import templateCertificat, {
  TemplateCertificatState
} from 'app/entities/template-certificat/template-certificat.reducer';
// prettier-ignore
import role, {
  RoleState
} from 'app/entities/role/role.reducer';
// prettier-ignore
import discipline, {
  DisciplineState
} from 'app/entities/discipline/discipline.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly pays: PaysState;
  readonly ville: VilleState;
  readonly structure: StructureState;
  readonly salle: SalleState;
  readonly typeSeance: TypeSeanceState;
  readonly seance: SeanceState;
  readonly seanceProgramme: SeanceProgrammeState;
  readonly sportif: SportifState;
  readonly employe: EmployeState;
  readonly typeIdentite: TypeIdentiteState;
  readonly adhesion: AdhesionState;
  readonly organismeAssurance: OrganismeAssuranceState;
  readonly assurance: AssuranceState;
  readonly echeance: EcheanceState;
  readonly paiementStatus: PaiementStatusState;
  readonly paiement: PaiementState;
  readonly modePaiement: ModePaiementState;
  readonly presence: PresenceState;
  readonly typeCertificat: TypeCertificatState;
  readonly certificat: CertificatState;
  readonly templateCertificat: TemplateCertificatState;
  readonly role: RoleState;
  readonly discipline: DisciplineState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  pays,
  ville,
  structure,
  salle,
  typeSeance,
  seance,
  seanceProgramme,
  sportif,
  employe,
  typeIdentite,
  adhesion,
  organismeAssurance,
  assurance,
  echeance,
  paiementStatus,
  paiement,
  modePaiement,
  presence,
  typeCertificat,
  certificat,
  templateCertificat,
  role,
  discipline,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
