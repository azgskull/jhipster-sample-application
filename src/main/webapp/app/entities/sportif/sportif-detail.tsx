import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './sportif.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISportifDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SportifDetail = (props: ISportifDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { sportifEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sportifDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.sportif.detail.title">Sportif</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.code">Code</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.prenom}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.photo}</dd>
          <dt>
            <span id="dateNaissance">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.dateNaissance">Date Naissance</Translate>
            </span>
          </dt>
          <dd>
            {sportifEntity.dateNaissance ? (
              <TextFormat value={sportifEntity.dateNaissance} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="numeroIdentite">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.numeroIdentite">Numero Identite</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.numeroIdentite}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.adresse}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.telephone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jhipsterSampleApplicationApp.sportif.email">Email</Translate>
            </span>
          </dt>
          <dd>{sportifEntity.email}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.sportif.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{sportifEntity.utilisateur ? sportifEntity.utilisateur.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.sportif.pays">Pays</Translate>
          </dt>
          <dd>{sportifEntity.pays ? sportifEntity.pays.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.sportif.ville">Ville</Translate>
          </dt>
          <dd>{sportifEntity.ville ? sportifEntity.ville.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.sportif.typeIdentite">Type Identite</Translate>
          </dt>
          <dd>{sportifEntity.typeIdentite ? sportifEntity.typeIdentite.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sportif" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sportif/${sportifEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ sportif }: IRootState) => ({
  sportifEntity: sportif.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SportifDetail);
