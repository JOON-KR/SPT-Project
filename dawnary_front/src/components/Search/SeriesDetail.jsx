import { useParams } from "react-router-dom";

const SeriesDetail = () => {
  const { seriesId } = useParams(); // 시리즈 ID 파라미터 가져오기

  // 가정한 시리즈 데이터
  const seriesData = {
    title: "시리즈 제목",
    background: "대표 배경 이미지 URL",
    diaries: [
      { id: 1, content: "일기 내용 1" },
      { id: 2, content: "일기 내용 2" },
      { id: 3, content: "일기 내용 3" },
      // 여러 개의 일기 데이터
    ],
  };

  return (
    <div>
      <div style={{ backgroundImage: `url(${seriesData.background})`, backgroundSize: "cover", height: "200px" }}>
        <h1>{seriesData.title}</h1>
      </div>
      <div style={{ maxHeight: "400px", overflowY: "auto" }}>
        {seriesData.diaries.map((diary) => (
          <div key={diary.id} style={{ border: "1px solid #ccc", padding: "10px", margin: "10px"}}>
            <p>{diary.content}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SeriesDetail;
