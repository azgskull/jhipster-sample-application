import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITypeCertificat, defaultValue } from 'app/shared/model/type-certificat.model';

export const ACTION_TYPES = {
  FETCH_TYPECERTIFICAT_LIST: 'typeCertificat/FETCH_TYPECERTIFICAT_LIST',
  FETCH_TYPECERTIFICAT: 'typeCertificat/FETCH_TYPECERTIFICAT',
  CREATE_TYPECERTIFICAT: 'typeCertificat/CREATE_TYPECERTIFICAT',
  UPDATE_TYPECERTIFICAT: 'typeCertificat/UPDATE_TYPECERTIFICAT',
  PARTIAL_UPDATE_TYPECERTIFICAT: 'typeCertificat/PARTIAL_UPDATE_TYPECERTIFICAT',
  DELETE_TYPECERTIFICAT: 'typeCertificat/DELETE_TYPECERTIFICAT',
  RESET: 'typeCertificat/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITypeCertificat>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TypeCertificatState = Readonly<typeof initialState>;

// Reducer

export default (state: TypeCertificatState = initialState, action): TypeCertificatState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TYPECERTIFICAT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TYPECERTIFICAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TYPECERTIFICAT):
    case REQUEST(ACTION_TYPES.UPDATE_TYPECERTIFICAT):
    case REQUEST(ACTION_TYPES.DELETE_TYPECERTIFICAT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TYPECERTIFICAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TYPECERTIFICAT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TYPECERTIFICAT):
    case FAILURE(ACTION_TYPES.CREATE_TYPECERTIFICAT):
    case FAILURE(ACTION_TYPES.UPDATE_TYPECERTIFICAT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TYPECERTIFICAT):
    case FAILURE(ACTION_TYPES.DELETE_TYPECERTIFICAT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPECERTIFICAT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPECERTIFICAT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TYPECERTIFICAT):
    case SUCCESS(ACTION_TYPES.UPDATE_TYPECERTIFICAT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TYPECERTIFICAT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TYPECERTIFICAT):
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

const apiUrl = 'api/type-certificats';

// Actions

export const getEntities: ICrudGetAllAction<ITypeCertificat> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TYPECERTIFICAT_LIST,
    payload: axios.get<ITypeCertificat>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITypeCertificat> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TYPECERTIFICAT,
    payload: axios.get<ITypeCertificat>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITypeCertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TYPECERTIFICAT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITypeCertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TYPECERTIFICAT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITypeCertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TYPECERTIFICAT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITypeCertificat> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TYPECERTIFICAT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
