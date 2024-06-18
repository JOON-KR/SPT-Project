import './SideSlide.css';

const SideSlide = ({ date, onClose }) => {

    const today = date ? date.toLocaleDateString() : '';

    return (
        <div className="slide-create">
            <button className="close-button" onClick={onClose}>X</button>
            <h3>{today}</h3>
            <div className="btn-set">
                <button className="create-btn" onClick={onClose}>일정 등록하기</button>
                <button className="create-btn" onClick={onClose}>일기 작성하기</button>
            </div>
        </div>
    );
};

export default SideSlide;