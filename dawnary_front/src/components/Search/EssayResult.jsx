import PropTypes from "prop-types";
import EssayFilter from "../Search/EssayFilter.jsx";
import EssayList from "./EssayList.jsx";
import Navbar from "./Navbar.jsx";

const EssayResult = () => (
  <div>
    <Navbar />
    <EssayFilter />
    <EssayList />
  </div>
);

EssayResult.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default EssayResult
