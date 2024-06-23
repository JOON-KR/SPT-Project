import { useEffect, useState } from "react";
import EssayItem from "./SeriesItem";
import { getBestSeries } from "./RESTapi"; 
import styles from "./SearchCss/BestSeries.module.css";

const BestSeries = () => {
  const [essays, setEssays] = useState([]);

  useEffect(() => {
    const fetchBestEssays = async () => {
      try {
        const bestEssays = await getBestSeries();
        setEssays(bestEssays);
      } catch (error) {
        console.error('명예의 전당 에세이 불러오기 실패', error);
      }
    };

    fetchBestEssays();
  }, []);

  return (
    <div>
      <div className={styles.essayList}>
        {essays.length > 0 ? (
          essays.map((essay, index) => (
            <EssayItem key={index} essay={essay} />
          ))
        ) : (
          <h1>명예의 전당에 등록된 시리즈가 없습니다</h1>
        )}
      </div>
    </div>
  );
};

export default BestSeries;
