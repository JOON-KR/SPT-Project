import axios from 'axios'
import _ from 'lodash';

const BASE_URL = 'http://localhost:8080'
// 통합 검색 api 호출 영역



// const ACCESS_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdW5odWs5NzAzQG5hdmVyLmNvbSIsImlkIjoxLCJuYW1lIjoi6rmA7KSA7ZiBIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MTkxMTAwMzEsImV4cCI6MTcyNTExMDAzMX0.xCiKPxZq9n8p0c1V9Gw3lFQyTjrayrWont2jNCzSVVc';

export const AutoSearch = async (keyword) => {
  try {
    const response = await axios.get(`${BASE_URL}/search/auto/${keyword}`);

    console.log(response.data)
    return response.data
  
  } catch (error) {
    console.error('검색에 실패하였습니다', error);
    throw error;
  }
}

export const SearchMemberByKeyword = async (keyword) => {
  try {
    const response = await axios.get(`${BASE_URL}/search/all/${keyword}`);
    
    const groupedData = _.groupBy(response.data, 'type');
    
    const memberResults = groupedData['member'] || []; // 'member' 타입의 데이터 배열

    return memberResults;
  } catch (error) {
    console.error('검색에 실패하였습니다', error);
    throw error;
  }
}

export const SearchSeriesByKeyword = async (keyword) => {
  try {
    const response = await axios.get(`${BASE_URL}/search/all/${keyword}`);
    
    const groupedData = _.groupBy(response.data, 'type');
    
    const seriesResults = groupedData['series'] || []; // 'series' 타입의 데이터 배열

    return seriesResults;
  
  } catch (error) {
    console.error('검색에 실패하였습니다' , error)
    throw error
  }
}


// 에세이 목록 불러오는 api 호출 영역
export const getAllSeries = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/series/all`);
    return response.data;
  } catch (error) {
    console.error('시리즈 불러오기 실패:', error);
    throw error;
  }
};

export const getBestSeries = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/series/best`);
    return response.data;
  } catch (error) {
    console.error('명예의 전당 시리즈 불러오기 실패:', error);
    throw error;
  }
};

export const getMonthlySeries = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/series/month`);
    return response.data;
  } catch (error) {
    console.error('이달의 시리즈 불러오기 실패:', error);
    throw error;
  }
};