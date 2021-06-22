import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPays } from 'app/shared/model/pays.model';
import { getEntities as getPays } from 'app/entities/pays/pays.reducer';
import { IVille } from 'app/shared/model/ville.model';
import { getEntities as getVilles } from 'app/entities/ville/ville.reducer';
import { IStructure } from 'app/shared/model/structure.model';
import { getEntities as getStructures } from 'app/entities/structure/structure.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './salle.reducer';
import { ISalle } from 'app/shared/model/salle.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISalleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SalleUpdate = (props: ISalleUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { salleEntity, pays, villes, structures, loading, updating } = props;

  const { logo, logoContentType } = salleEntity;

  const handleClose = () => {
    props.history.push('/salle' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPays();
    props.getVilles();
    props.getStructures();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...salleEntity,
        ...values,
        pays: pays.find(it => it.id.toString() === values.paysId.toString()),
        ville: villes.find(it => it.id.toString() === values.villeId.toString()),
        structure: structures.find(it => it.id.toString() === values.structureId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.salle.home.createOrEditLabel" data-cy="SalleCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.salle.home.createOrEditLabel">Create or edit a Salle</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : salleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="salle-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="salle-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomLabel" for="salle-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.nom">Nom</Translate>
                </Label>
                <AvField
                  id="salle-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="salle-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.description">Description</Translate>
                </Label>
                <AvField id="salle-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="siteWebLabel" for="salle-siteWeb">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.siteWeb">Site Web</Translate>
                </Label>
                <AvField id="salle-siteWeb" data-cy="siteWeb" type="text" name="siteWeb" />
              </AvGroup>
              <AvGroup>
                <Label id="adresseLabel" for="salle-adresse">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.adresse">Adresse</Translate>
                </Label>
                <AvField id="salle-adresse" data-cy="adresse" type="text" name="adresse" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="logoLabel" for="logo">
                    <Translate contentKey="jhipsterSampleApplicationApp.salle.logo">Logo</Translate>
                  </Label>
                  <br />
                  {logo ? (
                    <div>
                      {logoContentType ? (
                        <a onClick={openFile(logoContentType, logo)}>
                          <img src={`data:${logoContentType};base64,${logo}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {logoContentType}, {byteSize(logo)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('logo')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_logo" data-cy="logo" type="file" onChange={onBlobChange(true, 'logo')} accept="image/*" />
                  <AvInput type="hidden" name="logo" value={logo} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="salle-email">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.email">Email</Translate>
                </Label>
                <AvField id="salle-email" data-cy="email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="salle-telephone">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.telephone">Telephone</Translate>
                </Label>
                <AvField id="salle-telephone" data-cy="telephone" type="text" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label for="salle-pays">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.pays">Pays</Translate>
                </Label>
                <AvInput id="salle-pays" data-cy="pays" type="select" className="form-control" name="paysId">
                  <option value="" key="0" />
                  {pays
                    ? pays.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="salle-ville">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.ville">Ville</Translate>
                </Label>
                <AvInput id="salle-ville" data-cy="ville" type="select" className="form-control" name="villeId">
                  <option value="" key="0" />
                  {villes
                    ? villes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="salle-structure">
                  <Translate contentKey="jhipsterSampleApplicationApp.salle.structure">Structure</Translate>
                </Label>
                <AvInput id="salle-structure" data-cy="structure" type="select" className="form-control" name="structureId">
                  <option value="" key="0" />
                  {structures
                    ? structures.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/salle" replace color="info">
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
  pays: storeState.pays.entities,
  villes: storeState.ville.entities,
  structures: storeState.structure.entities,
  salleEntity: storeState.salle.entity,
  loading: storeState.salle.loading,
  updating: storeState.salle.updating,
  updateSuccess: storeState.salle.updateSuccess,
});

const mapDispatchToProps = {
  getPays,
  getVilles,
  getStructures,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SalleUpdate);
