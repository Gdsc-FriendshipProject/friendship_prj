<template>
  <div class="main">
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
    <div>
      <button v-on:click="goAddPlan" class="addplan_btn">Make Plans</button>
    </div>
    <div class="plan_card">
      <div>
        <table class="table_card">
          <div v-if="show" ref="planWrapper" class="planWrapper">
          <div v-for="(plan, idx) in plans" :key="idx">
            <tr class="tr_card">
              <td v-for="(index) in plan" :key="index" class="td_card">
                <b-card
                title="일정 제목"
                img-src='./date.png'
                img-alt="Image"
                img-top
                tag="article"
                class="mb-2">
                  <b-card-text>{{index}}</b-card-text>
                  <template>
                    <div>
                      <b-button v-b-toggle="'sidebar'+index" v-on:click="setIndex(index)">자세히 보기</b-button>
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

        // 'addPlan/list/{keyword}/{pageNum}'
        // 'addPlan/list/all/0'
        // 'addPlan/list/all/1'

        axios.get('http://localhost:8081/addPlan/list/'+ this.keyword+ "/" + this.pageNum)
          .then((res) => {
            this.plans = res.data;
            this.pageNum++;
          })
      } else {
        alert('검색어를 입력해주세요!');  //검색어를 입력하지 않은 경우
      }


    },
    infiniteHandler($state) {
      axios.get('http://localhost:8081/addPlan/list/'+ this.keyword + this.pageNum)
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
    axios.get('http://localhost:8081/addPlan/list/' + this.pageNum)
    .then((res) => {
      this.plans = res.data;
      this.pageNum++;
    })
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.main{
  margin: auto;
}

.search{
  align-items: center;

  display: flex;
  justify-content: center;
}
.search_plan{
  width: 70vw;
  height: 7vw;
  left: 354px;
  top: 336px;

  border: #F58498 !important;
  border: 1ch !important;

  font-size: 4vw;
}
.search_btn{
  width: 7vw;
  height: 7vw;
  left: 354px;
  top: 336px;

  background-color: #F58498;
  border: 0ch;
}
.search_icon{
  width: 4.5vw;
  height: 4.5vw;
}

.addplan_btn{
  float: right;

  background-color: #F5BB57;

  margin-top: 2.5vw;
  /* padding-top: 0.3vw; */
  margin-right: 12vw;
  border: none;
  border-radius: 5px;

  width: auto;
  height: auto;

  color: white;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 2vw;
  font-weight: bold;
}
.plan_card{
  clear: both;
  padding-top: 2vw;

  align-items: center;

  display: flex;
  justify-content: center;
}

.table_card{
  width: 77vw;
}
.td_card{
  /* align-items: center; */
  padding: 2vw;
  width: 27vw;
  margin: 10vw;
}
</style>
