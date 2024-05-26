<template>
  <div id="check-calendar">
    <h1>
      <i class="ph-bold ph-calendar-check"></i>
      운동 체크리스트
    </h1>
  
    <div id="calendar-flex">
      <div class="calendar-container">
        <div class="calendar-header">
          <i class="ph ph-caret-circle-left" @click="prevMonth"></i>
          <span id="currentMonth">{{ currentYear }}년 {{ currentMonth + 1 }}월</span>
          <i class="ph ph-caret-circle-right" @click="nextMonth"></i>
        </div>
        <div class="calendar-days">
          <span>Sun</span>
          <span>Mon</span>
          <span>Tue</span>
          <span>Wed</span>
          <span>Thu</span>
          <span>Fri</span>
          <span>Sat</span>
        </div>
        <div id="calendarDates" class="calendar-dates">
          <div v-for="date in dates"
               :key="date.id"
               :class="{ 'date': date.content !== '', 'empty': date.content === '', 'today': isToday(date.content) }">
            {{ date.content }}
          </div>
        </div>
      </div>
    
      <div id="todo-container">
        <p id="title">{{ today.getFullYear() }}년 {{ today.getMonth() + 1 }}월 {{ today.getDate() }}일 운동일지</p>
        <div id="add-list">
          <label for="exercise">운동</label>
          <input type="text" name="exercise" v-model="check.exerciseKind" placeholder="운동을 입력하세요">
          <label for="start-time">시작 시간</label>
          <input type="time" name="start-time" v-model="check.startTime">
          <label for="end-time">종료 시간</label>
          <input type="time" name="end-time" v-model="check.endTime">
          <i class="ph-fill ph-plus-circle" @click="createCheck"></i>
        </div>
        <div id="fitness-list">
          <ul>
            <li v-for="check in store.checkList" :key="check.startTime">
              <div class="fitness-info">
                <span>{{ check.exerciseKind }}</span>
                <span>&nbsp; | &nbsp;</span>
                <span>{{ formatTime(check.startTime) }}</span>
                <span> ~ </span>
                <span>{{ formatTime(check.endTime) }}</span>
              </div>
              <i class="ph-fill ph-minus-circle" @click="deleteCheck(check.exerciseKind, check.startTime, check.endTime)"></i>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCheckStore } from '@/stores/check';
import { useStickerStore } from '@/stores/sticker';
import { ref, computed, onMounted } from 'vue';

const today = new Date();
const currentMonth = ref(today.getMonth());
const currentYear = ref(today.getFullYear());
const dates = ref([]);

const renderCalendar = () => {
  const firstDayOfMonth = new Date(currentYear.value, currentMonth.value, 1);
  const daysInMonth = new Date(currentYear.value, currentMonth.value + 1, 0).getDate();
  const startDayOfWeek = firstDayOfMonth.getDay();

  const tempDates = [];

  // 빈 날짜 (이전 달)
  for (let i = 0; i < startDayOfWeek; i++) {
    tempDates.push({ id: `empty-${i}`, classes: 'date empty', content: '' });
  }

  // 현재 달의 날짜
  for (let i = 1; i <= daysInMonth; i++) {
    tempDates.push({ id: `date-${i}`, classes: 'date', content: i });
  }

  dates.value = tempDates;
};

const prevMonth = () => {
  currentMonth.value--;
  if (currentMonth.value < 0) {
    currentMonth.value = 11;
    currentYear.value--;
  }
  renderCalendar();
};

const nextMonth = () => {
  currentMonth.value++;
  if (currentMonth.value > 11) {
    currentMonth.value = 0;
    currentYear.value++;
  }
  renderCalendar();
};

const isToday = (day) => {
  const today = new Date();
  return today.getDate() === day && today.getMonth() === currentMonth.value && today.getFullYear() === currentYear.value;
};

const year = today.getFullYear();
const month = String(today.getMonth() + 1).padStart(2, '0'); 
const day = String(today.getDate()).padStart(2, '0');
const formattedDate = `${year}-${month}-${day}`;

onMounted(() => {
  renderCalendar();
  store.getCheckList(userId, formattedDate);
});

const store = useCheckStore();
const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

const formatTime = function(value) {
  if (!value) return '';
  return value.slice(0, 5);
};

const check = ref({
  userId: userId,
  exerciseDate: formattedDate,
  exerciseKind: "",
  startTime: "",
  endTime: ""
})

const createCheck = function() {
  store.createCheck(check.value);
  store.getCheckList(userId, formattedDate);
  window.location.reload();
}

const deleteCheck = function(exerciseKind, startTime, endTime) {
  const checkToDelete = {
    userId: userId,
    exerciseDate: formattedDate,
    exerciseKind: exerciseKind,
    startTime: startTime,
    endTime: endTime
  };
  store.deleteCheck(checkToDelete);
  store.getCheckList(userId, formattedDate);
  window.location.reload();
}
</script>

<style scoped>
@import url('/src/assets/css/calendar.css');

#check-calendar {
  width: 70%;
  margin: 56px auto 80px;
}

h1 {
  font-size: 20px;
  display: flex;
  align-items: center;
}

h1 i {
  font-size: 28px;
  margin-right: 8px;
}

#calendar-flex {
  display: flex;
  justify-content: space-between;
  margin-top: 24px;
  min-width: 1150px;
}
</style>