import { useState } from 'react';
import axios from 'axios';

export default function DiaryUpdate({ diary, onClose }) {
  const [title, setTitle] = useState(diary.title);
  const [content, setContent] = useState(diary.content);
  const [imagePath, setImagePath] = useState(diary.imagePath);

  const token = sessionStorage.getItem("token");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const updatedDiary = {
      title: title,
      content: content,
      imagePath: imagePath ? imagePath : diary.imagePath,
      weather: diary.weather,
      date: diary.date
    };

    try {
      const response = await axios.put(
        `http://localhost:8080/diary/${diary.id}`,
        updatedDiary,
        {
          headers: {
            Authorization: `Bearer ${token}`, // 인증 헤더 추가
          },
        }
      );
      console.log('Diary update success:', response.data);
      onClose();
    } catch (error) {
      console.error('Diary update failed:', error);
    }
  };

  const handleFileChange = async (e) => {
    e.preventDefault();

    const file = e.target.files[0];
    if (file && validateFileType(file)) {
      try {
        const formData = new FormData();
        formData.append("imageFile", file);
        const response = await axios.post(
          "http://localhost:8080/images",
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ${token}`, // 인증 헤더 추가
            },
          }
        );
        console.log("파일 업로드 성공:", response.data);
        setImagePath(response.data);
      } catch (error) {
        console.error("파일 등록 실패:", error);
      }
    } else {
      alert("허용된 파일 형식은 jpg, jpeg, gif, png 입니다.");
      e.target.value = ""; // 입력 필드 초기화
    }
  };

  const validateFileType = (file) => {
    const allowedExtensions = ["jpg", "jpeg", "gif", "png"];
    const fileExtension = file.name.split(".").pop().toLowerCase();
    return allowedExtensions.includes(fileExtension);
  };

  return (
    <div className="diary-update-form">
      <form onSubmit={handleSubmit}>
        <div>
          <label>제목</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label>내용</label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            rows="5"
            required
          />
        </div>
        <div>
          <label>사진 업로드</label>
          <input type="file" onChange={handleFileChange} accept="image/*" />
        </div>
        <button type="submit">저장</button>
        <button type="button" onClick={onClose}>취소</button>
      </form>
    </div>
  );
}