import PropTypes from 'prop-types';
import styles from "./SearchCss/SeriesList.module.css";

const SeriesItem = ({ series }) => {
  return (
    <li className={styles.seriesItem}>
      <h2>{series.title}</h2>
      <p>작성자: {series.name}</p>
      <p>등록일: {new Date(series.regDate).toLocaleString()}</p>
      {series.imagePath && <img src={series.imagePath} alt={series.title} style={{ width: '100px', height: '100px' }} />}
      <p>상태: {series.status === 1 ? '공개' : '비공개'}</p>
      <p>조회수: {series.viewCnt}</p>
      <p>다이어리: {series.diaries.length} 건</p>
    </li>
  );
};

SeriesItem.propTypes = {
  series: PropTypes.shape({
    id: PropTypes.number.isRequired,
    title: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    regDate: PropTypes.string.isRequired,
    imagePath: PropTypes.string,
    status: PropTypes.number.isRequired,
    viewCnt: PropTypes.number.isRequired,
    diaries: PropTypes.array.isRequired,
  }).isRequired,
};

export default SeriesItem;
