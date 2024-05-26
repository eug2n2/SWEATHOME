<template>
  <div id="like-container">
    <h1>
      <i class="ph-bold ph-calendar-heart"></i>
      좋아요한 영상 목록
    </h1>

    <div id="like-list">
      <p v-if="store.videoLikeList.length === 0" class="isEmpty">좋아요한 영상이 없습니다 ｡ﾟ( ﾟஇ‸இﾟ+)ﾟ｡</p>
      <ul>
        <li v-for="video in store.videoLikeList" :key="video.videoId">
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
  </div>
</template>

<script setup>
import { useLikeStore } from '@/stores/like';
import { onMounted } from 'vue';

const store = useLikeStore();
const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

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

onMounted(() => {
  store.getVideoLikeList(userId);
})
</script>

<style scoped>
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

#like-container {
  width: 70%;
  margin: 0 auto 160px;
}

#like-list ul {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 40px;
}

#like-list .video-item {
    background-color: #ffffff;
    width: 100%;
    height: 100%;
    border-radius: 20px;
    box-shadow: 4px 12px 30px 6px rgba(0,0,0,.09);
    transition: transform 0.4s ease;
}

#like-list .video-item:hover {
    transform: translateY(-10px);
}

.thumbnail {
    width: 100%;
    height: 0;
    padding-top: 56.25%;
    position: relative;
}

#like-list img {
    object-fit: cover;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    border-top-left-radius: 20px;
    border-top-right-radius: 20px;
}

#like-list .video-info {
    margin: 24px;
}

#like-list .title {
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
</style>