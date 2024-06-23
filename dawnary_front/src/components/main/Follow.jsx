import React from "react";
import { ListGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Follow = ({ items, cName }) => {
  const nav = useNavigate();

  const goUserPage = (member) => {
    nav(`/userPage/${member.id}`, { state: { member } }); // 유저의 ID를 이용해 URL을 생성하여 이동
  };

  return (
    <div className={`follow-list-container ${cName}`}>
      <h2
        className={`follow-list-title ${cName}`}
        style={{ textAlign: "center" }}
      >
        팔로우 목록
      </h2>
      <ListGroup variant="flush" className={`follow-list-items ${cName} `}>
        {items.map((item, index) => (
          <ListGroup.Item
            key={index}
            className={cName}
            onClick={() => goUserPage(item)} // 함수 참조 형태로 전달
            style={{ textAlign: "center", cursor: "pointer" }}
          >
            {item.nickName}
          </ListGroup.Item>
        ))}
      </ListGroup>
    </div>
  );
};

export default Follow;
