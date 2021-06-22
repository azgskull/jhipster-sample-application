import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISportif, defaultValue } from 'app/shared/model/sportif.model';

export const ACTION_TYPES = {
  FETCH_SPORTIF_LIST: 'sportif/FETCH_SPORTIF_LIST',
  FETCH_SPORTIF: 'sportif/FETCH_SPORTIF',
  CREATE_SPORTIF: 'sportif/CREATE_SPORTIF',
  UPDATE_SPORTIF: 'sportif/UPDATE_SPORTIF',
  PARTIAL_UPDATE_SPORTIF: 'sportif/PARTIAL_UPDATE_SPORTIF',
  DELETE_SPORTIF: 'sportif/DELETE_SPORTIF',
  RESET: 'sportif/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISportif>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SportifState = Readonly<typeof initialState>;

// Reducer

export default (state: SportifState = initialState, action): SportifState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SPORTIF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SPORTIF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SPORTIF):
    case REQUEST(ACTION_TYPES.UPDATE_SPORTIF):
    case REQUEST(ACTION_TYPES.DELETE_SPORTIF):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SPORTIF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SPORTIF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SPORTIF):
    case FAILURE(ACTION_TYPES.CREATE_SPORTIF):
    case FAILURE(ACTION_TYPES.UPDATE_SPORTIF):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SPORTIF):
    case FAILURE(ACTION_TYPES.DELETE_SPORTIF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SPORTIF_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SPORTIF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SPORTIF):
    case SUCCESS(ACTION_TYPES.UPDATE_SPORTIF):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SPORTIF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SPORTIF):
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

const apiUrl = 'api/sportifs';

// Actions

export const getEntities: ICrudGetAllAction<ISportif> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SPORTIF_LIST,
    payload: axios.get<ISportif>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISportif> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SPORTIF,
    payload: axios.get<ISportif>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISportif> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SPORTIF,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISportif> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SPORTIF,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISportif> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SPORTIF,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISportif> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SPORTIF,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
