import React from "react";

const Feed = ({ items }) => {
  return (
    <div style={{ maxHeight: "400px", overflowY: "auto" }}>
      <ul>
        {items.map((item, index) => (
          <li key={index}>{item}</li>
        ))}
      </ul>
    </div>
  );
};

export default Feed;
