import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPaiementStatus, defaultValue } from 'app/shared/model/paiement-status.model';

export const ACTION_TYPES = {
  FETCH_PAIEMENTSTATUS_LIST: 'paiementStatus/FETCH_PAIEMENTSTATUS_LIST',
  FETCH_PAIEMENTSTATUS: 'paiementStatus/FETCH_PAIEMENTSTATUS',
  CREATE_PAIEMENTSTATUS: 'paiementStatus/CREATE_PAIEMENTSTATUS',
  UPDATE_PAIEMENTSTATUS: 'paiementStatus/UPDATE_PAIEMENTSTATUS',
  PARTIAL_UPDATE_PAIEMENTSTATUS: 'paiementStatus/PARTIAL_UPDATE_PAIEMENTSTATUS',
  DELETE_PAIEMENTSTATUS: 'paiementStatus/DELETE_PAIEMENTSTATUS',
  RESET: 'paiementStatus/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPaiementStatus>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PaiementStatusState = Readonly<typeof initialState>;

// Reducer

export default (state: PaiementStatusState = initialState, action): PaiementStatusState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAIEMENTSTATUS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAIEMENTSTATUS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAIEMENTSTATUS):
    case REQUEST(ACTION_TYPES.UPDATE_PAIEMENTSTATUS):
    case REQUEST(ACTION_TYPES.DELETE_PAIEMENTSTATUS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAIEMENTSTATUS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAIEMENTSTATUS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAIEMENTSTATUS):
    case FAILURE(ACTION_TYPES.CREATE_PAIEMENTSTATUS):
    case FAILURE(ACTION_TYPES.UPDATE_PAIEMENTSTATUS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAIEMENTSTATUS):
    case FAILURE(ACTION_TYPES.DELETE_PAIEMENTSTATUS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAIEMENTSTATUS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAIEMENTSTATUS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAIEMENTSTATUS):
    case SUCCESS(ACTION_TYPES.UPDATE_PAIEMENTSTATUS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAIEMENTSTATUS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAIEMENTSTATUS):
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

const apiUrl = 'api/paiement-statuses';

// Actions

export const getEntities: ICrudGetAllAction<IPaiementStatus> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PAIEMENTSTATUS_LIST,
    payload: axios.get<IPaiementStatus>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPaiementStatus> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAIEMENTSTATUS,
    payload: axios.get<IPaiementStatus>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPaiementStatus> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAIEMENTSTATUS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPaiementStatus> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAIEMENTSTATUS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPaiementStatus> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAIEMENTSTATUS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPaiementStatus> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAIEMENTSTATUS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
