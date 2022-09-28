import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { getOtherInfo } from '../../redux/actions/profileAction';
import { RootStore, IUser } from '../../utils/TypeScript';
import Loading from '../global/Loading';

interface IProps {
  id: string;
}

const OtherInfo: React.FC<IProps> = ({ id }) => {
  const [other, setOther] = useState<IUser>();
  const { otherInfo } = useSelector((state: RootStore) => state);
  const dispatch = useDispatch();
  
  useEffect(() => {
    if (!id) return;

    if (otherInfo.every(user => user.id != id)) {
      dispatch(getOtherInfo(id));
    } else {
      const newUser = otherInfo.find(user => user.id == id);
      if (newUser) setOther(newUser);
    }

  }, [id, otherInfo, dispatch]);

  if (!other) return <Loading />;
  return (
    <div className="profile_info text-center rounded">
      <div className="info_avatar">
        <img src={other.avatar} alt="avatar" />
      </div>

      <h5 className="text-uppercase text-danger">
        {other.role}
      </h5>

      <div>
        이름: <span className="text-info">{other.name}</span>
      </div>

      <div>
        이메일
      </div>
      <span className="text-info">{other.email}</span>

      <div>
        가입일: <span style={{ color: "#ffc107" }}>{ new Date(other.createdAt).toLocaleString() }</span>
      </div>
    </div>
  );
};

export default OtherInfo;
