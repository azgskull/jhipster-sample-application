import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITypeSeance, defaultValue } from 'app/shared/model/type-seance.model';

export const ACTION_TYPES = {
  FETCH_TYPESEANCE_LIST: 'typeSeance/FETCH_TYPESEANCE_LIST',
  FETCH_TYPESEANCE: 'typeSeance/FETCH_TYPESEANCE',
  CREATE_TYPESEANCE: 'typeSeance/CREATE_TYPESEANCE',
  UPDATE_TYPESEANCE: 'typeSeance/UPDATE_TYPESEANCE',
  PARTIAL_UPDATE_TYPESEANCE: 'typeSeance/PARTIAL_UPDATE_TYPESEANCE',
  DELETE_TYPESEANCE: 'typeSeance/DELETE_TYPESEANCE',
  RESET: 'typeSeance/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITypeSeance>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TypeSeanceState = Readonly<typeof initialState>;

// Reducer

export default (state: TypeSeanceState = initialState, action): TypeSeanceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TYPESEANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TYPESEANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TYPESEANCE):
    case REQUEST(ACTION_TYPES.UPDATE_TYPESEANCE):
    case REQUEST(ACTION_TYPES.DELETE_TYPESEANCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TYPESEANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TYPESEANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TYPESEANCE):
    case FAILURE(ACTION_TYPES.CREATE_TYPESEANCE):
    case FAILURE(ACTION_TYPES.UPDATE_TYPESEANCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TYPESEANCE):
    case FAILURE(ACTION_TYPES.DELETE_TYPESEANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPESEANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPESEANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TYPESEANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_TYPESEANCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TYPESEANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TYPESEANCE):
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

const apiUrl = 'api/type-seances';

// Actions

export const getEntities: ICrudGetAllAction<ITypeSeance> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TYPESEANCE_LIST,
    payload: axios.get<ITypeSeance>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITypeSeance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TYPESEANCE,
    payload: axios.get<ITypeSeance>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITypeSeance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TYPESEANCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITypeSeance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TYPESEANCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITypeSeance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TYPESEANCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITypeSeance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TYPESEANCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
