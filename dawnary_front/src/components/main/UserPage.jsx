import UserFeed from "./UserFeed";
import "./UserPage.css";

const UserPage = () => {
  const items = [
    "첫 번째 아이템",
    "두 번째 아이템",
    "세 번째 아이템",
    "네 번째 아이템",
    "다섯 번째 아이템",
    "여섯 번째 아이템",
    "일곱 번째 아이템",
    "여덟 번째 아이템",
    "아홉 번째 아이템",
    "열 번째 아이템",
    "열한 번째 아이템",
    "열두 번째 아이템",
  ];

  return (
    <div>
      <h1>다른 유저 페이지</h1>
      <div className="feed-box">
        <div className="series-feed">
          <UserFeed items={items} />
        </div>
        <div className="dairy-feed">
          <UserFeed items={items} />
        </div>
      </div>
    </div>
  );
};

export default UserPage;
