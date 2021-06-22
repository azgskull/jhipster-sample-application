import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/pays">
      <Translate contentKey="global.menu.entities.pays" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/ville">
      <Translate contentKey="global.menu.entities.ville" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/structure">
      <Translate contentKey="global.menu.entities.structure" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/salle">
      <Translate contentKey="global.menu.entities.salle" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/type-seance">
      <Translate contentKey="global.menu.entities.typeSeance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/seance">
      <Translate contentKey="global.menu.entities.seance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/seance-programme">
      <Translate contentKey="global.menu.entities.seanceProgramme" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sportif">
      <Translate contentKey="global.menu.entities.sportif" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employe">
      <Translate contentKey="global.menu.entities.employe" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/type-identite">
      <Translate contentKey="global.menu.entities.typeIdentite" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/adhesion">
      <Translate contentKey="global.menu.entities.adhesion" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/organisme-assurance">
      <Translate contentKey="global.menu.entities.organismeAssurance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/assurance">
      <Translate contentKey="global.menu.entities.assurance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/echeance">
      <Translate contentKey="global.menu.entities.echeance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/paiement-status">
      <Translate contentKey="global.menu.entities.paiementStatus" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/paiement">
      <Translate contentKey="global.menu.entities.paiement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/mode-paiement">
      <Translate contentKey="global.menu.entities.modePaiement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/presence">
      <Translate contentKey="global.menu.entities.presence" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/type-certificat">
      <Translate contentKey="global.menu.entities.typeCertificat" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/certificat">
      <Translate contentKey="global.menu.entities.certificat" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/template-certificat">
      <Translate contentKey="global.menu.entities.templateCertificat" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/role">
      <Translate contentKey="global.menu.entities.role" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/discipline">
      <Translate contentKey="global.menu.entities.discipline" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
