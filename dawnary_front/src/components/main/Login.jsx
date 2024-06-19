import { Link } from "react-router-dom";
import { useEffect } from "react";
import "./Login.css";

const Login = () => {
  //네이버 로그인 라이브러리를 스크립트로 삽입
  useEffect(() => {
    const script = document.createElement("script");
    script.src = "https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js";
  });
  return (
    <div className="container">
      <div className="login-box">
        <div>
          <h1>로그인</h1>
        </div>
        <div>
          <label htmlFor="email">이메일</label>
          <input type="text" id="email" />
        </div>
        <div>
          <label htmlFor="password">비밀번호</label>
          <input type="password" id="password" />
        </div>
        <div>
          <button>로그인</button>
        </div>
        <div>
          계정이 없으신가요? <Link to={"/regist"}>회원가입</Link>
        </div>
        <hr />
        <div>소셜 계정으로 간편 로그인</div>
        <div className="social-login">
          <div className="kakao">카카오</div>
          <div className="naver">네이버</div>
        </div>
      </div>
    </div>
  );
};

export default Login;
