import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './assurance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAssuranceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssuranceDetail = (props: IAssuranceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { assuranceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="assuranceDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.assurance.detail.title">Assurance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{assuranceEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.assurance.code">Code</Translate>
            </span>
          </dt>
          <dd>{assuranceEntity.code}</dd>
          <dt>
            <span id="dateDebut">
              <Translate contentKey="jhipsterSampleApplicationApp.assurance.dateDebut">Date Debut</Translate>
            </span>
          </dt>
          <dd>
            {assuranceEntity.dateDebut ? <TextFormat value={assuranceEntity.dateDebut} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dateFin">
              <Translate contentKey="jhipsterSampleApplicationApp.assurance.dateFin">Date Fin</Translate>
            </span>
          </dt>
          <dd>
            {assuranceEntity.dateFin ? <TextFormat value={assuranceEntity.dateFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="details">
              <Translate contentKey="jhipsterSampleApplicationApp.assurance.details">Details</Translate>
            </span>
          </dt>
          <dd>{assuranceEntity.details}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.assurance.organismeAssurance">Organisme Assurance</Translate>
          </dt>
          <dd>{assuranceEntity.organismeAssurance ? assuranceEntity.organismeAssurance.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.assurance.sportif">Sportif</Translate>
          </dt>
          <dd>
            {assuranceEntity.sportifs
              ? assuranceEntity.sportifs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nom}</a>
                    {assuranceEntity.sportifs && i === assuranceEntity.sportifs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/assurance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/assurance/${assuranceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ assurance }: IRootState) => ({
  assuranceEntity: assurance.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssuranceDetail);
