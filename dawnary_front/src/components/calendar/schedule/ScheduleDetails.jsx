const ScheduleDetails = ({ event, onClose }) => {
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

  return (
    <div className="event-detail-popup">
      <h3>{event.title}</h3>
      <p>{event.extendedProps.content}</p>
      <p>{formatDateTime(event.startStr)}</p>
      <button onClick={onClose}>닫기</button>
    </div>
  );
};

export default ScheduleDetails;
