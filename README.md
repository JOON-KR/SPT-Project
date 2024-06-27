# 🌅 Dawnary 🌅  / 프로젝트 회고록

## 프로젝트 개요
프로젝트 **[Dawnary]**는 달력 & 감정 일기장 공유 서비스를 제공하는 플랫폼을 구현하고자 하였습니다. 회원들이 일기를 통해 감정을 표현하고 공유하는 공간을 제공합니다.

##  🛠️ 프로젝트 핵심 기능
- **유저 일기 작성**: 일기 작성 및 감정 표현
- **일기 감정 분석**: 일기 내용의 텍스트를 읽고 감정 분석 기능
- **유저 간 일기 공유**: 작성한 일기를 공유하고 감정을 공유하는 기능
- **일정 관리**: 개인 일정 기록과 관리

##  👨🏻‍💻 사용 기술 / 환경
###   Frontend:
- 언어: JavaScript
- 프레임워크: React.js / VITE
- 라이브러리: axios, react-router-dom, lodash, react bootstrap, fullCalendar, zustand, QueryDsl

###  Backend:
- 언어: JAVA 
- 인프라: nginx , Elastic Stack
- 프레임워크: Spring Boot , Spring Security 
- 데이터베이스: MySql, redis , Elastic Search

---
# 구현 페이지

## 메인 페이지
### 사용 기술
- 마우스 애니메이션 훅
### 구현 내용
- 로그인 / 비로그인 상태 관리
- 로고 시각화처리
### 👨🏻‍💻 시연 영상

## 마이 페이지
### 사용 기술
- 감정 캘린더 : react-calendar라이브러리 커스텀
- BootStrap
- Offcanvas side bar
### 구현 내용
- 마이페이지 CRUD
- 팔로잉 피드 확인
- 감정 캘린더 : 해당 날짜의 감정을 이모티콘으로 시각화, 클릭시 확인
### 👨🏻‍💻 시연 영상
#### 내 일기&시리즈 조회
![마이페이지_내일기_내시리즈조회](https://github.com/JOON-KR/SPT-Project/assets/52641434/21d7d20d-7031-45d1-86cc-4d8c1e49b0d0)
#### 팔로잉 일기 조회
![마이페이지_팔로잉일기조회](https://github.com/JOON-KR/SPT-Project/assets/52641434/7a6e16b1-ed1b-4ab8-ad26-8ae6fa136f15)

## 로그인 페이지
### 사용 기술
- SpringSecurity
- JWT
- Redis
- JPA
- Naver Login API
### 구현 내용
- access / refresh 토큰 사용하여 로그인 상태 보안 관리
- ADMIN과 MEMBER 권한 구분 : 권한 별 접근 가능 페이지 구분
- 비밀번호 암호화
- Redis를 활용하여 로그아웃 및 로그인 상태관리
- JPA의 Valid 어노테이션으로 회원가입시 유효성 검사처리
- 네이버 로그인 연동
### 👨🏻‍💻 시연 영상
#### 회원가입
![메인화면_회원가입](https://github.com/JOON-KR/SPT-Project/assets/52641434/92388d3e-7be6-4db4-9976-cdae39c56954)
#### 로그인
![로그인](https://github.com/JOON-KR/SPT-Project/assets/52641434/35acc8de-ee41-41f2-8d94-dbf76bdb9eec)


## 메인 달력 페이지
### 사용 기술 
- FullCalendar lib
- kakao Map API
- OpenWeather API
- GCP NLP API
- nginx
- UUID
- QueryDsl
### 구현 내용
- 스케쥴 입력 달력
- 스케쥴 및 다이어리 CRUD
- 스케쥴 위치 지도상 검색, 마킹
- 일기 작성시 날씨 자동생성
- 일기의 감정을 NLP-Sentiment로 분석하고 해당 날짜의 감정을 기록
- nginx 서버에 이미지 UUID로 이름 변환하여 저장
- record클래스를 사용하요 불변성 dto구성
- JPQL을 사용한 복잡한 쿼리처리
### 👨🏻‍💻 시연 영상
#### 스케줄 등록 & 조회
![일정등록](https://github.com/JOON-KR/SPT-Project/assets/52641434/d2b1fb42-23c1-4331-80d1-e3725a172898)
#### 다이어리 등록 & 조회
![일기등록](https://github.com/JOON-KR/SPT-Project/assets/52641434/16d5c33b-99e9-4435-b0e1-ee42d2bf1cfa)


## 통합 검색 페이지
### 사용 기술
- ElasticSearch
- Redis
- Lodash
- react-router-dom
### 구현 내용
- ElasticSearch를 사용한 빠른 검색, 자동완성 키워드 기능
- Redis를 사용한 최근 검색어 조회기능
- 시리즈 / 멤버를 구분하여 통합 검색어 추천 리스트 생성
- Lodash로 ES에 저장된 모든 키워드를 재정렬
### 👨🏻‍💻 시연 영상

## 시리즈 페이지
### 사용 기술
- Lodash
### 구현 내용
- 이달의 추천, 신작 시리즈 보기 기능
- 최신순, 추천순 정렬
- 명예의 전당

# ELK STACK
![image](https://github.com/JOON-KR/SPT-Project/assets/171137407/ab1459ad-38e2-49db-bb55-24d0a35d2928)

- 개발 단계에서의 디버그시 필요한 로그만을 수집해 빠르게 확인, 자주 발생하는 에러를 기록하고 공유
- 사용자의 어플리케이션 이용 로그를 저장하고 추적하는 기능 사용
- 이상 행동을 모니터링 하도록 대시보드화


## 경험 및 교훈
### 📌 Front - End
- **다운**: 제한된 시간 내에 결과를 내려다 보니 작동에만 집중해서 써보고 싶었던 기술을 다 못써본게 아쉬웠습니다. 디자인 적인 부분도 신경을 거의 쓰지 못해서 따로 더 공부하면서 프로젝트구조와 코드의 리팩토링을 진행하고 싶습니다. 
- **윤주**: 아쉬운 부분
 1. 카카오 로그인 구현 : 백엔드와 소통을 통해서 구현해야 하는 부분을 일정 및 개인 스케쥴로 인해 시간안에 구현하지 못했습니다.
 2. 프론트엔드 디자인 : Atomic design을 활용해서 재사용성을 늘리고 통일된 UX를 만들었으면 좋았을 것 같습니다.
 3. 스타일링 라이브러리 : 리액트가 아직 미숙하여 새로운 라이브러리를 추가하는데 어려움이 있었습니다.

  처음으로 프론트엔드와 백엔드를 분업하여 진행했던 프로젝트로, 많이 배우고 부족한 부분이 많다는 것도 깨달을 수 있었습니다.

- **준혁**: 리액트를 이용해 처음 만들어보는 프로젝트였습니다. 새로 배운 내용을 토대로 결과물을 내는 것이 쉽지만은 않았고, 프로젝트의 크기와 별개로 많은 양의 시간투자가 필요함을 깨달았습니다. 이번 사이드 프로젝트 경험을 통해 팀원간 잦은 모임이 필요함을 느꼈고, 협업과 개발환경을 일치시키는 것의 중요성도 많이 느낄 수 있었습니다. 

### 📌 Back - End
- **여준**: ElasticSearch의 쿼리를 제가 구상했던 대로 작성하는 것이 굉장히 어려웠고, 해당 쿼리대로 실행하기 위해 인덱스를 매핑하는 것을 공부하는 과정이 어려웠습니다. 또한 config 설정이 제대로 되지 않아서 시간을 오래 소비하였기에 다음부터는 이와같은 실수를 반복하지 않게 되었습니다. 또한 팀원이 많아지니 개발환경도 다양해져, 내 컴퓨터에선 되는데 너 컴퓨터에선 왜 안돼? 같은 경우가 많아져 빌드테스트를 하는 시간도 상당히 오래 걸리게 되었습니다. 그래도 2학기 프로젝트를 진행하기 앞서 처음으로 6명이라는 많은 인원을 바탕으로 프로젝트를 진행하면서 하지말아야할 것들을 많이 깨닫게 되어 다행이라는 생각이 들었습니다. 처음에는 새로 공부한 기술을 그저 적용해보자는 취지의 프로젝트 였으나 오히려 효율적으로 협업하는 방법을 깨우치게 되었고, 개발환경을 통합시키는 것보다 docker 같은 기술을 다같이 도입하는 것이 빠른 방법이라는 것을 몸소 체감하였습니다. 이번 프로젝트를 바탕으로 2학기를 확실하게 대비할 수 있게 되었습니다.
- **창호**: [경험 및 교훈]
- **서현**: 공부했던 JPA를 사용하여 프로젝트를 구현해볼 수 있어서 뿌듯했습니다. 좋은 팀원들과 함께해서 좋았고 2학기에도 같이 프로젝트를 진행하고 싶습니다.
다만 생각보다 로컬 환경 설정이 상이하여 기본 환경 셋팅에 시간이 오래걸렸고, 다음엔 꼭 도커를 사용해보고 싶습니다.

---

## 개선 사항
### 📌 Front - End
- **다운**:
1. 특정 형식 파일 첨부 : 파일을 첨부하는 부분에서 작은 이슈가 있었습니다. 이미지 파일만을 업로드 가능하도록 설정하였는데, 옵션 수정을 누르면 모든 파일이 업로드 가능하게 되었습니다. 디버그 끝에 파일명에서 확장자를 체크하는 함수를 추가해서 해결하였고 원하는 결과를 얻게 되었습니다.
2. Axios Interceptor 구현 : Axios 인터셉터를 사용하여 요청 시 헤더에 토큰을 추가하고, 응답에서 401 에러 발생 시 accessToken을 재발급해주었습니다. 이로 인해 코드의 반복이 줄어들고, 토큰 만료 시에도 기능의 중단 없이 작동하도록 개선되었습니다.
- **윤주**: 인풋 태그의 값이 바뀔때마다 렌더링되는 성능이 안 좋은 코드가 있었고, 로그인 버튼을 클릭했을 때만 바뀌도록 수정하여 성능을 개선하였습니다.
- **준혁**: elastic DB에 저장되어 있는 데이터를 가져와서 lodash를 이용해 타입별로 분류하는 작업이 필요했는데, 하나의 함수로 처리할 수 있는 방법이 있었지만 리액트 상태관리에 익숙하지 않아 3개의 컴포넌트에서 기능이 같지만 이름만 다르게 한 함수를 각각 호출해야 했습니다. 다음번엔 한 번의 함수 호출로 데이터를 가져와 분류후 각각 다른 컴포넌트에 전달하는 방법으로 프로젝트를 진행하고 싶습니다.

### 📌 Back - End
- **여준**:
1. ElasticSearch의 AutoComplete 의 경우 지속적인 api의 호출로 인해 서버에 과부하를 가져올 수 있었습니다. 따라서 불러오는 데이터를 Search 결과로 통합하여 한번의 api를 호출하여 모든 통합검색 데이터를 1개의 List로 반환할 수 있게 만들어 서버 과부하를 줄였습니다.
2. 최근검색어를 ElasticSearch에서 Redis로 전환하여 이 또한 서버과부하를 최소화 하기위해 노력하였습니다. 
3. AutoComplete기능을 구현할 때, keyup기능으로 인해 입력이 발생할 때마다 api를 호출하는 방식이었는데, 이 부분이 서버에 부담을 준다고 생각하여 프론트와 긴밀한 협업을 통해 React에서 lodash를 도입하고, 연속적인 입력이 있을 때는 api호출을 지연시키는 방식을 채택하여 서버부담을 줄였습니다.


- **창호**: [아쉬운 점 및 개선 사항]
- **서현**: [아쉬운 점 및 개선 사항]

---

## 결론
**[Dawnary]** 프로젝트를 통해 개인의 감정을 솔직하게 표현하고 공유하는 소중한 공간을 만들었습니다. 앞으로 이 경험을 통해 더 나은 프로젝트를 이끌어가고자 합니다.
