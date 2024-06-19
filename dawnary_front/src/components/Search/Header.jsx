import PropTypes from "prop-types";
import SearchBar from "./SearchBar.jsx";
import FilterButtons from "./SearchFilter.jsx";

const Header = ({ onSearch, onFilterChange }) => {
  return (
    <header>
      <h1>검색 헤더 입니다.</h1>
      <SearchBar onSearch={onSearch} />
      <FilterButtons onFilterChange={onFilterChange} />
    </header>
  );
};

Header.propTypes = {
  onSearch: PropTypes.func.isRequired,
  onFilterChange: PropTypes.func.isRequired,
};

export default Header;
