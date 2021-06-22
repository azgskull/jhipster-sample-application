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
import { ISeanceProgramme } from 'app/shared/model/seance-programme.model';
import { getEntities as getSeanceProgrammes } from 'app/entities/seance-programme/seance-programme.reducer';
import { ISportif } from 'app/shared/model/sportif.model';
import { getEntities as getSportifs } from 'app/entities/sportif/sportif.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adhesion.reducer';
import { IAdhesion } from 'app/shared/model/adhesion.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAdhesionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdhesionUpdate = (props: IAdhesionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { adhesionEntity, roles, seanceProgrammes, sportifs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/adhesion' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRoles();
    props.getSeanceProgrammes();
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
        ...adhesionEntity,
        ...values,
        role: roles.find(it => it.id.toString() === values.roleId.toString()),
        seanceProgramme: seanceProgrammes.find(it => it.id.toString() === values.seanceProgrammeId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.adhesion.home.createOrEditLabel" data-cy="AdhesionCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.adhesion.home.createOrEditLabel">Create or edit a Adhesion</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : adhesionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="adhesion-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="adhesion-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateDebutLabel" for="adhesion-dateDebut">
                  <Translate contentKey="jhipsterSampleApplicationApp.adhesion.dateDebut">Date Debut</Translate>
                </Label>
                <AvField
                  id="adhesion-dateDebut"
                  data-cy="dateDebut"
                  type="date"
                  className="form-control"
                  name="dateDebut"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateFinLabel" for="adhesion-dateFin">
                  <Translate contentKey="jhipsterSampleApplicationApp.adhesion.dateFin">Date Fin</Translate>
                </Label>
                <AvField id="adhesion-dateFin" data-cy="dateFin" type="date" className="form-control" name="dateFin" />
              </AvGroup>
              <AvGroup>
                <Label for="adhesion-role">
                  <Translate contentKey="jhipsterSampleApplicationApp.adhesion.role">Role</Translate>
                </Label>
                <AvInput id="adhesion-role" data-cy="role" type="select" className="form-control" name="roleId">
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
                <Label for="adhesion-seanceProgramme">
                  <Translate contentKey="jhipsterSampleApplicationApp.adhesion.seanceProgramme">Seance Programme</Translate>
                </Label>
                <AvInput
                  id="adhesion-seanceProgramme"
                  data-cy="seanceProgramme"
                  type="select"
                  className="form-control"
                  name="seanceProgrammeId"
                >
                  <option value="" key="0" />
                  {seanceProgrammes
                    ? seanceProgrammes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="adhesion-sportif">
                  <Translate contentKey="jhipsterSampleApplicationApp.adhesion.sportif">Sportif</Translate>
                </Label>
                <AvInput id="adhesion-sportif" data-cy="sportif" type="select" className="form-control" name="sportifId">
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
              <Button tag={Link} id="cancel-save" to="/adhesion" replace color="info">
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
  seanceProgrammes: storeState.seanceProgramme.entities,
  sportifs: storeState.sportif.entities,
  adhesionEntity: storeState.adhesion.entity,
  loading: storeState.adhesion.loading,
  updating: storeState.adhesion.updating,
  updateSuccess: storeState.adhesion.updateSuccess,
});

const mapDispatchToProps = {
  getRoles,
  getSeanceProgrammes,
  getSportifs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdhesionUpdate);
