import PropTypes from "prop-types";
import UserResults from "./UserResults.jsx";
import SeriesResults from "./SeriesResults.jsx";
import DiaryResults from "./DiaryResults.jsx";

const SearchResults = ({ keyword }) => (
  <div>
    <UserResults keyword={keyword} />
    <SeriesResults keyword={keyword} />
    <DiaryResults keyword={keyword} />
  </div>
);

SearchResults.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default SearchResults;
