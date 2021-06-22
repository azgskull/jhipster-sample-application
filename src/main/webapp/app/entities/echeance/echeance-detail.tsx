import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './echeance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEcheanceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EcheanceDetail = (props: IEcheanceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { echeanceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="echeanceDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.echeance.detail.title">Echeance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{echeanceEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.echeance.code">Code</Translate>
            </span>
          </dt>
          <dd>{echeanceEntity.code}</dd>
          <dt>
            <span id="datePrevuPaiement">
              <Translate contentKey="jhipsterSampleApplicationApp.echeance.datePrevuPaiement">Date Prevu Paiement</Translate>
            </span>
          </dt>
          <dd>
            {echeanceEntity.datePrevuPaiement ? (
              <TextFormat value={echeanceEntity.datePrevuPaiement} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="montant">
              <Translate contentKey="jhipsterSampleApplicationApp.echeance.montant">Montant</Translate>
            </span>
          </dt>
          <dd>{echeanceEntity.montant}</dd>
          <dt>
            <span id="payeTotalement">
              <Translate contentKey="jhipsterSampleApplicationApp.echeance.payeTotalement">Paye Totalement</Translate>
            </span>
          </dt>
          <dd>{echeanceEntity.payeTotalement ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.echeance.assurance">Assurance</Translate>
          </dt>
          <dd>{echeanceEntity.assurance ? echeanceEntity.assurance.code : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.echeance.certificat">Certificat</Translate>
          </dt>
          <dd>{echeanceEntity.certificat ? echeanceEntity.certificat.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.echeance.seance">Seance</Translate>
          </dt>
          <dd>
            {echeanceEntity.seances
              ? echeanceEntity.seances.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nom}</a>
                    {echeanceEntity.seances && i === echeanceEntity.seances.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.echeance.sportif">Sportif</Translate>
          </dt>
          <dd>{echeanceEntity.sportif ? echeanceEntity.sportif.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/echeance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/echeance/${echeanceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ echeance }: IRootState) => ({
  echeanceEntity: echeance.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EcheanceDetail);
