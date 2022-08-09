<template>
	<div>
		<img alt="Friendship Logo" src="../assets/logo.png" class="logo">
		<div class="container">
			<form @submit.prevent="submitForm">
				<div>
					<input type="text" id="email" v-model="email" placeholder="아이디"/>
				</div>
				<div>
					<input type="password" id="password" v-model="password" placeholder="비밀번호"/>
				</div>
				<div>
					<button type="submit">회원가입</button>  
					<button type = "button" @click="goBack">취소</button>
				</div>
			</form>
		</div>
	</div>
</template>


<script>
import { registerUser } from '@/api/index';

export default {
	name: 'SignupForm',
	data() {
		return {
			email: '',
			password: '',
		};
	},
	methods: {
		async submitForm() {
			const userData = {
				email: this.email,
				password: this.password,
			};
			var check = (this.email !== '') && (this.password !== '');
			
			if(check){
				alert("가입이 완료되었습니다.");
        const { data } = await registerUser(userData);
        console.log(data);
				this.initForm();
				this.$router.push('/login');
			}else if(this.email === '') {
        alert("가입하실 이메일을 입력해주세요.");
        // this.initPassword();
      }else{
        alert("이메일 혹은 비밀번호를 확인해주세요");
      }
			console.log(check);
			
		},
		goBack(){
			this.$router.go(-1);
			this.initForm();
		},
		initForm(){
			this.email='';
			this.password='';
		},
		initPassword(){
			this.password='';
		}
	},
};
</script>

<style scoped>
@font-face {
  font-family: 'Jua';
  src: url('@/assets/fonts/Jua-Regular.ttf') format('truetype');
}

* {
  box-sizing: border-box;
  font-family: 'Jua', sans-serif;
  border-radius: 5px;
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
 
#email {
   font-size: 14px;
   padding: 10px;
   border: none;
   width: 260px;
   margin-bottom: 10px;
}
 
#password {
   font-size: 14px;
   padding: 10px;
   border: none;
   width: 260px;
   margin-bottom: 10px;
}
 
button[type=submit] {
   font-size: 14px;
   border: 3px solid #393D4B !important;
   padding: 10px;
   width: 120px;
   background-color: #393D4B;
   margin: 20px 5px;
   color: white;
}

button[type=button] {
   font-size: 14px;
   padding: 10px;
   width: 120px;
   border: 3px solid #393D4B !important;
   /* margin-bottom: 30px; */
   margin: 20px 5px;
   background-color: white;
}
</style>