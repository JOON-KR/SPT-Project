import MainPage from "./components/main/MainPage";
import Login from "./components/main/Login";
import MyPage from "./components/main/MyPage";
import Regist from "./components/main/Regist";
import UserPage from "./components/main/UserPage";
import KakaoLogin from "./components/main/KakaoLogin";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/login" element={<Login />} />
      <Route path="/regist" element={<Regist />} />
      <Route path="/myPage" element={<MyPage />} />
      <Route path="/userPage" element={<UserPage />} />
      <Route path="/kakaoLogin" element={<KakaoLogin />} />
    </Routes>
  );
}

export default App;
