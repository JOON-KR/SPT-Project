import PropTypes from "prop-types";

const EssayFilter = ({ onFilterChange }) => (
  <div>
    <button onClick={() => onFilterChange("latest")}>최신순</button>
    <button onClick={() => onFilterChange("likes")}>좋아요순</button>
  </div>
);

EssayFilter.propTypes = {
  onFilterChange: PropTypes.func.isRequired,
};

export default EssayFilter;
