import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employe.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeDetail = (props: IEmployeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.employe.detail.title">Employe</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.code">Code</Translate>
            </span>
          </dt>
          <dd>{employeEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{employeEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{employeEntity.prenom}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{employeEntity.photo}</dd>
          <dt>
            <span id="dateNaissance">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.dateNaissance">Date Naissance</Translate>
            </span>
          </dt>
          <dd>
            {employeEntity.dateNaissance ? (
              <TextFormat value={employeEntity.dateNaissance} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="numeroIdentite">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.numeroIdentite">Numero Identite</Translate>
            </span>
          </dt>
          <dd>{employeEntity.numeroIdentite}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{employeEntity.adresse}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{employeEntity.telephone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jhipsterSampleApplicationApp.employe.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeEntity.email}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.employe.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{employeEntity.utilisateur ? employeEntity.utilisateur.login : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.employe.typeIdentite">Type Identite</Translate>
          </dt>
          <dd>{employeEntity.typeIdentite ? employeEntity.typeIdentite.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.employe.salle">Salle</Translate>
          </dt>
          <dd>{employeEntity.salle ? employeEntity.salle.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employe" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employe/${employeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employe }: IRootState) => ({
  employeEntity: employe.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeDetail);
