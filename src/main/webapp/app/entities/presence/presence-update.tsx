import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRole } from 'app/shared/model/role.model';
import { getEntities as getRoles } from 'app/entities/role/role.reducer';
import { ISeance } from 'app/shared/model/seance.model';
import { getEntities as getSeances } from 'app/entities/seance/seance.reducer';
import { ISportif } from 'app/shared/model/sportif.model';
import { getEntities as getSportifs } from 'app/entities/sportif/sportif.reducer';
import { getEntity, updateEntity, createEntity, reset } from './presence.reducer';
import { IPresence } from 'app/shared/model/presence.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPresenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PresenceUpdate = (props: IPresenceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { presenceEntity, roles, seances, sportifs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/presence' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRoles();
    props.getSeances();
    props.getSportifs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...presenceEntity,
        ...values,
        role: roles.find(it => it.id.toString() === values.roleId.toString()),
        seance: seances.find(it => it.id.toString() === values.seanceId.toString()),
        sportif: sportifs.find(it => it.id.toString() === values.sportifId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.presence.home.createOrEditLabel" data-cy="PresenceCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.presence.home.createOrEditLabel">Create or edit a Presence</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : presenceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="presence-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="presence-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="heureDebutLabel" for="presence-heureDebut">
                  <Translate contentKey="jhipsterSampleApplicationApp.presence.heureDebut">Heure Debut</Translate>
                </Label>
                <AvField id="presence-heureDebut" data-cy="heureDebut" type="text" name="heureDebut" />
              </AvGroup>
              <AvGroup>
                <Label id="heureFinLabel" for="presence-heureFin">
                  <Translate contentKey="jhipsterSampleApplicationApp.presence.heureFin">Heure Fin</Translate>
                </Label>
                <AvField id="presence-heureFin" data-cy="heureFin" type="text" name="heureFin" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="presence-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.presence.description">Description</Translate>
                </Label>
                <AvField id="presence-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="presence-role">
                  <Translate contentKey="jhipsterSampleApplicationApp.presence.role">Role</Translate>
                </Label>
                <AvInput id="presence-role" data-cy="role" type="select" className="form-control" name="roleId">
                  <option value="" key="0" />
                  {roles
                    ? roles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="presence-seance">
                  <Translate contentKey="jhipsterSampleApplicationApp.presence.seance">Seance</Translate>
                </Label>
                <AvInput id="presence-seance" data-cy="seance" type="select" className="form-control" name="seanceId">
                  <option value="" key="0" />
                  {seances
                    ? seances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="presence-sportif">
                  <Translate contentKey="jhipsterSampleApplicationApp.presence.sportif">Sportif</Translate>
                </Label>
                <AvInput id="presence-sportif" data-cy="sportif" type="select" className="form-control" name="sportifId">
                  <option value="" key="0" />
                  {sportifs
                    ? sportifs.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/presence" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  roles: storeState.role.entities,
  seances: storeState.seance.entities,
  sportifs: storeState.sportif.entities,
  presenceEntity: storeState.presence.entity,
  loading: storeState.presence.loading,
  updating: storeState.presence.updating,
  updateSuccess: storeState.presence.updateSuccess,
});

const mapDispatchToProps = {
  getRoles,
  getSeances,
  getSportifs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PresenceUpdate);
