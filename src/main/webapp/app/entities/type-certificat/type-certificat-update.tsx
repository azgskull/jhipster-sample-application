import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './type-certificat.reducer';
import { ITypeCertificat } from 'app/shared/model/type-certificat.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITypeCertificatUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypeCertificatUpdate = (props: ITypeCertificatUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { typeCertificatEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/type-certificat' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...typeCertificatEntity,
        ...values,
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
          <h2 id="jhipsterSampleApplicationApp.typeCertificat.home.createOrEditLabel" data-cy="TypeCertificatCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.home.createOrEditLabel">
              Create or edit a TypeCertificat
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : typeCertificatEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="type-certificat-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="type-certificat-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="type-certificat-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.code">Code</Translate>
                </Label>
                <AvField
                  id="type-certificat-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="type-certificat-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.nom">Nom</Translate>
                </Label>
                <AvField
                  id="type-certificat-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="type-certificat-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.description">Description</Translate>
                </Label>
                <AvField id="type-certificat-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/type-certificat" replace color="info">
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
  typeCertificatEntity: storeState.typeCertificat.entity,
  loading: storeState.typeCertificat.loading,
  updating: storeState.typeCertificat.updating,
  updateSuccess: storeState.typeCertificat.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeCertificatUpdate);
