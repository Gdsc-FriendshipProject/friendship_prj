import axios from 'axios';

const instance = axios.create({
	baseURL: 'http://localhost:8081/',
});

function registerUser(userData) {
	return instance.post('join', userData);
}
function loginUser(userData) {
	return instance.post('login', userData);
}
function addPlan(planData){
	return instance.post('addPlan', planData);
}
function getPlanList(keyword){
	console.log('일정 리스트 가져오기');
	return instance.post('addPlan/list', keyword);
}

export { loginUser };
export { registerUser };
export { addPlan };
export { getPlanList };