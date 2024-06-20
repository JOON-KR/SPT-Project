import { useState, useEffect } from "react";
import axios from "axios";

export default function DiaryCreate({ date, onClose }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [isPublic, setIsPublic] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);
  const [formattedDate, setFormattedDate] = useState("");
  const [weatherData, setWeatherData] = useState(null);

  useEffect(() => {
    if (date) {
      setFormattedDate(formatDate(date));
      fetchWeatherData(date);
    }
  }, [date]);

  const fetchWeatherData = async (date) => {
    const lat = 37.5665; // Latitude
    const lon = 126.978; // Longitude
    const formattedDate = date.toISOString().split("T")[0]; // YYYY-MM-DD format
    const apiKey = import.meta.env.VITE_WEATHER_KEY;

    try {
      const response = await axios.get(
        `https://api.openweathermap.org/data/2.5/weather`,
        {
          params: {
            lat: lat,
            lon: lon,
            date: formattedDate,
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

    let imageUrl = "";

    if (selectedFile) {
      const formData = new FormData();
      formData.append("image", selectedFile);

      try {
        const response = await axios.post(
          "https://api.imgur.com/3/image",
          formData,
          {
            headers: {
              Authorization: `Client-ID ${
                import.meta.env.VITE_IMGUR_CLIENT_ID
              }`,
              Accept: "application/json",
            },
          }
        );
        imageUrl = response.data.data.link;
      } catch (error) {
        console.error("Image upload failed:", error);
        alert("Image upload failed.");
        return;
      }
      // imageUrl = 'test'
    }

    const diaryData = {
      title: title,
      content: content,
      isPublic: isPublic,
      date: date.toISOString(),
      imagePath: imageUrl,
      weather: weatherData.weather[0].main,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/diary",
        diaryData
      );
      console.log("Diary entry successful:", response.data);
      onClose();
    } catch (error) {
      console.log(diaryData);
      console.error("Diary entry failed:", error);
    }
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    return `${year}. ${month}. ${day}`;
  };

  return (
    <div>
      <button className="close-button" onClick={onClose}>
        X
      </button>
      {formattedDate && <h2>{formattedDate}의 일기</h2>}
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
          <label>사진 업로드(한장만)</label>
          <input type="file" onChange={handleFileChange} accept="image/*" />
        </div>
        <div>
          <label>공개</label>
          <div>
            <label>
              <input
                type="radio"
                name="visibility"
                checked={isPublic}
                onChange={() => setIsPublic(true)}
              />
              <span>공개</span>
            </label>
            <label>
              <input
                type="radio"
                name="visibility"
                checked={!isPublic}
                onChange={() => setIsPublic(false)}
              />
              <span>비공개</span>
            </label>
          </div>
        </div>
        <button type="submit">저장</button>
      </form>
    </div>
  );
}
