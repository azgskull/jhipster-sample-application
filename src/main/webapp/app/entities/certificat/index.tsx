import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Certificat from './certificat';
import CertificatDetail from './certificat-detail';
import CertificatUpdate from './certificat-update';
import CertificatDeleteDialog from './certificat-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CertificatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CertificatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CertificatDetail} />
      <ErrorBoundaryRoute path={match.url} component={Certificat} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CertificatDeleteDialog} />
  </>
);

export default Routes;
