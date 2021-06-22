import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pays.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaysDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaysDetail = (props: IPaysDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { paysEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paysDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.pays.detail.title">Pays</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paysEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.pays.code">Code</Translate>
            </span>
          </dt>
          <dd>{paysEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.pays.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{paysEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.pays.description">Description</Translate>
            </span>
          </dt>
          <dd>{paysEntity.description}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="jhipsterSampleApplicationApp.pays.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {paysEntity.logo ? (
              <div>
                {paysEntity.logoContentType ? (
                  <a onClick={openFile(paysEntity.logoContentType, paysEntity.logo)}>
                    <img src={`data:${paysEntity.logoContentType};base64,${paysEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {paysEntity.logoContentType}, {byteSize(paysEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pays" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pays/${paysEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pays }: IRootState) => ({
  paysEntity: pays.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaysDetail);
