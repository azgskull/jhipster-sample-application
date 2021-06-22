import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmploye, defaultValue } from 'app/shared/model/employe.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYE_LIST: 'employe/FETCH_EMPLOYE_LIST',
  FETCH_EMPLOYE: 'employe/FETCH_EMPLOYE',
  CREATE_EMPLOYE: 'employe/CREATE_EMPLOYE',
  UPDATE_EMPLOYE: 'employe/UPDATE_EMPLOYE',
  PARTIAL_UPDATE_EMPLOYE: 'employe/PARTIAL_UPDATE_EMPLOYE',
  DELETE_EMPLOYE: 'employe/DELETE_EMPLOYE',
  RESET: 'employe/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmploye>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type EmployeState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeState = initialState, action): EmployeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYE):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYE):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EMPLOYE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYE):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYE):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EMPLOYE):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYE):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EMPLOYE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYE):
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

const apiUrl = 'api/employes';

// Actions

export const getEntities: ICrudGetAllAction<IEmploye> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYE_LIST,
    payload: axios.get<IEmploye>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IEmploye> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYE,
    payload: axios.get<IEmploye>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmploye> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmploye> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEmploye> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EMPLOYE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmploye> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
