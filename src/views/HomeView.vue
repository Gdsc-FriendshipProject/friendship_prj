<template>
  <div>
    <nav>
      <router-link to="login" class="nav_router_link">로그아웃</router-link>
    </nav>
    <div class="home">
      <HelloWorld msg="Welcome to Your Vue.js App" class="hello_component"/>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  },
  methods:{

  }
}
</script>

<style scoped>
@font-face {
  font-family: 'Jua';
  src: url('@/assets/fonts/Jua-Regular.ttf') format('truetype');
}
.hello_component{
  clear: both;
}
.nav_router_link{
  float: right;
  margin-top: 0.2vw;
  margin-right: 0.4vw;

  font-family: Jua, sans-serif;
}
</style>