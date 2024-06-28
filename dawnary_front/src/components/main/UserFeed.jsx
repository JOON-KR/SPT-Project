import React from "react";
import { ListGroup, Badge, Card } from "react-bootstrap";
import moment from "moment";
import "./UserFeed.css"; // UserFeed에 대한 CSS 파일을 따로 생성합니다.
import { Link } from "react-router-dom";

const UserFeed = ({ items, type }) => {
  // 오늘날짜랑 차이 계산
  const calculateDateDifference = (date) => {
    const today = moment().startOf("day");
    const itemDate = moment(date).startOf("day");
    const difference = today.diff(itemDate, "days");

    if (difference === 0) {
      return "오늘";
    } else {
      return `${difference}일 전`;
    }
  };

  //피드에 내용이 없는 경우
  if (items.length === 0) {
    return <div className="empty-feed-box">아직 피드가 없어요 😅</div>;
  }

  return (
    <>
      {items.map((item, index) => (
        <ListGroup.Item
          as="li"
          className="d-flex justify-content-between align-items-start h-30"
          key={index}
        >
          <div className="ms-2 me-auto">
            <Link
              to={type === "diary" ? `/diary/${item.id}` : `/series/${item.id}`}
            >
              <div className="fw-bold">{item.title}</div>
              {item.name}
            </Link>
          </div>
          <Badge pill bg="primary">
            {calculateDateDifference(item.date)}
          </Badge>
        </ListGroup.Item>
      ))}
    </>
  );
};

export default UserFeed;
