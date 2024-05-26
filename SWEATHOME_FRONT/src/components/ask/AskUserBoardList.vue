<template>
<div v-if="store.askUserList.length === 0" id="default">
    <p class="isEmpty">요청하신 영상이 없습니다 ｡ﾟ( ﾟஇ‸இﾟ+)ﾟ｡</p>
</div>

<div v-else id="ask-list">
    <div id="ask-item">
    <ul>
        <li v-for="ask in store.askUserList" :key="ask.askId">
        <div class="thumbnail">
            <img :src="makeImageUrl(ask.link)" alt="썸네일">
        </div>
        <div class="video-info">
            <p class="title">{{ ask.askTitle }}</p>
        </div>
        <div class="button">
            <button @click="deleteAsk(ask.askId)">요청 취소하기</button>
        </div>
        </li>
    </ul>
    </div>
</div>
</template>

<script setup>
import { useAskStore } from '@/stores/ask';
import { computed, onMounted, ref } from 'vue';
import _ from 'lodash';

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

const store = useAskStore();

const deleteAsk = function(askId) {
    console.log(askId)
    store.deleteAsk(askId);
    window.location.reload();
}

onMounted(() => {
    store.getAskUserList(userId);
});

</script>

<style scoped>
#default {
    width: 70%;
    margin: 48px auto 640px;
}

#ask-list ul {
    width: 70%;
    margin: 48px auto 120px;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 24px;
}

#ask-item li {
    background-color: white;
    box-shadow: 4px 12px 30px 6px rgba(0,0,0,.09);
    border-radius: 20px;
    padding: 24px;
}

.thumbnail {
    margin-bottom: 8px;
}

.thumbnail img {
    width: 100%;
    height: 100%;
}

.title {
    width: 240px;
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

#ask-item .button {
    margin-top: 12px;
    display: flex;
    justify-content: flex-end;
}

#ask-item button {
    padding: 4px 8px;
    cursor: pointer;
}
</style>