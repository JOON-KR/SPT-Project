import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { getSeriesByKeyword } from './RESTapi';

const SeriesSearchResults = ({ keyword }) => {
  const [series, setSeries] = useState([]);

  useEffect(() => {
    const fetchSeries = async () => {
      try {
        const seriesData = await getSeriesByKeyword(keyword);
        setSeries(seriesData);
      } catch (error) {
        console.error('시리즈 검색에 실패하였습니다', error);
      }
    };

    fetchSeries();
  }, [keyword]);

  return (
    <div>
      <h3>시리즈 검색결과</h3>
      {series && series.length > 0 ? (
        <ul>
          {series.map((entry) => (
            <li key={entry.id}>{entry.title}</li>
          ))}
        </ul>
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
