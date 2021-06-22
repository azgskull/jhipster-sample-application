import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Adhesion from './adhesion';
import AdhesionDetail from './adhesion-detail';
import AdhesionUpdate from './adhesion-update';
import AdhesionDeleteDialog from './adhesion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdhesionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdhesionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdhesionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Adhesion} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdhesionDeleteDialog} />
  </>
);

export default Routes;
