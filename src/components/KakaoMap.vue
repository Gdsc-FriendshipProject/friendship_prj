<template>
  <div>
    <div class="map_wrap">
      <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
      <div id="menu_wrap" class="bg_white">
        <div class="option">
          <div>
            <input type='text' v-model="keyword" @keyup.13="initMap()"/>
            <button id="search" @click="initMap()">검색하기</button>
          </div>
        </div>
        <hr>
        <ul id="placesList"></ul>
        <div id="pagination"></div>
      </div>
    </div>
    <div>
      <b-button class="check_sidebar" v-b-toggle.sidebar-footer>경유지 확인</b-button>
      <b-sidebar
          id="sidebar-footer"
          aria-label="Sidebar with custom footer"
          no-header
          :backdrop-variant="variant"
          backdrop
          shadow
      >
        <template #footer="{ hide }">
          <div class="d-flex bg-dark text-light align-items-center px-3 py-2" style="background: #4CC2CC !important;">
            <strong class="mr-auto">
              <b-button size="sm" @click="hide" class="close_btn">닫 기</b-button>
            </strong>
            <b-button size="sm" class="save_btn" @click="save(), hide(), toast('b-toaster-bottom-center', true)">저 장</b-button>

          </div>
        </template>
        <div class="px-3 py-2">
          <div class="sidebar">
            <p class="start">경유지 확인</p>
            <h3 v-for="(marker, index) in markerList" v-bind:key="index">
              <img v-if="index !== 0" class="plan_next_img" src="../assets/addPlan/plan_next.png">
              <br>
              <b-badge variant="gray" class="badge_marker">{{ marker.name }}</b-badge>
              <br>
            </h3>
          </div>
        </div>
      </b-sidebar>
    </div>
  </div>

</template>

<script>
export default {
  name: "KakaoMap",
  data() {
    return {
      variant: 'dark',
      keyword: "",
      //화면에 표시되어 있는 marker들
      markerList: [],
      placeName: [],
      nowLat: 0,
      nowLng: 0,
    }
  },
  mounted() {
    navigator.geolocation.getCurrentPosition(pos => {
      this.nowLat = pos.coords.latitude;
      this.nowLng = pos.coords.longitude;

      if (!window.kakao || !window.kakao.maps) {
        //script 태그 객체
        const script = document.createElement("script");

        script.src = '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=cc05708943bf4a2fc47483fada51a344';

        //document의 head에 script 추가
        document.head.append(script);

        /* global kakao */
        script.addEventListener("load", () => {
          kakao.maps.load(this.initMap);
        });
        document.head.appendChild(script);
      } else {
        // console.log("이미 로딩됨: ", window.kakao);
        this.initMap();
      }
    }, err => {
      console.log(err.message);
    })
  },
  methods: {
    initMap() {
      //마커를 담을 배열
      var markers = [];

      const container = document.getElementById("map");

      const latitude = this.nowLat; //위도
      const longitude = this.nowLng; //경도
      const options = {
        center: new window.kakao.maps.LatLng(latitude, longitude), //지도의 중심
        level: 4, //지도 확대 레벨
      };

      //지도 생성
      const map = new window.kakao.maps.Map(container, options);

      // 지도에 교통정보를 표시하도록 지도타입 추가
      map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);

      // 아래 코드는 위에서 추가한 교통정보 지도타입 제거
      // map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);

      // 장소 검색 객체 생성
      var ps = new window.kakao.maps.services.Places();

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우 생성
      var infowindow = new window.kakao.maps.InfoWindow({zIndex: 1});

      console.log('진입');
      // 키워드로 장소를 검색
      var keyword = this.keyword;
      var markerList = this.markerList;
      var placeName = this.placeName;
      searchPlaces(keyword, markerList, placeName);
      this.markerList = markerList;
      this.placeName = placeName;
      console.log("markerList : ", this.markerList);
      console.log("placeName : ", this.placeName);

      // 키워드 검색을 요청하는 함수
      function searchPlaces(keyword, markerList, placeName) {

        console.log("keyword : ", keyword);

        if (!keyword.replace(/^\s+|\s+$/g, '')) {
          alert('검색어를 입력해주세요');
          return false;
        }

        // 장소검색 객체를 통해 키워드로 장소검색을 요청
        ps.keywordSearch(keyword, (data, status, pagination) => {
          if (status === kakao.maps.services.Status.OK) {

            // 정상적으로 검색이 완료됐으면
            // 검색 목록과 마커를 표출
            displayPlaces(data, markerList, placeName);

            // 페이지 번호를 표출
            displayPagination(pagination);

          } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

            alert('검색 결과가 존재하지 않습니다.');
            return;

          } else if (status === kakao.maps.services.Status.ERROR) {

            alert('검색 결과 중 오류가 발생했습니다.');
            return;

          }
        });
      }

      // 검색 결과 목록과 마커를 표출하는 함수
      function displayPlaces(places, markerList, placeName) {

        var listEl = document.getElementById('placesList'),
            menuEl = document.getElementById('menu_wrap'),
            fragment = document.createDocumentFragment(),
            bounds = new kakao.maps.LatLngBounds();

        // 검색 결과 목록에 추가된 항목들을 제거
        removeAllChildNods(listEl);

        // 지도에 표시되고 있는 마커를 제거
        removeMarker();

        for (var i = 0; i < places.length; i++) {

          // 마커를 생성하고 지도에 표시
          var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
              marker = addMarker(placePosition, i),
              itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성

          // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
          // LatLngBounds 객체에 좌표 추가
          bounds.extend(placePosition);

          // 마커와 검색결과 항목에 mouseover 했을때,
          // 해당 장소에 인포윈도우에 장소명 표시
          // mouseout 했을 때는 인포윈도우를 닫음
          (function (marker, title, place = places[i]) {
            kakao.maps.event.addListener(marker, 'mouseover', function () {
              displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function () {
              infowindow.close();
            });

            kakao.maps.event.addListener(marker, 'click', function () {
              saveMarker(place, markerList, placeName, title);
            });

            itemEl.onmouseover = function () {
              displayInfowindow(marker, title);
            };

            itemEl.onmouseout = function () {
              infowindow.close();
            };

            itemEl.onclick = function () {
              saveMarker(place, markerList, placeName, title);
            };
          })(marker, places[i].place_name);

          fragment.appendChild(itemEl);
        }

        // 검색결과 항목들을 검색결과 목록 Element에 추가
        listEl.appendChild(fragment);
        menuEl.scrollTop = 0;

        // 검색된 장소 위치를 기준으로 지도 범위 재설정
        map.setBounds(bounds);
      }

      // 검색결과 항목을 Element로 반환하는 함수
      function getListItem(index, places) {

        var el = document.createElement('li'),
            itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
                '<div class="info">' +
                '   <h5>' + places.place_name + '</h5>';

        if (places.road_address_name) {
          itemStr += '    <span>' + places.road_address_name + '</span>' +
              '   <span class="jibun gray">' + places.address_name + '</span>';
        } else {
          itemStr += '    <span>' + places.address_name + '</span>';
        }

        itemStr += '  <span class="tel">' + places.phone + '</span>' +
            '</div>';

        el.innerHTML = itemStr;
        el.className = 'item';

        return el;
      }

      // 마커를 생성하고 지도 위에 마커를 표시하는 함수
      function addMarker(position, idx) {
        var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url
            imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
            imgOptions = {
              spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
              spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
              offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
            },
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
              position: position, // 마커의 위치
              image: markerImage
            });

        marker.setMap(map); // 지도 위에 마커 표출
        markers.push(marker);  // 배열에 생성된 마커 추가

        return marker;
      }

      // 지도 위에 표시되고 있는 마커 모두 제거
      function removeMarker() {
        for (var i = 0; i < markers.length; i++) {
          markers[i].setMap(null);
        }
        markers = [];
      }

      // 검색결과 목록 하단에 페이지번호 표시는 함수
      function displayPagination(pagination) {
        var paginationEl = document.getElementById('pagination'),
            fragment = document.createDocumentFragment(),
            i;

        // 기존에 추가된 페이지번호 삭제
        while (paginationEl.hasChildNodes()) {
          paginationEl.removeChild(paginationEl.lastChild);
        }

        for (i = 1; i <= pagination.last; i++) {
          var el = document.createElement('a');
          el.href = "#";
          el.innerHTML = i;

          if (i === pagination.current) {
            el.className = 'on';
          } else {
            el.onclick = (function (i) {
              return function () {
                pagination.gotoPage(i);
              }
            })(i);
          }

          fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
      }

      // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수
      // 인포윈도우에 장소명 표시
      function displayInfowindow(marker, title) {
        var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

        infowindow.setContent(content);
        infowindow.open(map, marker);
      }

      // 검색결과 목록의 자식 Element를 제거하는 함수
      function removeAllChildNods(el) {
        while (el.hasChildNodes()) {
          el.removeChild(el.lastChild);
        }
      }

      function saveMarker(place, markerList, placeName, title) {
        let check = true;
        var len = markerList.length;
        console.log("markerList.length : ", len);
        for (let index = 0; index < len; index++) {
          if (markerList[index].mapy === place.y) {
            console.log("markerList.mapy: ", markerList[index].mapy);
            if (markerList[index].mapx === place.x) {
              console.log("markerList.mapx: ", markerList[index].mapx);
              check = false;
              markerList.splice(index, 1);  // splice가 안먹힘..
              // placeName.splice(index, 1);
              console.log('delete');
            }
          }
        }

        if (check) {
          // markerList.push([place.y, place.x]);
          markerList.push({"name": title, "mapx": place.x, "mapy": place.y});
          // placeName.push(title);
        }
      }
    },
    save() {
      this.$store.commit('setMarkers', this.markerList);
      this.$store.commit('setPlace', this.placeName);

    },
    toast(toaster, append = false) {
      this.$bvToast.toast('저장 완료', {
        title: '',
        toaster: toaster,
        solid: true,
        appendToast: append
      })
    }
  },
}
</script>

<style>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
  font-size: 12px;
}

.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}

.map_wrap {
  position: relative;
  width: 100%;
  height: 500px;
}

#menu_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 250px;
  margin: 10px 0 30px 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
}

.bg_white {
  background: #fff;
}

#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5F5F5F;
  margin: 3px 0;
}

#menu_wrap .option {
  text-align: center;
}

#menu_wrap .option p {
  margin: 10px 0;
}

#menu_wrap .option button {
  margin-left: 5px;
}

#placesList li {
  list-style: none;
}

#placesList .item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
}

#placesList .item span {
  display: block;
  margin-top: 4px;
}

#placesList .item h5,
#placesList .item .info {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

#placesList .item .info {
  padding: 10px 0 10px 55px;
}

#placesList .info .gray {
  color: #8a8a8a;
}

#placesList .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;
}

#placesList .info .tel {
  color: #009900;
}

#placesList .item .markerbg {
  float: left;
  position: absolute;
  width: 36px;
  height: 37px;
  margin: 10px 0 0 10px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;
}

#placesList .item .marker_1 {
  background-position: 0 -10px;
}

#placesList .item .marker_2 {
  background-position: 0 -56px;
}

#placesList .item .marker_3 {
  background-position: 0 -102px
}

#placesList .item .marker_4 {
  background-position: 0 -148px;
}

#placesList .item .marker_5 {
  background-position: 0 -194px;
}

#placesList .item .marker_6 {
  background-position: 0 -240px;
}

#placesList .item .marker_7 {
  background-position: 0 -286px;
}

#placesList .item .marker_8 {
  background-position: 0 -332px;
}

#placesList .item .marker_9 {
  background-position: 0 -378px;
}

#placesList .item .marker_10 {
  background-position: 0 -423px;
}

#placesList .item .marker_11 {
  background-position: 0 -470px;
}

#placesList .item .marker_12 {
  background-position: 0 -516px;
}

#placesList .item .marker_13 {
  background-position: 0 -562px;
}

#placesList .item .marker_14 {
  background-position: 0 -608px;
}

#placesList .item .marker_15 {
  background-position: 0 -654px;
}

#pagination {
  margin: 10px auto;
  text-align: center;
}

#pagination a {
  display: inline-block;
  margin-right: 10px;
}

#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}

.check_sidebar{
  float: right;
  margin-bottom: 0.5vw;
  margin-top: 0.5vw;

  font-weight: lighter !important;
  background-color: #F5BB57 !important;
  font-family: 'Jua',sans-serif;
  font-size: 1vw !important;
  border: none !important;
}
.sidebar{
  display: block;
  justify-content: center;
}
.start{
  border: none !important;
  width: auto;
  height: auto;
  margin-top: 2vw;

  font-family: 'Inter';
  font-weight: bold !important;
  font-style: normal;
  font-size: 1.5vw;
  color: white;

  background-color: #F58498;
  border-radius: 5px;
}
.plan_next_img{
  width: 1.5vw;
  margin-bottom: 0.8vw;
  margin-top: 0.4vw;
}
.badge_marker{
  background-color: #E7E7E7;
  width: 18vw;
  height: auto;

  border-radius: 5px;
}
.d-flex bg-dark text-light align-items-center px-3 py-2{
  background: #4CC2CC !important;
}
.close_btn{
  background: #F5F5F5 !important;
  border-radius: 4px;
  border: none !important;

  font-weight: bold !important;
  color: black !important;
}
.save_btn{
  background: #F58498 !important;
  border-radius: 4px;
  border: none !important;

  font-weight: bold !important;
  color: white !important;
}
</style>