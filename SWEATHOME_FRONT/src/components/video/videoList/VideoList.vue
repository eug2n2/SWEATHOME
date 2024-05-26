<template>
    <VideoSearchInput/>
    <VideoPartButton/>
    <div id="button-container">
        <RouterLink id="button" :to="{name: 'videoCreate'}" v-if="userId === 'oyj5775'">영상 등록</RouterLink>
    </div>
    <div id="video-list">
        <p v-if="store.videoList.length === 0" class="isEmpty">조건과 일치하는 영상이 없습니다 ｡ﾟ( ﾟஇ‸இﾟ+)ﾟ｡</p>

        <ul>
            <li v-for="video in currentPageVideoList" :key="video.videoId">
                <div class="video-item">
                    <RouterLink :to="`/video/${video.videoId}`">
                        <div class="thumbnail">
                            <img :src="makeImageUrl(video.link)" alt="썸네일">
                        </div>
                        <div class="video-info">
                            <p class="title">{{ video.videoTitle }}</p>
                            <div class="video-info-detail">
                                <div class="video-rate">
                                    <i class="ph-fill ph-star"></i>
                                    <span>{{ video.rateAvg }}</span>
                                </div>
                                <div class="video-like">
                                    <i class="ph-fill ph-heart"></i>
                                    <span>{{ video.likeCnt }}</span>
                                </div>
                                <div class="view-cnt">
                                    <i class="ph-fill ph-eye"></i>
                                    <span>{{ video.viewCnt }}</span>
                                </div>
                            </div>
                        </div>
                    </RouterLink>
                </div>
            </li>
        </ul>
    </div>

    <div id="pagination">
        <ul>
            <li class="page-item">
                <a href="#" class="page-link" @click.prevent="goToPreviousRange" :class="{ disabled: currentPageRange[0] <= 1 }">
                    <i class="ph ph-caret-left"></i>
                </a>
            </li>
            <li class="page-item page-number" :class="{ active: currentPage === page }" v-for="page in visiblePages" :key="page">
                <a href="#" class="page-link" @click.prevent="clickPage(page)">
                    {{ page }}
                </a>
            </li>
            <li class="page-item">
                <a href="#" class="page-link" @click.prevent="goToNextRange" :class="{ disabled: currentPageRange[1] >= pageCount }">
                    <i class="ph ph-caret-right"></i>
                </a>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { useVideoStore } from '@/stores/video';
import { onMounted, ref, computed } from 'vue';
import VideoSearchInput from './VideoSearchInput.vue';
import VideoPartButton from './VideoPartButton.vue';

const store = useVideoStore();
onMounted(() => {
    store.getVideoList()
})

const extractVideoId = (link) => {
    const videoIdStart = link.indexOf("v=") + 2;
    const youtubeId = link.substr(videoIdStart);
    return youtubeId;
}

const makeImageUrl = (link) => {
    const youtubeId = extractVideoId(link);
    const imageUrl = `http://img.youtube.com/vi/${youtubeId}/0.jpg`;
    return imageUrl;
}

const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

const perPage = 9;
const pagesPerRange = 5;
const currentPage = ref(1);
const currentPageRange = ref([1, pagesPerRange]);

const pageCount = computed(() => {
  return Math.ceil(store.videoList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageVideoList = computed(() => {
  return store.videoList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
});

const visiblePages = computed(() => {
    let [start, end] = currentPageRange.value;
    if (pageCount.value < pagesPerRange) {
        start = 1;
        end = pageCount.value;
    }
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
});

const goToPreviousRange = () => {
  const newEnd = currentPageRange.value[0] - 1;
  const newStart = Math.max(1, newEnd - pagesPerRange + 1);
  currentPageRange.value = [newStart, newEnd];
  currentPage.value = newEnd;
};

const goToNextRange = () => {
  const newStart = currentPageRange.value[1] + 1;
  const newEnd = Math.min(pageCount.value, newStart + pagesPerRange - 1);
  currentPageRange.value = [newStart, newEnd];
  currentPage.value = newStart;
};
</script>

<style scoped>
#video-list {
    width: 70%;
    margin: 0 auto;
}

#video-list ul {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 40px;
}

#video-list .video-item {
    background-color: #ffffff;
    width: 100%;
    height: 100%;
    border-radius: 20px;
    box-shadow: 4px 12px 30px 6px rgba(0,0,0,.09);
    transition: transform 0.4s ease;
}

#video-list .video-item:hover {
    transform: translateY(-10px);
}

.isEmpty {
    margin-bottom: 360px;
}

.thumbnail {
    width: 100%;
    height: 0;
    padding-top: 56.25%;
    position: relative;
}

#video-list img {
    object-fit: cover;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
}

#video-list .video-info {
    margin: 24px;
}

#video-list .title {
    font-size: 18px;
    font-weight: 500;
    display: inline-block;
    height: 4.5em;
    line-height: 1.5;
    white-space: normal;
    overflow: hidden;
    text-overflow: ellipsis;
    word-wrap: break-word;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
}

.video-info-detail {
    display: flex;
    margin: 16px 0;
}

.video-info-detail div {
    margin-right: 10px;
}

/* 영상 추가 버튼 */
#button-container {
    width: 70%;
    margin: 0 auto 40px;
}

#button {
    border: 1px solid #ff5c8a;
    padding: 8px 20px;
    border-radius: 24px;
}

#button:hover {
    background-color: #ff5c8a;
    color: white;
}

/* 페이징 처리 */
#pagination {
    width: 70%;
    margin: 80px auto 0;
}

#pagination ul {
    display: flex;
    justify-content: center;
    align-items: center;
}

#pagination a {
    padding: 5px 12px;
    margin: 0 4px;
}

.page-number a:hover {
    background-color: #ff5c8a;
    border-radius: 50%;
    color: white;
}

.active a {
    background-color: #ff5c8a;
    border-radius: 50%;
    color: white;
}

.disabled {
    display: none;
}

</style>