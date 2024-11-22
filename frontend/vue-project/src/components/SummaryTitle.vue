<template>
    <div class="summary-title summary-div">
      <p class="summary-p">제목</p>
      <div class="input-button">
        <input 
          type="text" 
          class="summary-text" 
          v-model="title"
          :disabled="!isEditing"
        >
        <button 
          type="submit" 
          class="summary-submit"
          @click="updateTitle"
        >
          <img :src="pencilIcon" alt="연필" class="summary-submit-img">
        </button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SummaryTitle',
    data() {
      return {
        title: '남성이 넘어지는 영상',
        isEditing: false,
        pencilIcon: '' // 연필 아이콘 경로
      }
    },
    methods: {
      updateTitle() {
        this.isEditing = !this.isEditing;
        if (!this.isEditing) {
          // API 호출하여 제목 업데이트
        }
      }
    }
  }
  </script>