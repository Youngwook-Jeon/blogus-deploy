import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { IParams } from '../../utils/TypeScript';
import { getAPI } from '../../utils/FetchData';
import { showErrMsg, showSuccessMsg } from '../../components/alert/Alert';

const Activate = () => {
  const { slug }: IParams = useParams();
  const [err, setErr] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    if (slug) {
      getAPI('auth/accountVerification/' + slug)
        .then(res => setSuccess(res.data.msg))
        .catch(err => setErr(err.response.data.msg));
    }
  }, [slug]);

  return (
    <div>
      { err && showErrMsg(err) }
      { success && showSuccessMsg(success) }
    </div>
  );
};

export default Activate;