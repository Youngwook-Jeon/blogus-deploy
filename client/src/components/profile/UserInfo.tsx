import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootStore, InputChange, IUserInfo, FormSubmit } from '../../utils/TypeScript';
import NotFound from '../global/NotFound';
import { resetPassword, updateUser } from '../../redux/actions/profileAction';

const UserInfo = () => {
  const initState = {
    name: '', email: '', avatar: '', password: '', cfPassword: ''
  };

  const { auth } = useSelector((state: RootStore) => state);
  const dispatch = useDispatch();
  const [user, setUser] = useState<IUserInfo>(initState);
  const [typePass, setTypePass] = useState(false);
  const [typeCfPass, setTypeCfPass] = useState(false);

  const handleChangeInput = (e: InputChange) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleChangeFile = (e: InputChange) => {
    const target = e.target as HTMLInputElement;
    const files = target.files;
    
    if (files) {
      const file = files[0];
      setUser({ ...user, avatar: file })
    }
  }

  const handleSubmit = (e: FormSubmit) => {
    e.preventDefault();
    if (avatar || name) {
      dispatch(updateUser((avatar as File), name, auth));
    }

    if (password && auth.access_token) {
      dispatch(resetPassword(password, cfPassword, auth.access_token));
    }
  }

  const { name, avatar, password, cfPassword } = user;

  if (!auth.user) return <NotFound />

  return (
    <form className="profile_info" onSubmit={handleSubmit}>
      <div className="info_avatar">
        <img src={avatar ? URL.createObjectURL(avatar as File) : auth.user.avatar} alt="avatar" />

        <span>
          <i className="fas fa-camera" />
          <p>프로필 이미지 바꾸기</p>
          <input type="file" accept="image/*" name="file" id="file_up" onChange={handleChangeFile} />
        </span>
      </div>

      <div className="form-group my-3">
        <label htmlFor="name">이름</label>
        <input 
          type="text" 
          className="form-control" 
          id="name" 
          name="name" 
          defaultValue={auth.user.name} 
          onChange={handleChangeInput}
        />
      </div>

      <div className="form-group my-3">
        <label htmlFor="account">이메일</label>
        <input 
          type="text" 
          className="form-control" 
          id="email" 
          name="email" 
          defaultValue={auth.user.email} 
          onChange={handleChangeInput}
          disabled={true}
        />
      </div>

      {/* {
        auth.user.type !== 'register' && 
        <small className="text-danger">
          * Quick login account with {auth.user.type} cannot use this function.
        </small>
      } */}

      <div className="form-group my-3">
        <label htmlFor="password">비밀번호</label>
        <div className="pass">
          <input 
            type={typePass ? "text" : "password"}
            className="form-control" 
            id="password" 
            name="password" 
            value={password} 
            onChange={handleChangeInput}
            // disabled={auth.user.type !== 'register'}
          />

          <small onClick={() => setTypePass(!typePass)}>
            { typePass ? 'Hide' : 'Show' }
          </small>
        </div>
      </div>

      <div className="form-group my-3">
        <label htmlFor="cf_password">비밀번호 확인</label>
        <div className="pass">
          <input 
            type={typeCfPass ? "text" : "password"}
            className="form-control" 
            id="cfPassword" 
            name="cfPassword" 
            value={cfPassword} 
            onChange={handleChangeInput}
            // disabled={auth.user.type !== 'register'}
          />

          <small onClick={() => setTypeCfPass(!typeCfPass)}>
            { typeCfPass ? 'Hide' : 'Show' }
          </small>
        </div>     
      </div>

      <button className="btn btn-dark w-100" type="submit">
        업데이트
      </button>
    </form>
  );
};

export default UserInfo;
