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
import { IDiscipline } from 'app/shared/model/discipline.model';
import { getEntities as getDisciplines } from 'app/entities/discipline/discipline.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './structure.reducer';
import { IStructure } from 'app/shared/model/structure.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStructureUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StructureUpdate = (props: IStructureUpdateProps) => {
  const [idsdiscipline, setIdsdiscipline] = useState([]);
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { structureEntity, pays, villes, disciplines, loading, updating } = props;

  const { logo, logoContentType } = structureEntity;

  const handleClose = () => {
    props.history.push('/structure' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPays();
    props.getVilles();
    props.getDisciplines();
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
        ...structureEntity,
        ...values,
        disciplines: mapIdList(values.disciplines),
        pays: pays.find(it => it.id.toString() === values.paysId.toString()),
        ville: villes.find(it => it.id.toString() === values.villeId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.structure.home.createOrEditLabel" data-cy="StructureCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.structure.home.createOrEditLabel">Create or edit a Structure</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : structureEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="structure-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="structure-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomLabel" for="structure-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.nom">Nom</Translate>
                </Label>
                <AvField
                  id="structure-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="structure-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.description">Description</Translate>
                </Label>
                <AvField id="structure-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="siteWebLabel" for="structure-siteWeb">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.siteWeb">Site Web</Translate>
                </Label>
                <AvField id="structure-siteWeb" data-cy="siteWeb" type="text" name="siteWeb" />
              </AvGroup>
              <AvGroup>
                <Label id="adresseLabel" for="structure-adresse">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.adresse">Adresse</Translate>
                </Label>
                <AvField id="structure-adresse" data-cy="adresse" type="text" name="adresse" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="logoLabel" for="logo">
                    <Translate contentKey="jhipsterSampleApplicationApp.structure.logo">Logo</Translate>
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
                <Label id="emailLabel" for="structure-email">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.email">Email</Translate>
                </Label>
                <AvField id="structure-email" data-cy="email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="structure-telephone">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.telephone">Telephone</Translate>
                </Label>
                <AvField id="structure-telephone" data-cy="telephone" type="text" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label for="structure-pays">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.pays">Pays</Translate>
                </Label>
                <AvInput id="structure-pays" data-cy="pays" type="select" className="form-control" name="paysId">
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
                <Label for="structure-ville">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.ville">Ville</Translate>
                </Label>
                <AvInput id="structure-ville" data-cy="ville" type="select" className="form-control" name="villeId">
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
                <Label for="structure-discipline">
                  <Translate contentKey="jhipsterSampleApplicationApp.structure.discipline">Discipline</Translate>
                </Label>
                <AvInput
                  id="structure-discipline"
                  data-cy="discipline"
                  type="select"
                  multiple
                  className="form-control"
                  name="disciplines"
                  value={!isNew && structureEntity.disciplines && structureEntity.disciplines.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {disciplines
                    ? disciplines.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/structure" replace color="info">
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
  disciplines: storeState.discipline.entities,
  structureEntity: storeState.structure.entity,
  loading: storeState.structure.loading,
  updating: storeState.structure.updating,
  updateSuccess: storeState.structure.updateSuccess,
});

const mapDispatchToProps = {
  getPays,
  getVilles,
  getDisciplines,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StructureUpdate);
