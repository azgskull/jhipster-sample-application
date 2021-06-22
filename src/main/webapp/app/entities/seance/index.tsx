import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Seance from './seance';
import SeanceDetail from './seance-detail';
import SeanceUpdate from './seance-update';
import SeanceDeleteDialog from './seance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SeanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SeanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SeanceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Seance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SeanceDeleteDialog} />
  </>
);

export default Routes;
