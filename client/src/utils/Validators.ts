import { IUserRegister, IBlog } from './TypeScript';

export const validRegister = (userRegister: IUserRegister) => {
  const { name, email, password, cfPassword } = userRegister;
  const errors: string[] = [];

  if (!name) {
    errors.push("이름을 입력하세요.");
  } else if (name.length > 20) {
    errors.push("이름은 20글자 이하여야 합니다.");
  }

  if (!email) {
    errors.push("이메일을 입력하세요.");
  } else if (!validateEmail(email)) {
    errors.push("이메일 형식이 올바르지 않습니다.");
  }

  const msg = checkPassword(password, cfPassword);
  if (msg) errors.push(msg);

  return {
    errMsg: errors,
    errLength: errors.length
  }
}

export function validPhone(phone: string) {
  const re = /^[+]/g;
  return re.test(phone);
}

export function validateEmail(email: string) {
  const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(String(email).toLowerCase());
}

export const checkPassword = (password: string, cfPassword: string) => {
  const passwordRules = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,50}$/;

  if (!passwordRules.test(password)) {
    return ("적어도 하나의 영문자, 숫자, 특수문자를 포함하여 8자리 이상으로 입력하세요.");
  } else if (password !== cfPassword) {
    return ("비밀번호가 일치하지 않습니다.");
  }
}

export const validateCreateBlog = ({ title, content, description, thumbnail, category }: IBlog) => {
  const err: string[] = [];
  if (title.trim().length < 2) {
    err.push("제목의 길이는 2자 이상, 50자 이하여야 합니다.");
  } else if (title.trim().length > 50) {
    err.push("제목의 길이는 2자 이상, 50자 이하여야 합니다.");
  }

  if (content.trim().length < 100) {
    err.push("내용은 100자 이상이어야 합니다.");
  } 

  if (description.trim().length < 10) {
    err.push("설명은 10자 이상, 200자 이하여야 합니다.");
  } else if (description.trim().length > 200) {
    err.push("설명은 10자 이상, 200자 이하여야 합니다.");
  }

  if (!thumbnail) {
    err.push("썸네일이 필요합니다.");
  }

  if (!category) {
    err.push("카테고리를 선택하세요.");
  }

  return {
    errMsg: err,
    errLength: err.length
  }
}

// Shallow equality
export const shallowEqual = (object1: any, object2: any) => {
  const keys1 = Object.keys(object1);
  const keys2 = Object.keys(object2);

  if (keys1.length !== keys2.length) {
    return false;
  }

  for (let key of keys1) {
    if (object1[key] !== object2[key]) {
      return false;
    }
  }

  return true;
}