import PropTypes from "prop-types";

const FilterButtons = ({ onFilterChange }) => (
  <div>
    <button onClick={() => onFilterChange("latest")}>최신순</button>
    <button onClick={() => onFilterChange("likes")}>좋아요순</button>
  </div>
);

FilterButtons.propTypes = {
  onFilterChange: PropTypes.func.isRequired,
};

export default FilterButtons;
