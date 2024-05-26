import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_USER_API = `http://localhost:8080/api-user`;

export const useCheckStore = defineStore('check', () => {
  // 운동 리스트 목록
  const checkList = ref([]);
  const getCheckList = function (id, date) {
    axios.get(`${REST_USER_API}/exercise/${id}/${date}`, {
        headers: {
          'access-token': sessionStorage.getItem('access-token'),
        },
      }).then((response) => {
        checkList.value = response.data;
      });
  };

  // 운동 추가
  const createCheck = function (check) {
    axios({
      url: `${REST_USER_API}/exercise`,
      method: 'POST',
      data: check,
    }).then(() => {
      router.push({ name: 'check' });
    });
  };

  // 운동 삭제
  const deleteCheck = function (check) {
    axios.delete(`${REST_USER_API}/exercise`, {
        headers: { 'access-token': sessionStorage.getItem('access-token') },
        data: check,
      }).then(() => {
        router.push({ name: 'check' });
      });
  };

  // 운동 그래프 7일치 띄우기
  const graphTimes = ref({
    one: 0,
    two: 0,
    three: 0,
    four: 0,
    five: 0,
    six: 0,
    seven: 0,
  });
  
  const getGraphCheck = function (id, date) {
    return axios.get(`${REST_USER_API}/exercise/weeklyTime`, {
        params: {
          userId: id,
          startDate: date,
        },
        headers: {
          'access-token': sessionStorage.getItem('access-token'),
        },
      }).then((response) => {
        graphTimes.value = response.data;
      });
  };

  return { checkList, getCheckList, createCheck, deleteCheck, graphTimes, getGraphCheck };
});
