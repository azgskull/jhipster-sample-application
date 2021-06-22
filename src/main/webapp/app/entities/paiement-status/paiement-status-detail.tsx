import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './paiement-status.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaiementStatusDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaiementStatusDetail = (props: IPaiementStatusDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { paiementStatusEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paiementStatusDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.paiementStatus.detail.title">PaiementStatus</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paiementStatusEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.paiementStatus.code">Code</Translate>
            </span>
          </dt>
          <dd>{paiementStatusEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.paiementStatus.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{paiementStatusEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.paiementStatus.description">Description</Translate>
            </span>
          </dt>
          <dd>{paiementStatusEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/paiement-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paiement-status/${paiementStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ paiementStatus }: IRootState) => ({
  paiementStatusEntity: paiementStatus.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaiementStatusDetail);
