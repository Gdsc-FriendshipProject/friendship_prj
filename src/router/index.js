import Vue from 'vue'
import VueRouter from 'vue-router'
import MainpageView from '../views/MainPageVIew.vue'
import AddplanView from '../views/AddPlanView.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'main',
    component: MainpageView,
    meta :{authRequired: true},

    beforeEnter: function(_to, _from, next) {
      var loginToken = sessionStorage.getItem("login_session");
      console.log(loginToken);
      if(loginToken !== null){
        alert('Welcome!');
        next();
      }else{
        alert('Please sign up or sign in.');
        next({path: '/login'});
      }
    }
  },
  {
    path: '/addPlan',
    name: 'addPlan',
    component: AddplanView,
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('@/components/SignUp.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/components/LogIn.vue'),
    meta :{authRequired: true},

    beforeEnter: function(_to, _from, next) {
      var loginToken = sessionStorage.getItem("login_session");
      if(loginToken !== null){
        sessionStorage.removeItem("login_session");
        next();
      }else{
        next();
      }
    }
  },
  {
    path: '/infinite',
    name: 'infinite',
    component: () => import('@/components/InfiniteLoading.vue'),
  },
  {
    path: '/map',
    name: 'map',
    component: () => import('@/components/KakaoMap.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
