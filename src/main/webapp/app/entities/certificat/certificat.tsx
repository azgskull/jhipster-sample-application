import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './certificat.reducer';
import { ICertificat } from 'app/shared/model/certificat.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICertificatProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Certificat = (props: ICertificatProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { certificatList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="certificat-heading" data-cy="CertificatHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.certificat.home.title">Certificats</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.certificat.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.certificat.home.createLabel">Create new Certificat</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {certificatList && certificatList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nom')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.nom">Nom</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateFin')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.dateFin">Date Fin</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fichier')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.fichier">Fichier</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.templateCertificat">Template Certificat</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.seance">Seance</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.certificat.sportif">Sportif</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {certificatList.map((certificat, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${certificat.id}`} color="link" size="sm">
                      {certificat.id}
                    </Button>
                  </td>
                  <td>{certificat.code}</td>
                  <td>{certificat.nom}</td>
                  <td>{certificat.description}</td>
                  <td>{certificat.date ? <TextFormat type="date" value={certificat.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>
                    {certificat.dateFin ? <TextFormat type="date" value={certificat.dateFin} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {certificat.fichier ? (
                      <div>
                        {certificat.fichierContentType ? (
                          <a onClick={openFile(certificat.fichierContentType, certificat.fichier)}>
                            <img src={`data:${certificat.fichierContentType};base64,${certificat.fichier}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {certificat.fichierContentType}, {byteSize(certificat.fichier)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {certificat.templateCertificat ? (
                      <Link to={`template-certificat/${certificat.templateCertificat.id}`}>{certificat.templateCertificat.nom}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{certificat.seance ? <Link to={`seance/${certificat.seance.id}`}>{certificat.seance.nom}</Link> : ''}</td>
                  <td>{certificat.sportif ? <Link to={`sportif/${certificat.sportif.id}`}>{certificat.sportif.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${certificat.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${certificat.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${certificat.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.certificat.home.notFound">No Certificats found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={certificatList && certificatList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ certificat }: IRootState) => ({
  certificatList: certificat.entities,
  loading: certificat.loading,
  totalItems: certificat.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Certificat);
