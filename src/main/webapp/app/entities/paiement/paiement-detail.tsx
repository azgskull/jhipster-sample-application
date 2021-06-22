import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './paiement.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaiementDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaiementDetail = (props: IPaiementDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { paiementEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paiementDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.paiement.detail.title">Paiement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paiementEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.paiement.code">Code</Translate>
            </span>
          </dt>
          <dd>{paiementEntity.code}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="jhipsterSampleApplicationApp.paiement.date">Date</Translate>
            </span>
          </dt>
          <dd>{paiementEntity.date ? <TextFormat value={paiementEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="montant">
              <Translate contentKey="jhipsterSampleApplicationApp.paiement.montant">Montant</Translate>
            </span>
          </dt>
          <dd>{paiementEntity.montant}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.paiement.description">Description</Translate>
            </span>
          </dt>
          <dd>{paiementEntity.description}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.paiement.modePaiement">Mode Paiement</Translate>
          </dt>
          <dd>{paiementEntity.modePaiement ? paiementEntity.modePaiement.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.paiement.status">Status</Translate>
          </dt>
          <dd>{paiementEntity.status ? paiementEntity.status.code : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.paiement.echeance">Echeance</Translate>
          </dt>
          <dd>
            {paiementEntity.echeances
              ? paiementEntity.echeances.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {paiementEntity.echeances && i === paiementEntity.echeances.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/paiement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paiement/${paiementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ paiement }: IRootState) => ({
  paiementEntity: paiement.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaiementDetail);
