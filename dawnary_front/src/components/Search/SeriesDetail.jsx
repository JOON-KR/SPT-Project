import { useParams, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getSeriesDetail, followMember } from './RESTapi'; 
import styles from './SearchCss/SeriesDetail.module.css';

const SeriesDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate(); // useNavigate 훅 사용
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

  const handleFollowClick = async (memberId) => {
    try {
      await followMember(memberId);
      console.log('유저를 팔로우했습니다.');
      // 원하는 팔로잉 완료 시 동작 추가
    } catch (error) {
      console.error('유저를 팔로우하는데 실패했습니다.', error);
    }
  };

  const handleBackClick = () => {
    navigate(-1); // -1로 navigate를 호출하면 이전 페이지로 이동
  };

  if (!seriesDetail) {
    return <p>세부 정보를 불러오는 중...</p>;
  }

  return (
    <div className={styles.seriesDetailContainer}>
      <div className={styles.buttonContainer}>
        <button className={styles.smallButton} onClick={handleBackClick}>뒤로가기</button>
        <button className={styles.smallButton} onClick={() => handleFollowClick(seriesDetail.memberId)}>팔로우하기</button>
      </div>
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
