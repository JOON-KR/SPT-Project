import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Badge, Card, ListGroup } from "react-bootstrap";
import UserFeed from "./UserFeed";
import "./UserPage.css";
import { useLocation, useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

const UserPage = () => {
  const { id } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const { member } = location.state || {};

  useEffect(() => {
    if (!member) {
      alert("잘못된 접근입니다. 이전 페이지로 돌아갑니다.");
      navigate(-1);
      return;
    }
  }, [member, navigate]);

  const [diaryFeeds, setDiaryFeeds] = useState([]);
  const [seriesFeeds, setSeriesFeeds] = useState([]);

  useEffect(() => {
    const access_token = "Bearer " + sessionStorage.getItem("token");

    // 일기 피드 요청
    const fetchDiaryFeeds = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/diary/member/${id}`,
          {
            headers: { Authorization: access_token },
          }
        );

        setDiaryFeeds(response.data);
      } catch (error) {
        console.error("일기 피드 로딩 실패:", error);
      }
    };
    // 시리즈 피드 요청
    const fetchSeriesFeeds = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/series/member/${id}`,
          {
            headers: { Authorization: access_token },
          }
        );

        setSeriesFeeds(response.data);
      } catch (error) {
        console.error("시리즈 피드 로딩 실패:", error);
      }
    };

    fetchDiaryFeeds();
    fetchSeriesFeeds();
  }, [id]);

  return (
    <div className="user-page">
      <h1 className="page-title titles">{member?.name}님의 피드입니다 😉</h1>
      <div className="feed-box">
        <div className="list-box">
          <h4 className="ms-3 mt-3 titles">일기</h4>
          <ListGroup
            as="ul"
            className="diary-feed m-3"
            style={{ maxHeight: "800px", overflowY: "auto" }}
          >
            <UserFeed items={diaryFeeds} type={"diary"} />
          </ListGroup>
        </div>

        <div className="list-box">
          <h4 className="ms-3 mt-3 titles">시리즈</h4>
          <ListGroup
            as="ul"
            className="series-feed m-3"
            style={{ maxHeight: "800px", overflowY: "auto" }}
          >
            <UserFeed items={seriesFeeds} type={"series"} />
          </ListGroup>
        </div>
      </div>
    </div>
  );
};

export default UserPage;
