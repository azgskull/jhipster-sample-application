import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './structure.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStructureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StructureDetail = (props: IStructureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { structureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="structureDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.structure.detail.title">Structure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{structureEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.structure.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{structureEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.structure.description">Description</Translate>
            </span>
          </dt>
          <dd>{structureEntity.description}</dd>
          <dt>
            <span id="siteWeb">
              <Translate contentKey="jhipsterSampleApplicationApp.structure.siteWeb">Site Web</Translate>
            </span>
          </dt>
          <dd>{structureEntity.siteWeb}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="jhipsterSampleApplicationApp.structure.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{structureEntity.adresse}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="jhipsterSampleApplicationApp.structure.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {structureEntity.logo ? (
              <div>
                {structureEntity.logoContentType ? (
                  <a onClick={openFile(structureEntity.logoContentType, structureEntity.logo)}>
                    <img src={`data:${structureEntity.logoContentType};base64,${structureEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {structureEntity.logoContentType}, {byteSize(structureEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="email">
              <Translate contentKey="jhipsterSampleApplicationApp.structure.email">Email</Translate>
            </span>
          </dt>
          <dd>{structureEntity.email}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="jhipsterSampleApplicationApp.structure.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{structureEntity.telephone}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.structure.pays">Pays</Translate>
          </dt>
          <dd>{structureEntity.pays ? structureEntity.pays.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.structure.ville">Ville</Translate>
          </dt>
          <dd>{structureEntity.ville ? structureEntity.ville.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.structure.discipline">Discipline</Translate>
          </dt>
          <dd>
            {structureEntity.disciplines
              ? structureEntity.disciplines.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nom}</a>
                    {structureEntity.disciplines && i === structureEntity.disciplines.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/structure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/structure/${structureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ structure }: IRootState) => ({
  structureEntity: structure.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StructureDetail);
