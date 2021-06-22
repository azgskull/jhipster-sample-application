import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IModePaiement, defaultValue } from 'app/shared/model/mode-paiement.model';

export const ACTION_TYPES = {
  FETCH_MODEPAIEMENT_LIST: 'modePaiement/FETCH_MODEPAIEMENT_LIST',
  FETCH_MODEPAIEMENT: 'modePaiement/FETCH_MODEPAIEMENT',
  CREATE_MODEPAIEMENT: 'modePaiement/CREATE_MODEPAIEMENT',
  UPDATE_MODEPAIEMENT: 'modePaiement/UPDATE_MODEPAIEMENT',
  PARTIAL_UPDATE_MODEPAIEMENT: 'modePaiement/PARTIAL_UPDATE_MODEPAIEMENT',
  DELETE_MODEPAIEMENT: 'modePaiement/DELETE_MODEPAIEMENT',
  RESET: 'modePaiement/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IModePaiement>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ModePaiementState = Readonly<typeof initialState>;

// Reducer

export default (state: ModePaiementState = initialState, action): ModePaiementState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MODEPAIEMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MODEPAIEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MODEPAIEMENT):
    case REQUEST(ACTION_TYPES.UPDATE_MODEPAIEMENT):
    case REQUEST(ACTION_TYPES.DELETE_MODEPAIEMENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MODEPAIEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MODEPAIEMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MODEPAIEMENT):
    case FAILURE(ACTION_TYPES.CREATE_MODEPAIEMENT):
    case FAILURE(ACTION_TYPES.UPDATE_MODEPAIEMENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MODEPAIEMENT):
    case FAILURE(ACTION_TYPES.DELETE_MODEPAIEMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MODEPAIEMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MODEPAIEMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MODEPAIEMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_MODEPAIEMENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MODEPAIEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MODEPAIEMENT):
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

const apiUrl = 'api/mode-paiements';

// Actions

export const getEntities: ICrudGetAllAction<IModePaiement> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MODEPAIEMENT_LIST,
    payload: axios.get<IModePaiement>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IModePaiement> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MODEPAIEMENT,
    payload: axios.get<IModePaiement>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IModePaiement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MODEPAIEMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IModePaiement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MODEPAIEMENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IModePaiement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MODEPAIEMENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IModePaiement> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MODEPAIEMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
