import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISeance, defaultValue } from 'app/shared/model/seance.model';

export const ACTION_TYPES = {
  FETCH_SEANCE_LIST: 'seance/FETCH_SEANCE_LIST',
  FETCH_SEANCE: 'seance/FETCH_SEANCE',
  CREATE_SEANCE: 'seance/CREATE_SEANCE',
  UPDATE_SEANCE: 'seance/UPDATE_SEANCE',
  PARTIAL_UPDATE_SEANCE: 'seance/PARTIAL_UPDATE_SEANCE',
  DELETE_SEANCE: 'seance/DELETE_SEANCE',
  RESET: 'seance/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISeance>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SeanceState = Readonly<typeof initialState>;

// Reducer

export default (state: SeanceState = initialState, action): SeanceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SEANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SEANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SEANCE):
    case REQUEST(ACTION_TYPES.UPDATE_SEANCE):
    case REQUEST(ACTION_TYPES.DELETE_SEANCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SEANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SEANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SEANCE):
    case FAILURE(ACTION_TYPES.CREATE_SEANCE):
    case FAILURE(ACTION_TYPES.UPDATE_SEANCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SEANCE):
    case FAILURE(ACTION_TYPES.DELETE_SEANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SEANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_SEANCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SEANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SEANCE):
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

const apiUrl = 'api/seances';

// Actions

export const getEntities: ICrudGetAllAction<ISeance> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SEANCE_LIST,
    payload: axios.get<ISeance>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISeance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SEANCE,
    payload: axios.get<ISeance>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISeance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SEANCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISeance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SEANCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISeance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SEANCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISeance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SEANCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
