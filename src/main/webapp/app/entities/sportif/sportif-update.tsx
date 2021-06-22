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
import { IPays } from 'app/shared/model/pays.model';
import { getEntities as getPays } from 'app/entities/pays/pays.reducer';
import { IVille } from 'app/shared/model/ville.model';
import { getEntities as getVilles } from 'app/entities/ville/ville.reducer';
import { ITypeIdentite } from 'app/shared/model/type-identite.model';
import { getEntities as getTypeIdentites } from 'app/entities/type-identite/type-identite.reducer';
import { IAssurance } from 'app/shared/model/assurance.model';
import { getEntities as getAssurances } from 'app/entities/assurance/assurance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './sportif.reducer';
import { ISportif } from 'app/shared/model/sportif.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISportifUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SportifUpdate = (props: ISportifUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { sportifEntity, users, pays, villes, typeIdentites, assurances, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/sportif' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getPays();
    props.getVilles();
    props.getTypeIdentites();
    props.getAssurances();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...sportifEntity,
        ...values,
        utilisateur: users.find(it => it.id.toString() === values.utilisateurId.toString()),
        pays: pays.find(it => it.id.toString() === values.paysId.toString()),
        ville: villes.find(it => it.id.toString() === values.villeId.toString()),
        typeIdentite: typeIdentites.find(it => it.id.toString() === values.typeIdentiteId.toString()),
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
          <h2 id="jhipsterSampleApplicationApp.sportif.home.createOrEditLabel" data-cy="SportifCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.sportif.home.createOrEditLabel">Create or edit a Sportif</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : sportifEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="sportif-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="sportif-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="sportif-code">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.code">Code</Translate>
                </Label>
                <AvField
                  id="sportif-code"
                  data-cy="code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomLabel" for="sportif-nom">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.nom">Nom</Translate>
                </Label>
                <AvField
                  id="sportif-nom"
                  data-cy="nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prenomLabel" for="sportif-prenom">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.prenom">Prenom</Translate>
                </Label>
                <AvField
                  id="sportif-prenom"
                  data-cy="prenom"
                  type="text"
                  name="prenom"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="photoLabel" for="sportif-photo">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.photo">Photo</Translate>
                </Label>
                <AvField id="sportif-photo" data-cy="photo" type="text" name="photo" />
              </AvGroup>
              <AvGroup>
                <Label id="dateNaissanceLabel" for="sportif-dateNaissance">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.dateNaissance">Date Naissance</Translate>
                </Label>
                <AvField id="sportif-dateNaissance" data-cy="dateNaissance" type="date" className="form-control" name="dateNaissance" />
              </AvGroup>
              <AvGroup>
                <Label id="numeroIdentiteLabel" for="sportif-numeroIdentite">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.numeroIdentite">Numero Identite</Translate>
                </Label>
                <AvField id="sportif-numeroIdentite" data-cy="numeroIdentite" type="text" name="numeroIdentite" />
              </AvGroup>
              <AvGroup>
                <Label id="adresseLabel" for="sportif-adresse">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.adresse">Adresse</Translate>
                </Label>
                <AvField id="sportif-adresse" data-cy="adresse" type="text" name="adresse" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="sportif-telephone">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.telephone">Telephone</Translate>
                </Label>
                <AvField id="sportif-telephone" data-cy="telephone" type="text" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="sportif-email">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.email">Email</Translate>
                </Label>
                <AvField id="sportif-email" data-cy="email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label for="sportif-utilisateur">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.utilisateur">Utilisateur</Translate>
                </Label>
                <AvInput id="sportif-utilisateur" data-cy="utilisateur" type="select" className="form-control" name="utilisateurId">
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
                <Label for="sportif-pays">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.pays">Pays</Translate>
                </Label>
                <AvInput id="sportif-pays" data-cy="pays" type="select" className="form-control" name="paysId">
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
                <Label for="sportif-ville">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.ville">Ville</Translate>
                </Label>
                <AvInput id="sportif-ville" data-cy="ville" type="select" className="form-control" name="villeId">
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
                <Label for="sportif-typeIdentite">
                  <Translate contentKey="jhipsterSampleApplicationApp.sportif.typeIdentite">Type Identite</Translate>
                </Label>
                <AvInput id="sportif-typeIdentite" data-cy="typeIdentite" type="select" className="form-control" name="typeIdentiteId">
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
              <Button tag={Link} id="cancel-save" to="/sportif" replace color="info">
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
  pays: storeState.pays.entities,
  villes: storeState.ville.entities,
  typeIdentites: storeState.typeIdentite.entities,
  assurances: storeState.assurance.entities,
  sportifEntity: storeState.sportif.entity,
  loading: storeState.sportif.loading,
  updating: storeState.sportif.updating,
  updateSuccess: storeState.sportif.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getPays,
  getVilles,
  getTypeIdentites,
  getAssurances,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SportifUpdate);
