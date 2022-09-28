import * as types from '../types/categoryTypes';
import { ICategory } from '../../utils/TypeScript';

const categoryReducer = (state: ICategory[] = [], action: types.ICategoryType): ICategory[] => {
  switch (action.type) {
    case types.CREATE_CATEGORY:
      return [action.payload, ...state];

    case types.GET_CATEGORIES:
      return action.payload;
    
    case types.UPDATE_CATEGORY:
      return state.map(item => (
        item.id === action.payload.id ? { ...item, name: action.payload.name } : item
      ));

    case types.DELETE_CATEGORY:
      return state.filter(item => (item.id !== action.payload));

    default:
      return state;
  }
}

export default categoryReducer;