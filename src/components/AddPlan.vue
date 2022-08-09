<template>
  <div class="accordion" role="tablist" id="sort">
    <div>
      <div class="body_top">
        <img alt="Vue logo" src="../assets/logo.png" class="addPlan_logo" v-on:click="goMain()">
        <b-button class="check_plan_sidebar" v-b-toggle.sidebar-right>전체 일정 확인</b-button>
      </div>
      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-button block v-b-toggle.accordion-1 variant="info">
            <img alt="Vue logo" src="../assets/addPlan/plan_position.png" class="addPlan_img"> 위치 선택
          </b-button>
        </b-card-header>
        <b-collapse id="accordion-1" visible accordion="my-accordion" role="tabpanel">
          <b-card-body>
            <kakao-map></kakao-map>
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-button block v-b-toggle.accordion-2 variant="info">
            <img alt="Vue logo" src="../assets/addPlan/plan_date.png" class="addPlan_img"> 날짜 선택
          </b-button>
        </b-card-header>
        <b-collapse id="accordion-2" accordion="my-accordion" role="tabpanel">
          <b-card-body>
            <div class="section text-center">
              <div class="flex flex-col items-center mb-6" style="object-fit: cover;" @click="getDate">
                <v-date-picker
                    trim-weeks
                    v-model="date"
                    locale="ko"
                    style="width:100%;height:100%;"
                />
              </div>
            </div>
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-button block v-b-toggle.accordion-3 variant="info">
            <img alt="Vue logo" src="../assets/addPlan/plan_detail.png" class="addPlan_img"> 일정 세부사항
          </b-button>
        </b-card-header>
        <b-collapse id="accordion-3" accordion="my-accordion" role="tabpanel">
          <b-card-body>
            <div class="plan_detail_div">
                <div class="detail_img"
                  @click="onSelectIcon">
                <b-carousel
                    id="carousel-1"
                    class="carousel-1"
                    v-model="slide"
                    :interval="10000"
                    controls
                    indicators
                    background="#ababab"
                    img-width="200"
                    img-height="200"
                    @sliding-start="onSlideStart"
                    @sliding-end="onSlideEnd"
                >
                  <!-- Text slides with image -->
                  <b-carousel-slide
                      v-for="(icon,index) in icons"
                      v-bind:key="index"
                      :img-src="icon.src">
                  </b-carousel-slide>
                </b-carousel>
              </div>
              <div class="plan_title_n_memo">
                <div>
                  <p class="plan_detail_p">일정 세부사항</p>
                </div>
                <div>
                  <input v-model="title" class="input_title" placeholder="일정 제목을 정해주세요.">
                </div>
                <div>
                  <input
                      type="textarea"
                      name="memo"
                      class="input_memo"
                      v-model="memo"
                      placeholder="기타 세부 사항이 있으신가요?">
                </div>
              </div>
            </div>
          </b-card-body>
        </b-collapse>
      </b-card>
      <div>
        <b-sidebar id="sidebar-right" right shadow>
          <b-badge class="tot_plan_sidebar_header">전체 일정 확인</b-badge>
          <div class="px-3 py-2">
            <div class="tot_plan_sidebar">
              <table class="tot_table">
                <tr class="tot_title_frame">
                  <td class="tot_title">제 목</td>
                  <td colspan="2">{{title}}</td>
                </tr>
              </table>
              <table class="tot_table">
                <tr class="tot_date_frame">
                  <td class="tot_date">날 짜</td>
                  <td colspan="2">{{ dateArr[3] }} {{ dateArr[1] }} {{ dateArr[2] }} {{ dateArr[0] }}</td>
                </tr>
              </table>
              <table class="tot_table">
                <tr class="tot_destination_frame">
                  <td colspan="3" class="tot_destination">목적지</td>
                </tr>
                <tr class="tot_destination_frame">
                  <h3 v-for="(marker, index) in markerList" v-bind:key="index">
                    <b-badge variant="gray" class="tot_marker">{{ marker.name }}</b-badge>
                    <br>
                    <img v-if="index !== markerList.length-1" class="tot_next_img" src="../assets/addPlan/plan_next.png">
                    <br>
                  </h3>
                </tr>
              </table>
              <table class="tot_table">
                <tr class="tot_memo_frame">
                  <td class="tot_memo">메 모</td>
                  <td colspan="2">{{memo}}</td>
                </tr>
              </table>
              <table class="tot_table">
                <tr class="tot_img_frame">
                  <td class="tot_img" colspan="3">대표 이미지</td>
                </tr>
                <tr class="tot_img_frame">
                  <b-img :src=img fluid thumbnail></b-img>
                </tr>
              </table>
              <div>
                <b-button type="button" v-on:click="submitForm" class="submit_btn">SAVE</b-button>
              </div>
            </div>
          </div>
        </b-sidebar>
      </div>
    </div>
  </div>
</template>

<script>
import KakaoMap from './KakaoMap.vue';
import {addPlan} from '@/api/index';

export default {
  components: {KakaoMap},
  computed: {
    getMarkerList() {
      return this.$store.getters.getMarkerList;
    },
    // getPlaceName(){
    //     return this.$store.getters.getPlaceName;
    // }
  },
  data() {
    return {
      title: '',
      img: '',
      icons: [
        {name: '음식 아이콘', src: require('@/assets/img/food.png')},
        {name: '오락 아이콘', src: require('@/assets/img/game.png')},
        {name: '데이트 아이콘', src: require('@/assets/img/date.png')},
        {name: '파티 아이콘', src: require('@/assets/img/party.png')},
        {name: '공부 아이콘', src: require('@/assets/img/study.png')}
      ],
      memo: '',
      date: '',
      dateArr: '',
      markerList: [],
      placeName: [],
      slide: 0,
      sliding: null
    }
  },
  created() {
    this.title = '';
    this.img = '';
    this.memo = '';
    this.dateArr = '';
    this.markerList = [];
    this.placeName = [];
  },
  updated() {
    this.markerList = this.getMarkerList
    // this.placeName = this.getPlaceName
  },
  methods: {
    async submitForm() {
      try {
        this.markerList = this.getMarkerList
        // this.placeName = this.getPlaceName
        const planData = {
          plan_title: this.title,
          plan_date: this.dateArr.toString(), // 요일, 월, 일, 년
          places: this.getMarkerList, //해당 마커의 위도 경도(2차원 배열)
          // placeName : this.getPlaceName, // 경유지 이름
          plan_img: this.img, //img path
          plan_memo: this.memo, //일정 세부사항
        };

        console.log(planData);

        const res = await addPlan(planData);
        console.log(res.status);
        // console.log(data);
      } catch (e) {
        console.log("에러 발생");
      } finally {
        this.initPlan();
        this.$router.push('/');
      }

    },
    initPlan() {
      this.title = '';
      this.img = '';
      this.memo = '';
      this.dateArr = '';
      this.markerList = [];
      this.placeName = [];
    },
    goMain(){
      this.$router.push('/');
    },
    onSelectIcon() {
      // console.log(this.slide);
      this.img = this.icons[this.slide].src;
      // console.log(this.img);
    },
    onSlideStart() {
      this.sliding = true
    },
    onSlideEnd() {
      this.sliding = false
    },
    getDate() {
      var date = this.date + "";
      var dateArr = date.split(" ");

      var arr = [];
      for (var i = 0; i < 4; i++) {
        if (i === 0) { // 요일
          if (dateArr[i] === "Sun") arr[i] = "일요일";
          else if (dateArr[i] === "Mon") arr[i] = "월요일";
          else if (dateArr[i] === "Tue") arr[i] = "화요일";
          else if (dateArr[i] === "Wed") arr[i] = "수요일";
          else if (dateArr[i] === "Thu") arr[i] = "목요일";
          else if (dateArr[i] === "Fri") arr[i] = "금요일";
          else dateArr[i] = "토요일"; //전부 else 로 들어감. ( 수정 필요 )
        }
        if (i === 1) { // 월
          if (arr[i] === "Jan") arr[i] = "1월";
          else if (arr[i] === "Feb") arr[i] = "2월";
          else if (arr[i] === "Mar") arr[i] = "3월";
          else if (arr[i] === "Apr") arr[i] = "4월";
          else if (arr[i] === "May") arr[i] = "5월";
          else if (arr[i] === "Jun") arr[i] = "6월";
          else if (arr[i] === "Jul") arr[i] = "7월";
          else if (arr[i] === "Aug") arr[i] = "8월";
          else if (arr[i] === "Sep") arr[i] = "9월";
          else if (arr[i] === "Oct") arr[i] = "10월";
          else if (arr[i] === "Nov") arr[i] = "11월";
          else arr[i] = "12월";
        }
        if (i === 2) { // 일
          var day = parseInt(dateArr[i]);
          arr[i] = day + "일";
        }
        if (i === 3) { // 년
          arr[i] = dateArr[i] + "년";
        }
      }
      this.dateArr = arr;
      // console.log(this.dateArr);
    }
  },
};
</script>

<style>
@font-face {
  font-family: 'Jua';
  src: url('@/assets/fonts/Jua-Regular.ttf') format('truetype');
}

.body_top {
  margin-top: 1vw;
}

.addPlan_logo {
  margin-bottom: 0.2vw;
  width: 6vw;
}
.addPlan_img{
  width: 0.6vw;
}

.check_plan_sidebar {
  margin-left: 50vw;
  /*margin-right: 3vw;*/
  margin-bottom: 0.1vw;

  font-weight: bold !important;

  font-family: 'Jua',sans-serif;
  font-size: 1.2vw !important;

  background-color: #F58498 !important;
  border: none !important;
}
.accordion {
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.vc-weeks {
  height: 500px;
  font-size: 30px !important;
}

span.vc-day-content.vc-focusable {
  font-size: 20px;
}

div.vc-time-picker.vc-bordered {
  justify-content: center;
}

div.vc-date {
  justify-content: center;
}

.btn-info {
  background-color: #4CC2CC !important;
  border: none !important;

  font-family: 'Jua', sans-serif;
  font-style: normal;
  font-weight: 700;
  font-size: 3vw;
  line-height: 4vw;
}

.p-1 {
  padding: 0 !important;
  background-color: #4CC2CC !important;
}
.detail_img{
  /*margin-left: 8vw;*/

  width:200px;
  height:200px;
}
.plan_detail_div{
  display: flex;
  vertical-align: middle;
  justify-content : center;
}
.carousel-1{
  text-shadow: 1px 1px 2px #333;

  border: 5px solid #F58498 !important;
  border-radius: 5px !important;
}
.carousel-control-next, .carousel-control-prev{
  align-items: inherit !important;
}
.carousel-control-next-icon{
  margin-top: 0.3vw;
  background-image: url("@/assets/arrow/right.png") !important;
}
.carousel-control-prev-icon{
  margin-top: 0.3vw;
  background-image: url("@/assets/arrow/left.png") !important;
}
.plan_title_n_memo{
  /*margin-top: 3vw;*/
  margin-left: 1vw;
}
.plan_detail_p{
  text-align: left;
  font-family: 'Jua',sans-serif;
  font-size: 2vw;
  font-weight: bold;
  color: #4CC2CC;
}
.input_title, .input_memo{
  width: 30vw;
  height: auto;

  border: 0.3vw solid !important;
  border-color: #4CC2CC !important;
  font-family: 'Jua', sans-serif;
  border-radius: 5px !important;
  font-size: 1.5vw;

  float: left;
}
.input_title{
  margin-bottom: 1vw;
}
.tot_plan_sidebar_header{
  background: #F58498 !important;
  border-radius: 5px;

  font-family: 'Jua',sans-serif;

  width: max-content;
  height: auto;
  margin-bottom: 0.8vw;

  font-size: 1.5vw !important;
}
.tot_plan_sidebar{
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.tot_table{
  margin-bottom: 1vw;
}
.tot_title_frame ,.tot_date_frame, .tot_destination_frame, .tot_memo_frame, .tot_img_frame{
  background: #FFFFFF;
  border: 3px solid #4CC2CC;
  border-radius: 8px !important;

  padding-bottom: 1vw;
}
.tot_title, .tot_date, .tot_destination, .tot_memo, .tot_img{
  background: #4CC2CC;
  width: 6vw;

  padding-top: 0.3vw;

  font-weight: normal;
  font-family: 'Jua', sans-serif;
  font-style: normal;
  text-align: center;

  font-size: 1vw;
  color: white;
}
.tot_marker{
  background-color: #E7E7E7;
  width: 18vw;
  height: auto;

  border-radius: 5px;
}
.tot_next_img{
  width: 1.2vw;
  /*margin-bottom: 0.8vw;*/
  margin-top: 0.8vw;
}
.submit_btn{
  background: #F58498 !important;
  border-radius: 4px;
  border: none !important;

  font-weight: bold !important;
  font-family: 'Jua',sans-serif !important;
  font-style: normal;
  text-align: center;

  color: white;
}
</style>