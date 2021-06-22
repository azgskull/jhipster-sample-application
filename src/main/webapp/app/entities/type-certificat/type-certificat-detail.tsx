import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './type-certificat.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITypeCertificatDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypeCertificatDetail = (props: ITypeCertificatDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { typeCertificatEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="typeCertificatDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.detail.title">TypeCertificat</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{typeCertificatEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.code">Code</Translate>
            </span>
          </dt>
          <dd>{typeCertificatEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{typeCertificatEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.typeCertificat.description">Description</Translate>
            </span>
          </dt>
          <dd>{typeCertificatEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/type-certificat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/type-certificat/${typeCertificatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ typeCertificat }: IRootState) => ({
  typeCertificatEntity: typeCertificat.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeCertificatDetail);
