import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrganismeAssurance } from 'app/shared/model/organisme-assurance.model';
import { getEntities as getOrganismeAssurances } from 'app/entities/organisme-assurance/organisme-assurance.reducer';
import { ISportif } from 'app/shared/model/sportif.model';
import { getEntities as getSportifs } from 'app/entities/sportif/sportif.reducer';
import { getEntity, updateEntity, createEntity, reset } from './assurance.reducer';
import { IAssurance } from 'app/shared/model/assurance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAssuranceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssuranceUpdate = (props: IAssuranceUpdateProps) => {
  const [idssportif, setIdssportif] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { assuranceEntity, organismeAssurances, sportifs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/assurance' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getOrganismeAssurances();
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
        ...assuranceEntity,
        ...values,
        sportifs: mapIdList(values.sportifs),
        organismeAssurance: organismeAssurances.find(it => it.id.toString() === values.organismeAssuranceId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.assurance.home.createOrEditLabel" data-cy="AssuranceCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.assurance.home.createOrEditLabel">Create or edit a Assurance</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : assuranceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="assurance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="assurance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="assurance-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.assurance.code">Code</Translate>
                </Label>
                <AvField
                  id="assurance-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateDebutLabel" for="assurance-dateDebut">
                  <Translate contentKey="jhipsterSampleApplicationApp.assurance.dateDebut">Date Debut</Translate>
                </Label>
                <AvField
                  id="assurance-dateDebut"
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
                <Label id="dateFinLabel" for="assurance-dateFin">
                  <Translate contentKey="jhipsterSampleApplicationApp.assurance.dateFin">Date Fin</Translate>
                </Label>
                <AvField
                  id="assurance-dateFin"
                  data-cy="dateFin"
                  type="date"
                  className="form-control"
                  name="dateFin"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="detailsLabel" for="assurance-details">
                  <Translate contentKey="jhipsterSampleApplicationApp.assurance.details">Details</Translate>
                </Label>
                <AvField id="assurance-details" data-cy="details" type="text" name="details" />
              </AvGroup>
              <AvGroup>
                <Label for="assurance-organismeAssurance">
                  <Translate contentKey="jhipsterSampleApplicationApp.assurance.organismeAssurance">Organisme Assurance</Translate>
                </Label>
                <AvInput
                  id="assurance-organismeAssurance"
                  data-cy="organismeAssurance"
                  type="select"
                  className="form-control"
                  name="organismeAssuranceId"
                >
                  <option value="" key="0" />
                  {organismeAssurances
                    ? organismeAssurances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="assurance-sportif">
                  <Translate contentKey="jhipsterSampleApplicationApp.assurance.sportif">Sportif</Translate>
                </Label>
                <AvInput
                  id="assurance-sportif"
                  data-cy="sportif"
                  type="select"
                  multiple
                  className="form-control"
                  name="sportifs"
                  value={!isNew && assuranceEntity.sportifs && assuranceEntity.sportifs.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {sportifs
                    ? sportifs.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/assurance" replace color="info">
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
  organismeAssurances: storeState.organismeAssurance.entities,
  sportifs: storeState.sportif.entities,
  assuranceEntity: storeState.assurance.entity,
  loading: storeState.assurance.loading,
  updating: storeState.assurance.updating,
  updateSuccess: storeState.assurance.updateSuccess,
});

const mapDispatchToProps = {
  getOrganismeAssurances,
  getSportifs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssuranceUpdate);
