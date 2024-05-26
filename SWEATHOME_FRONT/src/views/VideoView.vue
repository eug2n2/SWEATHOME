<template>
    <RouterView/>
    <button id="chat-bot" @click="toggleChatbot">
        <i class="ph-duotone ph-finn-the-human"></i>
    </button>
    <Stopwatch v-if="showStopwatch"/>
    <button id="stop-watch" @click="toggleStopwatch">
        <i class="ph-duotone ph-timer"></i>
    </button>
    <ChatBot v-if="showChatbot"/>
</template>

<script setup>
import Stopwatch from '@/components/stopwatch/Stopwatch.vue';
import ChatBot from '@/components/chatgpt/ChatBot.vue';
import { ref } from 'vue';

const showStopwatch = ref(false);
function toggleStopwatch() {
    showStopwatch.value = !showStopwatch.value;
}
const showChatbot = ref(false);
function toggleChatbot() {
    showChatbot.value = !showChatbot.value;
}
</script>

<style scoped>
#stop-watch, #chat-bot {
    border-radius: 50%;
    border: 3px solid #ff5c8a;
    width: 60px; height: 60px;
    cursor: pointer;
    background-color: rgba(255, 255, 255);
}

#stop-watch:hover, #chat-bot:hover {
    background-color: #fff3f6;
}

#stop-watch {
    position: fixed;
    bottom: 90px;
    right: 20px;
}

#chat-bot {
    position: fixed;
    bottom: 20px;
    right: 20px;
}

.ph-duotone {
    font-size: 32px;
    color: #ff5c8a;
}
</style>