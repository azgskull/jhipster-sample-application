import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './seance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISeanceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeanceDetail = (props: ISeanceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { seanceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="seanceDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.seance.detail.title">Seance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{seanceEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.seance.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{seanceEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.seance.description">Description</Translate>
            </span>
          </dt>
          <dd>{seanceEntity.description}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="jhipsterSampleApplicationApp.seance.date">Date</Translate>
            </span>
          </dt>
          <dd>{seanceEntity.date ? <TextFormat value={seanceEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="heureDebut">
              <Translate contentKey="jhipsterSampleApplicationApp.seance.heureDebut">Heure Debut</Translate>
            </span>
          </dt>
          <dd>{seanceEntity.heureDebut}</dd>
          <dt>
            <span id="heureFin">
              <Translate contentKey="jhipsterSampleApplicationApp.seance.heureFin">Heure Fin</Translate>
            </span>
          </dt>
          <dd>{seanceEntity.heureFin}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.seance.typeSeance">Type Seance</Translate>
          </dt>
          <dd>{seanceEntity.typeSeance ? seanceEntity.typeSeance.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.seance.seanceProgramme">Seance Programme</Translate>
          </dt>
          <dd>{seanceEntity.seanceProgramme ? seanceEntity.seanceProgramme.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/seance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/seance/${seanceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ seance }: IRootState) => ({
  seanceEntity: seance.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeanceDetail);
