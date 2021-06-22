import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPresence, defaultValue } from 'app/shared/model/presence.model';

export const ACTION_TYPES = {
  FETCH_PRESENCE_LIST: 'presence/FETCH_PRESENCE_LIST',
  FETCH_PRESENCE: 'presence/FETCH_PRESENCE',
  CREATE_PRESENCE: 'presence/CREATE_PRESENCE',
  UPDATE_PRESENCE: 'presence/UPDATE_PRESENCE',
  PARTIAL_UPDATE_PRESENCE: 'presence/PARTIAL_UPDATE_PRESENCE',
  DELETE_PRESENCE: 'presence/DELETE_PRESENCE',
  RESET: 'presence/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPresence>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PresenceState = Readonly<typeof initialState>;

// Reducer

export default (state: PresenceState = initialState, action): PresenceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRESENCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRESENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRESENCE):
    case REQUEST(ACTION_TYPES.UPDATE_PRESENCE):
    case REQUEST(ACTION_TYPES.DELETE_PRESENCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PRESENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRESENCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRESENCE):
    case FAILURE(ACTION_TYPES.CREATE_PRESENCE):
    case FAILURE(ACTION_TYPES.UPDATE_PRESENCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PRESENCE):
    case FAILURE(ACTION_TYPES.DELETE_PRESENCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESENCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESENCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRESENCE):
    case SUCCESS(ACTION_TYPES.UPDATE_PRESENCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PRESENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRESENCE):
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

const apiUrl = 'api/presences';

// Actions

export const getEntities: ICrudGetAllAction<IPresence> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRESENCE_LIST,
    payload: axios.get<IPresence>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPresence> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRESENCE,
    payload: axios.get<IPresence>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPresence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRESENCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPresence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRESENCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPresence> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PRESENCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPresence> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRESENCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
