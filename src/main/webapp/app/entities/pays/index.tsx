import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pays from './pays';
import PaysDetail from './pays-detail';
import PaysUpdate from './pays-update';
import PaysDeleteDialog from './pays-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaysUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaysUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaysDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pays} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaysDeleteDialog} />
  </>
);

export default Routes;
