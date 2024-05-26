<template>
  <div id="gotcha-container">
    <div id="gotcha">
      <div id="img-wrap">
        <div class="img-none" v-if="!selectedSticker">?</div>
        <div class="img-yes" v-else>
          <img :src="'src/assets/img/sticker/' + selectedSticker.stickerName + '.png'" :alt="selectedSticker.stickerName">
        </div>
      </div>

      <div id="sticker-info">
        <div v-if="isDuplicate !== null && isDuplicate === true">
          <i class="ph-fill ph-seal">
            <span class="new">NEW</span>
          </i>
        </div>
        <p class="sticker-name">{{ selectedSticker ? selectedSticker.stickerName : '도감을 뽑아보세요!' }}</p>
      </div>
      <div class="grade" v-if="selectedSticker">
        <span v-if="selectedSticker.grade === 'COMMON'" class="common">COMMON</span>
        <span v-if="selectedSticker.grade === 'RARE'" class="rare">RARE</span>
        <span v-if="selectedSticker.grade === 'EPIC'" class="epic">EPIC</span>
      </div>
    </div>

    <div class="pick-count">
      남은 뽑기권 개수: {{ gotcha - 1 }}개
    </div>
    <div class="buttons">
      <a class="pick" @click="pickSticker">뽑기</a>
      <a class="back" @click="back">뒤로가기</a>
    </div>
  </div>
</template>

<script setup>
import router from '@/router';
import { useStickerStore } from '@/stores/sticker';
import { computed, onBeforeUpdate, onMounted, ref, watch } from 'vue';

const store = useStickerStore();
const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

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
  {"stickerNo": 30,"stickerName": "양띵균","grade": "EPIC"}
]

const gotcha = computed(() => store.gotcha);
const selectedSticker = ref();
const isDuplicate = ref();

onMounted(() => {
  store.getGotchaCount(userId);
});

onBeforeUpdate(() => {
  store.getGotchaCount(userId);
})

const pickSticker = async () => {
  if (gotcha.value <= 0) {
    return;
  }

  await store.useGotcha(userId);

  const weights = {
    COMMON: 94,
    RARE: 4,
    EPIC: 2
  };

  const weightedStickers = stickers.flatMap(sticker => {
    const weight = weights[sticker.grade];
    return Array(weight).fill(sticker);
  });

  const randomIndex = Math.floor(Math.random() * weightedStickers.length);
  selectedSticker.value = weightedStickers[randomIndex];
  const sticker = {
    userId: userId,
    stickerNo: selectedSticker.value.stickerNo
  };

  await store.checkSticker(sticker);

  if (store.isCheckSticker && selectedSticker.value) {
    const newSticker = {
      userId: userId,
      stickerNo: selectedSticker.value.stickerNo,
    };
    store.createSticker(newSticker);
    isDuplicate.value = true;
  } else {
    isDuplicate.value = false;
  }
};

watch(gotcha, (newGotcha) => {
  const pickButton = document.querySelector('.pick');
  if (newGotcha <= 1) {
    if (pickButton) {
      pickButton.classList.add('disabled');
    }
  } else {
    if (pickButton) {
      pickButton.classList.remove('disabled');
    }
  }
});

const back = function() {
  router.push({name: 'mypage'});
}
</script>

<style scoped>
#gotcha-container {
  width: 70%;
  margin: 80px auto 120px;
  padding: 48px 40px;
  border-radius: 20px;
  box-shadow: 4px 12px 30px 6px rgba(0,0,0,.09);
  text-align: center;
}

#img-wrap {
  width: 400px;
  margin: 0 auto 32px;
}

.img-yes {
  background-color: white;
  border-radius: 50%;
  text-align: center;
  width: 400px;
  height: 400px;
  overflow: hidden;
  box-shadow: 0px 4px 18px 2px rgba(0,0,0,.04);
}

.img-none {
  background-color: #ccc;
  border-radius: 50%;
  text-align: center;
  width: 400px;
  height: 400px;
  line-height: 400px;
  font-size: 24px;
  box-shadow: 0px 4px 18px 2px rgba(0,0,0,.04);
}

img {
  object-fit: contain;
  width: 100%;
  height: 100%;
}

#sticker-info {
  display: flex;
  justify-content: center;
  align-items: center;
}

.sticker-name {
  font-size: 24px;
  font-weight: 500;
}

.grade {
  margin-top: 12px;
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

.ph-seal {
  font-size: 40px;
  position: relative;
  color: #ff5c8a;
  margin-right: 4px;
}

.new {
  position: absolute;
  font-size: 10px;
  font-weight: 500;
  left: 9px; top: 15px;
  color: white;
}

.pick-count {
  margin: 16px 0;
}

.buttons {
  display: flex;
  flex-direction: column;
}

.pick, .back {
  padding: 12px 0;
  margin: 4px 160px;
  border-radius: 20px;
  border: none;
  cursor: pointer;
  font-size: 18px;
}

.pick {
  background-color: #ff5c8a;
  color: white;
}

.disabled {
  display: none;
}

.pick:hover:not(:disabled) {
  background-color: #f05581;
}

.back {
  background-color: #eee;
}

.back:hover {
  background-color: #ddd;
}
</style>