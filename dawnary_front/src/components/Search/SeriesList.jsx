import { useEffect, useState } from "react";
import SeriesFilter from "./SeriesFilter"
import SeriesItem from "./SeriesItem";
import styles from "./SearchCss/SeriesList.module.css"
// import { getAllEssays } from "./RESTapi"; // api 호출 해서 테스트 할 때 주석 해제 하면 됨


const dummySeries = [ // 테스트용 더미 데이터
  {
    title: "첫 번째 에세이",
    content: "이것은 첫 번째 에세이의 내용입니다.",
    writer: "작성자1",
    likes: 10,
    thumbnail: "https://via.placeholder.com/100",
    date: new Date(2023, 0, 1) 
  },
  {
    title: "두 번째 에세이",
    content: "이것은 두 번째 에세이의 내용입니다.",
    writer: "작성자2",
    likes: 25,
    thumbnail: "https://via.placeholder.com/100",
    date: new Date(2023, 0, 2) 
  },
  {
    title: "세 번째 에세이",
    content: "이것은 세 번째 에세이의 내용입니다.",
    writer: "작성자3",
    likes: 5,
    thumbnail: "https://via.placeholder.com/100",
    date: new Date(2023, 0, 3) 
  },
  {
    title: "네 번째 에세이",
    content: "이것은 네 번째 에세이의 내용입니다.",
    writer: "작성자4",
    likes: 16,
    thumbnail: "https://via.placeholder.com/100",
    date: new Date(2023, 0, 4) 
  },
  {
    title: "다섯 번째 에세이",
    content: "이것은 다섯 번째 에세이의 내용입니다.",
    writer: "작성자5",
    likes: 8,
    thumbnail: "https://via.placeholder.com/100",
    date: new Date(2023, 0, 5)
  }
];

console.log(dummySeries)

const SeriesList = () => {

  const [series, setSeries] = useState([]);
  useEffect(() => {
    // API 호출 대신 더미 데이터로 컴포넌트 테스트
    setSeries(dummySeries);
  }, []);


  const [filter, setFilter] = useState("latest");

  const handleFilterChange = (selectedFilter) => {
    setFilter(selectedFilter);
  };

  const sortedSeries = series.sort((a, b) => {
    if (filter === "latest") {
      return b.date - a.date;
    } else if (filter === "likes") {
      return b.likes - a.likes;
    }
    return 0;
  });


  // useEffect(() => {
  //   const getEssays = async () => {
  //     const essaysData = await getAllEssays();
  //     setEssays(essaysData);
  //   };

  //   getEssays();
  // }, []); api 호출 영역

    return (
    <div>
      <SeriesFilter onFilterChange={handleFilterChange} />
      <div className={styles.essayList}>
        {sortedSeries.map((series, index) => (
          <SeriesItem key={index} series={series} />
        ))}
      </div>
    </div>
  );
};

export default SeriesList;
