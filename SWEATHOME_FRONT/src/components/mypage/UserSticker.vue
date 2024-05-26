<template>
  <div id="check-sticker">
    <h1>
      <i class="ph-bold ph-book-bookmark"></i>
      도감
    </h1>
    <div class="sticker-container">
      <ul>
        <li v-for="index in 30" :key="index">
          <div class="img-yes" v-if="stickerList.includes(index)">
            <img :src="'src/assets/img/sticker/' + getStickerByNum(index).stickerName + '.png'" :alt="getStickerByNum(index).stickerName">
          </div>
          <div class="img-none" v-else>?</div>

          <span class="sticker-name" v-if="stickerList.includes(index)">{{ getStickerByNum(index).stickerName }}</span>
          <span class="sticker-name" v-else>비공개</span>
          
          <div class="grade">
            <span v-if="getStickerByNum(index).grade === 'COMMON'" class="common">COMMON</span>
            <span v-if="getStickerByNum(index).grade === 'RARE'" class="rare">RARE</span>
            <span v-if="getStickerByNum(index).grade === 'EPIC'" class="epic">EPIC</span>
          </div>
        </li>
      </ul>
    </div>

    <div id="sticker-button">
      <p>보유 뽑기권 개수: {{ gotcha - 1 }}개</p>
      <RouterLink :to="{ name: 'gotcha' }" :class="{ disabled: gotcha <= 1 }" v-on:click.prevent="gotcha <= 1">뽑기</RouterLink>
    </div>
  </div>
</template>

<script setup>
import { useStickerStore } from '@/stores/sticker';
import { computed, onMounted } from 'vue';

const store = useStickerStore();
const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

onMounted(() => {
  store.getStickerList(userId);
  store.getGotchaCount(userId);
})

const stickerList = computed(() => store.stickerList);
const gotcha = computed(() => store.gotcha);

const stickers = [
  {"stickerNo": 1,"stickerName": "강성구","grade": "COMMON"},
  {"stickerNo": 2,"stickerName": "김광현","grade": "COMMON"},
  {"stickerNo": 3,"stickerName": "김도은","grade": "COMMON"},
  {"stickerNo": 4,"stickerName": "김범수","grade": "COMMON"},
  {"stickerNo": 5,"stickerName": "김선웅","grade": "COMMON"},
  {"stickerNo": 6,"stickerName": "김유석","grade": "COMMON"},
  {"stickerNo": 7,"stickerName": "김윤","grade": "RARE"},
  {"stickerNo": 8,"stickerName": "김일태","grade": "COMMON"},
  {"stickerNo": 9,"stickerName": "박민정","grade": "COMMON"},
  {"stickerNo": 10,"stickerName": "박상용","grade": "COMMON"},
  {"stickerNo": 11,"stickerName": "박성혁","grade": "COMMON"},
  {"stickerNo": 12,"stickerName": "변찬현","grade": "COMMON"},
  {"stickerNo": 13,"stickerName": "양지웅","grade": "COMMON"},
  {"stickerNo": 14,"stickerName": "오유진","grade": "RARE"},
  {"stickerNo": 15,"stickerName": "이강현","grade": "COMMON"},
  {"stickerNo": 16,"stickerName": "이동엽","grade": "COMMON"},
  {"stickerNo": 17,"stickerName": "이보경","grade": "COMMON"},
  {"stickerNo": 18,"stickerName": "이수완","grade": "COMMON"},
  {"stickerNo": 19,"stickerName": "이승철","grade": "COMMON"},
  {"stickerNo": 20,"stickerName": "이창현","grade": "COMMON"},
  {"stickerNo": 21,"stickerName": "정상영","grade": "COMMON"},
  {"stickerNo": 22,"stickerName": "정해준","grade": "COMMON"},
  {"stickerNo": 23,"stickerName": "조수연","grade": "COMMON"},
  {"stickerNo": 24,"stickerName": "최다환","grade": "COMMON"},
  {"stickerNo": 25,"stickerName": "최은혜","grade": "COMMON"},
  {"stickerNo": 26,"stickerName": "최이서","grade": "COMMON"},
  {"stickerNo": 27,"stickerName": "현경찬","grade": "COMMON"},
  {"stickerNo": 28,"stickerName": "황예빈","grade": "COMMON"},
  {"stickerNo": 29,"stickerName": "대상혁","grade": "EPIC"},
  {"stickerNo": 30,"stickerName": "양띵균","grade": "EPIC"},
]

const getStickerByNum = function(sticekrNo) {
  return stickers.find(sticker => sticker.stickerNo === sticekrNo);
}

</script>

<style scoped>
#check-sticker {
  width: 70%;
  margin: 0 auto 48px;
}

h1 {
  font-size: 20px;
  display: flex;
  align-items: center;
  margin-bottom: 24px;
}

h1 i {
  font-size: 28px;
  margin-right: 8px;
}

.sticker-container {
  background-color: #eee;
  border-radius: 20px;
  padding: 32px;
  margin-bottom: 16px;
  min-width: 1000px;
}

.sticker-container ul {
  display: flex;
  flex-wrap: wrap;
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  grid-template-rows: repeat(6, 1fr);
  grid-gap: 32px 0;
}

.sticker-container li {
  text-align: center;
  transition: transform 0.4s ease;
}

.sticker-container li:hover {
    transform: translateY(-10px);
}

/* 도감 이미지 */
img {
  object-fit: contain;
  width: 100%;
  height: 100%;
}

.img-yes {
  background-color: white;
  border-radius: 50%;
  text-align: center;
  width: 190px;
  height: 190px;
  overflow: hidden;
  box-shadow: 0px 4px 18px 2px rgba(0,0,0,.04);
  margin: 0 auto;
}

.img-none {
  background-color: #ccc;
  border-radius: 50%;
  text-align: center;
  width: 190px;
  height: 190px;
  line-height: 180px;
  font-size: 24px;
  box-shadow: 0px 4px 18px 2px rgba(0,0,0,.04);
  margin: 0 auto;
}

.sticker-name {
  display: inline-block;
  text-align: center;
  margin: 16px 0 10px;
  font-weight: 500;
}

.common {
  font-size: 12px;
  background-color: #88cc1a;
  color: white;
  padding: 4px 8px;
  border-radius: 8px;
}
.rare {
  font-size: 12px;
  background-color: #0FA4BE;
  color: white;
  padding: 4px 8px;
  border-radius: 8px;
}
.epic {
  font-size: 12px;
  background-color: #AF48BD;
  color: white;
  padding: 4px 8px;
  border-radius: 8px;
}

/* 수정해라 */
#sticker-button {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

#sticker-button a {
  background-color: #ff5c8a;
  color: white;
  margin-top: 8px;
  padding: 6px 18px;
  border-radius: 8px;
}

#sticker-button a:hover {
  background-color: #f05581;
}

.disabled {
  pointer-events: none !important;
  cursor: default !important;
  background-color: #bbb !important;
}
</style>