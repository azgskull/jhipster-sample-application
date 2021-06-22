import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pays from './pays';
import Ville from './ville';
import Structure from './structure';
import Salle from './salle';
import TypeSeance from './type-seance';
import Seance from './seance';
import SeanceProgramme from './seance-programme';
import Sportif from './sportif';
import Employe from './employe';
import TypeIdentite from './type-identite';
import Adhesion from './adhesion';
import OrganismeAssurance from './organisme-assurance';
import Assurance from './assurance';
import Echeance from './echeance';
import PaiementStatus from './paiement-status';
import Paiement from './paiement';
import ModePaiement from './mode-paiement';
import Presence from './presence';
import TypeCertificat from './type-certificat';
import Certificat from './certificat';
import TemplateCertificat from './template-certificat';
import Role from './role';
import Discipline from './discipline';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}pays`} component={Pays} />
      <ErrorBoundaryRoute path={`${match.url}ville`} component={Ville} />
      <ErrorBoundaryRoute path={`${match.url}structure`} component={Structure} />
      <ErrorBoundaryRoute path={`${match.url}salle`} component={Salle} />
      <ErrorBoundaryRoute path={`${match.url}type-seance`} component={TypeSeance} />
      <ErrorBoundaryRoute path={`${match.url}seance`} component={Seance} />
      <ErrorBoundaryRoute path={`${match.url}seance-programme`} component={SeanceProgramme} />
      <ErrorBoundaryRoute path={`${match.url}sportif`} component={Sportif} />
      <ErrorBoundaryRoute path={`${match.url}employe`} component={Employe} />
      <ErrorBoundaryRoute path={`${match.url}type-identite`} component={TypeIdentite} />
      <ErrorBoundaryRoute path={`${match.url}adhesion`} component={Adhesion} />
      <ErrorBoundaryRoute path={`${match.url}organisme-assurance`} component={OrganismeAssurance} />
      <ErrorBoundaryRoute path={`${match.url}assurance`} component={Assurance} />
      <ErrorBoundaryRoute path={`${match.url}echeance`} component={Echeance} />
      <ErrorBoundaryRoute path={`${match.url}paiement-status`} component={PaiementStatus} />
      <ErrorBoundaryRoute path={`${match.url}paiement`} component={Paiement} />
      <ErrorBoundaryRoute path={`${match.url}mode-paiement`} component={ModePaiement} />
      <ErrorBoundaryRoute path={`${match.url}presence`} component={Presence} />
      <ErrorBoundaryRoute path={`${match.url}type-certificat`} component={TypeCertificat} />
      <ErrorBoundaryRoute path={`${match.url}certificat`} component={Certificat} />
      <ErrorBoundaryRoute path={`${match.url}template-certificat`} component={TemplateCertificat} />
      <ErrorBoundaryRoute path={`${match.url}role`} component={Role} />
      <ErrorBoundaryRoute path={`${match.url}discipline`} component={Discipline} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
