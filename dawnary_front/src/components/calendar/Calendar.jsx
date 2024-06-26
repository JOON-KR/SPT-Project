import { useEffect, useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";

import ScheduleDetails from "./schedule/ScheduleDetails";
import DiaryDetails from "./diary/DiaryDetails";
import "./Calendar.css";

import axios from "@/utils/axiosInstance";

export default function Calendar({ onDateClick }) {
  const [selectedEventId, setSelectedEventId] = useState(null);
  const [selectedDiaryId, setSelectedDiaryId] = useState(null);
  const [events, setEvents] = useState([]);
  const navigate = useNavigate();

  const memberId = JSON.parse(sessionStorage.getItem("loginUser")).id;

  // 이벤트 클릭 시 팝업 표시
  const handleEventClick = (clickInfo) => {
    if (clickInfo.event.allDay) {
      setSelectedDiaryId(clickInfo.event.id);
    } else {
      setSelectedEventId(clickInfo.event.id);
    }
  };

  // 팝업 닫기
  const closePopup = () => {
    setSelectedEventId(null);
    setSelectedDiaryId(null);
    fetchEventsAndDiaries(); // 팝업 닫을 때 리스트 새로 고침
  };

  // 날짜 클릭 시 처리
  const handleDateClick = (dateClickInfo) => {
    onDateClick(dateClickInfo.date);
  };

  const fetchEventsAndDiaries = useCallback(async () => {
    try {
      const [eventsResponse, diaryResponse] = await Promise.all([
        axios.get("http://localhost:8080/schedule"),
        axios.get(`http://localhost:8080/diary/member/${memberId}`),
      ]);

      const combinedEvents = [
        ...eventsResponse.data.map(event => ({
          ...event,
          color: 'red'
        })),
        ...diaryResponse.data.map(diary => ({
          ...diary,
          allDay: true, // 다이어리 항목은 allDay로 설정
          color: 'orange'
        }))
      ];

      setEvents(combinedEvents);
    } catch (error) {
      console.error("Error fetching events and diaries:", error);
    }
  }, [memberId]);

  // 컴포넌트가 마운트될 때 이벤트 데이터를 가져옴
  useEffect(() => {
    fetchEventsAndDiaries();
  }, [fetchEventsAndDiaries]);

  const goToMyPage = () => {
    navigate("/mypage");
  };
  const goToSearch = () => {
    navigate("/searchResult");
  }
  const goToSeries = () => {
    navigate("/seriesResult");
  }

  return (
    <>
      <div className="calendar-header">
        <button onClick={goToSearch} className="mypage-button">검색</button>
        <button onClick={goToSeries} className="mypage-button">시리즈조회</button>
        <button onClick={goToMyPage} className="mypage-button">마이페이지</button>
      </div>
      <h2>달력</h2>
      <div className="calendar-container">
        <FullCalendar
          locale="kr"
          plugins={[dayGridPlugin, interactionPlugin]}
          defaultView="dayGridMonth"
          events={events}
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
      {selectedEventId && (
        <ScheduleDetails eventId={selectedEventId} onClose={closePopup} />
      )}
      {selectedDiaryId && (
        <DiaryDetails diaryId={selectedDiaryId} onClose={closePopup} />
      )}
    </>
  );
}