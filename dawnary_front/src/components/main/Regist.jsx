const Regist = () => {
  return (
    <div>
      <h1>회원가입</h1>
      <div>
        <div>
          <label htmlFor="email">이메일</label>
          <input type="text" id="email" />
        </div>
        <div>
          <label htmlFor="password">비밀번호</label>
          <input type="password" id="password" />
        </div>
        <div>
          <label htmlFor="name">닉네임</label>
          <input type="text" id="name" />
        </div>
        <hr />
        <div>소셜 계정으로 간편 가입하기</div>
        <div className="social-regist">
          <div className="kakao">카카오</div>
          <div className="naver">네이버</div>
        </div>
      </div>
    </div>
  );
};

export default Regist;
