<template>
    <div id="create">
        <div id="create-form">
            <h1>운동 영상 등록</h1>
            <div class="flex">
                <p class="title">- 영상 제목</p>
                <input type="text" id="title" placeholder="영상 제목을 입력하세요." v-model="video.videoTitle">
            </div>
            <div class="flex">
                <p class="title">- 영상 부위</p>
                <div id="select-box">
                    <label for="top">
                        상체 <input type="radio" id="top" name="part" value="상체" v-model="video.part">
                    </label>
                    <label for="bottom">
                        하체 <input type="radio" id="bottom" name="part" value="하체" v-model="video.part">
                    </label>
                    <label for="whole">
                        전신 <input type="radio" id="whole" name="part" value="전신" v-model="video.part">
                    </label>
                    <label for="stretch">
                        스트레칭 <input type="radio" id="stretch" name="part" value="스트레칭" v-model="video.part">
                    </label>
                </div>
            </div>
            <div class="flex">
                <p class="title">- 영상 링크</p>
                <input type="text" id="link" placeholder="유튜브 영상 링크를 입력하세요." v-model="video.link"/>
            </div>
            <button @click="createVideo">등록</button>
        </div>
    </div>
</template>

<script setup>
import { useVideoStore } from '@/stores/video';
import { ref } from 'vue';

const store = useVideoStore();

const video = ref({
    videoTitle: "",
    part: "",
    link: "",
})

const createVideo = function() {
    store.createVideo(video.value)
}
</script>

<style scoped>
#create {
    width: 70%;
    margin: 0 auto;
}

#create-form {
    width: 70%;
    margin: 0 auto;
    padding: 48px 40px;
    border-radius: 20px;
    box-shadow: 4px 12px 30px 6px rgba(0,0,0,.09);
}

h1 {
    font-size: 24px;
    text-align: center;
    margin-bottom: 48px;
}

.flex p {
    font-size: 18px;
    font-weight: 500;
    margin-bottom: 8px;
}

.flex input {
    margin-bottom: 40px;
}

.flex input[type=radio] {
    margin-right: 12px;
}

.flex input[type=text] {
    box-sizing: border-box;
    width: 100%;
    padding: 12px;
    font-size: 14px;
    border-radius: 8px;
    border: 1px solid #aaa;
}

button {
    width: 100%;
    padding: 12px 0;
    background-color: #ff5c8a;
    border-radius: 8px;
    border: none;
    font-size: 16px;
    color: white;
    cursor: pointer;
}

button:hover {
    background-color: #f05581;
}
</style>