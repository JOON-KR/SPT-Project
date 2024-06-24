import { useState, useEffect } from "react";
import axios from "axios";
import KakaoMap from "./KakaoMap";
import { formatDate, formatCombinedDateTime } from '../../../utils/dateUtils';
import "./Schedule.css";

export default function ScheduleCreate({ date, onClose }) {
  const [today, setToday] = useState(date ? date.toLocaleDateString() : "");
  const [searchValue, setSearchValue] = useState("");
  const [selectedPlace, setSelectedPlace] = useState(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [time, setTime] = useState("");
  const [isPopoverOpen, setIsPopoverOpen] = useState(false);

  const token = sessionStorage.getItem("token");

  useEffect(() => {
    if (date) {
      setToday(formatDate(date));
    }
  }, [date]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const combinedDateTime = formatCombinedDateTime(today, time);

    const scheduleData = {
      date: combinedDateTime,
      title: title,
      content: content,
      locationRequest: selectedPlace ? {
        name: selectedPlace.place_name,
        latitude: selectedPlace.y,
        longitude: selectedPlace.x
      } : null
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/schedule",
        scheduleData,
        {
          headers: {
            Authorization: `Bearer ${token}`, // 인증 헤더 추가
            'Content-Type': "application/json", // 콘텐츠 타입 설정
          },
        }
      );
      console.log("일정 등록 성공:", response.data);
      onClose();
    } catch (error) {
      console.error("일정 등록 실패:", error);
      console.log(scheduleData);
    }
  };

  const handleSearch = () => {
    setIsPopoverOpen(true);
  };

  const handleSelectPlace = (place) => {
    setSelectedPlace(place);
  };

  const handleConfirmPlace = () => {
    setIsPopoverOpen(false);
  };

  return (
    <div className="schedule-form">
      <h2>{today}의 일정 등록</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>일정 제목: </label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label>장소: </label>
          <div style={{ display: "flex", alignItems: "center" }}>
            <input
              type="text"
              value={searchValue}
              onChange={(e) => setSearchValue(e.target.value)}
              style={{ flex: 1 }}
              placeholder="장소를 입력하세요"
            />
            <button
              type="button"
              onClick={handleSearch}
              style={{ marginLeft: "8px" }}
            >
              🔍
            </button>
          </div>
          {selectedPlace && <p>선택된 장소: {selectedPlace.place_name}</p>}
        </div>
        <div>
          <label>일정 내용: </label>
          <input
            type="text"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            // required
          />
        </div>
        <div>
          <label>일정 시간: </label>
          <input
            type="time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
            required
          />
        </div>
        <button type="submit">저장</button>
      </form>

      {isPopoverOpen && (
        <KakaoMap
          initialSearchValue={searchValue}
          onSelect={handleSelectPlace}
          onConfirm={handleConfirmPlace}
          onClose={() => setIsPopoverOpen(false)}
        />
      )}
    </div>
  );
}