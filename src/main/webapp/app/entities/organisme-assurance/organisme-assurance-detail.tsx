import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './organisme-assurance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrganismeAssuranceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrganismeAssuranceDetail = (props: IOrganismeAssuranceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { organismeAssuranceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="organismeAssuranceDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.organismeAssurance.detail.title">OrganismeAssurance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{organismeAssuranceEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.organismeAssurance.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{organismeAssuranceEntity.nom}</dd>
        </dl>
        <Button tag={Link} to="/organisme-assurance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organisme-assurance/${organismeAssuranceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ organismeAssurance }: IRootState) => ({
  organismeAssuranceEntity: organismeAssurance.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrganismeAssuranceDetail);
