import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './type-seance.reducer';
import { ITypeSeance } from 'app/shared/model/type-seance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITypeSeanceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypeSeanceUpdate = (props: ITypeSeanceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { typeSeanceEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/type-seance' + props.location.search);
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
        ...typeSeanceEntity,
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
          <h2 id="jhipsterSampleApplicationApp.typeSeance.home.createOrEditLabel" data-cy="TypeSeanceCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.typeSeance.home.createOrEditLabel">Create or edit a TypeSeance</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : typeSeanceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="type-seance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="type-seance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="type-seance-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.typeSeance.code">Code</Translate>
                </Label>
                <AvField
                  id="type-seance-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="type-seance-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.typeSeance.nom">Nom</Translate>
                </Label>
                <AvField
                  id="type-seance-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="type-seance-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.typeSeance.description">Description</Translate>
                </Label>
                <AvField id="type-seance-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/type-seance" replace color="info">
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
  typeSeanceEntity: storeState.typeSeance.entity,
  loading: storeState.typeSeance.loading,
  updating: storeState.typeSeance.updating,
  updateSuccess: storeState.typeSeance.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeSeanceUpdate);
