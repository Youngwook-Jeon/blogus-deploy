import React from 'react';
import { Link } from 'react-router-dom';
import RegisterForm from '../components/auth/RegisterForm';

// TODO: Remember user's search keyword
const Register = () => {
  // const history = useHistory();

  return (
    <div className="auth_page">
      <div className="auth_box">
        <h3 className="text-uppercase text-center mb-4">가입하기</h3>
        <RegisterForm />

        <p className="mt-2">
          {`이미 계정이 있으신가요? `}
          {/* <Link to={`/login${history.location.search}`} style={{ color: 'crimson' }}>
            Login Now
          </Link> */}
          <Link to={`/login`} style={{ color: 'crimson' }}>
            지금 로그인하기
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Register;
