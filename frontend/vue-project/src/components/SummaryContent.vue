<template>
    <div class="summary-content summary-div">
      <p class="summary-p">내용</p>
      <div class="input-button-textarea">
        <textarea 
          class="summary-text"
          v-model="content"
          :disabled="!isEditing"
        ></textarea>
        <button 
          type="submit" 
          class="summary-submit-textarea"
          @click="updateContent"
        >
          <img :src="pencilIcon" alt="연필" class="summary-submit-img">
        </button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SummaryContent',
    data() {
      return {
        content: '한국어 더미 텍스트 예시입니다...',
        isEditing: false,
        pencilIcon: '' // 연필 아이콘 경로
      }
    },
    methods: {
      updateContent() {
        this.isEditing = !this.isEditing;
        if (!this.isEditing) {
          // API 호출하여 내용 업데이트
        }
      }
    }
  }
  </script>