import { useEffect, useState } from "react";
import SeriesFilter from "./SeriesFilter"
import SeriesItem from "./SeriesItem";
import styles from "./SearchCss/SeriesList.module.css"
import { getAllSeries } from "./RESTapi"; 

const SeriesList = () => {

  const [series, setSeries] = useState([]);

 
useEffect(() => {
  const fetchSeriesData = async () => {
    try {
      const fetchedSeries = await getAllSeries();
      setSeries(fetchedSeries);
      console.log(series)
    } catch (error) {
      console.error('시리즈 데이터 불러오기 실패:', error);
    }
  };

  fetchSeriesData();
}, []);

  const [filter, setFilter] = useState("latest");

  const handleFilterChange = (selectedFilter) => {
    setFilter(selectedFilter);
  };

  const sortedSeries = series.sort((a, b) => {
    if (filter === "latest") {
        return new Date(b.date) - new Date(a.date);
    } else if (filter === "likes") {
        return b.likes - a.likes;
    }
    return 0;
});



    return (
    <div>
      <SeriesFilter onFilterChange={handleFilterChange} />
      <div className={styles.essayList}>
        {sortedSeries.map((series, index) => (
          <SeriesItem key={index} series={series} />
        ))}
      </div>
    </div>
  );
};

export default SeriesList;
