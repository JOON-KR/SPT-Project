import { useState } from 'react';
import MainHeader from './MainHeader';
import UserSearchResults from './UserSearchResults';
import SeriesSearchResults from './SeriesSearchResults';
import "./SearchCss/Result.css"

const SearchResults = () => {
  const [keyword, setKeyword] = useState('');
  const [filter, setFilter] = useState('all');

  const handleSearch = (searchTerm) => {
    setKeyword(searchTerm);
  };

  const handleFilterChange = (newFilter) => {
    setFilter(newFilter);
  };

  return (
    <div>
      <MainHeader onSearch={handleSearch} onFilterChange={handleFilterChange} />
      {(filter === 'all' || filter === 'users') && <UserSearchResults keyword={keyword} />}
      {(filter === 'all' || filter === 'series') && <SeriesSearchResults keyword={keyword} />}
    </div>
  );
};

export default SearchResults