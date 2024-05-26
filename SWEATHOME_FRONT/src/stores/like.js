import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_VIDEO_LIKE_API = `http://localhost:8080/api-videolike`
const REST_USER_API = `http://localhost:8080/api-user`

export const useLikeStore = defineStore('like', () => {
  // 영상 좋아요 추가
  const addVideoLike = function(like) {
    axios({
      url: `${REST_VIDEO_LIKE_API}/insertlike`,
      method: 'POST',
      data: like,
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then(() => {
      router.push({name: 'videoDetail'})
    })
  }

  // 영상 좋아요 삭제
  const deleteVideoLike = function(like) {
    axios.delete(`${REST_VIDEO_LIKE_API}/deletelike`, {
      data: like,
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then(() => {
      router.push({name: 'videoDetail'})
    })
  }

  // 영상 좋아요 목록
  const videoLikeList = ref([]);
  const getVideoLikeList = function(id) {
    axios.get(`${REST_USER_API}/likeList`, {
      params: { userId: id },
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then((response) => {
      videoLikeList.value = response.data
    })
  }
  
  return { addVideoLike, deleteVideoLike, videoLikeList, getVideoLikeList }
})
