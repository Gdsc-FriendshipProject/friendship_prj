<template>
  <div>
    <img alt="Friendship Logo" src="../assets/logo.png" class="logo">
    <div class="container">
      <form @submit.prevent="submitForm" class="login">
        <div>
          <input type="text" id="email" v-model="email" placeholder="아이디" class="login_box"/>
        </div>
        <div>
          <input type="password" id="password" v-model="password" placeholder="비밀번호" class="password_box"/>
        </div>
        <div class="rl_signup_findidpw">
          <router-link to="/signup">회원 가입</router-link>  |  
          <router-link to="#">아이디/비밀번호 찾기</router-link>
        </div>
        <div>
          <button 
            :disabled="!email || !password"
            type="submit"
            class="login_btn"
            >
            로그인
          </button>
        </div>
      </form>
    </div>
  </div>
  
</template>

<script>
import { loginUser } from '@/api/index'
import tokenModule from '@/store/modules/tokenModule.js';

export default {
  name: 'LoginForm',
  data() {
    return{
      email: '',
      password: '',
      token: '',
    };
  },
  methods: {
    async submitForm(){
      try{
        const userData = {
          email: this.email,
          password: this.password
        };

        const res =  await loginUser(userData);
        if(res.status === 200){
          this.token = res.data;
          console.log('res.data : ', this.token);
          
          // token.token.actions.setToken(this.token); // -> 작동 안함
          tokenModule.token.state.loginToken = this.token;
          this.$cookies.set('login_token', this.token, 10);
          sessionStorage.setItem('login_session', this.token);

          console.log('flag');
          
          // this.$store.dispatch['token/saveStateToStorage', null]; // -> 먹통임 ㅅㅂ
          
        }
      } catch(e){
        console.log('에러 진입');
      } finally{
        //페이지 초기화
        this.initForm();
        this.$router.push('/');
      }
    },
    initForm(){
      this.email = '';
      this.password='';
    }
  },

}
</script>

<style scpoed>
@font-face {
  font-family: 'Jua';
  src: url('@/assets/fonts/Jua-Regular.ttf') format('truetype');
}

* {
  box-sizing: border-box;
  font-family: 'Jua', sans-serif;
  border-radius: 5px !important;
}

.login_box, .password_box{
  font-size: 14px;
  padding: 10px;
  border: none;
  width: 260px;
  margin-bottom: 10px;
}

.container {
  width: 400px;
  background-color: #e7e7e7;
  margin-right: auto;
  margin-left: auto;
  /* margin-top: 50px; */
  padding: 20px;
  text-align: center;
  border: none;   
}

.logo {
  padding-top: 9%;
  padding-bottom: 2%;
}

.rl_signup_findidpw{
  padding-top: 0.5%;
}

.login_btn{
  font-size: 14px;
  border: 3px solid #393D4B !important;
  padding: 10px;
  width: 120px;
  background-color: #393D4B;
  margin: 20px 5px;
  color: white;
}
</style>