import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { InputChange, FormSubmit } from '../../utils/TypeScript';
import { register } from '../../redux/actions/authAction';

const RegisterForm = () => {
  const initialState = { name: '', email: '', password: '', cfPassword: '' };
  const [userRegister, setUserRegister] = useState(initialState);
  const { name, email, password, cfPassword } = userRegister;

  const [typePass, setTypePass] = useState(false);
  const [typeCfPass, setTypeCfPass] = useState(false);

  const dispatch = useDispatch();

  const handleChangeInput = (e: InputChange) => {
    const { value, name } = e.target;
    setUserRegister({ ...userRegister, [name]: value });
  };

  const handleSubmit = (e: FormSubmit) => {
    e.preventDefault();
    dispatch(register(userRegister))
  }

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group mb-3">
        <label htmlFor="name" className="form-label">이름</label>
        <input 
          type="text" 
          className="form-control" 
          id="name" 
          name="name" 
          value={name} 
          onChange={handleChangeInput}
          placeholder="이름을 입력하세요."
        />
      </div>

      <div className="form-group mb-3">
        <label htmlFor="email" className="form-label">이메일</label>
        <input 
          type="text" 
          className="form-control" 
          id="email" 
          name="email" 
          value={email}
          onChange={handleChangeInput}
          placeholder="이메일 주소를 입력하세요."
        />
      </div>

      <div className="form-group mb-3">
        <label htmlFor="password" className="form-label">비밀번호</label>
        <div className="pass">
          <input 
            type={typePass ? "text" : "password"}
            className="form-control" 
            id="password" 
            name="password" 
            value={password} 
            onChange={handleChangeInput}
            placeholder="적어도 하나의 영문자, 숫자, 특수문자를 포함하여 8자리 이상으로 입력하세요."
          />

          <small onClick={() => setTypePass(!typePass)}>
            {typePass ? 'Hide' : 'Show'}
          </small>
        </div>
      </div>

      <div className="form-group mb-3">
        <label htmlFor="password" className="form-label">비밀번호 확인</label>
        <div className="pass">
          <input 
            type={typeCfPass ? "text" : "password"}
            className="form-control" 
            id="cfPassword" 
            name="cfPassword" 
            value={cfPassword} 
            onChange={handleChangeInput}
            placeholder="비밀번호를 다시 입력하세요."
          />

          <small onClick={() => setTypeCfPass(!typeCfPass)}>
            {typeCfPass ? 'Hide' : 'Show'}
          </small>
        </div>
      </div>

      <button type="submit" className="btn btn-dark w-100 my-1">
        가입
      </button>
    </form>
  );
};

export default RegisterForm;