<template>
    <div id="app">
      <TheHeaderNav/>
      <RouterView/>
      <TheFooter v-if="store.loginUserId"/>
    </div>
  </template>
  
  <script setup>
  import { onMounted } from 'vue';
  import TheHeaderNav from './components/common/TheHeaderNav.vue';
  import TheFooter from './components/common/TheFooter.vue';
  import { useUserStore } from '@/stores/user';
  
  const store = useUserStore();
  console.log(`
            .....:....    
          ..    :.   :   
          -.   :     .:  
        ..-:-:::::::.-   
     ...            :::.  
    .:    *    *      .. 
    :       __         - 
    .:                :. 
      .::.     :.......  
    ::::.:.....:.:: -.   
   .:....:.        .-:   
    -:...:-.:....:: :    
      ...  ..      .  
`);

  </script>
  
  <style scoped>
  /* 스타일 추가 */
  </style>
  