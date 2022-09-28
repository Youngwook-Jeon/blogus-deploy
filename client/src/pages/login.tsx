import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootStore } from '../utils/TypeScript';
import LoginPass from '../components/auth/LoginPass';

const Login = () => {
  const history = useHistory();
  const { auth } = useSelector((state: RootStore) => state);

  useEffect(() => {
    if (auth.access_token) {
      let url = history.location.search.replace('?', '/');
      return history.push(url);
    }
  }, [auth.access_token, history]);

  return (
    <div className="auth_page">
      <div className="auth_box">
        <h3 className="text-uppercase text-center mb-4">로그인하기</h3>

        <LoginPass />
        <small className="row my-2 text-primary" style={{ cursor: 'pointer' }}>
          <span className="col-6">
            <Link to="/forgot_password" className="col-6">
              비밀번호가 기억 안 나시나요?
            </Link>
          </span>
        </small>

        <p>
          {`계정이 없으신가요? `}
          {/* <Link to={`/register${history.location.search}`} style={{ color: 'crimson' }}>
            가입하러 가기
          </Link> */}
          <Link to={`/register`} style={{ color: 'crimson' }}>
            가입하러 가기
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;