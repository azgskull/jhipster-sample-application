import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './type-identite.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITypeIdentiteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypeIdentiteDetail = (props: ITypeIdentiteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { typeIdentiteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="typeIdentiteDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.typeIdentite.detail.title">TypeIdentite</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{typeIdentiteEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.typeIdentite.code">Code</Translate>
            </span>
          </dt>
          <dd>{typeIdentiteEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.typeIdentite.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{typeIdentiteEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.typeIdentite.description">Description</Translate>
            </span>
          </dt>
          <dd>{typeIdentiteEntity.description}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="jhipsterSampleApplicationApp.typeIdentite.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {typeIdentiteEntity.logo ? (
              <div>
                {typeIdentiteEntity.logoContentType ? (
                  <a onClick={openFile(typeIdentiteEntity.logoContentType, typeIdentiteEntity.logo)}>
                    <img
                      src={`data:${typeIdentiteEntity.logoContentType};base64,${typeIdentiteEntity.logo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {typeIdentiteEntity.logoContentType}, {byteSize(typeIdentiteEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/type-identite" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/type-identite/${typeIdentiteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ typeIdentite }: IRootState) => ({
  typeIdentiteEntity: typeIdentite.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeIdentiteDetail);
