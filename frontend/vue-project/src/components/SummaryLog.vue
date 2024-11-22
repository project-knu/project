<template>
    <div class="summary-log summary-div">
      <p class="summary-p">로그</p>
      <div 
        v-for="(log, index) in logs" 
        :key="index" 
        class="log"
      >
        <p class="time">{{ log.time }}</p>
        <p class="description">{{ log.description }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SummaryLog',
    data() {
      return {
        logs: [
          { time: '15:11', description: '한 남성이 길을 걷고있다.' },
          { time: '15:15', description: '한 남성이 넘어졌다.' }
        ]
      }
    }
  }
  </script>