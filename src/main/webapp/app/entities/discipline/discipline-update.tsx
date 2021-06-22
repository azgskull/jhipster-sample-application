import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStructure } from 'app/shared/model/structure.model';
import { getEntities as getStructures } from 'app/entities/structure/structure.reducer';
import { getEntity, updateEntity, createEntity, reset } from './discipline.reducer';
import { IDiscipline } from 'app/shared/model/discipline.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDisciplineUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DisciplineUpdate = (props: IDisciplineUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { disciplineEntity, structures, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/discipline' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getStructures();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...disciplineEntity,
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
          <h2 id="jhipsterSampleApplicationApp.discipline.home.createOrEditLabel" data-cy="DisciplineCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.discipline.home.createOrEditLabel">Create or edit a Discipline</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : disciplineEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="discipline-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="discipline-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="discipline-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.discipline.code">Code</Translate>
                </Label>
                <AvField
                  id="discipline-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="discipline-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.discipline.nom">Nom</Translate>
                </Label>
                <AvField
                  id="discipline-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="discipline-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.discipline.description">Description</Translate>
                </Label>
                <AvField id="discipline-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="photoLabel" for="discipline-photo">
                  <Translate contentKey="jhipsterSampleApplicationApp.discipline.photo">Photo</Translate>
                </Label>
                <AvField id="discipline-photo" data-cy="photo" type="text" name="photo" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/discipline" replace color="info">
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
  structures: storeState.structure.entities,
  disciplineEntity: storeState.discipline.entity,
  loading: storeState.discipline.loading,
  updating: storeState.discipline.updating,
  updateSuccess: storeState.discipline.updateSuccess,
});

const mapDispatchToProps = {
  getStructures,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DisciplineUpdate);
