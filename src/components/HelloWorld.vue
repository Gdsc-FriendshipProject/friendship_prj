<template>
  <div class="main">
    <div class="main_header">
      <img alt="Vue logo" src="../assets/logo.png" class="main_logo">
      <button v-on:click="goAddPlan" class="addplan_btn">일정 만들기</button>
    </div>
    <div class="search">
      <b-form-input 
        type="text"
        placeholder="찾고 있는 일정이 있나요?"
        v-model = "keyword"
        class="search_plan"
        @keyup.enter="searchPlan(keyword)"
      >
      </b-form-input>
      <b-button type="submit" @click="searchPlan(keyword)" class="search_btn">
        <b-icon icon="search" class="search_icon"></b-icon>
      </b-button>
    </div>
    <div class="plan_card">
      <div class="table_div">
        <table class="table_card">
          <div v-if="show" ref="planWrapper" class="planWrapper">
          <div v-for="(plan, idx) in plans" :key="idx">
            <tr class="tr_card">
              <td v-for="(index) in plan" :key="index" class="td_card">
                <b-card
                img-src='./date.png'
                img-alt="Image"
                img-top
                tag="article"
                class="mb-2">
                  <b-card-body class="main_card_body">
                    <div class="main_card_body_div">
                      <table class="main_card_table">
                        <tr>
                          <td class="main_semi_title">제 목</td>
                          <td colspan="2" style="width: 15vw">qdfgdfw</td>
                        </tr>
                      </table>
                    </div>
                    <div class="main_card_body_div">
                      <table class="main_card_memo">
                        <tr>
                          <td class="main_semi_memo">메 모</td>
                          <td colspan="2" style="width: 15vw">qdfgdfdw</td>
                        </tr>
                      </table>
                    </div>
                  </b-card-body>
                  <template>
                    <div>
                      <b-button v-b-toggle="'sidebar'+index" class="main_detail" v-on:click="setIndex(index)">자세히 보기</b-button>
                      <b-sidebar v-bind:id="'sidebar'+index" title="Detail" right shadow>
                        <div class="px-3 py-2">
                          <p>
                            {{index}}
                          </p>
                          <b-img src="https://picsum.photos/500/500/?image=54" fluid thumbnail></b-img>
                        </div>
                      </b-sidebar>
                    </div>
                  </template>
                </b-card>
              </td>
            </tr>
          </div>
        </div>
        </table>
        <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
      </div>
    </div>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading'
import { getPlanList } from '@/api/index'
import axios from 'axios'

export default {
  name: 'HelloWorld',
  components:{
    InfiniteLoading
  },
  data(){
    return{
      keyword: '',
      show: false,
      plans:[[1,2,3],[4,5,6],[7,8,9]],
      pageNum: 0,
      token: '',
      index: 0,
    }
  },
  methods: {
    async searchPlan(keyword) {
      if (keyword !== ''){ //검색어를 입력한 경우
        this.keyword = keyword;
        console.log('"',keyword,'"' + ' 검색');

        const resp = await getPlanList(this.keyword);
        console.log(resp.data);

        this.pageNum = 0;

        // 'list/{keyword}/{pageNum}'
        // 'list//0'
        // 'list/all/0'
        // 'list/all/1'

        axios.get('http://localhost:8081/list/'+ this.keyword+ "/" + this.pageNum)
          .then((res) => {
            this.plans = res.data;
            this.pageNum++;
          })
      } else {
        alert('검색어를 입력해주세요!');  //검색어를 입력하지 않은 경우
      }


    },
    infiniteHandler($state) {
      axios.get('http://localhost:8081/list/'+ this.keyword + this.pageNum)
      .then(res =>{
        setTimeout(() => {
          if(res.data.length){
            this.plans = this.plans.concat(res.data);
            this.pageNum += 1;
            $state.loaded();
          }else{
            $state.complete();
          }
        }, 1000);
      })
    },
    setIndex(index){
      this.index = index;
    },
    goAddPlan(){
      this.$router.push('/about');
    }
  },
  mounted() {
    this.show = true;
    this.token = sessionStorage.getItem('login_session');
  },
  created(){
    axios.get('http://localhost:8081/list/' + this.pageNum)
    .then((res) => {
      this.plans = res.data;
      this.pageNum++;
    })
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@font-face {
  font-family: 'Jua';
  src: url('@/assets/fonts/Jua-Regular.ttf') format('truetype');
}

.main{
  margin: auto;
}
.main_header{
  display: flex;
  justify-content: space-between;
}
.main_logo {
  margin-top: 4vw;
  margin-bottom: 1vw;
  margin-left: 13vw;

  float: left;
  width: 6vw;
}

.search{
  align-items: center;

  display: flex;
  justify-content: center;
}
.search_plan{
  width: 70vw;
  height: 5vw;
  left: 354px;
  top: 336px;

  border: 0.3vw solid #4CC2CC !important;

  font-size: 3vw;

  font-family: 'Jua',sans-serif;
}
.search_btn{
  width: 5vw;
  height: 5vw;
  left: 354px;
  top: 336px;

  background-color: #4CC2CC;
  border: 0;
}
.search_icon{
  width: 70%;
  height: 70%;
}

.addplan_btn{
  background-color: #F58498;

  margin-top: 4.7vw;
  margin-bottom: 0.3vw;
  margin-right: 12vw;

  border: none;
  border-radius: 5px;

  width: auto;
  height: 3vw;

  color: white;
  font-family: 'Jua', sans-serif;
  font-size: 1.5vw;
  /*font-weight: bold;*/
}
.plan_card{
  clear: both;
  padding-top: 2vw;

  align-items: center;

  display: flex;
  justify-content: center;
}
.card-img-top{
  width: 12vw;
  align-self: center;
}
.table_div{
  border-radius: 5px;
  border: 0.3vw solid #4CC2CC;
}
.table_card{
  width: 74.5vw;
}
.td_card{
  /* align-items: center; */
  padding: 1vw;
  width: 27vw;
  margin: 10vw;
}
.main_card_body{
  display: flex;
  flex-direction: column;
  justify-content: center;

  font-family: Jua, sans-serif;
  font-size: 1vw;
}
.main_card_body_div{
  border-radius: 5px;
  border: 0.3vw solid !important;
  border-color: #4CC2CC !important;

  margin-bottom: 0.3vw;
}
.main_card_table, .main_card_memo{
  border-collapse: collapse;
  margin: -0.1vw;
  float: left;
}
.main_semi_title, .main_semi_memo{
  background-color: #4CC2CC;
  border: 0.3vw solid #4CC2CC !important;
  padding-top: 0.28vw;

  width: 3vw;

  font-size: 1vw;
  color: white;
}
.main_detail{
  background-color: #F5BB57 !important;
  border: 0.3vw solid #F5BB57 !important;

  color: white;
  font-family: 'Jua', sans-serif;
  font-size: 1vw;
}
</style>
