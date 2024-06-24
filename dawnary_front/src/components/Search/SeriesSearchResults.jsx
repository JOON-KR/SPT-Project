import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { SearchSeriesByKeyword } from './RESTapi';
import { Link } from 'react-router-dom'
import styles from './SearchCss/SeriesSearchResults.module.css'
import _ from 'lodash'

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

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const filteredSeries = series.filter(series =>
    _.includes(series.title , keyword)
  )

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentSeries = filteredSeries.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(filteredSeries.length / itemsPerPage);

  console.log(filteredSeries);
  console.log(currentSeries);
  
  return (
    <div className={styles.seriesSearchContainer}>
      <h3 className={styles.seriesSearchTitle}>시리즈 검색결과</h3>
      {currentSeries.length > 0 ? (
        <>
          <ul className={styles.seriesList}>
            {currentSeries.map((entry) => (
              <li key={entry.id} className={styles.seriesItem}>
                <Link to={`/series/${entry.id}`}>
                  <img src={entry.thumbnail} alt="Thumbnail" className={styles.seriesThumbnail} />
                  <div className={styles.seriesInfo}>
                    <p className={styles.seriesTitle}>{entry.title}</p>
                    <p className={styles.seriesAuthor}>{entry.author}</p>
                  </div>
                </Link>
              </li>
            ))}
          </ul>
          <div>
            {totalPages > 1 && (
              <ul className={styles.pagination}>
                <li onClick={() => paginate(1)} className={currentPage === 1 ? styles.disabled : ''}>
                  처음
                </li>
                {Array(totalPages)
                  .fill()
                  .map((_, index) => (
                    <li key={index} onClick={() => paginate(index + 1)} 
                        className={currentPage === index + 1 ? styles.active : ''}>
                      {index + 1}
                    </li>
                  ))}
                <li onClick={() => paginate(totalPages)} className={currentPage === totalPages ? styles.disabled : ''}>
                  마지막
                </li>
              </ul>
            )}
          </div>
        </>
      ) : (
        <p className={styles.noneResult}>시리즈 검색 결과가 없습니다.</p>
      )}
    </div>
  );
};

SeriesSearchResults.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default SeriesSearchResults;




// import { useEffect, useState } from 'react';
// import PropTypes from 'prop-types';
// import { SearchSeriesByKeyword } from './RESTapi';
// import styles from './SearchCss/SeriesSearchResults.module.css'
// import _ from 'lodash'

// const SeriesSearchResults = ({ keyword }) => {
//   const [series, setSeries] = useState([]);
//   const [currentPage, setCurrentPage] = useState(1);
//   const itemsPerPage = 5;

//   useEffect(() => {
//     const fetchSeries = async () => {
//       try {
//         const seriesData = await SearchSeriesByKeyword(keyword);
//         setSeries(seriesData);
//       } catch (error) {
//         console.error('시리즈 검색에 실패하였습니다', error);
//       }
//     };

//     fetchSeries();
//   }, [keyword]);

//   const paginate = (pageNumber) => {
//     setCurrentPage(pageNumber);
//   };

//   const filteredSeries = series.filter(series =>
//     _.includes(series.title , keyword)
//   )

//   const indexOfLastItem = currentPage * itemsPerPage;
//   const indexOfFirstItem = indexOfLastItem - itemsPerPage;
//   const currentSeries = filteredSeries.slice(indexOfFirstItem, indexOfLastItem);
//   const totalPages = Math.ceil(filteredSeries.length / itemsPerPage);

//   console.log(filteredSeries)
//   console.log(currentSeries)
  
//   return (
//     <div className={styles.seriesSearchContainer}>
//       <h3 className={styles.seriesSearchTitle}>시리즈 검색결과</h3>
//       {currentSeries.length > 0 ? (
//         <>
//           <ul className={styles.seriesList}>
//             {currentSeries.map((entry) => ( // 현재 페이지의 유저만 렌더링
//               <li key={entry.id} className={styles.seriesItem}>
//                 <img src={series.thumbnail} alt="Thumbnail" className={styles.seriesThumbnail} />
//                 <div className={styles.seriesInfo}>
//                   <p className={styles.seriesTitle}>{entry.title}</p>
//                   <p className={styles.seriesAuthor}>{entry.author}</p>
//                 </div>
//               </li>
//             ))}
//           </ul>
//           <div>
//             {totalPages > 1 && (
//               <ul className={styles.pagination}>
//                 <li onClick={() => paginate(1)} className={currentPage === 1 ? styles.disabled : ''}>
//                   처음
//                 </li>
//                 {Array(totalPages)
//                   .fill()
//                   .map((_, index) => (
//                     <li key={index} onClick={() => paginate(index + 1)} 
//                         className={currentPage === index + 1 ? styles.active : ''}>
//                       {index + 1}
//                     </li>
//                   ))}
//                 <li onClick={() => paginate(totalPages)} className={currentPage === totalPages ? styles.disabled : ''}>
//                   마지막
//                 </li>
//               </ul>
//             )}
//           </div>
//         </>
//       ) : (
//         <p className={styles.noneResult}>시리즈 검색 결과가 없습니다.</p>
//       )}
//     </div>
//   );
  
// };





// SeriesSearchResults.propTypes = {
//   keyword: PropTypes.string.isRequired,
// };

// export default SeriesSearchResults;