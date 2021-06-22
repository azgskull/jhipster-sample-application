import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDiscipline, defaultValue } from 'app/shared/model/discipline.model';

export const ACTION_TYPES = {
  FETCH_DISCIPLINE_LIST: 'discipline/FETCH_DISCIPLINE_LIST',
  FETCH_DISCIPLINE: 'discipline/FETCH_DISCIPLINE',
  CREATE_DISCIPLINE: 'discipline/CREATE_DISCIPLINE',
  UPDATE_DISCIPLINE: 'discipline/UPDATE_DISCIPLINE',
  PARTIAL_UPDATE_DISCIPLINE: 'discipline/PARTIAL_UPDATE_DISCIPLINE',
  DELETE_DISCIPLINE: 'discipline/DELETE_DISCIPLINE',
  RESET: 'discipline/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDiscipline>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type DisciplineState = Readonly<typeof initialState>;

// Reducer

export default (state: DisciplineState = initialState, action): DisciplineState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISCIPLINE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISCIPLINE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DISCIPLINE):
    case REQUEST(ACTION_TYPES.UPDATE_DISCIPLINE):
    case REQUEST(ACTION_TYPES.DELETE_DISCIPLINE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DISCIPLINE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DISCIPLINE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISCIPLINE):
    case FAILURE(ACTION_TYPES.CREATE_DISCIPLINE):
    case FAILURE(ACTION_TYPES.UPDATE_DISCIPLINE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DISCIPLINE):
    case FAILURE(ACTION_TYPES.DELETE_DISCIPLINE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISCIPLINE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISCIPLINE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISCIPLINE):
    case SUCCESS(ACTION_TYPES.UPDATE_DISCIPLINE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DISCIPLINE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISCIPLINE):
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

const apiUrl = 'api/disciplines';

// Actions

export const getEntities: ICrudGetAllAction<IDiscipline> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DISCIPLINE_LIST,
    payload: axios.get<IDiscipline>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IDiscipline> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISCIPLINE,
    payload: axios.get<IDiscipline>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDiscipline> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISCIPLINE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDiscipline> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISCIPLINE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDiscipline> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DISCIPLINE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDiscipline> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISCIPLINE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
