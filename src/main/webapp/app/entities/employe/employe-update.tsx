import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ITypeIdentite } from 'app/shared/model/type-identite.model';
import { getEntities as getTypeIdentites } from 'app/entities/type-identite/type-identite.reducer';
import { ISalle } from 'app/shared/model/salle.model';
import { getEntities as getSalles } from 'app/entities/salle/salle.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employe.reducer';
import { IEmploye } from 'app/shared/model/employe.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeUpdate = (props: IEmployeUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { employeEntity, users, typeIdentites, salles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/employe' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getTypeIdentites();
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
        ...employeEntity,
        ...values,
        utilisateur: users.find(it => it.id.toString() === values.utilisateurId.toString()),
        typeIdentite: typeIdentites.find(it => it.id.toString() === values.typeIdentiteId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.employe.home.createOrEditLabel" data-cy="EmployeCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.employe.home.createOrEditLabel">Create or edit a Employe</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employe-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employe-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="employe-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.code">Code</Translate>
                </Label>
                <AvField
                  id="employe-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="employe-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.nom">Nom</Translate>
                </Label>
                <AvField
                  id="employe-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prenomLabel" for="employe-prenom">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.prenom">Prenom</Translate>
                </Label>
                <AvField
                  id="employe-prenom"
                  data-cy="prenom"
                  type="text"
                  name="prenom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="photoLabel" for="employe-photo">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.photo">Photo</Translate>
                </Label>
                <AvField id="employe-photo" data-cy="photo" type="text" name="photo" />
              </AvGroup>
              <AvGroup>
                <Label id="dateNaissanceLabel" for="employe-dateNaissance">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.dateNaissance">Date Naissance</Translate>
                </Label>
                <AvField id="employe-dateNaissance" data-cy="dateNaissance" type="date" className="form-control" name="dateNaissance" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroIdentiteLabel" for="employe-numeroIdentite">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.numeroIdentite">Numero Identite</Translate>
                </Label>
                <AvField id="employe-numeroIdentite" data-cy="numeroIdentite" type="text" name="numeroIdentite" />
              </AvGroup>
              <AvGroup>
                <Label id="adresseLabel" for="employe-adresse">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.adresse">Adresse</Translate>
                </Label>
                <AvField id="employe-adresse" data-cy="adresse" type="text" name="adresse" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="employe-telephone">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.telephone">Telephone</Translate>
                </Label>
                <AvField id="employe-telephone" data-cy="telephone" type="text" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="employe-email">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.email">Email</Translate>
                </Label>
                <AvField id="employe-email" data-cy="email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label for="employe-utilisateur">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.utilisateur">Utilisateur</Translate>
                </Label>
                <AvInput id="employe-utilisateur" data-cy="utilisateur" type="select" className="form-control" name="utilisateurId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employe-typeIdentite">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.typeIdentite">Type Identite</Translate>
                </Label>
                <AvInput id="employe-typeIdentite" data-cy="typeIdentite" type="select" className="form-control" name="typeIdentiteId">
                  <option value="" key="0" />
                  {typeIdentites
                    ? typeIdentites.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employe-salle">
                  <Translate contentKey="jhipsterSampleApplicationApp.employe.salle">Salle</Translate>
                </Label>
                <AvInput id="employe-salle" data-cy="salle" type="select" className="form-control" name="salleId">
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
              <Button tag={Link} id="cancel-save" to="/employe" replace color="info">
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
  users: storeState.userManagement.users,
  typeIdentites: storeState.typeIdentite.entities,
  salles: storeState.salle.entities,
  employeEntity: storeState.employe.entity,
  loading: storeState.employe.loading,
  updating: storeState.employe.updating,
  updateSuccess: storeState.employe.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getTypeIdentites,
  getSalles,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeUpdate);
