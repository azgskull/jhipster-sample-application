import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISalle, defaultValue } from 'app/shared/model/salle.model';

export const ACTION_TYPES = {
  FETCH_SALLE_LIST: 'salle/FETCH_SALLE_LIST',
  FETCH_SALLE: 'salle/FETCH_SALLE',
  CREATE_SALLE: 'salle/CREATE_SALLE',
  UPDATE_SALLE: 'salle/UPDATE_SALLE',
  PARTIAL_UPDATE_SALLE: 'salle/PARTIAL_UPDATE_SALLE',
  DELETE_SALLE: 'salle/DELETE_SALLE',
  SET_BLOB: 'salle/SET_BLOB',
  RESET: 'salle/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISalle>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SalleState = Readonly<typeof initialState>;

// Reducer

export default (state: SalleState = initialState, action): SalleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SALLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SALLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SALLE):
    case REQUEST(ACTION_TYPES.UPDATE_SALLE):
    case REQUEST(ACTION_TYPES.DELETE_SALLE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SALLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SALLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SALLE):
    case FAILURE(ACTION_TYPES.CREATE_SALLE):
    case FAILURE(ACTION_TYPES.UPDATE_SALLE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SALLE):
    case FAILURE(ACTION_TYPES.DELETE_SALLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SALLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SALLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SALLE):
    case SUCCESS(ACTION_TYPES.UPDATE_SALLE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SALLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SALLE):
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

const apiUrl = 'api/salles';

// Actions

export const getEntities: ICrudGetAllAction<ISalle> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SALLE_LIST,
    payload: axios.get<ISalle>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISalle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SALLE,
    payload: axios.get<ISalle>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISalle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SALLE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISalle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SALLE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISalle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SALLE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISalle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SALLE,
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
