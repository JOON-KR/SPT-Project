import PropTypes from "prop-types";

const SearchFilter = ({ onFilterChange }) => {
  return (
    <div>
      <button onClick={() => onFilterChange("all")}>통합</button>
      <button onClick={() => onFilterChange("users")}>유저만</button>
      <button onClick={() => onFilterChange("series")}>시리즈만</button>
    </div>
  );
};

SearchFilter.propTypes = {
  onFilterChange: PropTypes.func.isRequired,
};

export default SearchFilter
