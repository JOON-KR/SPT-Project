import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getSeriesDetail } from './RESTapi'; 
import styles from './SearchCss/SeriesDetail.module.css';

const SeriesDetail = () => {
  const { id } = useParams();
  const [seriesDetail, setSeriesDetail] = useState(null);

  useEffect(() => {
    const fetchSeriesDetail = async () => {
      try {
        const detailData = await getSeriesDetail(id);
        setSeriesDetail(detailData);
      } catch (error) {
        console.error('시리즈 세부 정보를 불러오는데 실패했습니다.', error);
      }
    };

    fetchSeriesDetail();
  }, [id]);

  if (!seriesDetail) {
    return <p>세부 정보를 불러오는 중...</p>;
  }

  return (
    <div className={styles.seriesDetailContainer}>
      <h2 className={styles.seriesTitle}>{seriesDetail.title}</h2>
      <img src={seriesDetail.imagePath} alt="썸네일 입니다" className={styles.seriesThumbnail} />
      <p>작성자: {seriesDetail.name}</p>
      <p>등록일: {new Date(seriesDetail.regDate).toLocaleDateString()}</p>
      <p>조회수: {seriesDetail.viewCnt}</p>

      <h3>일기 목록</h3>
      <ul className={styles.diaryList}>
        {seriesDetail.diaries.length > 0 ? (
          seriesDetail.diaries.map((diary) => (
            <li key={diary.id} className={styles.diaryItem}>
              <h4 className={styles.diaryTitle}>{diary.title}</h4>
              <p>{diary.contents}</p>
            </li>
          ))
        ) : (
          <p>일기가 없습니다.</p>
        )}
      </ul>
    </div>
  );
};

export default SeriesDetail;
