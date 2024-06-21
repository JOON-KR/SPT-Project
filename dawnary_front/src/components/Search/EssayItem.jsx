import PropTypes from 'prop-types';

const EssayItem = ({ essay, className }) => {
  // essay 객체가 없으면 기본 메시지 또는 로딩 메시지 출력
  if (!essay) {
    return <div className={`essay-item ${className}`}>에세이가 존재하지 않습니다.</div>;
  }

  return (
    <div className={`essay-item ${className}`}>
      <h3>{essay.title}</h3>
      <p>{essay.content}</p>
      <p>{essay.writer}</p>
      <div>👍 {essay.likes} 좋아요</div>
    </div>
  );
};

EssayItem.propTypes = {
  essay: PropTypes.shape({
    title: PropTypes.string.isRequired,
    content: PropTypes.string.isRequired,
    writer: PropTypes.string.isRequired,
    likes: PropTypes.number.isRequired
  }).isRequired,
  className: PropTypes.string
};

EssayItem.defaultProps = {
  className: ''
};

export default EssayItem;
