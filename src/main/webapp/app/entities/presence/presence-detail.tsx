import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './presence.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPresenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PresenceDetail = (props: IPresenceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { presenceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="presenceDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.presence.detail.title">Presence</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{presenceEntity.id}</dd>
          <dt>
            <span id="heureDebut">
              <Translate contentKey="jhipsterSampleApplicationApp.presence.heureDebut">Heure Debut</Translate>
            </span>
          </dt>
          <dd>{presenceEntity.heureDebut}</dd>
          <dt>
            <span id="heureFin">
              <Translate contentKey="jhipsterSampleApplicationApp.presence.heureFin">Heure Fin</Translate>
            </span>
          </dt>
          <dd>{presenceEntity.heureFin}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.presence.description">Description</Translate>
            </span>
          </dt>
          <dd>{presenceEntity.description}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.presence.role">Role</Translate>
          </dt>
          <dd>{presenceEntity.role ? presenceEntity.role.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.presence.seance">Seance</Translate>
          </dt>
          <dd>{presenceEntity.seance ? presenceEntity.seance.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.presence.sportif">Sportif</Translate>
          </dt>
          <dd>{presenceEntity.sportif ? presenceEntity.sportif.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/presence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/presence/${presenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ presence }: IRootState) => ({
  presenceEntity: presence.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PresenceDetail);
