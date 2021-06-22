import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adhesion.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdhesionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdhesionDetail = (props: IAdhesionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adhesionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adhesionDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.adhesion.detail.title">Adhesion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adhesionEntity.id}</dd>
          <dt>
            <span id="dateDebut">
              <Translate contentKey="jhipsterSampleApplicationApp.adhesion.dateDebut">Date Debut</Translate>
            </span>
          </dt>
          <dd>
            {adhesionEntity.dateDebut ? <TextFormat value={adhesionEntity.dateDebut} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dateFin">
              <Translate contentKey="jhipsterSampleApplicationApp.adhesion.dateFin">Date Fin</Translate>
            </span>
          </dt>
          <dd>
            {adhesionEntity.dateFin ? <TextFormat value={adhesionEntity.dateFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.adhesion.role">Role</Translate>
          </dt>
          <dd>{adhesionEntity.role ? adhesionEntity.role.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.adhesion.seanceProgramme">Seance Programme</Translate>
          </dt>
          <dd>{adhesionEntity.seanceProgramme ? adhesionEntity.seanceProgramme.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.adhesion.sportif">Sportif</Translate>
          </dt>
          <dd>{adhesionEntity.sportif ? adhesionEntity.sportif.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adhesion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adhesion/${adhesionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ adhesion }: IRootState) => ({
  adhesionEntity: adhesion.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdhesionDetail);
