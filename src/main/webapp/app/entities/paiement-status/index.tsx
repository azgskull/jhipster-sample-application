import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PaiementStatus from './paiement-status';
import PaiementStatusDetail from './paiement-status-detail';
import PaiementStatusUpdate from './paiement-status-update';
import PaiementStatusDeleteDialog from './paiement-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaiementStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaiementStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaiementStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={PaiementStatus} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaiementStatusDeleteDialog} />
  </>
);

export default Routes;
