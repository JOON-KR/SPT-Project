import { useState } from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import UserAutoComplete from './UserAutoComplete';
import SeriesAutoComplete from './SeriesAutoComplete';
import filterStyles from './SearchCss/SearchFilter.module.css';
import barStyles from './SearchCss/SearchBar.module.css';
import RecentAutoComplete from './RecentAutoComplete';

const MainHeader = ({ onSearch, onFilterChange }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const accessToken = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJra0Bnb29nbGUuY29tIiwiaWQiOjIzLCJuYW1lIjoi6rmA66-86528Iiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MTkyMjQwMjQsImV4cCI6MTcyNTIyNDAyNH0.NmrdTENnyKmlFNdVUuVXbV1WNncWk6j9dOrKQc3BNvA'; // Replace with the true logic for accessToken retrieval

  const handleSearch = () => {
    onSearch(searchTerm);
    clearSearchTerm();
  };

  const handleSuggestionClick = (suggestion) => {
    setSearchTerm(suggestion.title);
    onSearch(suggestion.title);
    clearSearchTerm();
  };

  const clearSearchTerm = () => {
    setSearchTerm('');
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <header className={barStyles.header}>
      <div className={barStyles.container}>
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="검색어를 입력하세요..."
        />
        <button onClick={handleSearch}>검색</button>
        <Link to="/mainCalendar">
          <button className={barStyles.routeButton}>달력</button>
        </Link>
        <Link to="/myPage">
          <button className={barStyles.routeButton}>마이페이지</button>
        </Link>
        <Link to="/seriesResult">
          <button className={barStyles.routeButton}>전체 시리즈 조회</button>
        </Link>
        <Link to="/createSeries">
          <button className={barStyles.routeButton}>시리즈 등록하기</button>
        </Link>
      </div>
      <UserAutoComplete
        searchTerm={searchTerm}
        onSuggestionClick={handleSuggestionClick}
      />
      <SeriesAutoComplete
        searchTerm={searchTerm}
        onSuggestionClick={handleSuggestionClick}
      />
      <RecentAutoComplete
        accessToken={accessToken}
        searchTerm={searchTerm} // searchTerm을 RecentAutoComplete에 전달
        onSuggestionClick={handleSuggestionClick}
      />
      <div className={filterStyles.container}>
        <button className={filterStyles.buttons} onClick={() => onFilterChange('all')}>통합 검색</button>
        <button className={filterStyles.buttons} onClick={() => onFilterChange('users')}>유저 검색</button>
        <button className={filterStyles.buttons} onClick={() => onFilterChange('series')}>시리즈만</button>
      </div>
    </header>
  );
};

MainHeader.propTypes = {
  onSearch: PropTypes.func.isRequired,
  onFilterChange: PropTypes.func.isRequired,
};

export default MainHeader;
