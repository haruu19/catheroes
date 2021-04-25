<template>
  <div style="text-align: center;">
    <br />
    <br />
    <br />
    <h2>{{centerDong}}</h2>
    <div>
      <vue-daum-map
        :appKey="appKey"
        :center.sync="center"
        :level.sync="level"
        :mapTypeId="mapTypeId"
        :libraries="libraries"
        @load="onLoad"
        @center_changed="onMapEvent('center_changed', $event)"
        @zoom_start="onMapEvent('zoom_start', $event)"
        @zoom_changed="onMapEvent('zoom_changed', $event)"
        @bounds_changed="onMapEvent('bounds_changed', $event)"
        @click="onMapEvent('bounds_changed', $event)"
        @dblclick="onMapEvent('dblclick', $event)"
        @rightclick="onMapEvent('rightclick', $event)"
        @mousemove="onMapEvent('mousemove', $event)"
        @dragstart="onMapEvent('dragstart', $event)"
        @drag="closeOverlay"
        @dragend="onMapEvent('dragend', $event)"
        @idle="onMapEvent('idle', $event)"
        @tilesloaded="onMapEvent('tilesloaded', $event)"
        @maptypeid_changed="onMapEvent('maptypeid_changed', $event)"
        style="height: 60vh; margin: auto;"
      ></vue-daum-map>
      <div class="description text-center" style="display:inline">
<<<<<<< HEAD
        <h4>🏠주소 입력 ...</h4>
        <!-- <h4 style="margin-top: 17px">&#x1F43E; 시</h4> -->
        <div class="md-layout-item" style="width:180px; margin:0 auto;">
          <md-field>
            <md-select name="sido" id="sido" placeholder="시" v-model="sido">
              <md-option value="서울특별시">서울특별시</md-option>
              <md-option value="부산광역시">부산광역시</md-option>
              <md-option value="대구광역시">대구광역시</md-option>
              <md-option value="인천광역시">인천광역시</md-option>
              <md-option value="광주광역시">광주광역시</md-option>
              <md-option value="대전광역시">대전광역시</md-option>
              <md-option value="울산광역시">울산광역시</md-option>
              <md-option value="세종특별자치사">세종특별자치사</md-option>
              <md-option value="경기도">경기도</md-option>
              <md-option value="강원도">강원도</md-option>
              <md-option value="충청북도">충청북도</md-option>
              <md-option value="충청남도">충청남도</md-option>
              <md-option value="전라북도">전라북도</md-option>
              <md-option value="전라남도">전라남도</md-option>
              <md-option value="경상북도">경상북도</md-option>
              <md-option value="경상남도">경상남도</md-option>
              <md-option value="제주특별자치도">제주특별자치도</md-option>
            </md-select>
          </md-field>
        </div>
        <!-- <h4 style="margin-top: 17px">&#x1F43E; 구</h4> -->
        <div class="md-layout-item" style="width:180px; margin:0 auto;">
          <md-field>
            <md-select name="gugun" id="gugun" placeholder="구" v-model="gugun" @click="setsido()">
              <md-option
                v-for="(gugun, index) in guguns"
                :key="'gugun_'+index"
                :value="gugun"
              >{{gugun}}</md-option>
            </md-select>
          </md-field>
        </div>
        <!-- <h4 style="margin-top: 17px">&#x1F43E; 동</h4> -->
        <div class="md-layout-item" style="width:180px; margin:0 auto;">
          <md-field>
            <md-select name="dong" id="dong" placeholder="동" v-model="dong" @click="setgugun()">
              <md-option v-for="(dong, index) in dongs" :key="'dong_'+index" :value="dong">{{dong}}</md-option>
            </md-select>
          </md-field>
        </div>
        <md-button
          v-if="isUpdated"
          style="width:60px; margin:0 auto;"
          class="md-success md-block"
          @click="setCenterByDong"
        >검색</md-button>
=======
      <!-- <h4 style="margin-top: 17px">&#x1F43E; 시</h4> -->
      <div class="md-layout-item" style="width:180px; margin:0 auto;">
        <md-field>
          <md-select name="sido" id="sido" placeholder="시" v-model="sido">
            <md-option value="서울특별시">서울특별시</md-option>
            <md-option value="부산광역시">부산광역시</md-option>
            <md-option value="대구광역시">대구광역시</md-option>
            <md-option value="인천광역시">인천광역시</md-option>
            <md-option value="광주광역시">광주광역시</md-option>
            <md-option value="대전광역시">대전광역시</md-option>
            <md-option value="울산광역시">울산광역시</md-option>
            <md-option value="세종특별자치사">세종특별자치사</md-option>
            <md-option value="경기도">경기도</md-option>
            <md-option value="강원도">강원도</md-option>
            <md-option value="충청북도">충청북도</md-option>
            <md-option value="충청남도">충청남도</md-option>
            <md-option value="전라북도">전라북도</md-option>
            <md-option value="전라남도">전라남도</md-option>
            <md-option value="경상북도">경상북도</md-option>
            <md-option value="경상남도">경상남도</md-option>
            <md-option value="제주특별자치도">제주특별자치도</md-option>
          </md-select>
        </md-field>
      </div>
      <!-- <h4 style="margin-top: 17px">&#x1F43E; 구</h4> -->
      <div class="md-layout-item" style="width:180px; margin:0 auto;">
        <md-field>
          <md-select name="gugun" id="gugun" placeholder="구" v-model="gugun" @click="setsido()">
            <md-option
              v-for="(gugun, index) in guguns"
              :key="'gugun_'+index"
              :value="gugun"
            >{{gugun}}</md-option>
          </md-select>
        </md-field>
      </div>
      <!-- <h4 style="margin-top: 17px">&#x1F43E; 동</h4> -->
      <div class="md-layout-item" style="width:180px; margin:0 auto;">
        <md-field>
          <md-select name="dong" id="dong" placeholder="동" v-model="dong" @click="setgugun()">
            <md-option v-for="(dong, index) in dongs" :key="'dong_'+index" :value="dong">{{dong}}</md-option>
          </md-select>
        </md-field>
      </div>
      <md-button
        v-if="isUpdated"
        style="width:60px; margin:0 auto;"
        class="md-success md-block"
        @click="setCenterByDong"
      >검색</md-button>
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      </div>
    </div>
  </div>
</template>

<script>
//   import '../../assets/css/style.scss'
import VueDaumMap from "vue-daum-map";
import config from "./config";
import axios from "axios";
// import geocoder from "Geocoder";

const SERVER_URL = "http://localhost:8080";

export default {
  name: "App",
  components: { VueDaumMap },

  data: () => ({
    appKey: config.appKey,
    center: { lat: 36.1069968, lng: 128.4163122 },
    level: 3,
    mapTypeId: VueDaumMap.MapTypeId.NORMAL,
    libraries: [],
    mapObject: null,
    myoverlay: [],
    mymap: null,
    mymarker: [],
    geocoder: null,
    centerDong: null,
    sido: null,
    gugun: null,
    dong: null,
    guguns: [],
    dongs: [],
    isUpdated: false,
    Catinfo: {
      nickname: null,
      age: null,
      breed: null,
      location: null,
      attr: null,
      nickconditionsname: null,
      image: null,
      lat: null,
      lng: null,
      catid: null
    }
  }),
  props: {
    iscreate: Boolean
  },
  methods: {
    senddata() {
      this.$emit("send-data", this.center);
    },
    onLoad(map) {
      ///////// 현재위치 받아오기/////////
      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(position => {
          this.center.lat = position.coords.latitude;
          this.center.lng = position.coords.longitude;
        });
      }

      //////////////////////////////////

      /////좌표값에서 법정동 가져오기/////

      // 주소-좌표 변환 객체를 생성합니다
      this.geocoder = new kakao.maps.services.Geocoder();

      this.searchAddrFromCoords(map.getCenter(), this.displayCenterInfo);

      kakao.maps.event.addListener(map, "tilesloaded", () => {
        this.searchAddrFromCoords(map.getCenter(), this.displayCenterInfo);
<<<<<<< HEAD
=======
        // console.log(this.centerDong);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
        this.loadMarker(this.centerDong);
      });
      //////////////////////////////////

      this.mymap = map;
      // 지도의 현재 영역을 얻어옵니다
      var bounds = map.getBounds();
      // 영역정보를 문자열로 얻어옵니다. ((남,서), (북,동)) 형식입니다
      var boundsStr = bounds.toString();

      this.mapObject = map;
    },
    loadMarker(dong) {
<<<<<<< HEAD
=======
      // console.log('고양이정보받아오라고했다')
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      this.closeMarker();

      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "cat/" + dong)
        .then(res => {
<<<<<<< HEAD
=======
          console.log(res.data);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
          var index = 0;
          res.data.forEach(e => {
            var imageSrc =
                "https://catheroes.s3.ap-northeast-2.amazonaws.com/static/marker/" +
                res.data[index++].breed +
                ".png", // 마커이미지의 주소입니다
              imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
              imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(
                imageSrc,
                imageSize,
                imageOption
              ),
              markerPosition = new kakao.maps.LatLng(e.lat, e.lng); // 마커가 표시될 위치입니다

            // 마커를 생성합니다
            let marker = new kakao.maps.Marker({
              position: markerPosition,
              image: markerImage // 마커이미지 설정
            });

            this.mymarker.push(marker);

            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(this.mymap);

            kakao.maps.event.addListener(marker, "click", () => {
              this.closeOverlay();
              this.createOverlay(e);
            });
          });
        })
        .catch(error => {
          console.log(error);
        });
    },
    closeOverlay() {
<<<<<<< HEAD
      this.myoverlay.forEach(element => {
        element.setMap(null);
      });
=======
      // console.dir(this.myoverlay);
      this.myoverlay.forEach(element => {
        element.setMap(null);
      });
      // this.myoverlay.setMap(null);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
    },
    createOverlay(data) {
      var content =
        '<div class="wrap">' +
        '    <div class="info2">' +
        '        <div class="title2">' +
        "길냥이 정보" +
        '<div id="close" class="close" onclick="closeOverlay" title="닫기"></div>' +
        "        </div>" +
        '        <div class="body2">' +
        '            <div class="img">' +
        '                <img src="https://catheroes.s3.ap-northeast-2.amazonaws.com/' +
        data.image +
        '" width="73" height="70">' +
        "           </div>" +
        '            <div class="desc">' +
        '                <div class="ellipsis">' +
        data.nickname +
        "</div>" +
        '                <div class="jibun ellipsis">' +
        data.breed +
        "</div>" +
        '                <div><a href="/detail/' +
        data.catid +
        '" class="link">상세정보</a></div>' +
        "            </div>" +
        "        </div>" +
        "    </div>" +
        "</div>";
      var overlay = new kakao.maps.CustomOverlay({
        content: content,
        map: this.mymap,
        position: new kakao.maps.LatLng(data.lat, data.lng)
      });
      var close = document.getElementById("close");
      close.addEventListener("click", () => {
        this.closeOverlay();
      });

      this.myoverlay.push(overlay);
    },
    closeMarker() {
      // console.dir(this.myoverlay);
      this.mymarker.forEach(element => {
        element.setMap(null);
      });
      // this.myoverlay.setMap(null);
    },
    onMapEvent(event, params) {},
    ////////
    searchAddrFromCoords(coords, callback) {
      // 좌표로 행정동 주소 정보를 요청합니다
      this.geocoder.coord2RegionCode(
        coords.getLng(),
        coords.getLat(),
        callback
      );
    },
    searchDetailAddrFromCoords(coords, callback) {
      // 좌표로 법정동 상세 주소 정보를 요청합니다
      this.sgeocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
    },
    displayCenterInfo(result, status) {
      if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById("centerAddr");

        for (var i = 0; i < result.length; i++) {
          // 행정동의 region_type 값은 'H' 이므로
          if (result[i].region_type === "H") {
            this.centerDong = result[i].address_name;
            break;
          }
        }
<<<<<<< HEAD
        this.$emit("submit-dong", this.centerDong);
      }
    },
    setCenterByDong() {
      var geocoder = new kakao.maps.services.Geocoder();

      var callback = (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
=======
        // console.log("@@@1")
        this.$emit("submit-dong", this.centerDong);
      }
    },
    //////////
    setCenterByDong() {
      var geocoder = new kakao.maps.services.Geocoder();

      // const lat = 37;
      // const lng = 128;

      // this.center.lat = lat;
      // this.center.lng = lng;

      var callback = (result, status) => {
        if (status === kakao.maps.services.Status.OK) {

>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
          const lat = result[0].y;
          const lng = result[0].x;
          this.center.lat = lat;
          this.center.lng = lng;
        }
      };

<<<<<<< HEAD
      const addr = this.gugun + " " + this.dong;
=======
      const addr = this.gugun + " " +  this.dong;
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d

      geocoder.addressSearch(addr, callback);
    },
    setsido() {
<<<<<<< HEAD
=======
      // console.log("시도셋팅")
      // console.log(this.sido);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      const request = new FormData();
      request.append("sidoname", this.sido);

      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "gugun", request)
        .then(res => {
          this.guguns = [];
          for (var i = 0; i < res.data.length; i++) {
<<<<<<< HEAD
=======
            // console.log(res.data[i].gugun);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
            this.guguns.push(res.data[i].gugun);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    setgugun() {
<<<<<<< HEAD
=======
      // console.log("시도셋팅")
      // console.log(this.sido);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      const request = new FormData();
      request.append("sidoname", this.sido);
      request.append("gugun", this.gugun);

      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "dong", request)
        .then(res => {
<<<<<<< HEAD
          this.dongs = [];
          for (var i = 0; i < res.data.length; i++) {
=======
          // console.log(res)
          this.dongs = [];
          for (var i = 0; i < res.data.length; i++) {
            // console.log(res.data[i]);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
            this.dongs.push(res.data[i].dong);
            this.isUpdated = true;
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  updated() {
    // this.setsido();
  },
  mounted() {}
};
</script>

<style>
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info2 {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info2:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info2 .close {
  position: absolute;
  top: 7.5px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info2 .close:hover {
  cursor: pointer;
}
.info2 .title2 {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info2 .body2 {
  position: relative;
  overflow: hidden;
}
.info2 .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info2 .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info2:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info2 .link {
  color: #5085bb;
}
</style>