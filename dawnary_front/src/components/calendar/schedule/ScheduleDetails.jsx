import { useState } from 'react'
import ScheduleUpdate from './ScheduleUpdate';

const ScheduleDetails = ({ event, onClose }) => {
  const [isEditing, setIsEditing] = useState(false);
  
  const formatDateTime = (dateStr) => {
    const date = new Date(dateStr);
    const options = {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "numeric",
      minute: "2-digit",
      hour12: true,
    };
    return date.toLocaleString("ko-KR", options);
  };

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleEditClose = () => {
    setIsEditing(false);
  };

  return (
    <div className="event-detail-popup">
      {!isEditing ? (
        <>
          <h3>{event.title}</h3>
          <p>{formatDateTime(event.startStr)}</p>
          <p>{event.extendedProps.location}</p>
          <p>{event.extendedProps.content}</p>
          <button onClick={onClose}>닫기</button>
          <button onClick={handleEditClick}>수정</button>
        </>
      ) : (
        <ScheduleUpdate event={event} onClose={handleEditClose} />
      )}
    </div>
  );
};

export default ScheduleDetails;
