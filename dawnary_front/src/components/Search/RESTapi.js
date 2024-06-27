import axios from 'axios'
import _ from 'lodash';

const BASE_URL = 'http://localhost:8080'

const ACCESS_TOKEN = sessionStorage.getItem("token")

// elasticSearch DB에 존재하는 키워드와 일치하는 유저 자동완성
export const AutoSearchUser = async (keyword) => {
  try {

    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };

    const response = await axios.get(`${BASE_URL}/search/auto/${keyword}` , config);

    const groupedData = _.groupBy(response.data, 'type');
    
    const memberResults = groupedData['member'] || [];
    console.log(memberResults);
    return memberResults;
  
  } catch (error) {
    console.error('검색에 실패하였습니다', error);
    throw error;
  }
}

// elasticSearch DB에 존재하는 키워드와 일치하는 시리즈 자동완성
export const AutoSearchSeries = async (keyword) => {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };
    const response = await axios.get(`${BASE_URL}/search/auto/${keyword}` , config);

    const groupedData = _.groupBy(response.data, 'type');
    
    const memberResults = groupedData['series'] || [];
    console.log(memberResults);
    return memberResults;
  
  } catch (error) {
    console.error('검색에 실패하였습니다', error);
    throw error;
  }
}

// 토큰값에 저장된 최근 검색어 목록을 불러와서 보여줌
export const AutoRecentSearch = async () => {
  
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };

    const response = await axios.get(`${BASE_URL}/search/recent` , config);
    
    console.log(response.data)
    return response.data; 

  } catch (error) {
    console.error('최근검색어 로딩에 실패하였습니다', error);
    throw error;
  }
}

// Keyword로 검색된 멤버를 컴포넌트로 뿌려주는 함수
export const SearchMemberByKeyword = async (keyword) => {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };
    
    const response = await axios.get(`${BASE_URL}/search/all/${keyword}`, config);
    
    const groupedData = _.groupBy(response.data, 'type');
    
    const memberResults = groupedData['member'] || [];
    console.log(memberResults);
    return memberResults;
  } catch (error) {
    console.error('멤버 검색에 실패하였습니다', error);
    throw error;
  }
};

// Keyword로 검색된 series를 컴포넌트로 뿌려주는 함수
export const SearchSeriesByKeyword = async (keyword) => {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };

    const response = await axios.get(`${BASE_URL}/search/all/${keyword}`, config);

    const groupedData = _.groupBy(response.data, 'type');

    const seriesResults = groupedData['series'] || [];
  
    return seriesResults;

  } catch (error) {
    console.error('시리즈 검색에 실패하였습니다', error);
    throw error;
  }
};

// 시리즈 등록하는 api 호출 영역
export const CreateMySeries = async (data) => {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
        'Content-Type': 'application/json',
      },
    };

    const response = await axios.post(`${BASE_URL}/series`, data, config);
    return response.data;
  } catch (error) {
    console.error('시리즈 등록 실패:', error);
    throw error;
  }
};

// 로그인한 유저가 작성한 다이어리 가져오는 api
export const GetMyDiary = async () => {
  
  const userJson = sessionStorage.getItem("loginUser"); // "loginUser" 키로 저장된 값을 가져옴
  const user = JSON.parse(userJson);
  const memberId = user.id;

  try {

    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };

    const response = await axios.get(`${BASE_URL}/diary/member/${memberId}`, config);
    return response.data;
  } catch (error) {
    console.error('일기 가져오기 실패:', error);
    throw error;
  }
};


// 시리즈 목록 불러오는 api 호출 영역
export const getAllSeries = async () => {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };
    const response = await axios.get(`${BASE_URL}/series/all` , config);
    return response.data;
  } catch (error) {
    console.error('시리즈 불러오기 실패:', error);
    throw error;
  }
};

// 명예의전당에 등록된 시리즈 불러오는 api 호출 영역
export const getBestSeries = async () => {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };

    const response = await axios.get(`${BASE_URL}/series/best` , config );
    return response.data;
  } catch (error) {
    console.error('명예의 전당 시리즈 불러오기 실패:', error);
    throw error;
  }
};

// 이달의 시리즈 불러오는 api 호출 영역
export const getMonthlySeries = async () => {
  try {

    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };

    const response = await axios.get(`${BASE_URL}/series/month` , config);
    return response.data;
  } catch (error) {
    console.error('이달의 시리즈 불러오기 실패:', error);
    throw error;
  }
};


export const getSeriesDetail = async (seriesId) => {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${ACCESS_TOKEN}`,
      },
    };
  
    const response = await axios.get(`${BASE_URL}/series/${seriesId}` , config)
    return response.data
  } catch (error) {
    console.error('시리즈 디테일 불러오기 실패' , error)
    throw error
  }
}
