import { useEffect, useState } from "react";
import EssayItem from "./EssayItem";
import { getAllEssays } from "./RESTapi";

const EssayList = () => {
  const [essays, setEssays] = useState([]);

  useEffect(() => {
    const getEssays = async () => {
      const essaysData = await getAllEssays();
      setEssays(essaysData);
    };

    getEssays();
  }, []);

  return (
    <div className="essay-list">
      <h1>에세이 목록</h1>
      {essays.map((essay, index) => (
        <EssayItem 
          key={index} 
          essay={essay} 
          className={index >= 4 ? "grid-item" : ""}
        />
      ))}
    </div>
  );
};

export default EssayList;
