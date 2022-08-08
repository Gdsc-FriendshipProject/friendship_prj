<template>
  <div id="app">
    <!-- <nav>
      <router-link to="/">Main</router-link> | 
      <router-link to="/about">AddPlan</router-link> | 
      <router-link to="/signup">SignUp</router-link> | 
      <router-link to="/login">LogIn</router-link>
    </nav> -->
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

body {
  margin: 0;
  background-color: #F5F5F5 !important;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
