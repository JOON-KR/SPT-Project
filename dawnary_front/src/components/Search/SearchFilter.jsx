import PropTypes from "prop-types";
import styles from "./SearchCss/SearchFilter.module.css"

const SearchFilter = ({ onFilterChange }) => {
  return (
    <div className={styles.container}>
      <button className={styles.buttons} onClick={() => onFilterChange("all")}>통합 검색</button>
      <button className={styles.buttons} onClick={() => onFilterChange("users")}>유저 검색</button>
      <button className={styles.buttons} onClick={() => onFilterChange("series")}>시리즈만</button>
    </div>
  );
};

SearchFilter.propTypes = {
  onFilterChange: PropTypes.func.isRequired,
};

export default SearchFilter
