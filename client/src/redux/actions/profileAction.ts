import { Dispatch } from 'redux';
import { AUTH, IAuth, IAuthType } from '../types/authTypes';
import { IAlertType, ALERT } from '../types/alertTypes';
import { checkImage, imageUpload } from '../../utils/ImageUpload';
import { patchAPI, getAPI } from '../../utils/FetchData';
import { checkPassword } from '../../utils/Validators';
import { GET_OTHER_INFO, IGetOtherInfoType } from '../types/profileTypes';
import { checkTokenExp } from '../../utils/CheckTokenExp';

export const updateUser = (avatar: File, name: string, auth: IAuth) => async (dispatch: Dispatch<IAlertType | IAuthType>) => {
  if (!auth.access_token || !auth.user) return;

  const result = await checkTokenExp(auth.access_token, dispatch);
  const access_token = result ? result : auth.access_token;

  let url = '';
  try {
    dispatch({ type: ALERT, payload: { loading: true }});

    if (avatar) {
      const check = checkImage(avatar);
      if (check) {
        return dispatch({ type: ALERT, payload: { errors: check }});
      }

      const photo = await imageUpload(avatar);
      url = photo.url;
    }

    dispatch({
      type: AUTH,
      payload: {
        access_token: access_token,
        user: {
          ...auth.user,
          avatar: url ? url : auth.user.avatar, 
          name: name ? name : auth.user.name
        }
      }
    });

    const res = await patchAPI('users/update_profile', { 
      avatar: url ? url : auth.user.avatar, name: name ? name : auth.user.name 
    }, access_token);

    dispatch({ type: ALERT, payload: { success: res.data.msg }});
  } catch (err: any) {
    dispatch({ type: ALERT, payload: { errors: err.response.data.msg }});
  }
}

export const resetPassword = (password: string, cfPassword: string, token: string) => async (dispatch: Dispatch<IAlertType | IAuthType>) => {
  const result = await checkTokenExp(token, dispatch);
  const access_token = result ? result : token;

  const msg = checkPassword(password, cfPassword);
  if (msg) return dispatch({ type: ALERT, payload: { errors: msg }});
  try {
    dispatch({ type: ALERT, payload: { loading: true }});  

    const res = await patchAPI('users/reset_password', { password }, access_token);
    dispatch({ type: ALERT, payload: { success: res.data.msg }});  
  } catch (err: any) {
    dispatch({ type: ALERT, payload: { errors: err.response.data.msg }});
  }
}

export const getOtherInfo = (id: string) => async (dispatch: Dispatch<IAlertType | IGetOtherInfoType>) => {
  try {
    dispatch({ type: ALERT, payload: { loading: true }});  

    const res = await getAPI(`users/${id}`);
    
    dispatch({
      type: GET_OTHER_INFO,
      payload: res.data
    })

    dispatch({ type: ALERT, payload: {  }});  
  } catch (err: any) {
    dispatch({ type: ALERT, payload: { errors: err.response.data.msg }});
  }
}
