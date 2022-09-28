import { CREATE_COMMENT, DELETE_COMMENT, DELETE_REPLY, GET_COMMENTS, ICommentState, ICommentType, REPLY_COMMENT, UPDATE_COMMENT, UPDATE_REPLY } from '../types/commentTypes';

const initialState = {
  data: [],
  total: 1
};

const commentReducer = (
  state: ICommentState = initialState,
  action: ICommentType
): ICommentState => {
  switch (action.type) {
    case CREATE_COMMENT:
      return {
        ...state,
        data: [...state.data, action.payload],
        total: state.total + 1
      };

    case GET_COMMENTS:
      return action.payload;

    // case REPLY_COMMENT:
    //   return {
    //     ...state,
    //     data: state.data.map(item => (
    //       item.id === action.payload.commentRoot ? { 
    //         ...item, 
    //         replyCM: [
    //           action.payload,
    //           ...item.replyCM
    //         ]
    //       } : item
    //     ))
    //   }

    case UPDATE_COMMENT:
      return {
        ...state,
        data: state.data.map(item => (
          item.id === action.payload.id ? action.payload : item
        ))
      }

    // case UPDATE_REPLY:
    //   return {
    //     ...state,
    //     data: state.data.map(item => (
    //       item.id === action.payload.commentRoot ? {
    //         ...item,
    //         replyCM: item.replyCM?.map(rp => (
    //           rp.id === action.payload.id ? action.payload : rp
    //         ))
    //       } 
    //       : item
    //     ))
    //   }

    case DELETE_COMMENT:
      return {
        ...state,
        data: state.data.map(item => (
          item.id === action.payload.id ? action.payload : item
        ))
      }

    // case DELETE_REPLY:
    //   return {
    //     ...state,
    //     data: state.data.map(item => (
    //       item.id === action.payload.commentRoot
    //       ? {
    //         ...item,
    //         replyCM: item.replyCM?.filter(rp => (
    //           rp.id !== action.payload.id
    //         ))
    //       }
    //       : item
    //     ))
    //   }

    default:
      return state;
  }
}

export default commentReducer;
