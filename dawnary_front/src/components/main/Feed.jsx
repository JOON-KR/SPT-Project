import React from "react";
import { ListGroup } from "react-bootstrap";

const Feed = ({ items }) => {
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
            {item.author}
          </div>
        </ListGroup.Item>
      ))}
    </>
  );
};

export default Feed;
