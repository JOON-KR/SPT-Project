import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Badge, Card, ListGroup } from "react-bootstrap";
import React, { useState } from "react";
import Feed from "./Feed";
import Follow from "./Follow";
import "./MyPage.css";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import moment from "moment";
import "./emotion-calendar.css"; // 커스텀 CSS 임포트

const MyPage = () => {
  const [showFollowing, setShowFollowing] = useState(false);
  const [showFollowers, setShowFollowers] = useState(false);
  const [value, onChange] = useState(new Date());

  const loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
  const nickName = loginUser.name;

  const [feeds, setFeeds] = useState([
    { title: "첫 번째 아이템", author: "작성자1" },
    { title: "두 번째 아이템", author: "작성자2" },
    { title: "세 번째 아이템", author: "작성자3" },
    { title: "네 번째 아이템", author: "작성자4" },
    { title: "다섯 번째 아이템", author: "작성자5" },
    { title: "여섯 번째 아이템", author: "작성자6" },
    { title: "일곱 번째 아이템", author: "작성자7" },
    { title: "여덟 번째 아이템", author: "작성자8" },
    { title: "아홉 번째 아이템", author: "작성자9" },
    { title: "열 번째 아이템", author: "작성자10" },
    { title: "열한 번째 아이템", author: "작성자11" },
    { title: "열두 번째 아이템", author: "작성자12" },
  ]);

  const [followings, setFollowings] = useState([
    { nickName: "팔로잉1", id: 1 },
    { nickName: "팔로잉2", id: 2 },
    { nickName: "팔로잉3", id: 3 },
    { nickName: "팔로잉4", id: 4 },
    { nickName: "팔로잉5", id: 5 },
    { nickName: "팔로잉6", id: 6 },
    { nickName: "팔로잉7", id: 7 },
    { nickName: "팔로잉8", id: 8 },
    { nickName: "팔로잉9", id: 9 },
    { nickName: "팔로잉10", id: 10 },
    { nickName: "팔로잉11", id: 11 },
    { nickName: "팔로잉12", id: 12 },
    // 추가적인 팔로잉들
  ]);

  const [followers, setFollowers] = useState([
    { nickName: "팔로워1", id: 101 },
    { nickName: "팔로워2", id: 102 },
    { nickName: "팔로워3", id: 103 },
    { nickName: "팔로워4", id: 104 },
    { nickName: "팔로워5", id: 105 },
    { nickName: "팔로워6", id: 106 },
    { nickName: "팔로워7", id: 107 },
    { nickName: "팔로워8", id: 108 },
    { nickName: "팔로워9", id: 109 },
    { nickName: "팔로워10", id: 110 },
    { nickName: "팔로워11", id: 111 },
    { nickName: "팔로워12", id: 112 },
    // 추가적인 팔로워들
  ]);

  const emotions = [
    { date: "2024-06-01", emotion: "happy" },
    { date: "2024-06-02", emotion: "good" },
    { date: "2024-06-03", emotion: "soso" },
    { date: "2024-06-04", emotion: "bad" },
    { date: "2024-06-05", emotion: "upset" },
    // 나머지 날짜와 감정 추가
  ];

  const getEmotionImage = (emotion) => {
    switch (emotion) {
      case "happy":
        return "src/assets/happy.png"; // 해당 이미지 경로
      case "good":
        return "src/assets/good.png"; // 해당 이미지 경로
      case "soso":
        return "src/assets/soso.png"; // 해당 이미지 경로
      case "bad":
        return "src/assets/bad.png"; // 해당 이미지 경로
      case "upset":
        return "src/assets/upset.png"; // 해당 이미지 경로
      default:
        return null;
    }
  };

  const renderTileContent = ({ date, view }) => {
    if (view === "month") {
      const emotion = emotions.find((emotion) =>
        moment(emotion.date).isSame(date, "day")
      );
      if (emotion) {
        return (
          <img
            src={getEmotionImage(emotion.emotion)}
            alt={emotion.emotion}
            className="emotion-image"
          />
        );
      }
    }
    return null;
  };

  const cName = "inside";

  const toggleFollowing = () => {
    setShowFollowing(!showFollowing);
    setShowFollowers(false);
  };

  const toggleFollowers = () => {
    setShowFollowers(!showFollowers);
    setShowFollowing(false);
  };

  const closeBox = () => {
    setShowFollowing(false);
    setShowFollowers(false);
  };

  const handleOutsideClick = (e) => {
    if (
      e.target.classList.contains("following") ||
      e.target.classList.contains("follower") ||
      (!showFollowing && !showFollowers) ||
      e.target.classList.contains("inside")
    ) {
      return;
    }

    setShowFollowing(false);
    setShowFollowers(false);
  };

  return (
    <div className="container" onClick={handleOutsideClick}>
      <div
        className={`overlay ${showFollowing || showFollowers ? "show" : ""}`}
      />
      <Card className="profile-box p-5 h-75">
        <h2>MyPage</h2>
        <div className="profile-sub m-5">
          <h4>{nickName}</h4>
          <div className="clickable">
            <span onClick={toggleFollowing}>팔로잉</span>
            <Badge bg="secondary" className="mx-2">
              0
            </Badge>
            <span onClick={toggleFollowers}>팔로워</span>
            <Badge bg="secondary" className="mx-2">
              0
            </Badge>
          </div>
          <div>
            <Button variant="dark" className="my-3">
              설정
            </Button>
          </div>
        </div>
        <div className="emo-calendar">
          <Calendar
            onChange={onChange}
            value={value}
            formatDay={(locale, date) => moment(date).format("D")}
            tileContent={renderTileContent}
          />
        </div>
      </Card>

      <div className={`following ${showFollowing ? "show" : ""}`}>
        <div className="follow-box-close" onClick={closeBox}>
          X
        </div>
        <Follow items={followings} cName={cName} />
      </div>

      <div className={`follower ${showFollowers ? "show" : ""}`}>
        <div className="follow-box-close" onClick={closeBox}>
          X
        </div>
        <Follow items={followers} cName={cName} />
      </div>

      <div className="feed">
        <ListGroup
          as="ul"
          className="following-feed"
          style={{ maxHeight: "350px", overflowY: "auto" }}
        >
          <Feed items={feeds} />
        </ListGroup>
        <ListGroup
          as="ul"
          className="follower-feed"
          style={{ maxHeight: "350px", overflowY: "auto" }}
        >
          <Feed items={feeds} />
        </ListGroup>
      </div>
    </div>
  );
};

export default MyPage;
