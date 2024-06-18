import { useState } from "react";
import "./MainCalendar.css";
import Calendar from "./Calendar";
import SideSlide from "./SideSlide";

export default function MainCalendar() {
  const [selectedDate, setSelectedDate] = useState(null);
  const [isSlideOpen, setIsSlideOpen] = useState(false);

  const openSlide = (date) => {
    setSelectedDate(date);
    setIsSlideOpen(true);
  };

  const closeSlide = () => {
    setIsSlideOpen(false);
    setSelectedDate(null);
  };

  return (
    <>
      <div className="cal-container">
        <Calendar onDateClick={openSlide} />
      </div>
      {/* 슬라이드 화면 */}
      <div className={`slide ${isSlideOpen ? "open" : ""}`}>
        <SideSlide date={selectedDate} onClose={closeSlide} />
      </div>
    </>
  );
}
