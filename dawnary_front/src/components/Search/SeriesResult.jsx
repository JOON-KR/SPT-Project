import PropTypes from "prop-types";
import SeriesList from "./SeriesList.jsx";
import Navbar from "./Navbar.jsx";


const SeriesResult = () => (
  <div>
    <Navbar />
    <SeriesList />
  </div>
);

SeriesResult.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default SeriesResult
