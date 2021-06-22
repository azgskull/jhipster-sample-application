import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVille, defaultValue } from 'app/shared/model/ville.model';

export const ACTION_TYPES = {
  FETCH_VILLE_LIST: 'ville/FETCH_VILLE_LIST',
  FETCH_VILLE: 'ville/FETCH_VILLE',
  CREATE_VILLE: 'ville/CREATE_VILLE',
  UPDATE_VILLE: 'ville/UPDATE_VILLE',
  PARTIAL_UPDATE_VILLE: 'ville/PARTIAL_UPDATE_VILLE',
  DELETE_VILLE: 'ville/DELETE_VILLE',
  RESET: 'ville/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVille>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type VilleState = Readonly<typeof initialState>;

// Reducer

export default (state: VilleState = initialState, action): VilleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VILLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VILLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VILLE):
    case REQUEST(ACTION_TYPES.UPDATE_VILLE):
    case REQUEST(ACTION_TYPES.DELETE_VILLE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_VILLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VILLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VILLE):
    case FAILURE(ACTION_TYPES.CREATE_VILLE):
    case FAILURE(ACTION_TYPES.UPDATE_VILLE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_VILLE):
    case FAILURE(ACTION_TYPES.DELETE_VILLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VILLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_VILLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VILLE):
    case SUCCESS(ACTION_TYPES.UPDATE_VILLE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_VILLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VILLE):
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

const apiUrl = 'api/villes';

// Actions

export const getEntities: ICrudGetAllAction<IVille> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VILLE_LIST,
    payload: axios.get<IVille>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IVille> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VILLE,
    payload: axios.get<IVille>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVille> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VILLE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVille> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VILLE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IVille> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_VILLE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVille> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VILLE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
