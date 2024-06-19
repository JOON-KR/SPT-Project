import { useEffect, useState } from "react";
import EssayItem from "./EssayItem";
import { fetchEssays } from "../RESTapi";

const EssayList = () => {
  const [essays, setEssays] = useState([]);

  useEffect(() => {
    const getEssays = async () => {
      const essaysData = await fetchEssays();
      setEssays(essaysData);
    };

    getEssays();
  }, []);

  return (
    <div className="essay-list">
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
