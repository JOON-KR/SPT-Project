import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import barStyles from './SearchCss/AutoComplete.module.css';
import { AutoSearch } from './RESTapi';

const AutoComplete = ({ searchTerm, onSuggestionClick }) => {
  const [suggestions, setSuggestions] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchSuggestions = async () => {
      if (!searchTerm.trim()) {
        setSuggestions([]);
        return;
      }

      setLoading(true);
      try {
        const response = await AutoSearch(searchTerm);
        if (Array.isArray(response)) {
          setSuggestions(response);
        } else {
          setSuggestions([]);
          console.error('Invalid response structure', response);
        }
      } catch (error) {
        console.error('자동완성 검색 오류:', error);
        setSuggestions([]);
      } finally {
        setLoading(false);
      }
    };

    fetchSuggestions();

  }, [searchTerm]);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (!suggestions.length) {
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
          {suggestion.name} ({suggestion.email})
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
