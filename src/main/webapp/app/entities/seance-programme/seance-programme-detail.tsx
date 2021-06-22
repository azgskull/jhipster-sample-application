import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './seance-programme.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISeanceProgrammeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeanceProgrammeDetail = (props: ISeanceProgrammeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { seanceProgrammeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="seanceProgrammeDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.detail.title">SeanceProgramme</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{seanceProgrammeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.code">Code</Translate>
            </span>
          </dt>
          <dd>{seanceProgrammeEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{seanceProgrammeEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.description">Description</Translate>
            </span>
          </dt>
          <dd>{seanceProgrammeEntity.description}</dd>
          <dt>
            <span id="programmeExpression">
              <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.programmeExpression">Programme Expression</Translate>
            </span>
          </dt>
          <dd>{seanceProgrammeEntity.programmeExpression}</dd>
          <dt>
            <span id="duree">
              <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.duree">Duree</Translate>
            </span>
          </dt>
          <dd>{seanceProgrammeEntity.duree}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.discipline">Discipline</Translate>
          </dt>
          <dd>{seanceProgrammeEntity.discipline ? seanceProgrammeEntity.discipline.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.seanceProgramme.salle">Salle</Translate>
          </dt>
          <dd>{seanceProgrammeEntity.salle ? seanceProgrammeEntity.salle.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/seance-programme" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/seance-programme/${seanceProgrammeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ seanceProgramme }: IRootState) => ({
  seanceProgrammeEntity: seanceProgramme.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeanceProgrammeDetail);
