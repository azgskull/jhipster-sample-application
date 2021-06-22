import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TemplateCertificat from './template-certificat';
import TemplateCertificatDetail from './template-certificat-detail';
import TemplateCertificatUpdate from './template-certificat-update';
import TemplateCertificatDeleteDialog from './template-certificat-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TemplateCertificatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TemplateCertificatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TemplateCertificatDetail} />
      <ErrorBoundaryRoute path={match.url} component={TemplateCertificat} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TemplateCertificatDeleteDialog} />
  </>
);

export default Routes;
