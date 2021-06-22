import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITypeIdentite, defaultValue } from 'app/shared/model/type-identite.model';

export const ACTION_TYPES = {
  FETCH_TYPEIDENTITE_LIST: 'typeIdentite/FETCH_TYPEIDENTITE_LIST',
  FETCH_TYPEIDENTITE: 'typeIdentite/FETCH_TYPEIDENTITE',
  CREATE_TYPEIDENTITE: 'typeIdentite/CREATE_TYPEIDENTITE',
  UPDATE_TYPEIDENTITE: 'typeIdentite/UPDATE_TYPEIDENTITE',
  PARTIAL_UPDATE_TYPEIDENTITE: 'typeIdentite/PARTIAL_UPDATE_TYPEIDENTITE',
  DELETE_TYPEIDENTITE: 'typeIdentite/DELETE_TYPEIDENTITE',
  SET_BLOB: 'typeIdentite/SET_BLOB',
  RESET: 'typeIdentite/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITypeIdentite>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TypeIdentiteState = Readonly<typeof initialState>;

// Reducer

export default (state: TypeIdentiteState = initialState, action): TypeIdentiteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TYPEIDENTITE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TYPEIDENTITE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TYPEIDENTITE):
    case REQUEST(ACTION_TYPES.UPDATE_TYPEIDENTITE):
    case REQUEST(ACTION_TYPES.DELETE_TYPEIDENTITE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TYPEIDENTITE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TYPEIDENTITE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TYPEIDENTITE):
    case FAILURE(ACTION_TYPES.CREATE_TYPEIDENTITE):
    case FAILURE(ACTION_TYPES.UPDATE_TYPEIDENTITE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TYPEIDENTITE):
    case FAILURE(ACTION_TYPES.DELETE_TYPEIDENTITE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEIDENTITE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPEIDENTITE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TYPEIDENTITE):
    case SUCCESS(ACTION_TYPES.UPDATE_TYPEIDENTITE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TYPEIDENTITE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TYPEIDENTITE):
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

const apiUrl = 'api/type-identites';

// Actions

export const getEntities: ICrudGetAllAction<ITypeIdentite> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TYPEIDENTITE_LIST,
    payload: axios.get<ITypeIdentite>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITypeIdentite> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TYPEIDENTITE,
    payload: axios.get<ITypeIdentite>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITypeIdentite> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TYPEIDENTITE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITypeIdentite> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TYPEIDENTITE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITypeIdentite> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TYPEIDENTITE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITypeIdentite> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TYPEIDENTITE,
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
