import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './discipline.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDisciplineDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DisciplineDetail = (props: IDisciplineDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { disciplineEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="disciplineDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.discipline.detail.title">Discipline</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{disciplineEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.discipline.code">Code</Translate>
            </span>
          </dt>
          <dd>{disciplineEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.discipline.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{disciplineEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.discipline.description">Description</Translate>
            </span>
          </dt>
          <dd>{disciplineEntity.description}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="jhipsterSampleApplicationApp.discipline.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{disciplineEntity.photo}</dd>
        </dl>
        <Button tag={Link} to="/discipline" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/discipline/${disciplineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ discipline }: IRootState) => ({
  disciplineEntity: discipline.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DisciplineDetail);
