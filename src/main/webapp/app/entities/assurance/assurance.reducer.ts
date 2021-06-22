import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAssurance, defaultValue } from 'app/shared/model/assurance.model';

export const ACTION_TYPES = {
  FETCH_ASSURANCE_LIST: 'assurance/FETCH_ASSURANCE_LIST',
  FETCH_ASSURANCE: 'assurance/FETCH_ASSURANCE',
  CREATE_ASSURANCE: 'assurance/CREATE_ASSURANCE',
  UPDATE_ASSURANCE: 'assurance/UPDATE_ASSURANCE',
  PARTIAL_UPDATE_ASSURANCE: 'assurance/PARTIAL_UPDATE_ASSURANCE',
  DELETE_ASSURANCE: 'assurance/DELETE_ASSURANCE',
  RESET: 'assurance/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAssurance>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AssuranceState = Readonly<typeof initialState>;

// Reducer

export default (state: AssuranceState = initialState, action): AssuranceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ASSURANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ASSURANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ASSURANCE):
    case REQUEST(ACTION_TYPES.UPDATE_ASSURANCE):
    case REQUEST(ACTION_TYPES.DELETE_ASSURANCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ASSURANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ASSURANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ASSURANCE):
    case FAILURE(ACTION_TYPES.CREATE_ASSURANCE):
    case FAILURE(ACTION_TYPES.UPDATE_ASSURANCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ASSURANCE):
    case FAILURE(ACTION_TYPES.DELETE_ASSURANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSURANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSURANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ASSURANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_ASSURANCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ASSURANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ASSURANCE):
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

const apiUrl = 'api/assurances';

// Actions

export const getEntities: ICrudGetAllAction<IAssurance> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ASSURANCE_LIST,
    payload: axios.get<IAssurance>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAssurance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ASSURANCE,
    payload: axios.get<IAssurance>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAssurance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ASSURANCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAssurance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ASSURANCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAssurance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ASSURANCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAssurance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ASSURANCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
