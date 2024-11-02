<template>
    <div class="summary-area">
      <HomeButton />
      <VideoPlayer />
      <SummaryTitle />
      <SummaryDate />
      <SummaryContent />
      <SummaryCreateDate />
      <SummaryLocation />
      <SummaryLog />
    </div>
  </template>
  
  <script>
  import HomeButton from './HomeButton.vue';
  import VideoPlayer from './VideoPlayer.vue';
  import SummaryTitle from './SummaryTitle.vue';
  import SummaryDate from './SummaryDate.vue';
  import SummaryContent from './SummaryContent.vue';
  import SummaryCreateDate from './SummaryCreateDate.vue';
  import SummaryLocation from './SummaryLocation.vue';
  import SummaryLog from './SummaryLog.vue';
  
  export default {
    name: 'SummaryArea',
    components: {
      HomeButton,
      VideoPlayer,
      SummaryTitle,
      SummaryDate,
      SummaryContent,
      SummaryCreateDate,
      SummaryLocation,
      SummaryLog
    }
  }
  </script>