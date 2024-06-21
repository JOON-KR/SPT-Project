import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import queryString from "query-string";
import base64 from "base-64";
import utf8 from "utf8";
import useUserStore from "../../stores/user";

const NaverLogin = () => {
  const navigate = useNavigate();
  // const [userData, setUserData] = useState({ email: "", name: "" });
  const password = "Abcdefg999!"; // 비밀번호는 변경되지 않는 것으로 가정

  const { allUserEmail, setAllUserEmail } = useUserStore();

  useEffect(() => {
    const fetchAllUserEmails = async () => {
      try {
        const response = await axios.get("http://localhost:8080/member/all");
        if (response.status === 200) {
          console.log(response.data.emails);
          setAllUserEmail(response.data.emails);
          console.log(allUserEmail);
        }
      } catch (error) {
        console.error("Failed to fetch all user emails", error);
      }
    };

    fetchAllUserEmails();

    const signUpAndLogin = async (email, name) => {
      try {
        console.log(allUserEmail);
        //회원가입 전에 이미 등록되어있는지 체크 하기
        const isExist = allUserEmail.includes(email);
        console.log(isExist);
        if (!isExist) {
          // 회원가입 요청
          const signUpResponse = await axios.post(
            "http://localhost:8080/member/signup",
            {
              email: email,
              password,
              name: name,
            }
          );

          if (signUpResponse.status === 200) {
            console.log("회원가입이 성공적으로 완료되었습니다.");

            // 로그인 요청
            const loginResponse = await axios.post(
              "http://localhost:8080/member/login",
              {
                email: email,
                password,
              }
            );

            if (loginResponse.status === 200) {
              const token = loginResponse.data;
              const payload = token.substring(
                token.indexOf(".") + 1,
                token.lastIndexOf(".")
              );
              const dec = base64.decode(payload);
              const dec_utf8 = utf8.decode(dec);
              //세션스토리지에 백엔드 서버에서 받아온 토큰(원본), 토큰 디코딩해서 로그인유저 정보 저장
              sessionStorage.setItem("token", token);
              sessionStorage.setItem("loginUser", dec_utf8);
              alert("로그인이 성공적으로 완료되었습니다.");
              navigate("/mainCalendar");
            }
          }
        } else {
          //이미 있는 회원인 경우 로그인만 요청
          // 로그인 요청
          const loginResponse = await axios.post(
            "http://localhost:8080/member/login",
            {
              email: email,
              password,
            }
          );

          if (loginResponse.status === 200) {
            const token = loginResponse.data;
            const payload = token.substring(
              token.indexOf(".") + 1,
              token.lastIndexOf(".")
            );
            const dec = base64.decode(payload);
            const dec_utf8 = utf8.decode(dec);
            //세션스토리지에 백엔드 서버에서 받아온 토큰(원본), 토큰 디코딩해서 로그인유저 정보 저장
            sessionStorage.setItem("token", token);
            sessionStorage.setItem("loginUser", dec_utf8);
            alert("로그인이 성공적으로 완료되었습니다.");
            navigate("/mainCalendar");
          }
        }
      } catch (error) {
        alert("회원가입 또는 로그인 중 오류가 발생했습니다.");
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
            // setUserData({ email: userEmail, name: userName });

            const { access_token } = queryString.parse(
              window.location.hash.substring(1)
            );
            // 세션 스토리지에 네이버 엑세스토큰, 소셜 로그인 정보 저장
            sessionStorage.setItem("access_token", access_token);
            sessionStorage.setItem("socialLogin", "naver");

            // 네이버 로그인한 유저 회원가입 및 로그인
            signUpAndLogin(userEmail, userName);
          } else {
            console.error("네이버 로그인에 실패했습니다.");
            navigate("/login"); // 로그인 페이지로 리디렉션
          }
        });
      }
    };

    document.body.appendChild(script);

    // useEffect 클린업 함수에서 스크립트 제거하는 것도 고려해보세요.
    return () => {
      document.body.removeChild(script);
    };
  }, [navigate, setAllUserEmail, allUserEmail]);

  return <div>로그인 중...</div>;
};

export default NaverLogin;
