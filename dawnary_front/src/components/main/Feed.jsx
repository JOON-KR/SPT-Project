import React from "react";
import { Badge, ListGroup } from "react-bootstrap";
import moment from "moment";

const Feed = ({ items }) => {
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

  return (
    <>
      {items.map((item, index) => (
        <ListGroup.Item
          as="li"
          className="d-flex justify-content-between align-items-start"
          key={index}
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
