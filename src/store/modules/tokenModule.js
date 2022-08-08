const token = {
    namespaced:true,
    state : {
        loginToken : '',
    },
    getters: {
        isLogin(state){
            return state.loginToken !== '';
        },
        getToken(state){
            let token = state.loginToken;
            return token;
        },
    },
    mutations: {
        setToken(state, payload){
            console.log('setToken');
            state.loginToken = payload;
        },
        clearId(state){
            state.loginToken = '';
        },
    },
    actions: {
        setToken:({commit} , payload) => {
            commit('setToken' , payload);
        },
        isLogin:({commit}) => {
            commit('getToken');
        },
    },
}

export default {
    token
};