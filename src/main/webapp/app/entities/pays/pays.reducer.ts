import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPays, defaultValue } from 'app/shared/model/pays.model';

export const ACTION_TYPES = {
  FETCH_PAYS_LIST: 'pays/FETCH_PAYS_LIST',
  FETCH_PAYS: 'pays/FETCH_PAYS',
  CREATE_PAYS: 'pays/CREATE_PAYS',
  UPDATE_PAYS: 'pays/UPDATE_PAYS',
  PARTIAL_UPDATE_PAYS: 'pays/PARTIAL_UPDATE_PAYS',
  DELETE_PAYS: 'pays/DELETE_PAYS',
  SET_BLOB: 'pays/SET_BLOB',
  RESET: 'pays/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPays>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PaysState = Readonly<typeof initialState>;

// Reducer

export default (state: PaysState = initialState, action): PaysState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAYS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYS):
    case REQUEST(ACTION_TYPES.UPDATE_PAYS):
    case REQUEST(ACTION_TYPES.DELETE_PAYS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAYS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAYS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYS):
    case FAILURE(ACTION_TYPES.CREATE_PAYS):
    case FAILURE(ACTION_TYPES.UPDATE_PAYS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAYS):
    case FAILURE(ACTION_TYPES.DELETE_PAYS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYS):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAYS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/pays';

// Actions

export const getEntities: ICrudGetAllAction<IPays> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PAYS_LIST,
    payload: axios.get<IPays>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPays> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYS,
    payload: axios.get<IPays>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPays> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPays> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPays> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAYS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPays> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
