<template>
<div id="recommend-list">
    <div id="recommend">
        <ul>
            <li v-for="video in store.videoRecommendList" :key="video.videoId">
                <div class="video">
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
import { userRecommendStore } from '@/stores/recommend';
import { defineProps, onBeforeUpdate } from 'vue';

const store = userRecommendStore();
const props = defineProps({
  video: Object
});

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

onBeforeUpdate(() => {
    store.getVideoRecommendList(props.video);
})
</script>

<style scoped>
#recommend-list {
    width: 30%;
    height: 650px;
}

#recommend ul {
    height: 650px;
    padding: 0;
    margin: 0;
}

#recommend li {
    height: 19%;
    margin-bottom: 12px;
    background-color: #eee;
    padding: 12px;
    border-radius: 20px;
}

#recommend li a {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 100%;
}

.thumbnail {
    width: calc(120px / 9 * 16);
    height: 120px;
    margin-right: 12px;
    position: relative;
}

.thumbnail img {
    object-fit: cover;
    width: 100%;
    height: 100%;
    border-radius: 20px;
    position: absolute;
    top: 0;
    left: 0;
}

.video-info {
    width: 70%;
}

.video-info .title {
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
    font-size: 12px;
}

.video-info-detail div {
    margin-right: 8px;
}

.video-info-detail i {
    margin-right: 2px;
}
</style>
