import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sportif from './sportif';
import SportifDetail from './sportif-detail';
import SportifUpdate from './sportif-update';
import SportifDeleteDialog from './sportif-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SportifUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SportifUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SportifDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sportif} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SportifDeleteDialog} />
  </>
);

export default Routes;
