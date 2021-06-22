import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISeanceProgramme, defaultValue } from 'app/shared/model/seance-programme.model';

export const ACTION_TYPES = {
  FETCH_SEANCEPROGRAMME_LIST: 'seanceProgramme/FETCH_SEANCEPROGRAMME_LIST',
  FETCH_SEANCEPROGRAMME: 'seanceProgramme/FETCH_SEANCEPROGRAMME',
  CREATE_SEANCEPROGRAMME: 'seanceProgramme/CREATE_SEANCEPROGRAMME',
  UPDATE_SEANCEPROGRAMME: 'seanceProgramme/UPDATE_SEANCEPROGRAMME',
  PARTIAL_UPDATE_SEANCEPROGRAMME: 'seanceProgramme/PARTIAL_UPDATE_SEANCEPROGRAMME',
  DELETE_SEANCEPROGRAMME: 'seanceProgramme/DELETE_SEANCEPROGRAMME',
  RESET: 'seanceProgramme/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISeanceProgramme>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SeanceProgrammeState = Readonly<typeof initialState>;

// Reducer

export default (state: SeanceProgrammeState = initialState, action): SeanceProgrammeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SEANCEPROGRAMME_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SEANCEPROGRAMME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SEANCEPROGRAMME):
    case REQUEST(ACTION_TYPES.UPDATE_SEANCEPROGRAMME):
    case REQUEST(ACTION_TYPES.DELETE_SEANCEPROGRAMME):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SEANCEPROGRAMME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SEANCEPROGRAMME_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SEANCEPROGRAMME):
    case FAILURE(ACTION_TYPES.CREATE_SEANCEPROGRAMME):
    case FAILURE(ACTION_TYPES.UPDATE_SEANCEPROGRAMME):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SEANCEPROGRAMME):
    case FAILURE(ACTION_TYPES.DELETE_SEANCEPROGRAMME):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEANCEPROGRAMME_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEANCEPROGRAMME):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SEANCEPROGRAMME):
    case SUCCESS(ACTION_TYPES.UPDATE_SEANCEPROGRAMME):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SEANCEPROGRAMME):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SEANCEPROGRAMME):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/seance-programmes';

// Actions

export const getEntities: ICrudGetAllAction<ISeanceProgramme> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SEANCEPROGRAMME_LIST,
    payload: axios.get<ISeanceProgramme>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISeanceProgramme> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SEANCEPROGRAMME,
    payload: axios.get<ISeanceProgramme>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISeanceProgramme> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SEANCEPROGRAMME,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISeanceProgramme> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SEANCEPROGRAMME,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISeanceProgramme> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SEANCEPROGRAMME,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISeanceProgramme> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SEANCEPROGRAMME,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
