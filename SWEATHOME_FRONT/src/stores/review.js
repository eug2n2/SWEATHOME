import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_REVIEW_API = `http://localhost:8080/api-review/review`

export const useReviewStore = defineStore('review', () => {
  // 리뷰 목록 조회
  const reviewList = ref([])
  const getReviewList = function(id) {
    axios.get(`${REST_REVIEW_API}/${id}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then((response) => {
      reviewList.value = response.data;
    })
  }

  // 리뷰 추가
  const createReview = function(review) {
    axios({
      url: REST_REVIEW_API,
      method: 'POST',
      data: review,
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then(() => {
      reviewList.value.push(review)
      // router.push({name: 'videoDetail'})
    })
  }

  // 리뷰 삭제
  const deleteReview = function(id) {
    axios.delete(`${REST_REVIEW_API}/${id}`, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    }).then(() => {
      // router.push({name: 'videoDetail'})
    })
  }

  return { reviewList, getReviewList, createReview, deleteReview }
})
