import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import queryString from "query-string";
import base64 from "base-64";
import utf8 from "utf8";
import useUserStore from "../../stores/user";

const NaverLogin = () => {
  const navigate = useNavigate();
  const password = "qwerqwer!"; // 비밀번호는 변경되지 않는 것으로 가정

  const { setAllUserEmail } = useUserStore();

  useEffect(() => {
    const fetchAllUserEmails = async () => {
      try {
        const response = await axios.get("http://localhost:8080/member/all");
        if (response.status === 200) {
          console.log(response.data.emails);
          setAllUserEmail(response.data.emails);
        }
      } catch (error) {
        console.error("Failed to fetch all user emails", error);
      }
    };

    fetchAllUserEmails();
  }, [setAllUserEmail]);

  useEffect(() => {
    const signUpAndLogin = async (email, name) => {
      try {
        const response = await axios.get("http://localhost:8080/member/all");
        const allUserEmail = response.data.emails;

        const isExist = allUserEmail.includes(email);
        if (!isExist) {
          const signUpResponse = await axios.post(
            "http://localhost:8080/member/signup",
            { email, password, name }
          );

          if (signUpResponse.status === 200) {
            console.log("회원가입이 성공적으로 완료되었습니다.");

            const loginResponse = await axios.post(
              "http://localhost:8080/member/login",
              { email, password }
            );

            if (loginResponse.status === 200) {
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
              navigate("/mainCalendar"); // 로그인 후 이동할 페이지로 이동합니다.
            }
          }
        } else {
          const loginResponse = await axios.post(
            "http://localhost:8080/member/login",
            { email, password }
          );

          if (loginResponse.status === 200) {
            const token = loginResponse.data.accessToken;
            const refreshToken = loginResponse.data.refreshToken;
            const payload = token.substring(
              token.indexOf(".") + 1,
              token.lastIndexOf(".")
            );
            const dec = base64.decode(payload.trim());
            const dec_utf8 = utf8.decode(dec);
            //세션스토리지에 백엔드 서버에서 받아온 토큰(원본), 토큰 디코딩해서 로그인유저 정보 저장
            sessionStorage.setItem("token", token);
            sessionStorage.setItem("loginUser", dec_utf8);
            sessionStorage.setItem("socialLogin", "none");
            //리프레쉬토큰 저장
            sessionStorage.setItem("refresh_token", refreshToken);
            alert("로그인이 성공적으로 완료되었습니다.");
            navigate("/mainCalendar"); // 로그인 후 이동할 페이지로 이동합니다.
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
            const userEmail = naverLogin.user.getEmail();
            const userName = naverLogin.user.getNickName();

            const { access_token } = queryString.parse(
              window.location.hash.substring(1)
            );
            sessionStorage.setItem("naver_access_token", access_token);
            sessionStorage.setItem("socialLogin", "naver");

            signUpAndLogin(userEmail, userName);
          } else {
            console.error("네이버 로그인에 실패했습니다.");
            navigate("/login");
          }
        });
      }
    };

    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, [navigate]);

  return <div>로그인 중...</div>;
};

export default NaverLogin;
