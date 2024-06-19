import { useState } from "react";
import PropTypes from "prop-types";
import SearchResults from "./SearchResults";

const SearchBar = () => {
  const [keyword, setKeyword] = useState("");
  const [searchKeyword, setSearchKeyword] = useState("");

  const handleSearch = () => {
    setSearchKeyword(keyword);
  };

  return (
    <div>
      <input 
        type="text" 
        value={keyword} 
        onChange={(e) => setKeyword(e.target.value)} 
        placeholder="검색어를 입력하세요" 
      />
      <button onClick={handleSearch}>검색</button>
      {searchKeyword && <SearchResults keyword={searchKeyword} />}
    </div>
  );
};

SearchBar.propTypes = {
  onSearch: PropTypes.func.isRequired
};

export default SearchBar;
