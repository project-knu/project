<template>
    <div class="header">
      <div class="logo">
        <img :src="logoSrc" alt="logo" class="logo-img">
        <p class="logo-letter">Title</p>
      </div>
      <button type="button" class="login" @click="goToLogin">로그인</button>
    </div>
  </template>
  
  <script>
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
  </script>