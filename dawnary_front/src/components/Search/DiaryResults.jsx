import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { getDiaryByKeyword } from './RESTapi';

const DiaryResults = ({ keyword }) => {
  const [diaries, setDiaries] = useState([]);

  useEffect(() => {
    const fetchDiaries = async () => {
      try {
        const diaryData = await getDiaryByKeyword(keyword);
        setDiaries(diaryData);
      } catch (error) {
        console.error('일기 검색에 실패하였습니다', error);
      }
    };

    fetchDiaries();
  }, [keyword]);

  return (
    <div>
      <h3>일기 검색결과</h3>
      {diaries && diaries.length > 0 ? (
        <ul>
          {diaries.map((entry) => (
            <li key={entry.id}>{entry.title}</li>
          ))}
        </ul>
      ) : (
        <p>일기 검색 결과가 없습니다.</p>
      )}
    </div>
  );
};

DiaryResults.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default DiaryResults;
