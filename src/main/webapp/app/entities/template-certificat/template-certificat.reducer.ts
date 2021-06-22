import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITemplateCertificat, defaultValue } from 'app/shared/model/template-certificat.model';

export const ACTION_TYPES = {
  FETCH_TEMPLATECERTIFICAT_LIST: 'templateCertificat/FETCH_TEMPLATECERTIFICAT_LIST',
  FETCH_TEMPLATECERTIFICAT: 'templateCertificat/FETCH_TEMPLATECERTIFICAT',
  CREATE_TEMPLATECERTIFICAT: 'templateCertificat/CREATE_TEMPLATECERTIFICAT',
  UPDATE_TEMPLATECERTIFICAT: 'templateCertificat/UPDATE_TEMPLATECERTIFICAT',
  PARTIAL_UPDATE_TEMPLATECERTIFICAT: 'templateCertificat/PARTIAL_UPDATE_TEMPLATECERTIFICAT',
  DELETE_TEMPLATECERTIFICAT: 'templateCertificat/DELETE_TEMPLATECERTIFICAT',
  SET_BLOB: 'templateCertificat/SET_BLOB',
  RESET: 'templateCertificat/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITemplateCertificat>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TemplateCertificatState = Readonly<typeof initialState>;

// Reducer

export default (state: TemplateCertificatState = initialState, action): TemplateCertificatState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATECERTIFICAT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATECERTIFICAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TEMPLATECERTIFICAT):
    case REQUEST(ACTION_TYPES.UPDATE_TEMPLATECERTIFICAT):
    case REQUEST(ACTION_TYPES.DELETE_TEMPLATECERTIFICAT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TEMPLATECERTIFICAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATECERTIFICAT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATECERTIFICAT):
    case FAILURE(ACTION_TYPES.CREATE_TEMPLATECERTIFICAT):
    case FAILURE(ACTION_TYPES.UPDATE_TEMPLATECERTIFICAT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TEMPLATECERTIFICAT):
    case FAILURE(ACTION_TYPES.DELETE_TEMPLATECERTIFICAT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATECERTIFICAT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATECERTIFICAT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TEMPLATECERTIFICAT):
    case SUCCESS(ACTION_TYPES.UPDATE_TEMPLATECERTIFICAT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TEMPLATECERTIFICAT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TEMPLATECERTIFICAT):
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

const apiUrl = 'api/template-certificats';

// Actions

export const getEntities: ICrudGetAllAction<ITemplateCertificat> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATECERTIFICAT_LIST,
    payload: axios.get<ITemplateCertificat>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITemplateCertificat> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATECERTIFICAT,
    payload: axios.get<ITemplateCertificat>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITemplateCertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TEMPLATECERTIFICAT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITemplateCertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TEMPLATECERTIFICAT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITemplateCertificat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TEMPLATECERTIFICAT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITemplateCertificat> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TEMPLATECERTIFICAT,
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
