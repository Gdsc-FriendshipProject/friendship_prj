<template>
  <div class="about" id="horizen">
    <div>
      <img alt="Vue logo" src="../assets/logo.png" class="logo">
      <add-plan></add-plan>
      <!-- <date-calendar></date-calendar> -->
      <!-- <detail-tab></detail-tab> -->
    </div>
  </div>
</template>

<script>
// import DateCalendar from '@/components/addPlan/DateCalendar.vue'
// import DetailTab from '@/components/addPlan/DetailTab.vue';
import AddPlan from '@/components/AddPlan.vue';

export default {
  name : 'AboutView', //출처 이름
  components: {
    // DateCalendar,
    // DetailTab,
    AddPlan,
  },
};
</script>

<style scoped>
#horizen{
  float: left;
}
.logo {
  padding-top: 1vw;
  padding-bottom:5vw;
  padding-left: 1vw;

  float: left;
}
img{
  width: 6vw;
}
</style>
