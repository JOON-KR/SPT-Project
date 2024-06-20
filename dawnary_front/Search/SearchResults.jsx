import PropTypes from "prop-types";
import { useEffect, memo, useState } from "react";
import UserResults from "./UserResults.jsx";
import SeriesResults from "./SeriesResults.jsx";
import DiaryResults from "./DiaryResults.jsx";
import Header from "./Header.jsx";

const MemoizedHeader = memo(Header);

const SearchResults = ({ keyword }) => {
  const [internalKeyword, setInternalKeyword] = useState(keyword);

  useEffect(() => {
    setInternalKeyword(keyword);
  }, [keyword]);

  return (
    <div>
      <MemoizedHeader />
      <UserResults keyword={internalKeyword} />
      <SeriesResults keyword={internalKeyword} />
      <DiaryResults keyword={internalKeyword} />
    </div>
  );
};

SearchResults.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default SearchResults;
