import { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { getDiaryByKeyword } from "../RESTapi"

const DiaryResults = ({ keyword }) => {
  const [diaries, setDiaries] = useState([]);

  useEffect(() => {
    const fetchDiaries = async () => {
      try {
        const diaryData = await getDiaryByKeyword(keyword);
        setDiaries(diaryData);
      } catch (error) {
        console.error('검색에 실패하였습니다', error);
      }
    };

    fetchDiaries();
  }, [keyword]);

  return (
    <div>
      <h3>일기 검색결과</h3>
      <ul>
        {diaries && Array.isArray(diaries) && diaries.map((entry, index) => (
          <li key={index}>{entry.title}</li>
        ))}
      </ul>
    </div>
  );
};

DiaryResults.propTypes = {
  keyword: PropTypes.string.isRequired
};

export default DiaryResults;
