import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { CreateMySeries, GetMyDiary } from './RESTapi';
import styles from './SearchCss/CreateSeries.module.css';
import { useNavigate } from 'react-router-dom';

const CreateSeries = ({ ACCESS_TOKEN }) => {
  const [title, setTitle] = useState('');
  const [status, setStatus] = useState(1);
  const [diaries, setDiaries] = useState([]);
  const [selectedDiaries, setSelectedDiaries] = useState([]);
  const [imageFile, setImageFile] = useState(null);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    fetchDiaries();
  }, []);

  const fetchDiaries = async () => {
    try {
      const fetchedDiaries = await GetMyDiary(ACCESS_TOKEN);
      setDiaries(fetchedDiaries);
    } catch (error) {
      console.error('다이어리 가져오기 실패:', error);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    let imagePath = '/images/diary.png';
    if (imageFile) {
      imagePath = URL.createObjectURL(imageFile);
    }

    const seriesData = {
      title,
      status,
      imagePath,
      diaries: selectedDiaries,
    };

    try {
      const response = await CreateMySeries(seriesData, ACCESS_TOKEN);
      console.log('시리즈 작성 완료:', response);
      navigate('/seriesResult'); // 성공적으로 시리즈를 작성한 후 /seriesResult로 이동
    } catch (err) {
      setError('시리즈 작성에 실패했습니다');
    }
  };

  const toggleStatus = () => {
    setStatus((prevStatus) => (prevStatus === 1 ? 0 : 1));
  };

  const handleDiarySelection = (diaryId) => {
    setSelectedDiaries((prevSelectedDiaries) =>
      prevSelectedDiaries.includes(diaryId)
        ? prevSelectedDiaries.filter((id) => id !== diaryId)
        : [...prevSelectedDiaries, diaryId]
    );
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>시리즈 작성하기</h1>
      <form className={styles.form} onSubmit={handleSubmit}>
        <div>
          <label className={styles.label}>제목:</label>
          <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required className={styles.textInput} />
        </div>
        <div>
          <label className={styles.label}>시리즈 대표사진:</label>
          <input type="file" onChange={(e) => setImageFile(e.target.files[0])} />
        </div>
        <div>
          <label className={styles.label}>공개여부:</label>
          <button type="button" onClick={toggleStatus} className={status === 1 ? styles.public : styles.private}>
            {status === 1 ? '공개' : '비공개'}
          </button>
        </div>
        <div>
          <h2 className={styles.diariesSelection}>일기 선택하기</h2>
          {diaries.map((diary) => (
            <div key={diary.id} className={styles.diaryItem}>
              <input
                type="checkbox"
                checked={selectedDiaries.includes(diary.id)}
                onChange={() => handleDiarySelection(diary.id)}
                className={styles.checkbox}
              />
              <div className={styles.diaryContent}>
                <label>{diary.title}</label>
                <label>{new Date(diary.date).toISOString().split('T')[0]}</label>
              </div>
            </div>
          ))}
        </div>
        <button type="submit" className={styles.button}>나만의 시리즈 작성하기</button>
      </form>
      {error && <p className={styles.errorMessage}>{error}</p>}
    </div>
  );
};

CreateSeries.propTypes = {
  ACCESS_TOKEN: PropTypes.string.isRequired,
};

export default CreateSeries;
