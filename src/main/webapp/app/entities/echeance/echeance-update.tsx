import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAssurance } from 'app/shared/model/assurance.model';
import { getEntities as getAssurances } from 'app/entities/assurance/assurance.reducer';
import { ICertificat } from 'app/shared/model/certificat.model';
import { getEntities as getCertificats } from 'app/entities/certificat/certificat.reducer';
import { ISeance } from 'app/shared/model/seance.model';
import { getEntities as getSeances } from 'app/entities/seance/seance.reducer';
import { ISportif } from 'app/shared/model/sportif.model';
import { getEntities as getSportifs } from 'app/entities/sportif/sportif.reducer';
import { IPaiement } from 'app/shared/model/paiement.model';
import { getEntities as getPaiements } from 'app/entities/paiement/paiement.reducer';
import { getEntity, updateEntity, createEntity, reset } from './echeance.reducer';
import { IEcheance } from 'app/shared/model/echeance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEcheanceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EcheanceUpdate = (props: IEcheanceUpdateProps) => {
  const [idsseance, setIdsseance] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { echeanceEntity, assurances, certificats, seances, sportifs, paiements, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/echeance' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAssurances();
    props.getCertificats();
    props.getSeances();
    props.getSportifs();
    props.getPaiements();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...echeanceEntity,
        ...values,
        seances: mapIdList(values.seances),
        assurance: assurances.find(it => it.id.toString() === values.assuranceId.toString()),
        certificat: certificats.find(it => it.id.toString() === values.certificatId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.echeance.home.createOrEditLabel" data-cy="EcheanceCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.echeance.home.createOrEditLabel">Create or edit a Echeance</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : echeanceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="echeance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="echeance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="echeance-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.code">Code</Translate>
                </Label>
                <AvField
                  id="echeance-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="datePrevuPaiementLabel" for="echeance-datePrevuPaiement">
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.datePrevuPaiement">Date Prevu Paiement</Translate>
                </Label>
                <AvField
                  id="echeance-datePrevuPaiement"
                  data-cy="datePrevuPaiement"
                  type="date"
                  className="form-control"
                  name="datePrevuPaiement"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="montantLabel" for="echeance-montant">
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.montant">Montant</Translate>
                </Label>
                <AvField
                  id="echeance-montant"
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
              <AvGroup check>
                <Label id="payeTotalementLabel">
                  <AvInput
                    id="echeance-payeTotalement"
                    data-cy="payeTotalement"
                    type="checkbox"
                    className="form-check-input"
                    name="payeTotalement"
                  />
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.payeTotalement">Paye Totalement</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="echeance-assurance">
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.assurance">Assurance</Translate>
                </Label>
                <AvInput id="echeance-assurance" data-cy="assurance" type="select" className="form-control" name="assuranceId">
                  <option value="" key="0" />
                  {assurances
                    ? assurances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.code}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="echeance-certificat">
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.certificat">Certificat</Translate>
                </Label>
                <AvInput id="echeance-certificat" data-cy="certificat" type="select" className="form-control" name="certificatId">
                  <option value="" key="0" />
                  {certificats
                    ? certificats.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="echeance-seance">
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.seance">Seance</Translate>
                </Label>
                <AvInput
                  id="echeance-seance"
                  data-cy="seance"
                  type="select"
                  multiple
                  className="form-control"
                  name="seances"
                  value={!isNew && echeanceEntity.seances && echeanceEntity.seances.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {seances
                    ? seances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="echeance-sportif">
                  <Translate contentKey="jhipsterSampleApplicationApp.echeance.sportif">Sportif</Translate>
                </Label>
                <AvInput id="echeance-sportif" data-cy="sportif" type="select" className="form-control" name="sportifId">
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
              <Button tag={Link} id="cancel-save" to="/echeance" replace color="info">
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
  assurances: storeState.assurance.entities,
  certificats: storeState.certificat.entities,
  seances: storeState.seance.entities,
  sportifs: storeState.sportif.entities,
  paiements: storeState.paiement.entities,
  echeanceEntity: storeState.echeance.entity,
  loading: storeState.echeance.loading,
  updating: storeState.echeance.updating,
  updateSuccess: storeState.echeance.updateSuccess,
});

const mapDispatchToProps = {
  getAssurances,
  getCertificats,
  getSeances,
  getSportifs,
  getPaiements,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EcheanceUpdate);
