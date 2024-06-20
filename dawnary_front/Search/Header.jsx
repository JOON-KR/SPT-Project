import PropTypes from "prop-types";
import SearchBar from "./SearchBar.jsx";
import FilterButtons from "./SearchFilter.jsx";

const Header = ({ onSearch, onFilterChange }) => {

  const hrStyle = {
    width : '100%'
  }

  return (
      <div>
      <SearchBar onSearch={onSearch} />
      <hr style={hrStyle}/>
      <FilterButtons onFilterChange={onFilterChange} />
    </div>
  );
};

Header.propTypes = {
  onSearch: PropTypes.func.isRequired,
  onFilterChange: PropTypes.func.isRequired,
};

export default Header;
