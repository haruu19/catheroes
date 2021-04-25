<template>
  <div style="text-align: center;">
    <h4>{{centerDong}}</h4>
    <div class="custom_typecontrol radius_border">
      <span id="btnRoadmap" class="selected_btn" @click="this.setCenter">내 위치</span>
    </div>
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
        @click="null"
        @dblclick="onMapEvent('dblclick', $event)"
        @rightclick="onMapEvent('rightclick', $event)"
        @mousemove="onMapEvent('mousemove', $event)"
        @dragstart="onMapEvent('dragstart', $event)"
        @drag="onMapEvent('dragstart', $event)"
        @dragend="onMapEvent('dragend', $event)"
        @idle="onMapEvent('idle', $event)"
        @tilesloaded="onMapEvent('tilesloaded', $event)"
        @maptypeid_changed="onMapEvent('maptypeid_changed', $event)"
        style="height: 30vh; margin: auto;"
      ></vue-daum-map>
    </div>
    <div class="md-layout-item md-size-35" style="margin: 0 auto; margin-top:10px">
      <md-button class="md-success md-block" v-if="iscreate" @click="senddata">위치정보입력!</md-button>
    </div>
  </div>
</template>

<script>
//   import '../../assets/css/style.scss'
import VueDaumMap from "vue-daum-map";
import config from "./config";

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
    myoverlay: null,
    mymap: null,
    mymarker: null,
    centerDong: null
  }),
  props: {
    iscreate: Boolean
  },

  methods: {
    senddata() {
      this.$emit("classicModal", false);
      this.$emit("send-data", this.mymarker);
      this.$emit("send-dong", this.centerDong);
    },
    onLoad(map) {
      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(position => {
          this.center.lat = position.coords.latitude;
          this.center.lng = position.coords.longitude;
        });
      }

      this.mymap = map;
      // 지도의 현재 영역을 얻어옵니다
      var bounds = map.getBounds();
      // 영역정보를 문자열로 얻어옵니다. ((남,서), (북,동)) 형식입니다
      var boundsStr = bounds.toString();
      this.mapObject = map;

      /////좌표값에서 법정동 가져오기/////

      // 주소-좌표 변환 객체를 생성합니다
      this.geocoder = new kakao.maps.services.Geocoder();

      // // 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
      this.searchAddrFromCoords(map.getCenter(), this.displayCenterInfo);

      kakao.maps.event.addListener(map, "idle", () => {
        this.searchAddrFromCoords(map.getCenter(), this.displayCenterInfo);
      });
      //////////////////////////////////

      var imageSrc =
          "https://raw.githubusercontent.com/khg6152450/AboutMe/master/CatMarker.png", // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imageOption
        ),
        markerPosition = new kakao.maps.LatLng(36.1069968, 128.4163122); // 마커가 표시될 위치입니다
      // 마커를 생성합니다
      let marker = new kakao.maps.Marker({
        position: "",
        image: markerImage // 마커이미지 설정
      });
      kakao.maps.event.addListener(this.mymap, "click", mouseEvent => {
        var latlng = mouseEvent.latLng;
        marker.setPosition(latlng);

        this.mymarker = latlng;
      });

      marker.setMap(this.mymap);
    },
    onMapEvent(event, params) {},
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
      }
    },
    setCenter() {
      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(position => {
          this.center.lat = position.coords.latitude;
          this.center.lng = position.coords.longitude;
        });
      }
    }
  }
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

.custom_typecontrol {
  position: absolute;
  top: 50px;
  right: -45px;
  overflow: hidden;
  width: 130px;
  height: 30px;
  margin: 0;
  padding: 0;
  z-index: 10;
  font-size: 12px;
  font-family: "Malgun Gothic", "맑은 고딕", sans-serif;
}
.custom_typecontrol span {
  display: block;
  width: 65px;
  height: 30px;
  float: left;
  text-align: center;
  line-height: 30px;
  cursor: pointer;
}
.custom_typecontrol .selected_btn {
  color: #fff;
  background: #425470;
  background: linear-gradient(#425470, #5b6d8a);
}
</style>