<template>
  <div class="wrapper">
    <modal v-if="loadingModal">
      <template slot="body" style="padding-top:0">
        <md-progress-spinner
          class="md-success"
          style="margin: 0 auto"
          id="loading"
          :md-diameter="100"
          :md-stroke="10"
          md-mode="indeterminate"
        ></md-progress-spinner>
        <p>길냥이 이미지를 분석하고 있어요.</p>
      </template>
    </modal>

    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <!-- 입력 폼 사이즈 조정하는 곳 -->
          <div
            class="md-layout-item md-size-66 md-small-size-100 md-xsmall-size-100 md-medium-size-66 mx-auto"
          >
            <login-card header-color="green">
              <h2 slot="title" class="card-title" style="font-family: 'Do Hyeon', sans-serif;">길냥이 정보 입력</h2>

              <md-field class="md-form-group" slot="inputs">
                <md-icon style="z-index:-1;">
                  <i class="fas fa-cat"></i>
                </md-icon>
                <label>고양이 이름</label>
                <md-input v-model="nickname" id="nickname" ref="nickname"></md-input>
              </md-field>
              <md-field slot="inputs">
                <label for>이미지입력</label>
                <md-file accept="image/*" @change="onChangeImages" />
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon style="z-index:-1;">
                  <i class="fas fa-paw"></i>
                </md-icon>
                <label>길냥이 종류</label>
                <md-select v-model="breed" name="breed" id="breed" style="margin: 0px 0px 0px 12px">
                  <md-option value="삼색이">삼색이</md-option>
                  <md-option value="고등어태비">고등어태비</md-option>
                  <md-option value="치즈태비">치즈태비</md-option>
                  <md-option value="검은고양이">검은고양이</md-option>
                  <md-option value="흰고양이">흰고양이</md-option>
                  <md-option value="턱시도">턱시도</md-option>
                  <md-option value="잘모르겠습니다">잘모르겠습니다</md-option>
                </md-select>
                <span class="md-helper-text">예상 종류를 입력해주세요</span>
              </md-field>

              <md-field slot="inputs" v-if="imgpreview">
                <img :src="imgpreview" />
              </md-field>

              <div class="md-layout" slot="inputs">
                <div class="md-layout-item md-size-33">
                  <md-button class="md-success md-block" @click="classicModal = true">
                    <md-icon>library_books</md-icon>위치선택
                  </md-button>
                  <!-- 모달창 -->
                  <modal v-if="classicModal" @close="classicModalHide">
                    <template slot="header">
                      <h4 class="modal-title">{{nickname}}이 사는 곳은?</h4>
                      <md-button
                        class="md-simple md-just-icon md-round modal-default-button"
                        @click="classicModalHide"
                      >
                        <md-icon>clear</md-icon>
                      </md-button>
                    </template>

                    <template slot="body">
                      <div class="section section-map" style="padding:0px">
                        <div class="container" style="margin: auto;">
                          <Map2
                            :classicModal="classicModal"
                            :iscreate="iscreate"
                            @send-data="getdata"
                            @send-dong="getdong"
                          ></Map2>
                        </div>
                      </div>
                    </template>
                  </modal>
                </div>
              </div>
              <md-button @click="checkCat" slot="footer" class="md-simple md-success md-lg">길냥이 등록!</md-button>
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { LoginCard } from "@/components";
import axios from "axios";
import { Modal } from "@/components";
import Map2 from "./components/Map2.vue";

export default {
  components: {
    LoginCard,
    Modal,
    Map2
  },
  bodyClass: "login-page",
  data() {
    return {
      // 데이터
      dong: null,
      nickname: null,
      breed: null,
      lat: null,
      lng: null,
      image: null,
      classicModal: false,
      imgpreview: null,
      iscreate: true,
      loadingModal: false
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/profile_city.jpg")
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  methods: {
    getdata(mymarker) {
      this.lng = mymarker.Ga;
      this.lat = mymarker.Ha;
      this.classicModalHide();
    },
    getdong(dong) {
      this.dong = dong;
    },
    onChangeImages(e) {
      const file = e.target.files[0];
      this.image = file;
      this.imgpreview = URL.createObjectURL(file);
      const fd = new FormData();
      fd.append("image", this.image);
      this.loadingModal = true;

      axios
        .post(process.env.VUE_APP_DJANGO_API_SERVER_URL + "keras/", fd, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          this.loadingModal = !this.loadingModal;
          this.breed = res.data;
        })
        .catch(err => {
          console.log(err);
          this.loadingModal = !this.loadingModal;
          alert("고양이 이미지를 넣어 주세요.");
        });
    },
    checkCat() {
      let err = true;
      let msg = "";
      err && !this.nickname && ((msg = "이름을 입력해주세요."), (err = false));
      err && !this.breed && ((msg = "품종을 입력해주세요."), (err = false));
      err && !this.lat && ((msg = "위치정보를 입력해주세요."), (err = false));
      // err && !this.image && ((msg = '비밀번호를 입력해주세요.'), (err = false));
      // err && !this. && ((msg = '비밀번호를 입력해주세요.'), (err = false));

      if (!err) alert(msg);
      else this.createHandler();
    },
    // 고양이 등록
    createHandler() {
      const request = new FormData();
      request.append("file", this.image);
      request.append("nickname", this.nickname);
      request.append("lat", this.lat);
      request.append("lng", this.lng);
      request.append("location", this.dong);
      request.append("breed", this.breed);

      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "cat", request, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(response => {
          alert("등록이 완료되었습니다.");
          this.$router.push("/");
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {});
    },
    classicModalHide() {
      this.classicModal = false;
    }
  }
};
</script>

<style lang="css" scoped>
</style>
