import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import barStyles from './SearchCss/AutoCompleteSeries.module.css';
import { AutoSearchSeries } from './RESTapi';

const AutoComplete = ({ searchTerm, onSuggestionClick }) => {
  const [suggestions, setSuggestions] = useState([]);

  useEffect(() => {
    const fetchSuggestions = async () => {
      if (searchTerm) {
        try {
          const response = await AutoSearchSeries(searchTerm);
          // response 배열이 올바른지 확인
          if (Array.isArray(response)) {
            setSuggestions(response);
          } else {
            setSuggestions([]);
            console.error('Invalid response structure', response);
          }
        } catch (error) {
          console.error('시리즈 자동완성 검색 오류:', error);
          setSuggestions([]);
        }
      } else {
        setSuggestions([]);
      }
    };

    const timerId = setTimeout(fetchSuggestions, 300); // 디바운싱을 위해 300ms 대기

    return () => {
      clearTimeout(timerId);
    };
  }, [searchTerm]);

  if (!suggestions.length) {
    return null;
  }

  return (
    <ul className={barStyles.autocompleteResults}>
      {suggestions.map((suggestion) => (
        <li
          key={suggestion.id}
          className={barStyles.suggestion}
          onClick={() => onSuggestionClick(suggestion.title)}
        >
          {suggestion.title} ({suggestion.name})
        </li>
      ))}
    </ul>
  );
};

AutoComplete.propTypes = {
  searchTerm: PropTypes.string.isRequired,
  onSuggestionClick: PropTypes.func.isRequired,
};

export default AutoComplete;