import { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import ScheduleUpdate from './ScheduleUpdate';
import { formatDateTime } from '../../../utils/dateUtils';
import './Schedule.css'; 

const ScheduleDetails = ({ eventId, onClose }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [event, setEvent] = useState(null);

  const token = sessionStorage.getItem('token');

  const fetchEventDetails = useCallback(async () => {
    try {
      const response = await axios.get(`http://localhost:8080/schedule/${eventId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setEvent(response.data);
    } catch (error) {
      console.error('Error fetching event details:', error);
    }
  }, [eventId, token]);

  useEffect(() => {
    fetchEventDetails();
  }, [fetchEventDetails]);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleEditClose = () => {
    setIsEditing(false);
    fetchEventDetails(); // 업데이트 후 일정 상세 정보 다시 가져오기
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm('정말 삭제하시겠습니까?');
    if (confirmDelete) {
      try {
        await axios.delete(`http://localhost:8080/schedule/${eventId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log('일정 삭제 성공');
        onClose(); // 팝업 닫기
      } catch (error) {
        console.error('일정 삭제 실패:', error);
      }
    }
  };

  return (
    <div className="event-detail-popup">
      {!isEditing ? (
        <>
          <h2>{event?.title}</h2>
          <p>{event && formatDateTime(event.date)}</p>
          {event?.locationResponse && <p>장소 : {event.locationResponse.name}</p>}
          <p>{event?.content}</p>
          <div className="button-container">
            <button onClick={onClose}>닫기</button>
            <button onClick={handleEditClick}>수정</button>
            <button onClick={handleDelete}>삭제</button>
          </div>
        </>
      ) : (
        <ScheduleUpdate event={event} onClose={handleEditClose} />
      )}
    </div>
  );
};

export default ScheduleDetails;