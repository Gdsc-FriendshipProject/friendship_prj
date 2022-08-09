<template>
  <div>
    <nav>
      <router-link to="login" class="nav_router_link">로그아웃</router-link>
    </nav>
    <div class="about" id="horizen">
      <div>
        <add-plan></add-plan>
      </div>
    </div>
  </div>
</template>

<script>
import AddPlan from '@/components/AddPlan.vue';

export default {
  name : 'AboutView', //출처 이름
  components: {
    AddPlan,
  },
  methods:{
  }
};
</script>

<style scoped>
@font-face {
  font-family: 'Jua';
  src: url('@/assets/fonts/Jua-Regular.ttf') format('truetype');
}

#horizen{
  float: left;
}
.nav_router_link{
  float: right;
  margin-top: 0.2vw;
  margin-right: 0.4vw;

  font-family: Jua, sans-serif;
}
</style>
