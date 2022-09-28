import React from 'react';
import { Link } from 'react-router-dom'; 
import { IUser } from '../../utils/TypeScript';

interface IProps {
  user: IUser;
  replyUser?: IUser;
}

const AvatarReply: React.FC<IProps> = ({ user, replyUser }) => {
  return (
    <div className="avatar_reply">
      <img src={user.avatar} alt="avatar"/>

      <div className="ms-1">
        <small>
          <Link to={`/profile/${user.id}`} style={{ textDecoration: 'none' }}>
            {user.name}
          </Link>
        </small>

        <small className="reply-text">
          Reply to <Link to={`/profile/${replyUser?.id}`}>
            {replyUser?.name}
          </Link>
        </small>
      </div>
    </div>
  );
};

export default AvatarReply;
