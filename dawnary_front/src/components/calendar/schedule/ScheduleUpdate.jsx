import { useState, useEffect } from "react";
import axios from "axios";
import KakaoMap from "./KakaoMap";

export default function ScheduleUpdate({ event, onClose }) {
  const [today, setToday] = useState(event ? event.date : "");
  const [searchValue, setSearchValue] = useState(event.locationResponse ? event.locationResponse.name : "");
  const [selectedPlace, setSelectedPlace] = useState(event.locationResponse ? { place_name: event.locationResponse.name, y: event.locationResponse.latitude, x: event.locationResponse.longitude } : null);
  const [title, setTitle] = useState(event.title);
  const [content, setContent] = useState(event.content);
  const [time, setTime] = useState(event.date ? new Date(event.date).toLocaleString().substring(11, 16) : "");
  const [isPopoverOpen, setIsPopoverOpen] = useState(false);

  const token = sessionStorage.getItem('token');

  useEffect(() => {
    if (event) {
      setToday(formatDate(new Date(event.date)));
    }
  }, [event]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const combinedDateTime = formatCombinedDateTime(today, time);
    const locationId = event ? event.locationResponse.id : null;

    const scheduleData = {
      date: combinedDateTime,
      title: title,
      content: content,
      locationRequest: selectedPlace ? {
        id: locationId,
        name: selectedPlace.place_name,
        latitude: selectedPlace.y,
        longitude: selectedPlace.x
      } : null
    };

    try {
      const response = await axios.put(
        `http://localhost:8080/schedule/${event.id}`,
        scheduleData, {
          headers: {
            Authorization: `Bearer ${token}`, // 인증 헤더 추가
            'Content-Type': "application/json", // 콘텐츠 타입 설정
          },
        }
      );
      console.log("일정 수정 성공:", response.data);
      onClose();
    } catch (error) {
      console.error("일정 수정 실패:", error);
      console.log(scheduleData)
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

  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
  };

  const formatCombinedDateTime = (date, time) => {
    return `${date}T${time}:00`;
  };

  return (
    <div>
      <h3>{today}의 일정 수정</h3>
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
        <button onClick={onClose}>취소</button>
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