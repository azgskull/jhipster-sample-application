import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStructure, defaultValue } from 'app/shared/model/structure.model';

export const ACTION_TYPES = {
  FETCH_STRUCTURE_LIST: 'structure/FETCH_STRUCTURE_LIST',
  FETCH_STRUCTURE: 'structure/FETCH_STRUCTURE',
  CREATE_STRUCTURE: 'structure/CREATE_STRUCTURE',
  UPDATE_STRUCTURE: 'structure/UPDATE_STRUCTURE',
  PARTIAL_UPDATE_STRUCTURE: 'structure/PARTIAL_UPDATE_STRUCTURE',
  DELETE_STRUCTURE: 'structure/DELETE_STRUCTURE',
  SET_BLOB: 'structure/SET_BLOB',
  RESET: 'structure/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStructure>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type StructureState = Readonly<typeof initialState>;

// Reducer

export default (state: StructureState = initialState, action): StructureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STRUCTURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STRUCTURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_STRUCTURE):
    case REQUEST(ACTION_TYPES.UPDATE_STRUCTURE):
    case REQUEST(ACTION_TYPES.DELETE_STRUCTURE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_STRUCTURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_STRUCTURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STRUCTURE):
    case FAILURE(ACTION_TYPES.CREATE_STRUCTURE):
    case FAILURE(ACTION_TYPES.UPDATE_STRUCTURE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_STRUCTURE):
    case FAILURE(ACTION_TYPES.DELETE_STRUCTURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_STRUCTURE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_STRUCTURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_STRUCTURE):
    case SUCCESS(ACTION_TYPES.UPDATE_STRUCTURE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_STRUCTURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_STRUCTURE):
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

const apiUrl = 'api/structures';

// Actions

export const getEntities: ICrudGetAllAction<IStructure> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_STRUCTURE_LIST,
    payload: axios.get<IStructure>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IStructure> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STRUCTURE,
    payload: axios.get<IStructure>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IStructure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STRUCTURE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStructure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STRUCTURE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IStructure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_STRUCTURE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStructure> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STRUCTURE,
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
