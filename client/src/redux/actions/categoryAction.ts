import { Dispatch } from 'redux';
import { ALERT, IAlertType} from '../types/alertTypes';
import * as CategoryType from '../types/categoryTypes';
import { postAPI, getAPI, patchAPI, deleteAPI } from '../../utils/FetchData';
import { ICategoryType, CREATE_CATEGORY, GET_CATEGORIES, UPDATE_CATEGORY, DELETE_CATEGORY } from '../types/categoryTypes';
import { ICategory } from '../../utils/TypeScript';
import { checkTokenExp } from '../../utils/CheckTokenExp';

export const createCategory = (name: string, token: string) => async (dispatch: Dispatch<IAlertType | ICategoryType>) => {
  const result = await checkTokenExp(token, dispatch);
  const access_token = result ? result : token;
  try {
    dispatch({ type: ALERT, payload: { loading: true }});
    const res = await postAPI('categories', { name }, access_token);
    dispatch({
      type: CREATE_CATEGORY,
      payload: res.data
    });
    dispatch({ type: ALERT, payload: { loading: false }});
  } catch (err: any) {
    dispatch({ type: ALERT, payload: { errors: err.response.data.msg }});
  }
}

export const getCategories = () => async (dispatch: Dispatch<IAlertType | CategoryType.ICategoryType>) => {
  try {
    dispatch({ type: ALERT, payload: { loading: true }});

    const res = await getAPI('categories');
    
    dispatch({
      type: GET_CATEGORIES,
      payload: res.data
    });

    dispatch({ type: ALERT, payload: { loading: false }});
  } catch (err: any) {
    dispatch({ type: ALERT, payload: { errors: err.response.data.msg }});
  }
}

export const updateCategory = (data: ICategory, token: string) => async (dispatch: Dispatch<IAlertType | ICategoryType>) => {
  const result = await checkTokenExp(token, dispatch);
  const access_token = result ? result : token;
  try {
    dispatch({
      type: UPDATE_CATEGORY,
      payload: data
    });
    await patchAPI(`categories/${data.id}`, { name: data.name }, access_token);
    
  } catch (err: any) {
    dispatch({ type: ALERT, payload: { errors: err.response.data.msg }});
  }
}

export const deleteCategory = (id: string, token: string) => async (dispatch: Dispatch<IAlertType | ICategoryType>) => {
  const result = await checkTokenExp(token, dispatch);
  const access_token = result ? result : token;
  try {
    dispatch({
      type: DELETE_CATEGORY,
      payload: id
    });
    await deleteAPI(`categories/${id}`, access_token);
    
  } catch (err: any) {
    console.log(err.response);
    dispatch({ type: ALERT, payload: { errors: err.response.data.msg }});
  }
}
