const Login = () => {
  return (
    <div>
      <div>
        <h1>로그인</h1>
      </div>
      <div>
        <input type="text" id="email" />
        <label htmlFor="email"></label>
      </div>
      <div>
        <input type="password" id="password" />
        <label htmlFor="password"></label>
      </div>
      <div>
        <button>로그인</button>
      </div>
      <hr />
      <div className="social-login">
        <div className="kakao">카카오</div>
        <div className="naver">네이버</div>
      </div>
    </div>
  );
};

export default Login;
