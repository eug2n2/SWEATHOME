import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_ASK_API = `http://localhost:8080/api-ask/ask`

export const useAskStore = defineStore('ask', () => {
  // 유튜브 영상 검색 목록
  const youtubeList = ref([]);
  const getYoutubeList = function(keyword) {
    const URL = '/youtube-api/search'
    const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY

    axios({
      headers: {
        'access-token': sessionStorage.getItem('access-token'),
      },
      url: URL,
      method: 'GET',
      params: {
        key: API_KEY,
        part: 'snippet',
        maxResults: 16,
        q: keyword,
        type: 'video'
      }
    }).then((response) => {
      youtubeList.value = response.data.items;
    })
  }

  // 유튜브 요청 조회 목록 (관리자)
  const askAdminList = ref([])
  const getAskAdminList = function() {
    axios.get(REST_ASK_API, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then((response) => {
      askAdminList.value = response.data
    })
  }
  
  const askUserList = ref([])
  // 유튜브 요청 조회 목록 (사용자)
  const getAskUserList = function(id) {
    axios.get(`${REST_ASK_API}/${id}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then((response) => {
      askUserList.value = response.data
    })
  }

  // 유튜브 요청 추가
  const createAsk = function(ask) {
    axios({
      url: REST_ASK_API,
      method: 'POST',
      data: ask,
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then(() => {
      router.push({name: 'userList'})
    })
  }

  // 유튜브 요청 삭제
  const deleteAsk = function(id) {
    axios.delete(`${REST_ASK_API}/${id}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then(() => {

    })
  }

  return { getYoutubeList, youtubeList, askAdminList, getAskAdminList, askUserList, getAskUserList, createAsk, deleteAsk };
});
