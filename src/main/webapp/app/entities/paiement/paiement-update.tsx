import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IModePaiement } from 'app/shared/model/mode-paiement.model';
import { getEntities as getModePaiements } from 'app/entities/mode-paiement/mode-paiement.reducer';
import { IPaiementStatus } from 'app/shared/model/paiement-status.model';
import { getEntities as getPaiementStatuses } from 'app/entities/paiement-status/paiement-status.reducer';
import { IEcheance } from 'app/shared/model/echeance.model';
import { getEntities as getEcheances } from 'app/entities/echeance/echeance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './paiement.reducer';
import { IPaiement } from 'app/shared/model/paiement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPaiementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaiementUpdate = (props: IPaiementUpdateProps) => {
  const [idsecheance, setIdsecheance] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { paiementEntity, modePaiements, paiementStatuses, echeances, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/paiement' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getModePaiements();
    props.getPaiementStatuses();
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
        ...paiementEntity,
        ...values,
        echeances: mapIdList(values.echeances),
        modePaiement: modePaiements.find(it => it.id.toString() === values.modePaiementId.toString()),
        status: paiementStatuses.find(it => it.id.toString() === values.statusId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.paiement.home.createOrEditLabel" data-cy="PaiementCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.paiement.home.createOrEditLabel">Create or edit a Paiement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : paiementEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="paiement-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="paiement-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="paiement-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.paiement.code">Code</Translate>
                </Label>
                <AvField
                  id="paiement-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="paiement-date">
                  <Translate contentKey="jhipsterSampleApplicationApp.paiement.date">Date</Translate>
                </Label>
                <AvField
                  id="paiement-date"
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
                <Label id="montantLabel" for="paiement-montant">
                  <Translate contentKey="jhipsterSampleApplicationApp.paiement.montant">Montant</Translate>
                </Label>
                <AvField
                  id="paiement-montant"
                  data-cy="montant"
                  type="string"
                  className="form-control"
                  name="montant"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="paiement-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.paiement.description">Description</Translate>
                </Label>
                <AvField id="paiement-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="paiement-modePaiement">
                  <Translate contentKey="jhipsterSampleApplicationApp.paiement.modePaiement">Mode Paiement</Translate>
                </Label>
                <AvInput id="paiement-modePaiement" data-cy="modePaiement" type="select" className="form-control" name="modePaiementId">
                  <option value="" key="0" />
                  {modePaiements
                    ? modePaiements.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="paiement-status">
                  <Translate contentKey="jhipsterSampleApplicationApp.paiement.status">Status</Translate>
                </Label>
                <AvInput id="paiement-status" data-cy="status" type="select" className="form-control" name="statusId">
                  <option value="" key="0" />
                  {paiementStatuses
                    ? paiementStatuses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.code}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="paiement-echeance">
                  <Translate contentKey="jhipsterSampleApplicationApp.paiement.echeance">Echeance</Translate>
                </Label>
                <AvInput
                  id="paiement-echeance"
                  data-cy="echeance"
                  type="select"
                  multiple
                  className="form-control"
                  name="echeances"
                  value={!isNew && paiementEntity.echeances && paiementEntity.echeances.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {echeances
                    ? echeances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.code}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/paiement" replace color="info">
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
  modePaiements: storeState.modePaiement.entities,
  paiementStatuses: storeState.paiementStatus.entities,
  echeances: storeState.echeance.entities,
  paiementEntity: storeState.paiement.entity,
  loading: storeState.paiement.loading,
  updating: storeState.paiement.updating,
  updateSuccess: storeState.paiement.updateSuccess,
});

const mapDispatchToProps = {
  getModePaiements,
  getPaiementStatuses,
  getEcheances,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaiementUpdate);
