import PropTypes from "prop-types";
import { useEffect, useState } from 'react';
import SeriesList from "./SeriesList.jsx";
import Navbar from "./Navbar.jsx";

const SeriesResult = ({ keyword }) => {
  const [accessToken, setAccessToken] = useState(null);

  useEffect(() => {
    const token = sessionStorage.getItem('token');
    setAccessToken(token);
  }, []);

  if (!accessToken) {
    return <p>토큰이 유효하지 않습니다. 다시 로그인해 주세요.</p>;
  }

  return (
    <div>
      <Navbar />
      <SeriesList ACCESS_TOKEN={accessToken} />
    </div>
  );
};

SeriesResult.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default SeriesResult;
