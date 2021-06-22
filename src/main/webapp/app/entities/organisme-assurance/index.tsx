import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrganismeAssurance from './organisme-assurance';
import OrganismeAssuranceDetail from './organisme-assurance-detail';
import OrganismeAssuranceUpdate from './organisme-assurance-update';
import OrganismeAssuranceDeleteDialog from './organisme-assurance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrganismeAssuranceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrganismeAssuranceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrganismeAssuranceDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrganismeAssurance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrganismeAssuranceDeleteDialog} />
  </>
);

export default Routes;
