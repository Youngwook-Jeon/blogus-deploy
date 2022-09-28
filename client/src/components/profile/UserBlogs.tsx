import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, useHistory } from 'react-router-dom';
import { getBlogsByUserId } from '../../redux/actions/blogAction';

import { IBlog, IParams, RootStore } from '../../utils/TypeScript';
import CardHoriz from '../cards/CardHoriz';
import Loading from '../global/Loading';
import Pagination from '../global/Pagination';

const UserBlogs = () => {
  const { blogsUser } = useSelector((state: RootStore) => state);
  const dispatch = useDispatch();
  const userId = useParams<IParams>().slug;

  const [blogs, setBlogs] = useState<IBlog[]>();
  const [total, setTotal] = useState(0);

  const history = useHistory();
  const { search } = history.location;

  useEffect(() => {
    if (!userId) return;

    if (blogsUser.every(item => item.id !== userId)) {
      dispatch(getBlogsByUserId(userId, search));
    } else {
      const data = blogsUser.find(item => item.id === userId);
      if (!data) return;

      setBlogs(data.blogs);
      setTotal(data.total);
      if (data.search) history.push(data.search);
    }
  }, [userId, blogsUser, dispatch, search, history]);

  const handlePagination = (num: number) => {
    const search = `?page=${num}`;
    dispatch(getBlogsByUserId(userId, search));
  }

  if (!blogs) return <Loading />;

  if (blogs.length === 0 && total < 1) return (
    <h3 className="text-center">블로그가 없습니다.</h3>
  );

  return (
    <div>
      <div>
        {
          blogs.map(blog => (
            <CardHoriz key={blog.id} blog={blog} />
          ))
        }
      </div>

      <div>
        <Pagination total={total} callback={handlePagination} />  
      </div>
    </div>
  );
};

export default UserBlogs;
