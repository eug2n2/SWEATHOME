<template>
  <div id="clock">
    <span class="time">{{ time }}</span>
    <div class="btn-container">
      <a @click="start" id="start">START</a>
      <a @click="stop" id="stop">STOP</a>
      <a @click="reset" id="reset">RESET</a>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const time = ref('00:00:00');
let timeBegan = null;
let timeStopped = null;
let stoppedDuration = 0;
let started = null;
let running = false;

function start() {
  if (running) return;
  
  if (timeBegan === null) {
    reset();
    timeBegan = new Date();
  }

  if (timeStopped !== null) {
    stoppedDuration += (new Date() - timeStopped);
  }

  started = setInterval(clockRunning, 10);	
  running = true;
}

function stop() {
  running = false;
  timeStopped = new Date();
  clearInterval(started);
}

function reset() {
  running = false;
  clearInterval(started);
  stoppedDuration = 0;
  timeBegan = null;
  timeStopped = null;
  time.value = "00:00:00";
}

function clockRunning() {
  const currentTime = new Date();
  const timeElapsed = new Date(currentTime - timeBegan - stoppedDuration);
  const hour = timeElapsed.getUTCHours();
  const min = timeElapsed.getUTCMinutes();
  const sec = timeElapsed.getUTCSeconds();

  time.value = zeroPrefix(hour, 2) + ":" + 
               zeroPrefix(min, 2) + ":" + 
               zeroPrefix(sec, 2);
}

function zeroPrefix(num, digit) {
  let zero = '';
  for (let i = 0; i < digit; i++) {
    zero += '0';
  }
  return (zero + num).slice(-digit);
}
</script>

<style scoped>
@font-face {
    font-family: 'GangwonEduPowerExtraBoldA';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/GangwonEduPowerExtraBoldA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

#clock {
  width: 400px;
  box-sizing: border-box;
  background-color: white;
  z-index: 5;
  position: fixed;
  top: 20px; right: 20px;
  padding: 24px 24px 8px;
  border-radius: 20px;
  box-shadow: 4px 12px 30px 6px rgba(0,0,0,.09);
}

#clock .time {
  font-family: 'GangwonEduPowerExtraBoldA';
  font-size: 48px;
  display: inline-block;
  width: 100%;
  text-align: center;
}

#clock a {
  font-family: 'GangwonEduPowerExtraBoldA';
  font-size: 18px;
  cursor: pointer;
  background-color: #ff5c8a;
  color: white;
  margin: 0 16px;
  padding: 16px 16px 8px;
  border-radius: 8px;
  line-height: 50px;
}

#clock a:hover {
  background-color: #f05581;
}
</style>
