import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDiscipline } from 'app/shared/model/discipline.model';
import { getEntities as getDisciplines } from 'app/entities/discipline/discipline.reducer';
import { ISalle } from 'app/shared/model/salle.model';
import { getEntities as getSalles } from 'app/entities/salle/salle.reducer';
import { getEntity, updateEntity, createEntity, reset } from './seance-programme.reducer';
import { ISeanceProgramme } from 'app/shared/model/seance-programme.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISeanceProgrammeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeanceProgrammeUpdate = (props: ISeanceProgrammeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { seanceProgrammeEntity, disciplines, salles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/seance-programme' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDisciplines();
    props.getSalles();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...seanceProgrammeEntity,
        ...values,
        discipline: disciplines.find(it => it.id.toString() === values.disciplineId.toString()),
        salle: salles.find(it => it.id.toString() === values.salleId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.seanceProgramme.home.createOrEditLabel" data-cy="SeanceProgrammeCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.home.createOrEditLabel">
              Create or edit a SeanceProgramme
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : seanceProgrammeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="seance-programme-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="seance-programme-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="seance-programme-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.code">Code</Translate>
                </Label>
                <AvField
                  id="seance-programme-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="seance-programme-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.nom">Nom</Translate>
                </Label>
                <AvField
                  id="seance-programme-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="seance-programme-description">
                  <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.description">Description</Translate>
                </Label>
                <AvField id="seance-programme-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="programmeExpressionLabel" for="seance-programme-programmeExpression">
                  <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.programmeExpression">Programme Expression</Translate>
                </Label>
                <AvField id="seance-programme-programmeExpression" data-cy="programmeExpression" type="text" name="programmeExpression" />
              </AvGroup>
              <AvGroup>
                <Label id="dureeLabel" for="seance-programme-duree">
                  <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.duree">Duree</Translate>
                </Label>
                <AvField id="seance-programme-duree" data-cy="duree" type="string" className="form-control" name="duree" />
              </AvGroup>
              <AvGroup>
                <Label for="seance-programme-discipline">
                  <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.discipline">Discipline</Translate>
                </Label>
                <AvInput id="seance-programme-discipline" data-cy="discipline" type="select" className="form-control" name="disciplineId">
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
                <Label for="seance-programme-salle">
                  <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.salle">Salle</Translate>
                </Label>
                <AvInput id="seance-programme-salle" data-cy="salle" type="select" className="form-control" name="salleId">
                  <option value="" key="0" />
                  {salles
                    ? salles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/seance-programme" replace color="info">
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
  salles: storeState.salle.entities,
  seanceProgrammeEntity: storeState.seanceProgramme.entity,
  loading: storeState.seanceProgramme.loading,
  updating: storeState.seanceProgramme.updating,
  updateSuccess: storeState.seanceProgramme.updateSuccess,
});

const mapDispatchToProps = {
  getDisciplines,
  getSalles,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeanceProgrammeUpdate);
