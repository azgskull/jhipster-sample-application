import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPays } from 'app/shared/model/pays.model';
import { getEntities as getPays } from 'app/entities/pays/pays.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ville.reducer';
import { IVille } from 'app/shared/model/ville.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVilleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VilleUpdate = (props: IVilleUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { villeEntity, pays, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/ville' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPays();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...villeEntity,
        ...values,
        pays: pays.find(it => it.id.toString() === values.paysId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.ville.home.createOrEditLabel" data-cy="VilleCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.ville.home.createOrEditLabel">Create or edit a Ville</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : villeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="ville-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="ville-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="ville-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.ville.code">Code</Translate>
                </Label>
                <AvField
                  id="ville-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="ville-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.ville.nom">Nom</Translate>
                </Label>
                <AvField
                  id="ville-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="ville-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.ville.description">Description</Translate>
                </Label>
                <AvField id="ville-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label for="ville-pays">
                  <Translate contentKey="jhipsterSampleApplicationApp.ville.pays">Pays</Translate>
                </Label>
                <AvInput id="ville-pays" data-cy="pays" type="select" className="form-control" name="paysId">
                  <option value="" key="0" />
                  {pays
                    ? pays.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/ville" replace color="info">
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
  villeEntity: storeState.ville.entity,
  loading: storeState.ville.loading,
  updating: storeState.ville.updating,
  updateSuccess: storeState.ville.updateSuccess,
});

const mapDispatchToProps = {
  getPays,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VilleUpdate);
