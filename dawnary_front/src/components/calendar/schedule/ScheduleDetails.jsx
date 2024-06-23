import { useState, useEffect } from 'react';
import axios from 'axios';
import ScheduleUpdate from './ScheduleUpdate';
import { formatDateTime } from '../../../utils/dateUtils';

const ScheduleDetails = ({ eventId, onClose }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [event, setEvent] = useState(null);

  const token = sessionStorage.getItem('token');
  
  useEffect(() => {
    const fetchEventDetails = async () => {
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
    };

    fetchEventDetails();
  }, [eventId, token]);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleEditClose = () => {
    setIsEditing(false);
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
          <h3>{event?.title}</h3>
          <p>{event && formatDateTime(event.date)}</p>
          {event?.locationResponse && <p>{event.locationResponse.name}</p>}
          <p>{event?.content}</p>
          <button onClick={onClose}>닫기</button>
          <button onClick={handleEditClick}>수정</button>
          <button onClick={handleDelete}>삭제</button>
        </>
      ) : (
        <ScheduleUpdate event={event} onClose={handleEditClose} />
      )}
    </div>
  );
};

export default ScheduleDetails;