import axios from 'axios';

const instance = axios.create({
	baseURL: 'http://localhost:8080/',
});

function registerUser(userData) {
	return instance.post('join', userData);
}
function loginUser(userData) {
	return instance.post('login', userData);
}
function addPlan(planData){
	console.log("요청시도");
	return instance.post('addPlan', planData);
}
function getPlanList(keyword){
	console.log('일정 리스트 가져오기');
	return instance.post('list', keyword);
}

export { loginUser };
export { registerUser };
export { addPlan };
export { getPlanList };