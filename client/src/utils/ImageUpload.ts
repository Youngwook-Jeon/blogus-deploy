export const checkImage = (file: File) => {
  const types = ['image/png', 'image/jpeg'];
  let err = '';
  if (!file) return err = "파일이 존재하지 않습니다.";

  if (file.size > 2 * 1024 * 1024) // 2MB
    err = "이미지 파일의 크기는 최대 2MB이어야 합니다.";
  
  if (!types.includes(file.type))
    err = "파일 포맷이 png / jpeg 이어야 합니다.";
  
  return err;
}

export const imageUpload = async (file: File) => {
  const formData = new FormData();
  formData.append("file", file);
  formData.append("upload_preset", "dstnawnf");
  formData.append("cloud_name", "dw6i0vp1r");

  const res = await fetch("https://api.cloudinary.com/v1_1/dw6i0vp1r/upload", {
    method: "POST",
    body: formData
  });
  const data = await res.json();
  return { public_id: data.public_id, url: data.secure_url };
}