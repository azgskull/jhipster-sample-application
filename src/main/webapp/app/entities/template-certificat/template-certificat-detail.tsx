import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './template-certificat.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITemplateCertificatDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TemplateCertificatDetail = (props: ITemplateCertificatDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { templateCertificatEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="templateCertificatDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.detail.title">TemplateCertificat</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{templateCertificatEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.code">Code</Translate>
            </span>
          </dt>
          <dd>{templateCertificatEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{templateCertificatEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.description">Description</Translate>
            </span>
          </dt>
          <dd>{templateCertificatEntity.description}</dd>
          <dt>
            <span id="fichier">
              <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.fichier">Fichier</Translate>
            </span>
          </dt>
          <dd>
            {templateCertificatEntity.fichier ? (
              <div>
                {templateCertificatEntity.fichierContentType ? (
                  <a onClick={openFile(templateCertificatEntity.fichierContentType, templateCertificatEntity.fichier)}>
                    <img
                      src={`data:${templateCertificatEntity.fichierContentType};base64,${templateCertificatEntity.fichier}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {templateCertificatEntity.fichierContentType}, {byteSize(templateCertificatEntity.fichier)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.discipline">Discipline</Translate>
          </dt>
          <dd>{templateCertificatEntity.discipline ? templateCertificatEntity.discipline.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.templateCertificat.typeCertificat">Type Certificat</Translate>
          </dt>
          <dd>{templateCertificatEntity.typeCertificat ? templateCertificatEntity.typeCertificat.nom : ''}</dd>
        </dl>
        <Button tag={Link} to="/template-certificat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/template-certificat/${templateCertificatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ templateCertificat }: IRootState) => ({
  templateCertificatEntity: templateCertificat.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TemplateCertificatDetail);
