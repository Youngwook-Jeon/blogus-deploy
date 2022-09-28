import { ChangeEvent, FormEvent } from 'react';
import rootReducer from '../redux/reducers/index';

export type InputChange = ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>

export type FormSubmit = FormEvent<HTMLFormElement>

export type RootStore = ReturnType<typeof rootReducer>

export interface IParams {
  page: string;
  slug: string;
}

export interface IUserLogin {
  email: string;
  password: string;
}

export interface IUserRegister extends IUserLogin {
  name: string;
  cfPassword: string;
}

export interface IUser extends IUserLogin {
  avatar: string;
  createdAt: string;
  name: string;
  role: string;
  type: string;
  updatedAt: string;
  id: string
}

export interface IUserInfo extends IUserRegister {
  avatar: string | File;
}

export interface IAlert {
  loading?: boolean;
  success?: string | string[];
  errors?: string | string[];
}

export interface ICategory {
  id: string;
  name: string;
  createdAt: string;
  updatedAt: string;
}

export interface IBlog {
  id?: string;
  user: string | IUser;
  title: string;
  content: string;
  description: string;
  thumbnail: string | File;
  category: string;
  createdAt: string;
}

export interface IComment {
  id?: string;
  user: IUser;
  blogId: string;
  blogUserId: string;
  parentId?: string;
  content: string;
  createdAt: string;
  updatedAt?: string;
  isDeleted?: boolean;
}
