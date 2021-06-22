import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ville from './ville';
import VilleDetail from './ville-detail';
import VilleUpdate from './ville-update';
import VilleDeleteDialog from './ville-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VilleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VilleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VilleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ville} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VilleDeleteDialog} />
  </>
);

export default Routes;
