<template>
    <div class="summary-create-date summary-div">
      <p class="summary-p">영상 생성 시간</p>
      <p class="summary-text">{{ createTime }}</p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SummaryCreateDate',
    data() {
      return {
        createTime: '15:14'
      }
    }
  }
  </script>
  