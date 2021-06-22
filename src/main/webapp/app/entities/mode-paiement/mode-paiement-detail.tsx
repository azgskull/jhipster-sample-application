import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mode-paiement.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IModePaiementDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ModePaiementDetail = (props: IModePaiementDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { modePaiementEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="modePaiementDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.modePaiement.detail.title">ModePaiement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{modePaiementEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.modePaiement.code">Code</Translate>
            </span>
          </dt>
          <dd>{modePaiementEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.modePaiement.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{modePaiementEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.modePaiement.description">Description</Translate>
            </span>
          </dt>
          <dd>{modePaiementEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/mode-paiement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mode-paiement/${modePaiementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ modePaiement }: IRootState) => ({
  modePaiementEntity: modePaiement.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ModePaiementDetail);
