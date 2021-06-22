import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Echeance from './echeance';
import EcheanceDetail from './echeance-detail';
import EcheanceUpdate from './echeance-update';
import EcheanceDeleteDialog from './echeance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EcheanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EcheanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EcheanceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Echeance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EcheanceDeleteDialog} />
  </>
);

export default Routes;
