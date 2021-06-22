import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Presence from './presence';
import PresenceDetail from './presence-detail';
import PresenceUpdate from './presence-update';
import PresenceDeleteDialog from './presence-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PresenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PresenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PresenceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Presence} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PresenceDeleteDialog} />
  </>
);

export default Routes;
