import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ville.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVilleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VilleDetail = (props: IVilleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { villeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="villeDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.ville.detail.title">Ville</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{villeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.ville.code">Code</Translate>
            </span>
          </dt>
          <dd>{villeEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.ville.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{villeEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.ville.description">Description</Translate>
            </span>
          </dt>
          <dd>{villeEntity.description}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.ville.pays">Pays</Translate>
          </dt>
          <dd>{villeEntity.pays ? villeEntity.pays.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ville" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ville/${villeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ ville }: IRootState) => ({
  villeEntity: ville.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VilleDetail);
