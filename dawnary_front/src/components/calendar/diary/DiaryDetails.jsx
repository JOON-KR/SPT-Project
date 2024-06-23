import { useState, useEffect } from "react";
import axios from "axios";
import DiaryUpdate from "./DiaryUpdate";

export default function DiaryDetails({ diaryId, onClose }) {
  const [diary, setDiary] = useState(null);
  const [isEditing, setIsEditing] = useState(false);

  const token = sessionStorage.getItem("token");

  useEffect(() => {
    const fetchDiaryDetails = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/diary/${diaryId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setDiary(response.data);
      } catch (error) {
        console.error("Error fetching diary details:", error);
      }
    };

    fetchDiaryDetails();
  }, [diaryId, token]);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleEditClose = () => {
    setIsEditing(false);
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (confirmDelete) {
      try {
        await axios.delete(`http://localhost:8080/diary/${diary.id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log("일정 삭제 성공");
        onClose(); // 팝업 닫기
      } catch (error) {
        console.error("일정 삭제 실패:", error);
      }
    }
  };

  return (
    <div className="event-detail-popup">
      {diary ? (
        !isEditing ? (
          <>
            <h2>{diary.title}</h2>
            <p>날씨 : {diary.weather}</p>
            <p>내용 : {diary.content}</p>
            <p>기분 : {diary.sentiment}</p>
            <button onClick={onClose}>닫기</button>
            <button onClick={handleEditClick}>수정</button>
            <button onClick={handleDelete}>삭제</button>
          </>
        ) : (
          <DiaryUpdate event={diary} onClose={handleEditClose} />
        )
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}
