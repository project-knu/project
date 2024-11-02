
<template>
  <div class="home-div">
    <a class="home" @click.prevent="goToHome">
      <img src="@/assets/prev-icon.png" alt="">
      목록으로 돌아가기
    </a>
  </div>
</template>

<script>
export default {
  name: 'HomeButton',
  methods: {
    goToHome() {
      this.$router.push('/');
    }
  }
}
</script>