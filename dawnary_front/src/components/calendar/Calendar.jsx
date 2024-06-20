import { useEffect, useState } from "react";
import axios from "axios";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";

import ScheduleDetails from "./schedule/ScheduleDetails";
// import DiaryCreate from "./diary/DiaryCreate";
import "./Calendar.css";

export default function Calendar({ onDateClick }) {
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [events, setEvents] = useState([]);

  // 이벤트 클릭 시 팝업 표시
  const handleEventClick = (clickInfo) => {
    setSelectedEvent(clickInfo.event);
  };

  // 팝업 닫기
  const closePopup = () => {
    setSelectedEvent(null);
  };

  // 날짜 클릭 시 처리
  const handleDateClick = (dateClickInfo) => {
    onDateClick(dateClickInfo.date);
  };

  let eventsArr = [
    { title: "event 0", start: new Date("2024-05-15"), content: "TEST" },
    { title: "event 1", start: new Date("2024-06-15"), content: "TEST" },
    { title: "event 1-1", start: new Date("2024-06-15"), content: "TEST" },
    { title: "event 2", start: new Date("2024-06-16 14:00:00"), content: "TEST" },
    { title: "event 3", start: new Date("2024-06-18 15:00:00"), content: "TEST" },
  ];

  async function getEvents() {
    try {
      const response = await axios.get("http://localhost:8080/schedule");
      setEvents(response.data);
    } catch (error) {
      console.error("Error fetching events:", error);
    }
  }

  // 컴포넌트가 마운트될 때 이벤트 데이터를 가져옴
  useEffect(() => {
    getEvents();
  }, []);

  return (
    <>
      <h2>달력</h2>
      <div className="calendar-container">
        <FullCalendar
          locale="kr"
          plugins={[dayGridPlugin, interactionPlugin]}
          defaultView="dayGridMonth"
          events={eventsArr}
          headerToolbar={{
            left: "prev",
            center: "title",
            right: "today next",
          }}
          eventClick={handleEventClick}
          selectable={true}
          dateClick={handleDateClick}
        />
      </div>

      {/* 선택된 이벤트가 있을 때 팝업 표시 */}
      {selectedEvent && (
        <ScheduleDetails event={selectedEvent} onClose={closePopup} />
      )}
    </>
  );
}
