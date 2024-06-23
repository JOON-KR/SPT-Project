import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { SearchSeriesByKeyword } from './RESTapi';
import styles from './SearchCss/SeriesSearchResults.module.css'

const SeriesSearchResults = ({ keyword }) => {
  const [series, setSeries] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  useEffect(() => {
    const fetchSeries = async () => {
      try {
        const seriesData = await SearchSeriesByKeyword(keyword);
        setSeries(seriesData);
      } catch (error) {
        console.error('시리즈 검색에 실패하였습니다', error);
      }
    };

    fetchSeries();
  }, [keyword]);

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentSeries = series.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div>
      <h3>시리즈 검색결과</h3>
      {currentSeries.length > 0 ? (
        <>
          <ul className={styles.seriesList}>
            {currentSeries.map((entry) => (
              <li key={entry.id} className={styles.seriesItem}>
                <img src={entry.thumbnail} alt="Thumbnail" className={styles.seriesThumbnail} />
                <div className={styles.seriesInfo}>
                  <p className={styles.seriesTitle}>{entry.title}</p>
                  <p className={styles.seriesAuthor}>{entry.author}</p>
                </div>
              </li>
            ))}
          </ul>
          <div className={styles.paginationContainer}>
            {series.length > itemsPerPage && (
              <ul className={styles.pagination}>
                {Array(Math.ceil(series.length / itemsPerPage)).fill().map((_, index) => (
                  <li key={index} className={styles.paginationItem} onClick={() => paginate(index + 1)}>
                    {index + 1}
                  </li>
                ))}
              </ul>
            )}
          </div>
        </>
      ) : (
        <p>시리즈 검색 결과가 없습니다.</p>
      )}
    </div>
  );
};

SeriesSearchResults.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default SeriesSearchResults;
