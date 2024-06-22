import PropTypes from "prop-types";
import styles from './SearchCss/SeriesFilter.module.css';

const SeriesFilter = ({ onFilterChange }) => (
  <div className={styles.essayFilter}>
    <button onClick={() => onFilterChange("latest")}>최신순</button>
    <button onClick={() => onFilterChange("likes")}>좋아요순</button>
  </div>
);

SeriesFilter.propTypes = {
  onFilterChange: PropTypes.func.isRequired,
};

export default SeriesFilter;
