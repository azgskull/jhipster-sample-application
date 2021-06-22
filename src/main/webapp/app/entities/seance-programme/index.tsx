import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SeanceProgramme from './seance-programme';
import SeanceProgrammeDetail from './seance-programme-detail';
import SeanceProgrammeUpdate from './seance-programme-update';
import SeanceProgrammeDeleteDialog from './seance-programme-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SeanceProgrammeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SeanceProgrammeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SeanceProgrammeDetail} />
      <ErrorBoundaryRoute path={match.url} component={SeanceProgramme} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SeanceProgrammeDeleteDialog} />
  </>
);

export default Routes;
