import React, { useState, useEffect, useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { IBlog, RootStore, IUser, IComment } from '../../utils/TypeScript';
import Input from '../comments/Input';
import Comments from '../comments/Comments';
import { createComment, getComments } from '../../redux/actions/commentAction';
import Loading from '../global/Loading';

interface IProps {
  blog: IBlog;
}

const DisplayBlog: React.FC<IProps> = ({ blog }) => {
  const { auth, comments } = useSelector((state: RootStore) => state);
  // const { auth } = useSelector((state: RootStore) => state);
  const dispatch = useDispatch();

  const [allComments, setAllComments] = useState<IComment[]>([]);
  const [loading, setLoading] = useState(false);
  const rootComments = allComments.filter((comment) => comment.parentId == null);

  const getReplies = (commentId: string) => {
    return allComments.filter((c) => c.parentId === commentId);
  }

  const handleComment = (body: string, parentId?: string) => {
    if (!auth.user || !auth.access_token) return;

    const data = {
      content: body,
      user: auth.user,
      parentId: parentId,
      blogUserId: (blog.user as IUser).id,
      blogId: (blog.id as string),
      createdAt: new Date().toISOString()
    }
    
    setAllComments([...allComments, data]);
    dispatch(createComment(data, auth.access_token));
  };

  useEffect(() => {
    setAllComments(comments.data);
  }, [comments.data]);

  const fetchComments = useCallback(async (id: string) => {
    setLoading(true);
    await dispatch(getComments(id));
    setLoading(false);
  }, [dispatch]);

  useEffect(() => {
    if (!blog.id) return;
    fetchComments(blog.id);
  }, [blog.id, fetchComments]);

  return (
    <div>
      <h2 className="text-center my-3 text-capitalize fs-1" style={{ color: "#ff7a00" }}>
        {blog.title}
      </h2>

      <div className="text-end fst-italic" style={{ color: "teal" }}>
        <small>
          {
            typeof(blog.user) !== 'string' && 
            `작성자: ${blog.user.name}`
          }
        </small>

        <small className="ms-2">
          { new Date(blog.createdAt).toLocaleString() }
        </small>
      </div>

      <div dangerouslySetInnerHTML={{
        __html: blog.content
      }} />

      <hr className="my-1" />
      <h3 style={{ color: "#ff7a00" }}>댓글</h3>
      {
        auth.user 
        ? <Input callback={handleComment} />
        : <h5>
          댓글을 다시려면 <Link to={`/login?blog/${blog.id}`}>로그인</Link>이 필요합니다.
        </h5>
      }

      {
        loading
        ? <Loading />
        : rootComments.map((rootComment) => (
            <Comments 
              key={rootComment.id} 
              comment={rootComment} 
              replies={getReplies((rootComment.id as string))} 
              handleComment={handleComment}
            />
          ))
      }
    </div>
  );
};

export default DisplayBlog;
