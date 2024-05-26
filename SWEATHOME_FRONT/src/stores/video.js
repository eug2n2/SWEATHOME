import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_VIDEO_API = `http://localhost:8080/api-video/video`

export const useVideoStore = defineStore('video', () => {
  // 영상 목록 조회
  const videoList = ref([])
  const getVideoList = function() {
    axios.get(REST_VIDEO_API, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then((response) => {
      videoList.value = response.data
    })
  }

  // 영상 상세 조회
  const video = ref({})
  const getVideo = function(id) {
    axios.get(`${REST_VIDEO_API}/${id}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then((response) => {
      video.value = response.data;
    }).catch((error) => {
      router.push({name: 'error'})
    })
  }
  
  // 영상 검색
  const videoSearch = function(keyword) {
    axios.get(`${REST_VIDEO_API}/search/${keyword}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then((response) => {
      videoList.value = response.data
    })
  }

  // 영상 부위별 조회
  const videoPartSearch = function(part) {
    axios.get(`${REST_VIDEO_API}/searchpart/${part}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then((response) => {
      if (part === '전체') {
        getVideoList();
      } else {
        videoList.value = response.data
      }
    })
  }

  // 영상 추가
  const createVideo = function(video) {
    axios({
      url: REST_VIDEO_API,
      method: 'POST',
      data: video,
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then(() => {
      router.push({name: 'videoList'})
    })
  }

  // 영상 삭제
  const deleteVideo = function(id) {
    axios.delete(`${REST_VIDEO_API}/${id}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then(() => {
      router.push({name: 'videoList'})
    })
  }

  // 영상 수정
  const updateVideo = function(id) {
    axios.put(`${REST_VIDEO_API}/${id}`, video.value, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then(() => {
      router.push({name: 'videoList'})
    })
  }

  return { videoList, getVideoList, video, getVideo, videoSearch, videoPartSearch, createVideo, deleteVideo, updateVideo }
})
