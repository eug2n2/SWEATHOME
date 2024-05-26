<template>
  <div id="check-graph">
    <h1>
      <i class="ph-bold ph-chart-line-up"></i>
      운동 시간 추이
    </h1>
    <Line v-if="chartReady" id="my-chart-id" :options="chartOptions" :data="chartData" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, LineController, CategoryScale, LinearScale, Filler } from 'chart.js';
import { useCheckStore } from '@/stores/check';
import { useStickerStore } from '@/stores/sticker';

const store = useCheckStore();
const stickerStore = useStickerStore();
const token = sessionStorage.getItem('access-token');
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;

const today = new Date();
const year = today.getFullYear();
const month = String(today.getMonth() + 1).padStart(2, '0'); 
const day = String(today.getDate()).padStart(2, '0');
const formattedDate = `${year}-${month}-${day}`;

ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, LineController, CategoryScale, LinearScale, Filler);

const generateLabels = () => {
  const labels = [];
  const today = new Date();
  for (let i = 6; i >= 0; i--) {
    const date = new Date(today);
    date.setDate(today.getDate() - i);
    const year = String(date.getFullYear()).slice(-2);
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    labels.push(`${year}-${month}-${day}`);
  }
  return labels;
};

const chartData = ref({
  labels: generateLabels(),
  datasets: [
    {
      label: '총 운동 시간 (단위: 분)',
      data: [],
      borderColor: '#fd3e81',
      fill: true, 
      backgroundColor: '#fd3e813a',
      tension: 0.3,
      pointRadius: 6,
      pointHoverRadius: 8,
      pointBackgroundColor: '#fd3e81',
    },
  ],
});

const chartOptions = ref({
  responsive: true,
  plugins: {
    legend: {
      display: true,
      position: 'bottom',
      labels: {
        font: {
          family: 'SCoreDream',
          size: 14
        }
      }
    },
  },
  scales: {
    x: {
      ticks: {
        font: {
          family: 'SCoreDream',
          size: 14
        }
      }
    },
    y: {
      ticks: {
        font: {
          family: 'SCoreDream',
          size: 14
        },
        beginAtZero: true
      }
    }
  }
});

const chartReady = ref(false);

const updateChartData = () => {
  chartData.value.datasets[0].data = [
    store.graphTimes.one,
    store.graphTimes.two,
    store.graphTimes.three,
    store.graphTimes.four,
    store.graphTimes.five,
    store.graphTimes.six,
    store.graphTimes.seven,
  ];
  chartReady.value = true; // 데이터를 업데이트한 후 차트를 표시
};

const checkAndRewardUser = async () => {
  const events = await stickerStore.selectEvent(userId);

  // events가 숫자 타입인 경우 처리
  if (events === false && store.graphTimes.seven >= 30) {
    await stickerStore.addGotcha(userId);
    await stickerStore.insertEvent(userId);
    alert('오늘 도감 뽑기권을 획득했습니다. 마이페이지에서 확인하세요!');
  }
};

onMounted(async () => {
  await store.getGraphCheck(userId, formattedDate);
  updateChartData();
  await checkAndRewardUser();
});

</script>

<style scoped>
#check-graph {
  width: 70%;
  margin: 0 auto 100px;
}

h1 {
  font-size: 20px;
  display: flex;
  align-items: center;
  margin-bottom: 24px;
}

h1 i {
  font-size: 28px;
  margin-right: 8px;
}
</style>
