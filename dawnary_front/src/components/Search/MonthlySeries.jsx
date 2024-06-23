import { useEffect, useState } from "react";
import EssayItem from "./SeriesItem";
import { getMonthlySeries } from "./RESTapi"; 
import styles from "./SearchCss/BestSeries.module.css";

const MonthlySeries = () => {
  const [essays, setEssays] = useState([]);

  useEffect(() => {
    const fetchMonthlyEssays = async () => {
      try {
        const monthlyEssays = await getMonthlySeries();
        setEssays(monthlyEssays);
      } catch (error) {
        console.error('이달의 시리즈 불러오기 실패', error);
      }
    };

    fetchMonthlyEssays();
  }, []);

  return (
    <div>
      <div className={styles.essayList}>
        {essays.length > 0 ? (
          essays.map((essay, index) => (
            <EssayItem key={index} essay={essay} />
          ))
        ) : (
          <h1>이달의 시리즈에 등록된 시리즈가 없습니다</h1>
        )}
      </div>
    </div>
  );
};

export default MonthlySeries;
