import React, { useState, useEffect } from "react";
import { FloatingLabel, Form } from "react-bootstrap";

const Setting = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");

  useEffect(() => {
    // sessionStorage에서 "loginUser" 데이터 가져오기
    const loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
    if (loginUser) {
      setEmail(loginUser.sub);
      setName(loginUser.name);
    }
  }, []);

  const handleSubmit = () => {
    // 수정 버튼 눌렀을 때 동작할 함수
    console.log("수정 버튼이 눌렸습니다.");
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
            id="email"
            placeholder="name@example.com"
            className="input-box"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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
      </div>
    </div>
  );
};

export default Setting;
