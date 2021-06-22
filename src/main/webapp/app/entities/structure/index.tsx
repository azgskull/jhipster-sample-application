import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Structure from './structure';
import StructureDetail from './structure-detail';
import StructureUpdate from './structure-update';
import StructureDeleteDialog from './structure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StructureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StructureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StructureDetail} />
      <ErrorBoundaryRoute path={match.url} component={Structure} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StructureDeleteDialog} />
  </>
);

export default Routes;
