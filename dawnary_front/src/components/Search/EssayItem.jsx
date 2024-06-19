import PropTypes from "prop-types";

const EssayItem = ({ essay, className }) => (
  <div className={`essay-item ${className}`}>
    <h3>{essay.title}</h3>
    <p>{essay.content}</p>
    <div>👍 {essay.likes} 좋아요</div>
  </div>
);

EssayItem.propTypes = {
  essay: PropTypes.shape({
    title: PropTypes.string.isRequired,
    content: PropTypes.string.isRequired,
    likes: PropTypes.number.isRequired
  }).isRequired,
  className: PropTypes.string
};

export default EssayItem;
