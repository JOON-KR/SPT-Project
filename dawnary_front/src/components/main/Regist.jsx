import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Regist.css";

const Regist = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
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
      const response = await axios.post("http://localhost:8080/member/signup", {
        email,
        password,
        name,
      });

      if (response.status === 200) {
        alert("회원가입이 성공적으로 완료되었습니다.");
        nav("/login");
      }
    } catch (error) {
      setMessage("회원가입 중 오류가 발생했습니다.");
      console.error("There was an error!", error);
    }
  };

  // const handleSocialRegist = (platform) => {
  //   if (platform === "naver") {
  //     // 네이버 소셜 로그인 처리
  //   } else if (platform === "kakao") {
  //     // 카카오 소셜 로그인 처리
  //   }
  // };

  return (
    <div className="container">
      <div className="regist-box">
        <h1>회원가입</h1>
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
            <label htmlFor="name">이름</label>
            <input
              type="text"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <button type="submit">회원가입</button>
        </form>
        {message && <p>{message}</p>}
        <hr />
        <div>소셜 계정으로 간편 가입하기</div>
        <div className="social-regist">
          {/* <div className="kakao" onClick={() => handleSocialRegist("kakao")}>
            카카오
          </div> */}
          <div className="naver" id="naverIdLogin">
            네이버
          </div>
        </div>
        <div>
          이미 계정이 있나요? <Link to={"/login"}>로그인</Link>
        </div>
      </div>
    </div>
  );
};

export default Regist;
