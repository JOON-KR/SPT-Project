import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const NaverLogin = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({ email: "", name: "" });
  const password = "Abcdefg999!"; // 비밀번호는 변경되지 않는 것으로 가정

  useEffect(() => {
    const signUp = async () => {
      try {
        const response = await axios.post(
          "http://localhost:8080/member/signup",
          {
            email: userData.email,
            password,
            name: userData.name,
          }
        );

        if (response.status === 200) {
          alert("회원가입 및 로그인이 성공적으로 완료되었습니다.");
          navigate("/mainCalendar");
        }
      } catch (error) {
        alert("회원가입 중 오류가 발생했습니다.");
        console.error("에러 발생!", error);
      }
    };

    const script = document.createElement("script");
    script.src = "https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js";
    script.async = true;
    script.onload = () => {
      if (window.naver) {
        const naverLogin = new window.naver.LoginWithNaverId({
          clientId: import.meta.env.VITE_NAVER_CLIENT_ID,
          callbackUrl: "http://localhost:5173/naverLogin",
          isPopup: false,
        });

        naverLogin.init();

        naverLogin.getLoginStatus((status) => {
          if (status) {
            console.log(naverLogin.user);
            const userEmail = naverLogin.user.getEmail();
            const userName = naverLogin.user.getNickName();
            setUserData({ email: userEmail, name: userName });

            const { access_token } = queryString.parse(location.search);
            // 세션 스토리지에 사용자 정보 저장 (닉네임만 필요한 경우)
            sessionStorage.setItem("loginUser", userName);
            sessionStorage.setItem("access_token", access_token);

            // 네이버 로그인한 유저 회원가입
            signUp();
          } else {
            console.error("네이버 로그인에 실패했습니다.");
            navigate("/login"); // 로그인 페이지로 리디렉션
          }
        });
      }
    };

    document.body.appendChild(script);

    // useEffect 클린업 함수에서 스크립트 제거하는 것도 고려해보세요.
  }, [navigate, userData]);

  return <div>로그인 중...</div>;
};

export default NaverLogin;
