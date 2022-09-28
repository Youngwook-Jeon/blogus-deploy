import axios from 'axios';

const SERVER_URL = 'http://localhost:8080';
const CLIENT_URL = 'http://localhost:3000';
const AUTH_PREFIX = 'Bearer ';

// const instance = axios.create ({
//   headers: {
//       'Access-Control-Allow-Origin': 'http://localhost:8080'	// 서버 domain
//   },
//   baseURL: 'http://localhost:8080', 
//   withCredentials: true
// });

export const postAPI = async (url: string, post: object, token?: string) => {
  // const res = await axios.post(SERVER_URL + `/api/${url}`, post, {
  //   headers: { Authorization: token ? AUTH_PREFIX + token : token }
  // });

  const res = await axios.post(SERVER_URL + `/api/${url}`, post, {
    headers: { 
      Authorization: token ? AUTH_PREFIX + token : token,
      'Access-Control-Allow-Origin': CLIENT_URL,
    },
    // baseURL: SERVER_URL, 
    withCredentials: true,
  });

  return res;
};

export const getAPI = async (url: string, token?: string) => {
  // const res = await axios.get(SERVER_URL + `/api/${url}`, {
  //   headers: { Authorization: token ? AUTH_PREFIX + token : token }
  // });
  const res = await axios.get(SERVER_URL + `/api/${url}`, {
    headers: { 
      Authorization: token ? AUTH_PREFIX + token : token,
      'Access-Control-Allow-Origin': CLIENT_URL,
    },
    // baseURL: SERVER_URL, 
    withCredentials: true,
  });

  return res;
};

export const patchAPI = async (url: string, post: object, token?: string) => {
  const res = await axios.patch(SERVER_URL + `/api/${url}`, post, {
    headers: { 
      Authorization: token ? AUTH_PREFIX + token : token,
      'Access-Control-Allow-Origin': CLIENT_URL,
    },
    // baseURL: SERVER_URL, 
    withCredentials: true,
  });

  return res;
};

export const deleteAPI = async (url: string, token?: string) => {
  // const res = await axios.delete(SERVER_URL + `/api/${url}`, {
  //   headers: { Authorization: token ? AUTH_PREFIX + token : token }
  // });
  const res = await axios.delete(SERVER_URL + `/api/${url}`, {
    headers: { 
      Authorization: token ? AUTH_PREFIX + token : token,
      'Access-Control-Allow-Origin': CLIENT_URL,
    },
    // baseURL: SERVER_URL, 
    withCredentials: true,
  });

  return res;
};

export const putAPI = async (url: string, post: object, token?: string) => {
  // const res = await axios.put(SERVER_URL + `/api/${url}`, post, {
  //   headers: { Authorization: token ? AUTH_PREFIX + token : token }
  // });
  const res = await axios.put(SERVER_URL + `/api/${url}`, post, {
    headers: { 
      Authorization: token ? AUTH_PREFIX + token : token,
      'Access-Control-Allow-Origin': CLIENT_URL,
    },
    // baseURL: SERVER_URL, 
    withCredentials: true,
  });

  return res;
};