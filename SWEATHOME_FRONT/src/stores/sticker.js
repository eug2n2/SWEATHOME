import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_STICKER_API = `http://localhost:8080/api-sticker`
const REST_USER_API = `http://localhost:8080/api-user`

export const useStickerStore = defineStore('sticker', () => {
  // 도감 목록 조회
  const stickerList = ref([])
  const getStickerList = function(id) {
    axios.get(`${REST_STICKER_API}/sticker`, {
      params: {
        userId: id
      },
      headers: {
        'access-token': sessionStorage.getItem('access-token'),
      },
    }).then((response) => {
      stickerList.value = response.data;
    })
  }

  // 도감 추가
  const createSticker = function(sticker) {
    axios({
      url: `${REST_STICKER_API}/sticker`,
      method: 'POST',
      data: sticker,
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then(() => {
      router.push({name: 'gotcha'})
    })
  }

  // 스티커 중복 확인
  const isCheckSticker = ref();
  const checkSticker = async (sticker) => {
    try {
      const response = await axios({
        url: `${REST_STICKER_API}/stickercheck`,
        method: 'POST',
        data: sticker,
        headers: { 'access-token': sessionStorage.getItem('access-token') }
      });
      isCheckSticker.value = response.data;
    } catch (error) {
    }
  };

  // 뽑기권 개수 조회
  const gotcha = ref(0);
  const getGotchaCount = function(id) {
    axios.get(`${REST_USER_API}/coupon`, {
      params: { userId: id },
      headers: {'access-token': sessionStorage.getItem('access-token')}
    }).then((response) => {
      gotcha.value = response.data;
    })
  }

  // 뽑기권 추가
  const addGotcha = async (id) => {
    try {
      const response = await axios.put(`${REST_USER_API}/addcoupon`, null, {
        params: { userId: id },
        headers: {'access-token': sessionStorage.getItem('access-token')}
      });
      gotcha.value = response.data;
    } catch (error) {
    }
  };

  // 뽑기권 사용
const useGotcha = async (id) => {
  try {
    const response = await axios.put(`${REST_USER_API}/usecoupon`, null, {
      params: { userId: id },
      headers: {'access-token': sessionStorage.getItem('access-token')}
    });
    gotcha.value = response.data;
  } catch (error) {
  }
};

  // 뽑기권 받은 내역 추가
  const insertEvent = async (id) => {
    try {
      await axios.post(`${REST_USER_API}/event`, null, {
        params: { userId: id },
        headers: {'access-token': sessionStorage.getItem('access-token')}
      });
    } catch (error) {
    }
  };
  

  // 뽑기권 받은 내역 조회
  const selectEvent = async function(id) {
    const response = await axios.get(`${REST_USER_API}/event`, {
      params: { userId: id },
      headers: {'access-token': sessionStorage.getItem('access-token')}
    });
    return response.data;
  }
  
  return { stickerList, getStickerList, createSticker, isCheckSticker, checkSticker, gotcha, getGotchaCount, addGotcha, useGotcha, insertEvent, selectEvent }
})
