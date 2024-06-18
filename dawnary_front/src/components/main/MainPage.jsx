import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import useMouseInteractive from "./useMouseInteractive";
import "../../css/MainPage.css";

const MainPage = () => {
  const nav = useNavigate();
  const frameRef = useRef(null);
  const logoRef = useRef(null);

  useMouseInteractive(frameRef, 16);
  useMouseInteractive(logoRef, 9);

  const goLogin = () => {
    nav("/login");
  };
  return (
    <main>
      <div ref={frameRef} className="frame">
        <h1 ref={logoRef}>Dawnary</h1>
      </div>
      <div className="login-div">
        <h2 className="login-hover" onClick={goLogin}>
          로그인
        </h2>
      </div>
    </main>
  );
};

export default MainPage;
