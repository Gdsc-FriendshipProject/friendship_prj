<template>
    <div class="accordion" role="tablist" id="sort">
        <form @submit.prevent="submitForm">
            <b-card no-body class="mb-1">
            <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-1 variant="info">위치 선택</b-button>
            </b-card-header>
            <b-collapse id="accordion-1" visible accordion="my-accordion" role="tabpanel">
                <b-card-body>
                    <kakao-map></kakao-map>
                </b-card-body>
            </b-collapse>
            </b-card>

            <b-card no-body class="mb-1">
            <b-card-header header-tag="header" class="p-1" role="tab">
                <b-button block v-b-toggle.accordion-2 variant="info">날짜 선택</b-button>
            </b-card-header>
            <b-collapse id="accordion-2" accordion="my-accordion" role="tabpanel">
                <b-card-body>
                    <div class="section text-center">
                        <div class="flex flex-col items-center mb-6" style = "object-fit: cover;" @click="getDate">
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
                <b-button block v-b-toggle.accordion-3 variant="info">일정 세부사항</b-button>
            </b-card-header>
            <b-collapse id="accordion-3" accordion="my-accordion" role="tabpanel">
                <b-card-body>
                    <div id="sort" style = "width:100%">
                        <div>
                            <input v-model="title" placeholder="일정 제목을 정해주세요.">
                        </div>
                        <br><br>
                        <div 
                        style="width:200px; height:200px;"
                        @click="onSelectIcon">
                            <b-carousel
                                id="carousel-1"
                                v-model="slide"
                                :interval="4000"
                                controls
                                indicators
                                background="#ababab"
                                img-width="200"
                                img-height="200"
                                style="text-shadow: 1px 1px 2px #333;"
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
                        <br><br>
                        <div>
                            <input 
                                type="textarea" 
                                name="memo" 
                                id="memo" 
                                v-model="memo"
                                v-b-tooltip.hover.bottom title="기타 세부 사항이 있으신가요?"
                                placeholder="메모장 입니다.">
                        </div>
                    </div>
                </b-card-body>
            </b-collapse>
            </b-card>
            <div>
                <b-button v-b-toggle.sidebar-right>전체 일정 확인</b-button>
                <b-sidebar id="sidebar-right" title="전체 일정" right shadow>
                    <div class="px-3 py-2">
                        <p>
                            일정 제목 : {{title}}
                        </p>
                        <p >
                            일정 날짜 : {{dateArr[3]}} {{dateArr[1]}} {{dateArr[2]}} {{dateArr[0]}}
                        </p>
                        <p v-for="(place, index) in placeName" v-bind:key="index">
                            {{index + 1}}번 경유지 : {{place}}
                        </p>
                        <p>
                            일정 메모 : {{memo}}
                        </p>
                        일정 대표 이미지<br>
                        <b-img :src=img fluid thumbnail></b-img>
                    </div>
                    <div>
                        <b-button type="submit">일정 저장</b-button>
                    </div>
                </b-sidebar>
            </div>
        </form>
    </div>
</template>

<script>
import KakaoMap from './KakaoMap.vue';
import { addPlan } from '@/api/index';
    export default {
        components: { KakaoMap },
        computed:{
            getMarkerList(){
                return this.$store.getters.getMarkerList;
            },
            getPlaceName(){
                return this.$store.getters.getPlaceName;
            }
        },
        data() {
            return {
                title: '',
                img: '',
                icons:[
                    {name:'음식 아이콘', src:require('@/assets/img/food.png')},
                    {name:'오락 아이콘', src:require('@/assets/img/game.png')},
                    {name:'데이트 아이콘', src:require('@/assets/img/date.png')},
                    {name:'파티 아이콘', src:require('@/assets/img/party.png')},
                    {name:'공부 아이콘', src:require('@/assets/img/study.png')}
                ],
                memo:'',
                date:'',
                dateArr:'',
                markerList: [],
                placeName: [],
                slide: 0,
                sliding: null
            }
        },
        created(){
            this.title= '';
            this.img= '';
            this.memo='';
            this.dateArr='';
            this.markerList= [];
            this.placeName= [];
        },
        updated(){
            this.markerList = this.getMarkerList
            this.placeName = this.getPlaceName
        },
        methods:{
            async submitForm(){
                this.markerList = this.getMarkerList
                this.placeName = this.getPlaceName
                const planData = {
                    title : this.title,
                    dateArr : this.dateArr, // 요일, 월, 일, 년
                    markerList : this.getMarkerList, //해당 마커의 위도 경도(2차원 배열)
                    placeName : this.getPlaceName, // 경유지 이름
                    img : this.img, //img path
                    memo : this.memo, //일정 세부사항
                };

                console.log(planData);

                const { data } = await addPlan(planData);
                console.log(data);

                // if(data !== null){

                // }
            },
            onSelectIcon(){
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
            getDate(){
                var date = this.date + "";
                var dateArr = date.split(" ");

                var arr = [];
                for(var i = 0; i < 4; i++){
                    if(i === 0){ // 요일
                        if(dateArr[i] == "Sun") arr[i] = "일요일";
                        else if (dateArr[i] == "Mon") arr[i] = "월요일";
                        else if (dateArr[i] == "Tue") arr[i] = "화요일";
                        else if (dateArr[i] == "Wed") arr[i] = "수요일";
                        else if (dateArr[i] == "Thu") arr[i] = "목요일";
                        else if (dateArr[i] == "Fri") arr[i] = "금요일";
                        else dateArr[i] = "토요일"; //전부 else 로 들어감. ( 수정 필요 )
                    }
                    if(i === 1){ // 월
                        if(arr[i] == "Jan") arr[i] = "1월";
                        else if (arr[i] == "Feb") arr[i] = "2월";
                        else if (arr[i] == "Mar") arr[i] = "3월";
                        else if (arr[i] == "Apr") arr[i] = "4월";
                        else if (arr[i] == "May") arr[i] = "5월";
                        else if (arr[i] == "Jun") arr[i] = "6월";
                        else if (arr[i] == "Jul") arr[i] = "7월";
                        else if (arr[i] == "Aug") arr[i] = "8월";
                        else if (arr[i] == "Sep") arr[i] = "9월";
                        else if (arr[i] == "Oct") arr[i] = "10월";
                        else if (arr[i] == "Nov") arr[i] = "11월";
                        else arr[i] = "12월";
                    }
                    if(i === 2){ // 일
                        var day = parseInt(dateArr[i]);
                        arr[i] = day + "일";
                    }
                    if(i === 3){ // 년
                        arr[i] = dateArr[i] + "년";
                    }
                }
                this.dateArr = arr;
                // console.log(this.dateArr);
            },
            initPage(){
                this.title= '';
                this.img= '';
                this.memo='';
                this.dateArr='';
                this.markerList= [];
                this.placeName= [];
            }
        },
    };
</script>

<style>
#sort{
    width: 100vw;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
.vc-weeks{
    height: 500px;
    font-size: 30px !important;
}
span.vc-day-content.vc-focusable {
    font-size: 20px;
}
div.vc-time-picker.vc-bordered{
    justify-content: center;
}
div.vc-date{
    justify-content: center;
}
.mb-1{
    width: 60vw !important;
}
</style>