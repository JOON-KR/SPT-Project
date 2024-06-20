import { useState, useCallback } from "react";
import SearchResults from "./SearchResults";

import styles from "./SearchCss/SearchBar.module.css"

const SearchBar = () => {
  const [keyword, setKeyword] = useState("");
  const [searchKeyword, setSearchKeyword] = useState("");

  const handleSearch = useCallback(() => {
    setSearchKeyword(keyword);
  }, [keyword]);

  return (
    <div className={styles.container}>
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

export default SearchBar;
