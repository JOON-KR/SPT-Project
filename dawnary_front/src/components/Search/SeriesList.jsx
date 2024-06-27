import { useEffect, useState } from "react";
import PropTypes from 'prop-types';
import SeriesFilter from "./SeriesFilter";
import SeriesItem from "./SeriesItem";
import styles from "./SearchCss/SeriesList.module.css";
import { getAllSeries } from "./RESTapi"; 

const SeriesList = ({ ACCESS_TOKEN }) => {
  const [series, setSeries] = useState([]);
  const [filter, setFilter] = useState("latest");
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 3; 

  useEffect(() => {
    const fetchSeriesData = async () => {
      try {
        const fetchedSeries = await getAllSeries(ACCESS_TOKEN);
        setSeries(fetchedSeries);
      } catch (error) {
        console.error('시리즈 데이터 불러오기 실패:', error);
        setError('시리즈 데이터 불러오기 실패');
      }
    };

    fetchSeriesData();
  }, [ACCESS_TOKEN]);

  const handleFilterChange = (selectedFilter) => {
    setFilter(selectedFilter);
    setCurrentPage(1); // Reset to the first page when changing filter
  };

  const sortedSeries = series.sort((a, b) => {
    if (filter === "latest") {
      return new Date(b.regDate) - new Date(a.regDate);
    } else if (filter === "viewCnt") {
      return b.viewCnt - a.viewCnt;
    }
    return 0;
  });

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentSeries = sortedSeries.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div>
      <SeriesFilter onFilterChange={handleFilterChange} />
      <div className={styles.seriesContainer}>
      <ul className={styles.seriesList}>
        {currentSeries.map((serie) => (
          <SeriesItem key={serie.id} series={serie} />
        ))}
      </ul>
      <div>
        <button disabled={currentPage === 1} onClick={() => setCurrentPage(1)}>처음</button>
        <button disabled={currentPage === 1} onClick={() => paginate(currentPage - 1)}>{'<'}</button>
        {Array.from(Array(Math.ceil(sortedSeries.length / itemsPerPage)).keys()).map((pageNumber) => (
          <button key={pageNumber} onClick={() => paginate(pageNumber + 1)}>
            {pageNumber + 1}
          </button>
        ))}
        <button disabled={currentPage === Math.ceil(sortedSeries.length / itemsPerPage)} onClick={() => paginate(currentPage + 1)}>{'>'}</button>
        <button disabled={currentPage === Math.ceil(sortedSeries.length / itemsPerPage)} onClick={() => setCurrentPage(Math.ceil(sortedSeries.length / itemsPerPage))}>마지막</button>
      </div>
      </div>
    </div>
  );
};

SeriesList.propTypes = {
  ACCESS_TOKEN: PropTypes.string.isRequired,
};

export default SeriesList;
