import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Badge, Card, ListGroup } from "react-bootstrap";
import UserFeed from "./UserFeed";
import "./UserPage.css";
import { useLocation, useParams } from "react-router-dom";
import { useState } from "react";

const UserPage = () => {
  const { id } = useParams();

  const location = useLocation();
  const { member } = location.state || {};

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

  return (
    <div>
      <h1 className="page-title">{member.nickName}님의 피드입니다 😉</h1>
      <div className="feed-box">
        <div className="list-box">
          <h4 className="ms-3 mt-3">Series</h4>
          <ListGroup
            as="ul"
            className="dairy-feed m-3"
            style={{ maxHeight: "700px", overflowY: "auto" }}
          >
            <UserFeed items={feeds} />
          </ListGroup>
        </div>

        <div className="list-box">
          <h4 className="ms-3 mt-3">Diary</h4>
          <ListGroup
            as="ul"
            className="series-feed m-3"
            style={{ maxHeight: "700px", overflowY: "auto" }}
          >
            <UserFeed items={feeds} />
          </ListGroup>
        </div>
      </div>
    </div>
  );
};

export default UserPage;
