import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Assurance from './assurance';
import AssuranceDetail from './assurance-detail';
import AssuranceUpdate from './assurance-update';
import AssuranceDeleteDialog from './assurance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AssuranceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AssuranceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AssuranceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Assurance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AssuranceDeleteDialog} />
  </>
);

export default Routes;
