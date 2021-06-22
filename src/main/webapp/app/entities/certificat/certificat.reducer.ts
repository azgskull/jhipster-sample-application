import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICertificat, defaultValue } from 'app/shared/model/certificat.model';

export const ACTION_TYPES = {
  FETCH_CERTIFICAT_LIST: 'certificat/FETCH_CERTIFICAT_LIST',
  FETCH_CERTIFICAT: 'certificat/FETCH_CERTIFICAT',
  CREATE_CERTIFICAT: 'certificat/CREATE_CERTIFICAT',
  UPDATE_CERTIFICAT: 'certificat/UPDATE_CERTIFICAT',
  PARTIAL_UPDATE_CERTIFICAT: 'certificat/PARTIAL_UPDATE_CERTIFICAT',
  DELETE_CERTIFICAT: 'certificat/DELETE_CERTIFICAT',
  SET_BLOB: 'certificat/SET_BLOB',
  RESET: 'certificat/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICertificat>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CertificatState = Readonly<typeof initialState>;

// Reducer

export default (state: CertificatState = initialState, action): CertificatState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CERTIFICAT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CERTIFICAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CERTIFICAT):
    case REQUEST(ACTION_TYPES.UPDATE_CERTIFICAT):
    case REQUEST(ACTION_TYPES.DELETE_CERTIFICAT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CERTIFICAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CERTIFICAT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CERTIFICAT):
    case FAILURE(ACTION_TYPES.CREATE_CERTIFICAT):
    case FAILURE(ACTION_TYPES.UPDATE_CERTIFICAT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CERTIFICAT):
    case FAILURE(ACTION_TYPES.DELETE_CERTIFICAT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CERTIFICAT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CERTIFICAT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CERTIFICAT):
    case SUCCESS(ACTION_TYPES.UPDATE_CERTIFICAT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CERTIFICAT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CERTIFICAT):
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

const apiUrl = 'api/certificats';

// Actions

export const getEntities: ICrudGetAllAction<ICertificat> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CERTIFICAT_LIST,
    payload: axios.get<ICertificat>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICertificat> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CERTIFICAT,
    payload: axios.get<ICertificat>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CERTIFICAT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CERTIFICAT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CERTIFICAT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICertificat> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CERTIFICAT,
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
