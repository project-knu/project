
// SummaryDate.vue
<template>
  <div class="summary-date summary-div">
    <p class="summary-p">보고서 날짜 및 시간</p>
    <p class="summary-text">{{ formattedDate }}</p>
  </div>
</template>

<script>
export default {
  name: 'SummaryDate',
  data() {
    return {
      date: new Date('2024-10-02')
    }
  },
  computed: {
    formattedDate() {
      // 날짜를 원하는 형식으로 포맷팅
      const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
      const year = this.date.getFullYear();
      const month = String(this.date.getMonth() + 1).padStart(2, '0');
      const day = String(this.date.getDate()).padStart(2, '0');
      const weekday = days[this.date.getDay()];
      
      return `${year}/${month}/${day}/${weekday}`;
    }
  }
}
</script>