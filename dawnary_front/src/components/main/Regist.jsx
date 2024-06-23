import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./Regist.css";
import { FloatingLabel, Form } from "react-bootstrap";

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
          loginButton: { color: "green", type: 3, height: "50" },
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
      <div className="login-box">
        <h1 className="mb-4">회원가입</h1>
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
            autoComplete="off"
            required
          />
        </FloatingLabel>

        <FloatingLabel
          controlId="nickName"
          label="Nickname"
          style={{ width: "70%", marginTop: "16px" }}
        >
          <Form.Control
            type="text"
            placeholder="Nickname"
            className="input-box"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            require
          />
        </FloatingLabel>

        <div style={{ width: "70%" }}>
          <button
            type="submit"
            onClick={handleSubmit}
            style={{ width: "100%", margin: "20px 0" }}
          >
            회원가입
          </button>
        </div>
        {message && <p>{message}</p>}
        <hr />
        <div>소셜 계정으로 간편 가입하기</div>
        <div className="social-login">
          {/* <div className="kakao" onClick={() => handleSocialRegist("kakao")}>
            카카오
          </div> */}
          <div className="naver" id="naverIdLogin">
            네이버
          </div>
        </div>
        <div className="mt-3">
          이미 계정이 있나요?{" "}
          <Link to={"/login"} className="link">
            로그인
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Regist;
