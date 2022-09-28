import React, { useState, useRef, useEffect } from 'react';
import { IComment } from '../../utils/TypeScript';
import LiteQuill from '../editor/LiteQuill';

interface IProps {
  callback: (body: string, parentId?: string) => void;
  edit?: IComment;
  setEdit?: (edit?: IComment) => void;
  parentId?: string;
}

const Input: React.FC<IProps> = ({ callback, edit, setEdit, parentId}) => {
  const [body, setBody] = useState('');
  const divRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (edit) setBody(edit.content);
  }, [edit]);

  const handleSubmit = () => {
    const div = divRef.current;
    const text = (div?.innerText as string)
    if (!text.trim()) {
      if (setEdit) return setEdit(undefined);
      return;
    }

    callback(body, parentId);
    setBody('');
  }

  return (
    <div>
      <LiteQuill body={body} setBody={setBody} />

      <div ref={divRef} dangerouslySetInnerHTML={{
        __html: body
      }} style={{ display: 'none' }} />

      <button className="btn btn-dark ms-auto d-block px-4 mt-2" onClick={handleSubmit}>
        { edit ? 'Update' : 'Send' }
      </button>
    </div>
  );
};

export default Input;
