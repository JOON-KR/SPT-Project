import React, { useState, useEffect } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Setting = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");

  const nav = useNavigate();

  // sessionStorage에서 "loginUser" 데이터 가져오기
  const loginUser = JSON.parse(sessionStorage.getItem("loginUser"));

  //페이지 첫 로딩
  useEffect(() => {
    console.log("useEffect 실행");
    if (loginUser) {
      setEmail(loginUser.sub);
      setName(loginUser.name);
    }
  }, []);

  // 수정 버튼 눌렀을 때 동작할 함수
  const handleSubmit = async (e) => {
    e.preventDefault();

    const access_token = "Bearer " + sessionStorage.getItem("token");
    let requestBody = {};

    if (name !== loginUser.name) {
      requestBody.name = name;
    }

    if (password) {
      requestBody.password = password;
    }

    try {
      const response = await axios.put(
        "http://localhost:8080/member/update",
        requestBody,
        {
          headers: {
            Authorization: access_token,
          },
        }
      );
      alert("수정이 완료되었습니다.");

      // 응답에서 받은 name으로 loginUser 업데이트
      const updatedLoginUser = { ...loginUser, name: response.data.name };
      sessionStorage.setItem("loginUser", JSON.stringify(updatedLoginUser));

      nav("/myPage");
    } catch (error) {
      console.error("Error updating profile", error);
      alert("수정에 실패하였습니다.");
    }
  };

  // 회원 탈퇴 버튼 눌렀을 때 동작할 함수
  const handleDelete = async () => {
    const confirmDelete = window.confirm(
      "회원탈퇴 하시겠습니까? 회원님의 모든 정보가 사라집니다."
    );
    if (!confirmDelete) return;

    const access_token = "Bearer " + sessionStorage.getItem("token");

    try {
      await axios.delete("http://localhost:8080/member/delete", {
        headers: {
          Authorization: access_token,
        },
      });
      alert("회원탈퇴가 완료되었습니다.");
      sessionStorage.removeItem("loginUser");
      nav("/login");
    } catch (error) {
      console.error("Error deleting account", error);
      alert("회원탈퇴에 실패하였습니다.");
    }
  };

  return (
    <div className="container">
      <div className="login-box">
        <h2 className="mb-4">회원정보수정</h2>
        <FloatingLabel
          controlId="email"
          label="Email"
          className="mb-3"
          style={{ width: "70%" }}
        >
          <Form.Control
            type="email"
            placeholder="name@example.com"
            className="input-box"
            value={email}
            required
            disabled
            readOnly
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
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </FloatingLabel>

        <div style={{ width: "70%" }}>
          <button
            type="submit"
            onClick={handleSubmit}
            style={{ width: "100%", margin: "20px 0" }}
          >
            수정
          </button>
        </div>

        <a onClick={handleDelete} className="link">
          회원탈퇴
        </a>
      </div>
    </div>
  );
};

export default Setting;
