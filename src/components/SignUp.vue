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
			const { data } = await registerUser(userData);
			console.log(data);

			var check = (this.email != null) && (this.password != null);
			
			if(check){
				alert("로그인 후 이용해주세요.");
				this.initForm();
				this.$router.push('/login');
			}else{
				alert("비밀번호를 확인해주세요");
				this.initPassword();
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
* {
  box-sizing: border-box;
  font-family: 'Noto Sans KR', sans-serif;
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