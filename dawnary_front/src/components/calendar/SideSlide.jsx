import { useState } from "react";

import ScheduleCreate from "./schedule/ScheduleCreate";

import "./SideSlide.css";

const SideSlide = ({ date, onClose }) => {
  const [isEvent, setIsEvent] = useState(false);
  const [isDiary, setIsDiary] = useState(false);

  const today = date ? date.toLocaleDateString() : "";

  function handleEvent() {
    setIsEvent(true);
  }
  function handleDiary() {
    setIsDiary(true);
  }

  function save() {
    setIsEvent(false);
    setIsDiary(false);
    onClose();
  }

  return (
    <div className="slide-create">
      <button className="close-button" onClick={save}>
        X
      </button>
      
      {/* 이 아래쪽으로 컴포넌트 변경 
          버튼 눌렀을때 각각 state 업데이트 (isEvent / isDairy) */}
      {!isEvent && !isDiary && (<>
        <h3>{today}</h3>
        <div className="btn-set">
          <button className="create-btn" onClick={handleEvent}>
            일정 등록하기
          </button>
          <button className="create-btn" onClick={handleDiary}>
            일기 작성하기
          </button>
        </div>
      </>
      )}

      {isEvent && <ScheduleCreate date={date} onClose={save} />}
      {isDiary && <p>Create Diary</p>}

    </div>
  );
};

export default SideSlide;