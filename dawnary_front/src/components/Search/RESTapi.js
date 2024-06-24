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


// 시리즈 목록 불러오는 api 호출 영역
export const getAllSeries = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/series/all`);
    return response.data;
  } catch (error) {
    console.error('시리즈 불러오기 실패:', error);
    throw error;
  }
};

// 명예의전당에 등록된 시리즈 불러오는 api 호출 영역
export const getBestSeries = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/series/best`);
    return response.data;
  } catch (error) {
    console.error('명예의 전당 시리즈 불러오기 실패:', error);
    throw error;
  }
};

// 이달의 시리즈 불러오는 api 호출 영역
export const getMonthlySeries = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/series/month`);
    return response.data;
  } catch (error) {
    console.error('이달의 시리즈 불러오기 실패:', error);
    throw error;
  }
};


export const getSeriesDetail = async (seriesId) => {
  try {
    const response = await axios.get(`${BASE_URL}/series/${seriesId}`)
    return response.data
  } catch (error) {
    console.error('시리즈 디테일 불러오기 실패' , error)
    throw error
  }
}
