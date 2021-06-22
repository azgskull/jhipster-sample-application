import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ModePaiement from './mode-paiement';
import ModePaiementDetail from './mode-paiement-detail';
import ModePaiementUpdate from './mode-paiement-update';
import ModePaiementDeleteDialog from './mode-paiement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ModePaiementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ModePaiementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ModePaiementDetail} />
      <ErrorBoundaryRoute path={match.url} component={ModePaiement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ModePaiementDeleteDialog} />
  </>
);

export default Routes;
