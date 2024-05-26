<template>
  <div class="container">
    <h1>SWEATHOME AI 코치 (장원영 ver.)</h1>
    <div class="chatbox">
      <div v-if="response" v-for="(message, index) in chatHistory" :key="index" :class="message.type">
        <p>{{ message.content }}</p>
      </div>
    </div>
    <p v-if="exceededLimit" class="limit-exceeded">대화 횟수가 초과되었습니다.</p>
    <div id="input">
      <textarea v-model="userInput" placeholder="AI 코치에게 무엇이든 물어보세요!" @keyup.enter="sendQuestion"></textarea>
      <button @click="sendQuestion">전송</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const userInput = ref('')
const response = ref(null)
const chatHistory = ref([])
let conversationCount = 0
const conversationLimit = 10
let exceededLimit = false

const sendQuestion = async () => {
  if (conversationCount >= conversationLimit) {
    exceededLimit = true
    return
  }
  
  try {
    const result = await axios.post('http://localhost:8080/api-gpt/question', {
      content: userInput.value
    })
    response.value = result.data.choices[0].message.content
    chatHistory.value.push({ type: 'user-message', content: userInput.value })
    chatHistory.value.push({ type: 'bot-message', content: result.data.choices[0].message.content })
    conversationCount++
  } catch (error) {
    console.error('Error sending question:', error)
    response.value = 'Error: Unable to get response from the server.'
  }

  userInput.value = '';
}
</script>

<style scoped>
.container {
  width: 400px;
  height: 500px;
  min-height: 400px;
  box-sizing: border-box;
  background-color: white;
  z-index: 5;
  position: fixed;
  top: 200px; right: 20px;
  padding: 24px 24px 8px;
  border-radius: 20px;
  box-shadow: 4px 12px 30px 6px rgba(0,0,0,.09);
}

h1 {
  font-size: 18px;
  text-align: center;
  margin-bottom: 16px;
}

.chatbox {
  height: 340px;
  overflow: hidden;
  overflow-y: scroll;
}

.chatbox p {
  margin: 12px;
  padding: 12px 0 10px;
  font-size: 14px;
}

.user-message {
  background-color: #ffd2de;
  border-radius: 8px;
  margin-right: 12px;
}

.bot-message {
  background-color: #e7e7e7;
  border-radius: 8px;
  text-align: right;
  margin-right: 12px;
}

#input {
  display: flex;
  justify-content: space-between;
  margin-top: 16px;
}

#input textarea {
  width: 280px;
  resize: none;
  padding: 8px;
  border-radius: 8px;
  outline: none;
}

#input button {
  padding: 0 12px;
  background-color: #ff5c8a;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

#input button:hover {
  background-color: #f05581;
}

/* CSS 스타일
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.chatbox {
  border: 1px solid #ccc;
  padding: 10px;
  height: 400px;
  overflow-y: scroll;
  margin-bottom: 10px;
  text-align: left;
}

.chatbox > div {
  margin-bottom: 10px;
}

.user-message {
  background-color: #007bff;
  color: white;
  align-self: flex-end;
  border-radius: 10px;
  padding: 5px 10px;
}

.bot-message {
  background-color: #28a745;
  color: white;
  align-self: flex-start;
  border-radius: 10px;
  padding: 5px 10px;
}

.limit-exceeded {
  color: red;
  font-weight: bold;
} */
</style>
