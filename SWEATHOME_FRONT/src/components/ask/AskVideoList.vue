<template>
  <AskSearchInput/>
  <div v-if="store.youtubeList.length === 0" id="default">
    <p class="isEmpty">보고싶은 영상을 Youtube에서 바로 찾아보세요 ꉂꉂ꒰•̤▿•̤*ૢ꒱</p>
  </div>

  <div v-else id="ask-list">
    <div id="ask-item">
      <ul>
        <li v-for="video in store.youtubeList" :key="video.id.videoId">
          <div class="thumbnail">
            <img :src="makeImageUrl(getVideoId(video.snippet.thumbnails.default.url))" alt="썸네일">
          </div>
          <div class="video-info">
            <p class="title">{{ getVideoTitle(video) }}</p>
          </div>
          <div class="button">
            <button @click="create(video)">영상 등록 요청</button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { useAskStore } from '@/stores/ask';
import { computed, ref } from 'vue';
import _ from 'lodash';
import AskSearchInput from './AskSearchInput.vue';

const makeImageUrl = (videoId) => {
  return `http://img.youtube.com/vi/${videoId}/0.jpg`;
}

const getVideoId = (thumbnailUrl) => {
  const videoIdStart = thumbnailUrl.indexOf("vi/") + 3;
  const videoIdEnd = thumbnailUrl.indexOf("/default");
  return thumbnailUrl.substring(videoIdStart, videoIdEnd);
}

const getVideoTitle = (video) => {
  return _.unescape(video.snippet.title);
}

const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

const store = useAskStore();

const create = function(video) {
  const ask = {
    userId: userId,
    askTitle: getVideoTitle(video),
    link: `https://www.youtube.com/watch?v=${video.id.videoId}`
  };

  store.createAsk(ask);
  alert("관리자에게 요청이 전송되었습니다!");
}
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