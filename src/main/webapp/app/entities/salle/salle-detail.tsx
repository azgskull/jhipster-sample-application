import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './salle.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISalleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SalleDetail = (props: ISalleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { salleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="salleDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.salle.detail.title">Salle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{salleEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.salle.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{salleEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.salle.description">Description</Translate>
            </span>
          </dt>
          <dd>{salleEntity.description}</dd>
          <dt>
            <span id="siteWeb">
              <Translate contentKey="jhipsterSampleApplicationApp.salle.siteWeb">Site Web</Translate>
            </span>
          </dt>
          <dd>{salleEntity.siteWeb}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="jhipsterSampleApplicationApp.salle.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{salleEntity.adresse}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="jhipsterSampleApplicationApp.salle.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {salleEntity.logo ? (
              <div>
                {salleEntity.logoContentType ? (
                  <a onClick={openFile(salleEntity.logoContentType, salleEntity.logo)}>
                    <img src={`data:${salleEntity.logoContentType};base64,${salleEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {salleEntity.logoContentType}, {byteSize(salleEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="email">
              <Translate contentKey="jhipsterSampleApplicationApp.salle.email">Email</Translate>
            </span>
          </dt>
          <dd>{salleEntity.email}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="jhipsterSampleApplicationApp.salle.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{salleEntity.telephone}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.salle.pays">Pays</Translate>
          </dt>
          <dd>{salleEntity.pays ? salleEntity.pays.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.salle.ville">Ville</Translate>
          </dt>
          <dd>{salleEntity.ville ? salleEntity.ville.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.salle.structure">Structure</Translate>
          </dt>
          <dd>{salleEntity.structure ? salleEntity.structure.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/salle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/salle/${salleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ salle }: IRootState) => ({
  salleEntity: salle.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SalleDetail);
