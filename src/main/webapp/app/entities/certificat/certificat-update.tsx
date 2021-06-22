import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITemplateCertificat } from 'app/shared/model/template-certificat.model';
import { getEntities as getTemplateCertificats } from 'app/entities/template-certificat/template-certificat.reducer';
import { ISeance } from 'app/shared/model/seance.model';
import { getEntities as getSeances } from 'app/entities/seance/seance.reducer';
import { ISportif } from 'app/shared/model/sportif.model';
import { getEntities as getSportifs } from 'app/entities/sportif/sportif.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './certificat.reducer';
import { ICertificat } from 'app/shared/model/certificat.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICertificatUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CertificatUpdate = (props: ICertificatUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { certificatEntity, templateCertificats, seances, sportifs, loading, updating } = props;

  const { fichier, fichierContentType } = certificatEntity;

  const handleClose = () => {
    props.history.push('/certificat' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTemplateCertificats();
    props.getSeances();
    props.getSportifs();
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
        ...certificatEntity,
        ...values,
        templateCertificat: templateCertificats.find(it => it.id.toString() === values.templateCertificatId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.certificat.home.createOrEditLabel" data-cy="CertificatCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.certificat.home.createOrEditLabel">Create or edit a Certificat</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : certificatEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="certificat-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="certificat-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="certificat-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.code">Code</Translate>
                </Label>
                <AvField
                  id="certificat-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="certificat-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.nom">Nom</Translate>
                </Label>
                <AvField
                  id="certificat-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="certificat-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.description">Description</Translate>
                </Label>
                <AvField id="certificat-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="certificat-date">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.date">Date</Translate>
                </Label>
                <AvField
                  id="certificat-date"
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
                <Label id="dateFinLabel" for="certificat-dateFin">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.dateFin">Date Fin</Translate>
                </Label>
                <AvField id="certificat-dateFin" data-cy="dateFin" type="date" className="form-control" name="dateFin" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="fichierLabel" for="fichier">
                    <Translate contentKey="jhipsterSampleApplicationApp.certificat.fichier">Fichier</Translate>
                  </Label>
                  <br />
                  {fichier ? (
                    <div>
                      {fichierContentType ? (
                        <a onClick={openFile(fichierContentType, fichier)}>
                          <img src={`data:${fichierContentType};base64,${fichier}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {fichierContentType}, {byteSize(fichier)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('fichier')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_fichier" data-cy="fichier" type="file" onChange={onBlobChange(true, 'fichier')} accept="image/*" />
                  <AvInput type="hidden" name="fichier" value={fichier} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label for="certificat-templateCertificat">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.templateCertificat">Template Certificat</Translate>
                </Label>
                <AvInput
                  id="certificat-templateCertificat"
                  data-cy="templateCertificat"
                  type="select"
                  className="form-control"
                  name="templateCertificatId"
                >
                  <option value="" key="0" />
                  {templateCertificats
                    ? templateCertificats.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="certificat-seance">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.seance">Seance</Translate>
                </Label>
                <AvInput id="certificat-seance" data-cy="seance" type="select" className="form-control" name="seanceId">
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
                <Label for="certificat-sportif">
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.sportif">Sportif</Translate>
                </Label>
                <AvInput id="certificat-sportif" data-cy="sportif" type="select" className="form-control" name="sportifId">
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
              <Button tag={Link} id="cancel-save" to="/certificat" replace color="info">
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
  templateCertificats: storeState.templateCertificat.entities,
  seances: storeState.seance.entities,
  sportifs: storeState.sportif.entities,
  certificatEntity: storeState.certificat.entity,
  loading: storeState.certificat.loading,
  updating: storeState.certificat.updating,
  updateSuccess: storeState.certificat.updateSuccess,
});

const mapDispatchToProps = {
  getTemplateCertificats,
  getSeances,
  getSportifs,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CertificatUpdate);
