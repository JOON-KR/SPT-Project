import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FloatingLabel, Form } from "react-bootstrap";
import axios from "axios";
import base64 from "base-64";
import utf8 from "utf8";
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
          loginButton: {
            color: "green",
            type: 3,
            height: "50",
            // width: "266",
          },
        });
        naverLogin.init();
      }
    };
    document.body.appendChild(script);
  }, []);

  //일반 로그인
  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/member/login", {
        email,
        password,
      });

      if (response.status === 200) {
        const token = response.data.accessToken;
        const refreshToken = response.data.refreshToken;
        const payload = token.substring(
          token.indexOf(".") + 1,
          token.lastIndexOf(".")
        );
        const dec = base64.decode(payload);
        const dec_utf8 = utf8.decode(dec);
        //세션스토리지에 백엔드 서버에서 받아온 토큰(원본), 토큰 디코딩해서 로그인유저 정보 저장
        sessionStorage.setItem("token", token);
        sessionStorage.setItem("loginUser", dec_utf8);
        sessionStorage.setItem("socialLogin", "none");
        //리프레쉬토큰 저장
        sessionStorage.setItem("refresh_token", refreshToken);
        alert("로그인이 성공적으로 완료되었습니다.");
        nav("/mainCalendar"); // 로그인 후 이동할 페이지로 이동합니다.
      }
    } catch (error) {
      setMessage("로그인 중 오류가 발생했습니다.");
      console.error("There was an error!", error);
    }
  };

  //비밀번호 입력 후 enter 키 뗄 때 로그인 실행
  const handleKeyup = (e) => {
    if (e.key === "Enter") {
      handleSubmit(e);
    }
  };

  return (
    <div className="container">
      <div className="login-box">
        <div>
          <h1 className="mb-4">로그인</h1>
        </div>

        <FloatingLabel
          controlId="email"
          label="Email"
          className="mb-3"
          style={{ width: "70%" }}
        >
          <Form.Control
            type="email"
            id="email"
            placeholder="name@example.com"
            className="input-box"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </FloatingLabel>

        <FloatingLabel
          controlId="password"
          label="Password"
          style={{ width: "70%" }}
        >
          <Form.Control
            type="password"
            placeholder="Password"
            className="input-box"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            onKeyUp={handleKeyup}
            autoComplete="off"
            required
          />
        </FloatingLabel>

        <div style={{ width: "70%" }}>
          <button
            type="submit"
            onClick={handleSubmit}
            style={{ width: "100%", margin: "20px 0" }}
          >
            로그인
          </button>
        </div>
        {message && <p>{message}</p>}
        <div>
          계정이 없으신가요?{" "}
          <Link to={"/regist"} className="link">
            회원가입
          </Link>
        </div>
        <hr />
        <div>소셜 계정으로 간편 로그인</div>
        <div className="social-login">
          {/* <div className="kakao">카카오</div> */}
          <div id="naverIdLogin" className="naver">
            네이버
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
