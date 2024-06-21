import { useState } from 'react';
import axios from 'axios';
import DiaryUpdate from './DiaryUpdate';

export default function DiaryDetails({ event, onClose }) {
  const [isEditing, setIsEditing] = useState(false);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleEditClose = () => {
    setIsEditing(false);
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/diary/${event.id}`);
      console.log('일정 삭제 성공');
      onClose(); // 팝업 닫기
    } catch (error) {
      console.error('일정 삭제 실패:', error);
    }
  };

  return (
    <div className="event-detail-popup">
      {!isEditing ? (
        <>
          <h2>{event.title}</h2>
          <p>날씨 : {event.extendedProps.weather}</p>
          <p>내용 : {event.extendedProps.content}</p>
          <p>기분 : {event.extendedProps.sentiment}</p>
          <button onClick={onClose}>닫기</button>
          <button onClick={handleEditClick}>수정</button>
          <button onClick={handleDelete}>삭제</button>
        </>
      ) : (
        <DiaryUpdate event={event} onClose={handleEditClose} />
      )}
    </div>
  );
}