import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

import Token from '@/store/modules/tokenModule.js'

export default new Vuex.Store({
  state: {
    markerList:[],
    placeName: [],
  },
  getters: {
    markerListIsEmpty(state){
      return state.markerList == null;
    },
    getMarkerList(state){
      return state.markerList;
    },
    getPlaceName(state){
      return state.placeName;
    }
  },
  mutations: {
    setMarkers(state, markerList){
      state.markerList = markerList;
    },
    setPlace(state, placeName){
      state.placeName = placeName;
    }
  },
  actions: {
  },
  modules: {
    token : Token
  },
  plugins: [createPersistedState({
      paths: ['token'],
    })]
})