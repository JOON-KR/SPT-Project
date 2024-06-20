import React from "react";
import { useNavigate } from "react-router-dom";

const Follow = ({ items, cName }) => {
  const nav = useNavigate();
  const goUserPage = (user) => {
    nav("/userPage");
  };

  return (
    <div className={`follow-list-container ${cName}`}>
      <h2 className={`follow-list-title ${cName}`}>팔로우 목록</h2>
      <ul className={`follow-list-items ${cName}`}>
        {items.map((item, index) => (
          <li key={index} className={cName} onClick={goUserPage}>
            {item}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Follow;
