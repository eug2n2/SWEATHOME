<template>
  <!-- 컨텐츠 -->
  <div ref="pages" class="pages">
    <!-- 첫 번째 페이지 -->
    <div class="first page">
      <div id="first-title">
        <p class="big-title neon">SWEATHOME</p>
        <p class="small-title" style="font-size: 26px;">모두를 위한 홈트레이닝 플랫폼, SWEATHOME에 오신 것을 환영합니다.</p>
      </div>
      <div class="fill"></div>
      <img src="/src/assets/img/main.jpg" alt="메인"></img>
    </div>
    <!-- 두 번째 페이지 -->
    <div class="second page">
      <div id="second-title">
        <p class="big-title">나에게 필요한<br/> 운동을 시작해보세요.</p>
        <p class="small-title">파트별 검색과 영상 추천 기능을 활용하여 자신에게 맞는 운동 영상을 빠르게 찾을 수 있어요. 또한, 좋아요와 별점을 통해 인기 있는 운동 영상을 손쉽게 찾아보세요.
          <br>오윤완 AI 운동 코치와 함께 집에서도 일대일 코칭을 받으며 건강을 증진할 수 있습니다.</p>
        <div id="second-part">
          <ul>
            <li class="hovered">
              <div class="img-wrap" style="background-image: url('/src/assets/img/top.jpg'); background-position: 55% 50%;"></div>
              <span class="text-wrap">상체</span>
            </li>
            <li>
              <div class="img-wrap" style="background-image: url('/src/assets/img/bottom.jpg'); background-position: 55% 50%;"></div>
              <span class="text-wrap">하체</span>
            </li>
            <li>
              <div class="img-wrap" style="background-image: url('/src/assets/img/whole.jpg'); background-position: 55% 50%;"></div>
              <span class="text-wrap">전신</span>
            </li>
            <li>
              <div class="img-wrap" style="background-image: url('/src/assets/img/stretch.jpg'); background-position: 55% 50%;"></div>
              <span class="text-wrap">스트레칭</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- 세 번째 페이지 -->
    <div class="third page">
      <div id="third-title">
        <div>
          <p class="big-title">매일 운동을 기록하고<br/> 도감을 채우세요.</p>
          <p class="small-title"> 7일간의 운동 그래프를 관찰하고, 매일매일 뽑기권을 받아 도감을 채워보세요! 운이 좋으면 레어 스티커도 얻을 수 있습니다. 
            <br> 또한, 신규 회원 가입 시 50개의 뽑기권을 드리는 이벤트를 진행 중입니다. 지금 바로 가입하고 건강한 삶을 시작하세요!</p>
        </div>
        <div class="img-wrap">
          <video src="/src/assets/img/check.mp4" loop muted autoplay></video>
        </div>
      </div>
    </div>
    <div class="fourth page">
      <TheFooter/>
    </div>
  </div>
  <!-- 페이징 -->
  <ul ref="pagination" class="pagination">
    <li v-for="(slide, index) in slides" :key="index" @click="gotoNum(index)" :class="{ 'active': index === current }"></li>
  </ul>
</template>

<script setup>
import TheFooter from '@/components/common/TheFooter.vue';
import { ref, onMounted } from 'vue';

const pages = ref(null); // 페이지 컨테이너 요소를 참조하기 위한 변수
const pagination = ref(null); // 페이징 요소를 참조하기 위한 변수
const slides = ['first', 'second', 'third']; // 각 페이지의 내용을 담은 배열
let count = slides.length; // 슬라이드의 개수
let current = 0; // 현재 보여지는 슬라이드의 인덱스
let touchstart = 0; // 터치 시작 지점의 Y 좌표
let animation_state = false; // 슬라이드 애니메이션의 상태를 나타내는 변수

// 페이지 초기 상태 설정
const init = () => {
  const slidesElements = pages.value.children;
  for (let i = 0; i < count; i++) {
    slidesElements[i].style.bottom = -(i * 100) + '%';
  }
}

// 해당 인덱스 페이지로 이동
const gotoNum = (index) => {
  if ((index != current) && !animation_state) {
    animation_state = true;
    setTimeout(() => animation_state = false, 700); // 변경된 시간
    pagination.value.children[current].classList.remove('active');
    current = index;
    pagination.value.children[current].classList.add('active');
    const slidesElements = pages.value.children;
    for (let i = 0; i < count; i++) {
      slidesElements[i].style.bottom = (current - i) * 100 + '%';
    }
  }
}
// 다음 페이지, 이전 페이지로 이동
const gotoNext = () => current < count - 1 ? gotoNum(current + 1) : false;
const gotoPrev = () => current > 0 ? gotoNum(current - 1) : false;

const handleTouchStart = (e) => touchstart = e.touches[0].screenY;
const handleTouchEnd = (e) => touchstart < e.changedTouches[0].screenY ? gotoPrev() : gotoNext();
const handleWheel = (e) => e.deltaY < 0 ? gotoPrev() : gotoNext();

onMounted(() => {
  init();
  if (pages.value) {
    pages.value.ontouchstart = handleTouchStart;
    pages.value.ontouchend = handleTouchEnd;
    pages.value.onmousewheel = pages.value.onwheel = handleWheel;
  }

  const lis = document.querySelectorAll('#second-part li');

  lis.forEach(li => {
    li.addEventListener('mouseover', () => {
      lis.forEach(li => li.classList.remove('hovered'));
      li.classList.add('hovered');
    });
  });
});
</script>

<style scoped>
@import url('/src/assets/css/home.css');
</style>