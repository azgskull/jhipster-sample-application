import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TypeIdentite from './type-identite';
import TypeIdentiteDetail from './type-identite-detail';
import TypeIdentiteUpdate from './type-identite-update';
import TypeIdentiteDeleteDialog from './type-identite-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypeIdentiteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypeIdentiteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypeIdentiteDetail} />
      <ErrorBoundaryRoute path={match.url} component={TypeIdentite} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TypeIdentiteDeleteDialog} />
  </>
);

export default Routes;
