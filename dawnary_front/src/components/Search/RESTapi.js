import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

// 시리즈 검색 api 호출 영역
export const getSeriesByKeyword = async (keyword) => {
  try {
    const response = await axios.get(`${BASE_URL}/series`, {
      params: { q: keyword}
    })
    return response.data
  } catch (error) {
    console.error('검색에 실패하였습니다' , error)
    throw error
  }
}

// 일기 검색 api 호출 영역
export const getDiaryByKeyword = async (keyword) => {
  try {
    const response = await axios.get(`${BASE_URL}/diary`, {
      params: { q: keyword }
    })
    return response.data;
  } catch (error) {
    console.error('검색에 실패하였습니다', error);
    throw error
  }
}

// 유저 검색 api 호출 영역
export const getUserByKeyword = async (keyword) => {
  try {
    const response = await axios.get(`${BASE_URL}/user`, {
      params: { q: keyword }
    })
    return response.data;
  } catch (error) {
    console.error('검색에 실패하였습니다', error);
    throw error
  }
}

// 에세이 목록 불러오는 api 호출 영역
export const getAllEssays = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/essays`);
    return response.data;
  } catch (error) {
    console.error('에세이 불러오기 실패:', error);
    throw error;
  }
};
