import { useState, useEffect } from 'react';
import axios from 'axios';

export default function DiaryUpdate({ event, onClose }) {
  const [title, setTitle] = useState(event.title);
  const [content, setContent] = useState(event.extendedProps.content);
  const [weather, setWeather] = useState(event.extendedProps.weather);
  const [sentiment, setSentiment] = useState(event.extendedProps.sentiment);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const updatedDiary = {
      id: event.id,
      title,
      content,
      weather,
      sentiment,
      date: event.start,
    };

    try {
      const response = await axios.put(`http://localhost:8080/diary/${event.id}`, updatedDiary);
      console.log('Diary update success:', response.data);
      onClose();
    } catch (error) {
      console.error('Diary update failed:', error);
    }
  };

  return (
    <div className="diary-update-form">
      <form onSubmit={handleSubmit}>
        <div>
          <label>제목</label>
          <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
        </div>
        <div>
          <label>내용</label>
          <textarea value={content} onChange={(e) => setContent(e.target.value)} rows="5" required />
        </div>
        <div>
          <label>날씨</label>
          <input type="text" value={weather} onChange={(e) => setWeather(e.target.value)} />
        </div>
        <div>
          <label>기분</label>
          <input type="text" value={sentiment} onChange={(e) => setSentiment(e.target.value)} />
        </div>
        <button type="submit">저장</button>
        <button type="button" onClick={onClose}>취소</button>
      </form>
    </div>
  );
}