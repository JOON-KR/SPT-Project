import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Badge, Card, ListGroup } from "react-bootstrap";
import React, { useState, useEffect } from "react";
import Feed from "./Feed";
import Follow from "./Follow";
import "./MyPage.css";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import moment from "moment";
import "./emotion-calendar.css"; // 커스텀 CSS 임포트
import axios from "axios";
import { useNavigate } from "react-router-dom";
import DiaryDetails from "../calendar/diary/DiaryDetails";

const MyPage = () => {
  const [showFollowing, setShowFollowing] = useState(false);
  const [showFollowers, setShowFollowers] = useState(false);
  const [value, onChange] = useState(new Date());
  const [diaryFeeds, setDiaryFeeds] = useState([]);
  const [diarys, setDiarys] = useState([]);
  const [selectedDiaryId, setSelectedDiaryId] = useState(null);
  const [mySeries, setMySeries] = useState([]);
  const [seriesFeeds, setSeriesFeeds] = useState([]);
  const [followings, setFollowings] = useState([]);
  const [followers, setFollowers] = useState([]);

  const loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
  const nickName = loginUser.name;
  const memberId = loginUser.id;
  const cName = "inside";

  const nav = useNavigate();

  useEffect(() => {
    const fetchFollowingData = async () => {
      const access_token = "Bearer " + sessionStorage.getItem("token");

      try {
        const response = await axios.get(
          "http://localhost:8080/follow/following",
          {
            headers: {
              Authorization: access_token,
            },
          }
        );

        const followingsList = response.data.followMemberList;
        setFollowings(followingsList);
      } catch (error) {
        console.error("팔로잉 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    const fetchFollowerData = async () => {
      const access_token = "Bearer " + sessionStorage.getItem("token");

      try {
        const response = await axios.get(
          "http://localhost:8080/follow/follower",
          {
            headers: {
              Authorization: access_token,
            },
          }
        );

        const followersList = response.data.followMemberList;
        setFollowers(followersList);
      } catch (error) {
        console.error("팔로워 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    fetchFollowingData();
    fetchFollowerData();
  }, []);

  useEffect(() => {
    //팔로우 일기 피드 로딩
    const fetchDiaryFeeds = async () => {
      const access_token = "Bearer " + sessionStorage.getItem("token");

      try {
        const response = await axios.get("http://localhost:8080/diary/follow", {
          headers: {
            Authorization: access_token,
          },
        });

        const sortedFeeds = response.data.sort(
          (a, b) => new Date(b.date) - new Date(a.date)
        );

        setDiaryFeeds(sortedFeeds);
      } catch (error) {
        console.error("일기 데이터를 가져오는데 오류가 발생했습니다:", error);
      }
    };

    //감정일기에 표시할 유저 일기 로딩
    const fetchDiarys = async () => {
      const access_token = "Bearer " + sessionStorage.getItem("token");

      try {
        const response = await axios.get(
          `http://localhost:8080/diary/member/${memberId}`,
          {
            headers: {
              Authorization: access_token,
            },
          }
        );

        const updatedDiarys = response.data.map((diary) => {
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
            date: moment(diary.date).format("YYYY-MM-DD"),
            emotion: emotion,
          };
        });

        setDiarys(updatedDiarys);
      } catch (error) {
        console.error("일기 데이터를 가져오는데 오류가 발생했습니다:", error);
      }
    };

    //나의 시리즈 피드 가져오기
    const GetMyseries = async () => {
      const access_token = "Bearer " + sessionStorage.getItem("token");

      try {
        const config = {
          headers: {
            Authorization: access_token,
          },
        };

        const response = await axios.get(
          `http://localhost:8080/series/member/${memberId}`,
          config
        );
        setMySeries(response.data);
      } catch (error) {
        console.error("나의 시리즈 가져오기 실패:", error);
        throw error;
      }
    };

    //팔로우 시리즈 피드 가져오기
    const fetchSeriesFeeds = async () => {
      const access_token = "Bearer " + sessionStorage.getItem("token");

      try {
        const response = await axios.get(
          "http://localhost:8080/series/follow",
          {
            headers: {
              Authorization: access_token,
            },
          }
        );

        setSeriesFeeds(response.data);
      } catch (error) {
        console.error("팔로우한 시리즈를 가져오는 중 오류 발생:", error);
      }
    };

    fetchDiaryFeeds();
    fetchDiarys();
    GetMyseries();
    fetchSeriesFeeds();
  }, [memberId]);

  //달력 날짜 선택했을 때 실행되는 함수
  const handleCalendarChange = (date) => {
    // date를 moment로 변환하여 YYYY-MM-DD 형식의 문자열로 가져옵니다
    const selectedDate = moment(date).format("YYYY-MM-DD");

    // diarys 배열에서 선택된 날짜에 해당하는 일기의 id를 찾습니다
    const diary = diarys.find((diary) => diary.date === selectedDate);

    // 일기가 있으면 해당 일기의 id를 selectedDiaryId로 설정합니다
    if (diary) {
      setSelectedDiaryId(diary.id);
    } else {
      // 선택된 날짜에 일기가 없으면 selectedDiaryId를 초기화합니다
      setSelectedDiaryId(null);
    }
  };

  // 팝업 닫기
  const closePopup = () => {
    setSelectedDiaryId(null);
  };

  //감정상태에 따른 사진 경로
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

  //감정일기 타일 렌더링
  const renderTileContent = ({ date, view }) => {
    if (view === "month") {
      const emotionDiary = diarys.find((diary) =>
        moment(diary.date).isSame(date, "day")
      );
      if (emotionDiary) {
        return (
          <img
            src={getEmotionImage(emotionDiary.emotion)}
            alt={emotionDiary.emotion}
            className="emotion-image"
          />
        );
      }
    }
    return null;
  };

  //팔로잉 사이드바 토글
  const toggleFollowing = () => {
    setShowFollowing(!showFollowing);
    setShowFollowers(false);
  };

  //팔로워 사이드바 토글
  const toggleFollowers = () => {
    setShowFollowers(!showFollowers);
    setShowFollowing(false);
  };

  //사이드바 닫기 동작
  const closeBox = () => {
    setShowFollowing(false);
    setShowFollowers(false);
  };

  //사이드바 외부 영역 클릭
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

  const logout = () => {
    const access_token = "Bearer " + sessionStorage.getItem("token");
    const email = JSON.parse(sessionStorage.getItem("loginUser")).sub;
    const refresh_token = sessionStorage.getItem("refresh_token");

    axios
      .post(
        "http://localhost:8080/member/logout",
        { email: email, refreshToken: refresh_token },
        {
          headers: {
            Authorization: access_token,
            "Content-Type": "application/json",
          },
        }
      )
      .then((response) => {
        console.log("Logout successful:", response);
        sessionStorage.clear();
        alert("로그아웃 되었습니다.");
        nav("/login");
      })
      .catch((error) => {
        console.error("There was an error logging out:", error);
      });
  };

  const goSetting = () => {
    nav("/setting");
  };

  return (
    <div className="container" onClick={handleOutsideClick}>
      <div
        className={`overlay ${showFollowing || showFollowers ? "show" : ""}`}
      />
      <Card className="profile-box p-5 h-70">
        {/* <h2>마이페이지</h2> */}
        <div className="profile-sub m-5">
          <h4 className="nickname">{nickName}</h4>
          <div className="d-flex">
            <div className="clickable" onClick={toggleFollowing}>
              <span>팔로잉</span>
              <Badge bg="secondary" className="mx-2">
                {followings.length}
              </Badge>
            </div>
            <div>|</div>
            <div className="clickable" onClick={toggleFollowers}>
              <span className="ms-2">팔로워</span>
              <Badge bg="secondary" className="mx-2">
                {followers.length}
              </Badge>
            </div>
          </div>
          <div className="profile-btn-box">
            <Button variant="dark" onClick={goSetting}>
              설정
            </Button>
            <Button variant="outline-secondary" onClick={logout}>
              로그아웃
            </Button>
          </div>
        </div>
        <div className="emo-calendar">
          <Calendar
            onChange={handleCalendarChange}
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
        <Follow items={followings} cName={cName} type="following" />
      </div>

      <div className={`follower ${showFollowers ? "show" : ""}`}>
        <div className="follow-box-close" onClick={closeBox}>
          X
        </div>
        <Follow items={followers} cName={cName} type="follower" />
      </div>

      <div className="feed">
        <h4 className="feed-title">나의 시리즈 피드</h4>
        <ListGroup
          as="ul"
          className="my-series-feed"
          style={{ maxHeight: "200px", overflowY: "auto" }}
        >
          <Feed items={mySeries} type={"series"} />
        </ListGroup>

        <h4 className="feed-title">일기 피드</h4>
        <ListGroup
          as="ul"
          className="diary-feed"
          style={{ maxHeight: "200px", overflowY: "auto" }}
        >
          <Feed items={diaryFeeds} type={"diary"} />
        </ListGroup>
        <h4 className="feed-title">시리즈 피드</h4>
        <ListGroup
          as="ul"
          className="series-feed"
          style={{ maxHeight: "200px", overflowY: "auto" }}
        >
          <Feed items={seriesFeeds} type={"series"} />
        </ListGroup>
      </div>
      {selectedDiaryId && (
        <DiaryDetails diaryId={selectedDiaryId} onClose={closePopup} />
      )}
    </div>
  );
};

export default MyPage;
