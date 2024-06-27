import MainPage from "./components/main/MainPage";
import Login from "./components/main/Login";
import MyPage from "./components/main/MyPage";
import Regist from "./components/main/Regist";
import UserPage from "./components/main/UserPage";
import KakaoLogin from "./components/main/KakaoLogin";
import MainCalendar from "./components/calendar/MainCalendar";
import { Routes, Route } from "react-router-dom";
import NaverLogin from "./components/main/NaverLogin";
import SearchResults from "./components/Search/SearchResults";
import SeriesResult from "./components/Search/SeriesResult";
import BestSeries from "./components/Search/BestSeries";
import MonthlySeries from "./components/Search/MonthlySeries";
import SeriesDetail from "./components/Search/SeriesDetail";
import Setting from "./components/main/Setting";
import CreateSeries from "./components/Search/CreateSeries";
import DiaryView from "./components/calendar/diary/DiaryView";

function App() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/login" element={<Login />} />
      <Route path="/regist" element={<Regist />} />
      <Route path="/myPage" element={<MyPage />} />
      <Route path="/userPage/:id" element={<UserPage />} />
      <Route path="/kakaoLogin" element={<KakaoLogin />} />
      <Route path="/mainCalendar" element={<MainCalendar />} />
      <Route path="/naverLogin" element={<NaverLogin />} />
      <Route path="/searchResult" element={<SearchResults />} />
      <Route path="/seriesResult" element={<SeriesResult />} />
      <Route path="/bestSeries" element={<BestSeries />} />
      <Route path="/monthlySeries" element={<MonthlySeries />} />
      <Route path="/series/:id" element={<SeriesDetail />} />
      <Route path="/setting" element={<Setting />} />
      <Route path="/createSeries" element={<CreateSeries />} />
      <Route path="/diary/:id" element={<DiaryView />} />
    </Routes>
  );
}

export default App;
