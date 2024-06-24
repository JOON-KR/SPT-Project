import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import barStyles from './SearchCss/AutoCompleteSeries.module.css';
import { AutoRecentSearch } from './RESTapi';

const RecentAutoComplete = ({ accessToken, onSuggestionClick, searchTerm }) => {
  const [suggestions, setSuggestions] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await AutoRecentSearch(accessToken);

        console.log(response); // 응답 데이터를 콘솔에 출력

        if (Array.isArray(response)) {
          setSuggestions(response.slice(0, 5)); // 최대 5개의 항목만 사용
        } else {
          setSuggestions([]);
          console.error('Invalid response structure', response);
        }
      } catch (error) {
        console.error('최근검색어 로드 오류:', error);
        setSuggestions([]);
      }
    };

    fetchData();
  }, [accessToken]);

  if (searchTerm.length === 0 || !suggestions.length) {
    return null;
  }

  return (
    <ul className={barStyles.autocompleteResults}>
      {suggestions.map((suggestion) => (
        <li
          key={suggestion.id}
          className={barStyles.suggestion}
          onClick={() => onSuggestionClick(suggestion)}
        >
          {suggestion}
        </li>
      ))}
    </ul>
  );
};

RecentAutoComplete.propTypes = {
  accessToken: PropTypes.string.isRequired, // 로그인된 토큰 추가
  onSuggestionClick: PropTypes.func.isRequired,
  searchTerm: PropTypes.string.isRequired, // 현재 입력된 검색어 추가
};

export default RecentAutoComplete;
