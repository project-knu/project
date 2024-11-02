<!-- <script>
export default {
  data () {
    return {

    };
  };
};
</script>

// src/App.vue -->
<template>
  <div class="container">
    <Header />
    <SummaryArea />
  </div>
</template>

<script>
import Header from './components/Header.vue';
import SummaryArea from './components/SummaryArea.vue';

export default {
  name: 'App',
  components: {
    Header,
    SummaryArea
  }
}
</script>