import React, { useState } from "react";
import Feed from "./Feed";
import Follow from "./Follow";
import "./MyPage.css"; // 스타일링을 위한 CSS 파일을 임포트합니다.
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css"; // css import
import moment from "moment";

const MyPage = () => {
  const [showFollowing, setShowFollowing] = useState(false); // 팔로잉 목록의 표시 여부를 관리할 상태 변수
  const [showFollowers, setShowFollowers] = useState(false); // 팔로워 목록의 표시 여부를 관리할 상태 변수
  const [value, onChange] = useState(new Date());

  const token = sessionStorage.getItem("token");

  const items = [
    "첫 번째 아이템",
    "두 번째 아이템",
    "세 번째 아이템",
    "네 번째 아이템",
    "다섯 번째 아이템",
    "여섯 번째 아이템",
    "일곱 번째 아이템",
    "여덟 번째 아이템",
    "아홉 번째 아이템",
    "열 번째 아이템",
    "열한 번째 아이템",
    "열두 번째 아이템",
  ];

  const cName = "inside";

  const toggleFollowing = () => {
    setShowFollowing(!showFollowing); // 팔로잉 목록의 표시 여부를 반전시킵니다.
    setShowFollowers(false); // 팔로워 목록은 숨깁니다.
    console.log(showFollowing);
  };

  const toggleFollowers = () => {
    setShowFollowers(!showFollowers); // 팔로워 목록의 표시 여부를 반전시킵니다.
    setShowFollowing(false); // 팔로잉 목록은 숨깁니다.
    console.log(showFollowers);
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
      return; // 박스 내부를 클릭한 경우, 둘 다 안열려있는 경우 무시
    }
    console.log(e.target.classList.contains("following"));
    console.log(e.target.classList.contains("follower"));

    setShowFollowing(false);
    setShowFollowers(false);
  };

  return (
    <div className="container" onClick={handleOutsideClick}>
      <h1>마이페이지</h1>

      <div className="profile-box">
        <div>닉네임</div>
        <div>
          <span onClick={toggleFollowing}>팔로잉</span>
          <span>0</span>
          <span onClick={toggleFollowers}>팔로워</span>
          <span>0</span>
        </div>
        <div>
          <button>설정</button>{" "}
          {/* 설정 버튼에 대한 기능은 추가 구현이 필요합니다. */}
        </div>
        <div className="emo-calendar">
          <Calendar
            onChange={onChange}
            value={value}
            formatDay={(locale, date) => moment(date).format("D")}
          />
        </div>
      </div>

      <div className={`following ${showFollowing ? "show" : ""}`}>
        <div className="follow-box-close" onClick={closeBox}>
          X
        </div>
        <Follow items={items} cName={cName} />
      </div>

      <div className={`follower ${showFollowers ? "show" : ""}`}>
        <div className="follow-box-close" onClick={closeBox}>
          X
        </div>
        <Follow items={items} cName={cName} />
      </div>

      <div className="feed">
        <div className="following-feed">
          <Feed items={items} />
        </div>
        <div className="follower-feed">
          <Feed items={items} />
        </div>
      </div>
    </div>
  );
};

export default MyPage;
