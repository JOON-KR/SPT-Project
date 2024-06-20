import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const nav = useNavigate();

  // 네이버 로그인 라이브러리를 스크립트로 삽입
  useEffect(() => {
    const script = document.createElement("script");
    script.src = "https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js";
    script.async = true;
    script.onload = () => {
      // 네이버 로그인 버튼 생성
      if (window.naver) {
        const naverLogin = new window.naver.LoginWithNaverId({
          clientId: import.meta.env.VITE_NAVER_CLIENT_ID,
          callbackUrl: "http://localhost:5173/naverLogin",
          isPopup: false,
          loginButton: { color: "green", type: 3, height: "40" },
        });
        naverLogin.init();
      }
    };
    document.body.appendChild(script);
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/member/login", {
        email,
        password,
      });

      if (response.status === 200) {
        alert("로그인이 성공적으로 완료되었습니다.");
        nav("/mainCalendar"); // 로그인 후 이동할 페이지로 이동합니다.
      }
    } catch (error) {
      setMessage("로그인 중 오류가 발생했습니다.");
      console.error("There was an error!", error);
    }
  };

  return (
    <div className="container">
      <div className="login-box">
        <div>
          <h1>로그인</h1>
        </div>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="email">이메일</label>
            <input
              type="text"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="password">비밀번호</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div>
            <button type="submit">로그인</button>
          </div>
        </form>
        {message && <p>{message}</p>}
        <div>
          계정이 없으신가요? <Link to={"/regist"}>회원가입</Link>
        </div>
        <hr />
        <div>소셜 계정으로 간편 로그인</div>
        <div className="social-login">
          <div className="kakao">카카오</div>
          <div id="naverIdLogin" className="naver">
            네이버
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
