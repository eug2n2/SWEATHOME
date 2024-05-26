import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_VIDEO_API = `http://localhost:8080/api-video/video`

export const userRecommendStore = defineStore('recommend', () => {
    // 영상 추천 목록
    const videoRecommendList = ref([]);
    const getVideoRecommendList = async function(video) {
        try {
        const response = await axios.post(`${REST_VIDEO_API}/recommend`, video, {
            headers: {'access-token': sessionStorage.getItem('access-token')}
        });
        videoRecommendList.value = response.data;
        } catch (error) {
        }
    }

  return { videoRecommendList, getVideoRecommendList }
})
