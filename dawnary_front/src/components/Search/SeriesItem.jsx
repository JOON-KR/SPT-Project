import PropTypes from 'prop-types';
import styles from './SearchCss/SeriesItem.module.css';

const SeriesItem = ({ series, className }) => {

  console.log(series)

  if (!series) {
    return <div className={`${styles.essayItem} ${className}`}>에세이가 존재하지 않습니다.</div>;
  }


  // 날짜를 문자열 형식으로 변환
  const formattedDate = series.date.toISOString().split('T')[0];

  return (
    <div className={`${styles.essayItem} ${className}`}>     
      <h3>{series.title}</h3>
      <p>{series.content}</p>
      {series.thumbnail && <img src={series.thumbnail} alt={`${series.title} thumbnail`} className={styles.thumbnail} />}
    <div className={`${styles.essayInfo}`}>
      <div>{series.writer}</div>
      <div>👍 {series.likes} 좋아요</div>
      <div>📅 {formattedDate}</div>
    </div>
    </div>
  );
};

SeriesItem.propTypes = {
  series: PropTypes.shape({
    title: PropTypes.string.isRequired,
    content: PropTypes.string.isRequired,
    writer: PropTypes.string.isRequired,
    likes: PropTypes.number.isRequired,
    thumbnail: PropTypes.string, // 이 속성은 선택적입니다.
    date: PropTypes.instanceOf(Date).isRequired // Date 객체로 설정
  }).isRequired,
  className: PropTypes.string
};

SeriesItem.defaultProps = {
  className: ''
};

export default SeriesItem;
