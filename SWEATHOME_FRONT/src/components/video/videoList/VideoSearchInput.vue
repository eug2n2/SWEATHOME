<template>
    <div id="searchinput">
        <label for="search">
            <i class="ph ph-magnifying-glass"></i>
        </label>
        <input id="search" type="text" placeholder="검색어를 입력하세요" v-model="keyword" @keyup.enter="search" />
        <button @click="search">검색</button>
    </div>
</template>

<script setup>
import { useVideoStore } from '@/stores/video';
import { ref } from 'vue';

const store = useVideoStore();
const keyword = ref('');
const search = function() {
    store.videoSearch(keyword.value);
    keyword.value = '';
}
</script>

<style scoped>
#searchinput {
    width: 70%;
    margin: 0 auto 20px;
    position: relative;
    display: flex;
}

#searchinput i {
    position: absolute;
    top: 13px; left: 12px;
    color: #aaa
}

#searchinput input {
    width: 40%;
    padding: 12px 16px 12px 32px;
    border-radius: 8px;
    border: 1px solid #aaa;
    margin-right: 8px;
}

#searchinput input:focus {
    outline: none;
}

#searchinput button {
    padding: 10px 24px;
    background-color: #ff5c8a;
    border-radius: 8px;
    border: none;
    color: white;
    cursor: pointer;
    font-size: 16px;
}

#searchinput button:hover {
    background-color: #f05581;
}
</style>