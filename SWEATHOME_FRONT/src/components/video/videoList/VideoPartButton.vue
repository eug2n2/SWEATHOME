<template>
    <div id="part-button">
        <ul>
            <li :class="{ active: activePart === '전체' }" @click="searchPart('전체')">전체</li>
            <li :class="{ active: activePart === '상체' }" @click="searchPart('상체')">상체</li>
            <li :class="{ active: activePart === '하체' }" @click="searchPart('하체')">하체</li>
            <li :class="{ active: activePart === '전신' }" @click="searchPart('전신')">전신</li>
            <li :class="{ active: activePart === '스트레칭' }" @click="searchPart('스트레칭')">스트레칭</li>
        </ul>
    </div>
</template>

<script setup>
import { useVideoStore } from '@/stores/video';
import { ref } from 'vue';

const store = useVideoStore();
const activePart = ref('전체');

const searchPart = function(value) {
  activePart.value = value;
  store.videoPartSearch(value);
}
</script>

<style scoped>
#part-button {
    width: 70%;
    margin: 0 auto 40px;
}

#part-button ul {
    display: flex;
}

li {
    background-color: #eee;
    padding: 8px 20px;
    border-radius: 24px;
    font-size: 14px;
    margin-right: 12px;
    cursor: pointer;
}

.active {
    background-color: #ff5c8a;
    color: white;
    font-weight: 600;
}
</style>