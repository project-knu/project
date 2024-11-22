<script setup>
</script>

<template>
    <div class="header">
      <div class="logo">
        <img :src="logoSrc" alt="logo" class="logo-img">
        <p class="logo-letter">Title</p>
      </div>
      <button type="button" class="login" @click="goToLogin">로그인</button>
    </div>
  </template>
  
  <!-- <script>
  export default {
    name: 'Header',
    data() {
      return {
        logoSrc: '' // 로고 이미지 경로
      }
    },
    methods: {
      goToLogin() {
        this.$router.push('/login');
      }
    }
  }
  </script> -->

<style scoped>
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 64px;
  border-bottom: 1px solid #D9D9D9;
  z-index: 10;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}
</style>