import { useState, useEffect } from "react";
import axios from "axios";
import axiosInst from "@/utils/axiosInstance";
import "./Diary.css"; 

export default function DiaryCreate({ date, onClose }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [isPublic, setIsPublic] = useState(false);
  const [formattedDate, setFormattedDate] = useState("");
  const [weatherData, setWeatherData] = useState(null);
  const [imagePath, setImagePath] = useState("");

  useEffect(() => {
    if (date) {
      // 시간 맞추기.... ISOString 쓰면 무조건 UTC 시간대 되버림
      const offset = 1000 * 60 * 60 * 9;
      const kDate = new Date((new Date(date)).getTime() + offset);
      setFormattedDate(kDate.toISOString().split('.')[0]);
      fetchWeatherData(date);
    }
  }, [date]);

  const fetchWeatherData = async (date) => {
    const lat = 37.5665; // 
    const lon = 126.978; // 서울 위치
    const apiKey = import.meta.env.VITE_WEATHER_KEY;

    try {
      const response = await axios.get(
        `https://api.openweathermap.org/data/2.5/weather`,
        {
          params: {
            lat: lat,
            lon: lon,
            date: formattedDate.split("T")[0],
            appid: apiKey,
          },
        }
      );
      setWeatherData(response.data);
    } catch (error) {
      console.error("Weather data fetch failed:", error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const diaryData = {
      title: title,
      content: content,
      status: isPublic ? 1 : 0,
      date: formattedDate,
      weather: convertWeatherToEnum(weatherData.weather[0].main),
      imagePath: imagePath,
    };

    try {
      const response = await axiosInst.post(
        "http://localhost:8080/diary",
        diaryData
      );
      console.log("일기 등록 성공:", response.data);
      onClose();
      window.location.reload();
    } catch (error) {
      console.log(diaryData);
      console.error("일기 등록 실패:", error);
    }
  };

  const handleFileChange = async (e) => {
    e.preventDefault();

    const file = e.target.files[0];
    if (file && validateFileType(file)) {
      try {
        const formData = new FormData();
        formData.append("imageFile", file);
        const response = await axiosInst.post(
          "http://localhost:8080/images",
          formData
        );
        console.log("파일 업로드 성공:", response.data);
        setImagePath(response.data);
      } catch (error) {
        console.error("파일 등록 실패:", error);
      }
    } else {
      alert("허용된 파일 형식은 jpg, jpeg, gif, png 입니다.");
      e.target.value = ""; // 입력 필드 초기화
    }
  };

  const validateFileType = (file) => {
    const allowedExtensions = ["jpg", "jpeg", "gif", "png"];
    const fileExtension = file.name.split(".").pop().toLowerCase();
    return allowedExtensions.includes(fileExtension);
  };

  const convertWeatherToEnum = (weather) => {
    const weatherEnumMap = {
      Clear: "SUNNY",
      Wind: "WINDY",
      Clouds: "CLOUDY",
      Rain: "RAINING",
      Thunderstorm: "RAINING", // 머임 ;; 
      Snow: "SNOWING",
    };
    return weatherEnumMap[weather] || "UNKNOWN";
  };

  return (
    <div className="diary-create-form">
      <button className="close-button" onClick={onClose}>
        X
      </button>
      {formattedDate && <h2>{formattedDate.split('T')[0]}의 일기</h2>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>제목</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label>내용</label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            rows="10"
            required
          ></textarea>
        </div>
        <div>
          <label>사진 업로드</label>
          <input type="file" onChange={handleFileChange} accept="image/*" />
        </div>
        <div className="toggle-switch-container">
          <label>공개 여부</label>
          <div className="toggle-switch">
            <input
              type="checkbox"
              id="visibility-toggle"
              checked={isPublic}
              onChange={() => setIsPublic(!isPublic)}
            />
            <label htmlFor="visibility-toggle">Toggle</label>
            <span>{isPublic ? "공개" : "비공개"}</span>
          </div>
        </div>
        <button type="submit">저장</button>
      </form>
    </div>
  );
}