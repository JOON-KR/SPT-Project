import React from "react";
import { Badge, ListGroup } from "react-bootstrap";
import moment from "moment";

const Feed = ({ items, type }) => {
  //오늘날짜랑 차이 계산
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

  //디테일 페이지로 이동 - 다이어리 or 시리즈
  const goDetail = (id, type) => {};

  //피드에 내용이 없는 경우
  if (items.length === 0) {
    return <div className="empty-feed-box">아직 피드가 없어요 😅</div>;
  }

  return (
    <>
      {items.map((item, index) => (
        <ListGroup.Item
          as="li"
          className="d-flex justify-content-between align-items-start"
          key={index}
          onClick={goDetail(item.id, type)}
        >
          <div className="ms-2 me-auto">
            <div className="fw-bold">{item.title}</div>
            {item.name}
          </div>
          <div>
            <Badge pill bg="primary">
              {calculateDateDifference(item.date)}
            </Badge>
          </div>
        </ListGroup.Item>
      ))}
    </>
  );
};

export default Feed;
