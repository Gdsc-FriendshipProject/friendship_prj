<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" class="logo">
    <HelloWorld msg="Welcome to Your Vue.js App" class="hello_component"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  }
}
</script>

<style scoped>
.logo {
  padding-top: 1vw;
  padding-bottom: 10vw;
  padding-left: 1vw;

  float: left;
}
img{
  width: 6vw;
}
.hello_component{
  clear: both;
}
</style>