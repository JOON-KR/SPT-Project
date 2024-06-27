import { useState, useEffect, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "@/utils/axiosInstance";

export default function DiaryView() {
  const { id: diaryId } = useParams(); // URL에서 diaryId를 가져옴
  const [diary, setDiary] = useState(null);
  const [imageUrl, setImageUrl] = useState("");
  const navigate = useNavigate();

  const fetchDiaryDetails = useCallback(async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/diary/${diaryId}`
      );

      const updatedDiarys = (diary) => {
        let emotion = "";
        if (diary.sentiment >= -1 && diary.sentiment < -0.6) {
          emotion = "upset";
        } else if (diary.sentiment >= -0.6 && diary.sentiment < -0.2) {
          emotion = "bad";
        } else if (diary.sentiment >= -0.2 && diary.sentiment < 0.2) {
          emotion = "soso";
        } else if (diary.sentiment >= 0.2 && diary.sentiment < 0.6) {
          emotion = "good";
        } else if (diary.sentiment >= 0.6 && diary.sentiment <= 1) {
          emotion = "happy";
        }
        return {
          ...diary,
          emotion: emotion,
        };
      };
      setDiary(updatedDiarys(response.data));
      if (response.data.imagePath) {
        fetchImage(response.data.imagePath);
      }
    } catch (error) {
      console.error("Error fetching diary details:", error);
    }
  }, [diaryId]);

  const fetchImage = async (imagePath) => {
    try {
      const response = await axios.get(
        `http://localhost:80/images/` + encodeURIComponent(imagePath),
        {
          responseType: "blob",
        }
      );
      const url = URL.createObjectURL(response.data);
      setImageUrl(url);
    } catch (error) {
      console.error("Error fetching image:", error);
    }
  };

  useEffect(() => {
    fetchDiaryDetails();
  }, [fetchDiaryDetails]);

  const handleBackClick = () => {
    navigate(-1); // 이전 페이지로 이동
  };

  return (
    <div className="view-container">
      <div className="diary-view">
        {diary ? (
          <>
            <h2>{diary.title}</h2>
            <p>{diary.date.split("T")[0]}</p>
            {imageUrl && <img src={imageUrl} alt="Diary" />}
            <p>날씨 : {diary.weather}</p>
            <p>내용 : {diary.content}</p>
            <p>기분 : {diary.emotion}</p>
            <div className="button-container">
              <button onClick={handleBackClick}>뒤로가기</button>
            </div>
          </>
        ) : (
          <p>Loading...</p>
        )}
      </div>
    </div>
  );
}
