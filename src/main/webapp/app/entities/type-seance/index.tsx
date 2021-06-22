import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TypeSeance from './type-seance';
import TypeSeanceDetail from './type-seance-detail';
import TypeSeanceUpdate from './type-seance-update';
import TypeSeanceDeleteDialog from './type-seance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypeSeanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypeSeanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypeSeanceDetail} />
      <ErrorBoundaryRoute path={match.url} component={TypeSeance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TypeSeanceDeleteDialog} />
  </>
);

export default Routes;
