import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './certificat.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICertificatDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CertificatDetail = (props: ICertificatDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { certificatEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="certificatDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.certificat.detail.title">Certificat</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{certificatEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterSampleApplicationApp.certificat.code">Code</Translate>
            </span>
          </dt>
          <dd>{certificatEntity.code}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="jhipsterSampleApplicationApp.certificat.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{certificatEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jhipsterSampleApplicationApp.certificat.description">Description</Translate>
            </span>
          </dt>
          <dd>{certificatEntity.description}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="jhipsterSampleApplicationApp.certificat.date">Date</Translate>
            </span>
          </dt>
          <dd>{certificatEntity.date ? <TextFormat value={certificatEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="dateFin">
              <Translate contentKey="jhipsterSampleApplicationApp.certificat.dateFin">Date Fin</Translate>
            </span>
          </dt>
          <dd>
            {certificatEntity.dateFin ? <TextFormat value={certificatEntity.dateFin} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="fichier">
              <Translate contentKey="jhipsterSampleApplicationApp.certificat.fichier">Fichier</Translate>
            </span>
          </dt>
          <dd>
            {certificatEntity.fichier ? (
              <div>
                {certificatEntity.fichierContentType ? (
                  <a onClick={openFile(certificatEntity.fichierContentType, certificatEntity.fichier)}>
                    <img
                      src={`data:${certificatEntity.fichierContentType};base64,${certificatEntity.fichier}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {certificatEntity.fichierContentType}, {byteSize(certificatEntity.fichier)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.certificat.templateCertificat">Template Certificat</Translate>
          </dt>
          <dd>{certificatEntity.templateCertificat ? certificatEntity.templateCertificat.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.certificat.seance">Seance</Translate>
          </dt>
          <dd>{certificatEntity.seance ? certificatEntity.seance.nom : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.certificat.sportif">Sportif</Translate>
          </dt>
          <dd>{certificatEntity.sportif ? certificatEntity.sportif.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/certificat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/certificat/${certificatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ certificat }: IRootState) => ({
  certificatEntity: certificat.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CertificatDetail);
