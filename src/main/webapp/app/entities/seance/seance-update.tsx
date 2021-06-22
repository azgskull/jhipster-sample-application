import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITypeSeance } from 'app/shared/model/type-seance.model';
import { getEntities as getTypeSeances } from 'app/entities/type-seance/type-seance.reducer';
import { ISeanceProgramme } from 'app/shared/model/seance-programme.model';
import { getEntities as getSeanceProgrammes } from 'app/entities/seance-programme/seance-programme.reducer';
import { IEcheance } from 'app/shared/model/echeance.model';
import { getEntities as getEcheances } from 'app/entities/echeance/echeance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './seance.reducer';
import { ISeance } from 'app/shared/model/seance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISeanceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeanceUpdate = (props: ISeanceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { seanceEntity, typeSeances, seanceProgrammes, echeances, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/seance' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTypeSeances();
    props.getSeanceProgrammes();
    props.getEcheances();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...seanceEntity,
        ...values,
        typeSeance: typeSeances.find(it => it.id.toString() === values.typeSeanceId.toString()),
        seanceProgramme: seanceProgrammes.find(it => it.id.toString() === values.seanceProgrammeId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.seance.home.createOrEditLabel" data-cy="SeanceCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.seance.home.createOrEditLabel">Create or edit a Seance</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : seanceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="seance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="seance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomLabel" for="seance-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.seance.nom">Nom</Translate>
                </Label>
                <AvField
                  id="seance-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="seance-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.seance.description">Description</Translate>
                </Label>
                <AvField id="seance-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="seance-date">
                  <Translate contentKey="jhipsterSampleApplicationApp.seance.date">Date</Translate>
                </Label>
                <AvField
                  id="seance-date"
                  data-cy="date"
                  type="date"
                  className="form-control"
                  name="date"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="heureDebutLabel" for="seance-heureDebut">
                  <Translate contentKey="jhipsterSampleApplicationApp.seance.heureDebut">Heure Debut</Translate>
                </Label>
                <AvField id="seance-heureDebut" data-cy="heureDebut" type="text" name="heureDebut" />
              </AvGroup>
              <AvGroup>
                <Label id="heureFinLabel" for="seance-heureFin">
                  <Translate contentKey="jhipsterSampleApplicationApp.seance.heureFin">Heure Fin</Translate>
                </Label>
                <AvField id="seance-heureFin" data-cy="heureFin" type="text" name="heureFin" />
              </AvGroup>
              <AvGroup>
                <Label for="seance-typeSeance">
                  <Translate contentKey="jhipsterSampleApplicationApp.seance.typeSeance">Type Seance</Translate>
                </Label>
                <AvInput id="seance-typeSeance" data-cy="typeSeance" type="select" className="form-control" name="typeSeanceId">
                  <option value="" key="0" />
                  {typeSeances
                    ? typeSeances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="seance-seanceProgramme">
                  <Translate contentKey="jhipsterSampleApplicationApp.seance.seanceProgramme">Seance Programme</Translate>
                </Label>
                <AvInput
                  id="seance-seanceProgramme"
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
              <Button tag={Link} id="cancel-save" to="/seance" replace color="info">
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
  typeSeances: storeState.typeSeance.entities,
  seanceProgrammes: storeState.seanceProgramme.entities,
  echeances: storeState.echeance.entities,
  seanceEntity: storeState.seance.entity,
  loading: storeState.seance.loading,
  updating: storeState.seance.updating,
  updateSuccess: storeState.seance.updateSuccess,
});

const mapDispatchToProps = {
  getTypeSeances,
  getSeanceProgrammes,
  getEcheances,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeanceUpdate);
