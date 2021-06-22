import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDiscipline } from 'app/shared/model/discipline.model';
import { getEntities as getDisciplines } from 'app/entities/discipline/discipline.reducer';
import { ITypeCertificat } from 'app/shared/model/type-certificat.model';
import { getEntities as getTypeCertificats } from 'app/entities/type-certificat/type-certificat.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './template-certificat.reducer';
import { ITemplateCertificat } from 'app/shared/model/template-certificat.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITemplateCertificatUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TemplateCertificatUpdate = (props: ITemplateCertificatUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { templateCertificatEntity, disciplines, typeCertificats, loading, updating } = props;

  const { fichier, fichierContentType } = templateCertificatEntity;

  const handleClose = () => {
    props.history.push('/template-certificat' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDisciplines();
    props.getTypeCertificats();
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
        ...templateCertificatEntity,
        ...values,
        discipline: disciplines.find(it => it.id.toString() === values.disciplineId.toString()),
        typeCertificat: typeCertificats.find(it => it.id.toString() === values.typeCertificatId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.templateCertificat.home.createOrEditLabel" data-cy="TemplateCertificatCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.home.createOrEditLabel">
              Create or edit a TemplateCertificat
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : templateCertificatEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="template-certificat-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="template-certificat-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="template-certificat-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.code">Code</Translate>
                </Label>
                <AvField
                  id="template-certificat-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="template-certificat-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.nom">Nom</Translate>
                </Label>
                <AvField
                  id="template-certificat-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="template-certificat-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.description">Description</Translate>
                </Label>
                <AvField id="template-certificat-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="fichierLabel" for="fichier">
                    <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.fichier">Fichier</Translate>
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
                <Label for="template-certificat-discipline">
                  <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.discipline">Discipline</Translate>
                </Label>
                <AvInput
                  id="template-certificat-discipline"
                  data-cy="discipline"
                  type="select"
                  className="form-control"
                  name="disciplineId"
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
              <AvGroup>
                <Label for="template-certificat-typeCertificat">
                  <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.typeCertificat">Type Certificat</Translate>
                </Label>
                <AvInput
                  id="template-certificat-typeCertificat"
                  data-cy="typeCertificat"
                  type="select"
                  className="form-control"
                  name="typeCertificatId"
                >
                  <option value="" key="0" />
                  {typeCertificats
                    ? typeCertificats.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/template-certificat" replace color="info">
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
  disciplines: storeState.discipline.entities,
  typeCertificats: storeState.typeCertificat.entities,
  templateCertificatEntity: storeState.templateCertificat.entity,
  loading: storeState.templateCertificat.loading,
  updating: storeState.templateCertificat.updating,
  updateSuccess: storeState.templateCertificat.updateSuccess,
});

const mapDispatchToProps = {
  getDisciplines,
  getTypeCertificats,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TemplateCertificatUpdate);
