import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdhesion, defaultValue } from 'app/shared/model/adhesion.model';

export const ACTION_TYPES = {
  FETCH_ADHESION_LIST: 'adhesion/FETCH_ADHESION_LIST',
  FETCH_ADHESION: 'adhesion/FETCH_ADHESION',
  CREATE_ADHESION: 'adhesion/CREATE_ADHESION',
  UPDATE_ADHESION: 'adhesion/UPDATE_ADHESION',
  PARTIAL_UPDATE_ADHESION: 'adhesion/PARTIAL_UPDATE_ADHESION',
  DELETE_ADHESION: 'adhesion/DELETE_ADHESION',
  RESET: 'adhesion/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdhesion>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AdhesionState = Readonly<typeof initialState>;

// Reducer

export default (state: AdhesionState = initialState, action): AdhesionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADHESION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADHESION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADHESION):
    case REQUEST(ACTION_TYPES.UPDATE_ADHESION):
    case REQUEST(ACTION_TYPES.DELETE_ADHESION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADHESION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADHESION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADHESION):
    case FAILURE(ACTION_TYPES.CREATE_ADHESION):
    case FAILURE(ACTION_TYPES.UPDATE_ADHESION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADHESION):
    case FAILURE(ACTION_TYPES.DELETE_ADHESION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADHESION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADHESION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADHESION):
    case SUCCESS(ACTION_TYPES.UPDATE_ADHESION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADHESION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADHESION):
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

const apiUrl = 'api/adhesions';

// Actions

export const getEntities: ICrudGetAllAction<IAdhesion> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ADHESION_LIST,
    payload: axios.get<IAdhesion>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAdhesion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADHESION,
    payload: axios.get<IAdhesion>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdhesion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADHESION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdhesion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADHESION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdhesion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADHESION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdhesion> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADHESION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
