import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEcheance, defaultValue } from 'app/shared/model/echeance.model';

export const ACTION_TYPES = {
  FETCH_ECHEANCE_LIST: 'echeance/FETCH_ECHEANCE_LIST',
  FETCH_ECHEANCE: 'echeance/FETCH_ECHEANCE',
  CREATE_ECHEANCE: 'echeance/CREATE_ECHEANCE',
  UPDATE_ECHEANCE: 'echeance/UPDATE_ECHEANCE',
  PARTIAL_UPDATE_ECHEANCE: 'echeance/PARTIAL_UPDATE_ECHEANCE',
  DELETE_ECHEANCE: 'echeance/DELETE_ECHEANCE',
  RESET: 'echeance/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEcheance>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EcheanceState = Readonly<typeof initialState>;

// Reducer

export default (state: EcheanceState = initialState, action): EcheanceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ECHEANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ECHEANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ECHEANCE):
    case REQUEST(ACTION_TYPES.UPDATE_ECHEANCE):
    case REQUEST(ACTION_TYPES.DELETE_ECHEANCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ECHEANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ECHEANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ECHEANCE):
    case FAILURE(ACTION_TYPES.CREATE_ECHEANCE):
    case FAILURE(ACTION_TYPES.UPDATE_ECHEANCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ECHEANCE):
    case FAILURE(ACTION_TYPES.DELETE_ECHEANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ECHEANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ECHEANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ECHEANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_ECHEANCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ECHEANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ECHEANCE):
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

const apiUrl = 'api/echeances';

// Actions

export const getEntities: ICrudGetAllAction<IEcheance> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ECHEANCE_LIST,
    payload: axios.get<IEcheance>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEcheance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ECHEANCE,
    payload: axios.get<IEcheance>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEcheance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ECHEANCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEcheance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ECHEANCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEcheance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ECHEANCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEcheance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ECHEANCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
