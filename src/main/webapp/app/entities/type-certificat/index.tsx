import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TypeCertificat from './type-certificat';
import TypeCertificatDetail from './type-certificat-detail';
import TypeCertificatUpdate from './type-certificat-update';
import TypeCertificatDeleteDialog from './type-certificat-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypeCertificatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypeCertificatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypeCertificatDetail} />
      <ErrorBoundaryRoute path={match.url} component={TypeCertificat} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TypeCertificatDeleteDialog} />
  </>
);

export default Routes;
