<template>
    <div id="flex">
        <div id="video-container">
            <div id="iframe">
                <!-- width="880" height="495" -->
                <iframe width="880" height="495" :src="makeVideoUrl(store.video.link)" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
            </div>
            <div id="video-info">
                <p class="title">{{ store.video.videoTitle }}</p>
                <div class="video-info-detail">
                    <div class="video-rate">
                        <i class="ph-fill ph-star"></i>
                        <span>{{ store.video.rateAvg }}</span>
                    </div>
                    <div class="video-like">
                        <i class="ph-fill ph-heart"></i>
                        <span>{{ store.video.likeCnt }}</span>
                    </div>
                    <div class="view-cnt">
                        <i class="ph-fill ph-eye"></i>
                        <span>{{ store.video.viewCnt }}</span>
                    </div>
                </div>
                <button @click="toggleLike">
                    <i :class="heartClass"></i>
                </button>
            </div>
            <div class="buttons">
                <RouterLink :to="{name: 'videoUpdate'}" v-if="userId === 'oyj5775'">영상 수정</RouterLink>
                <a @click="deleteVideo" v-if="userId === 'oyj5775'">영상 삭제</a>
            </div>
        </div>
        <VideoRecommend :video="store.video"/>
    </div>
    <VideoReview :videoId="parseInt(route.params.videoId)"/>
</template>

<script setup>
import { useVideoStore } from '@/stores/video';
import { useUserStore } from '@/stores/user';
import { useLikeStore } from '@/stores/like';
import { onMounted, ref, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import VideoRecommend from './VideoRecommend.vue';
import VideoReview from './VideoReview.vue';

const store = useVideoStore();
const ustore = useUserStore();
const likeStore = useLikeStore();
const route = useRoute();
const router = useRouter();

const loadVideoData = () => {
    store.getVideo(route.params.videoId);
    ustore.getLikeList(decodedToken.id);
};

onMounted(loadVideoData);

watch(() => route.params.videoId, () => {
    loadVideoData();
});

const extractVideoId = (link) => {
    if (!link) return '';
    const videoIdStart = link.indexOf("v=") + 2;
    const youtubeId = link.substr(videoIdStart);
    return youtubeId;
}

const makeVideoUrl = (link) => {
    const youtubeId = extractVideoId(link);
    const videoUrl = `https://www.youtube.com/embed/${youtubeId}`;
    return videoUrl;
}

const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

const like = ref({
    videoId: route.params.videoId,
    userId: decodedToken.id
});

const toggleLike = () => {
    if (heartClass.value === 'ph-fill ph-heart') {
        likeStore.deleteVideoLike(like.value, () => {
            ustore.getLikeList(decodedToken.id);
        });
    } else {
        likeStore.addVideoLike(like.value, () => {
            ustore.getLikeList(decodedToken.id);
        });
    }

    window.location.reload();
};

const heartClass = computed(() => {
    const likeList = ustore.likeList;
    const videoId = route.params.videoId.toString();
    let isLiked = false;

    for (const item of likeList) {
        if (item.videoId.toString() === videoId) {
            isLiked = true;
            break;
        }
    }
    return isLiked ? 'ph-fill ph-heart' : 'ph ph-heart';
});

const deleteVideo = function() {
    store.deleteVideo(route.params.videoId);
}
</script>

<style scoped>
#flex {
    display: flex;
    justify-content: space-between;
    width: 70%;
    margin: 0 auto 200px;
    min-width: 1300px;
}
/* 
#iframe {
    position: relative;
    text-align: center;
} */

iframe {
    border-radius: 20px;
}

#video-info {
    width: 880px;
    position: relative;
}

.title {
    font-size: 20px;
    font-weight: 600;
    margin: 24px 0 32px;
}

.video-info-detail {
    display: flex;
    align-items: center;
}

.video-info-detail div {
    margin-right: 16px;
}

.video-info-detail div i {
    margin-right: 4px;
}

button {
    border: 1px solid #aaa;
    border-radius: 50%;
    padding: 8px;
    cursor: pointer;
    position: absolute;
    bottom: 0; left: 94%;
}

button:hover {
    background-color: #eaeaea;
}

button i {
    font-size: 24px;
    color: #ff5c8a;
}

.buttons {
    width: 70%;
    margin-top: 40px;
}

a {
    border: 1px solid #ff5c8a;
    padding: 8px 20px;
    border-radius: 24px;
    margin-right: 8px;
    cursor: pointer;
}

a:hover {
    background-color: #ff5c8a;
    color: white;
}
</style>
