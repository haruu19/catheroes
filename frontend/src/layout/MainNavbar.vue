<template>
<<<<<<< HEAD
  <div id="main">
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
=======
  <div>
    <modal v-if="snapshotModal" @close="snapshotModalHide">
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d

    <modal v-if="snapshotModal" @close="snapshotModalHide">
      <template slot="body" style="padding-top:0">
        <img src="@/assets/img/snapshot_guide.png" />
      </template>

      <template slot="footer">
        <md-button class="md-danger md-simple" @click="snapshotModalHide">확인했습니다.</md-button>
      </template>
    </modal>
    <md-toolbar
      id="toolbar"
      md-elevation="0"
      class="md-transparent md-absolute"
      :class="extraNavClasses"
      :color-on-scroll="colorOnScroll"
    >
      <div class="md-toolbar-row md-collapse-lateral">
        <div class="md-toolbar-section-start">
          <router-link to="/">
            <h3 class="md-title">길냥이히어로즈</h3>
          </router-link>
        </div>

        <div
          id="inputs"
          v-if="showSearchbar && isLoggedIn"
          v-show="this.$route.name == 'index'"
          style="margin-bottom: 15px;"
        >
          <div
            id="camera"
            style="margin-top:15px; margin-left:20px; display:inline-block; float:right;"
            @click="snapshotModal = true"
          >
            <!-- <label for="searchImage"> -->
            <i class="fas fa-camera"></i>
            <!-- </label> -->
            <md-input
              id="searchImage"
              ref="imageInput"
              type="file"
              v-model="Catimage"
              style="display:none;"
              @change="onChangeImages"
            ></md-input>
          </div>
          <div style="display:inline-block; float:left" class="md-layout">
            <md-field class="md-form-group">
              <i style="margin-top:10px;" class="fas fa-search" id="searchIcon"></i>
              <md-input
                @keydown.enter="searchName"
                v-model="search"
                placeholder="Search Name..."
                id="searchBar"
                class="searchBar"
              ></md-input>
            </md-field>
          </div>
        </div>

        <div class="md-toolbar-section-end">
          <md-menu md-close-on-click="true" md-size="big" md-direction="top-start">
            <div v-if="isLoggedIn">
              <div
                class="md-just-icon md-alarm-toggle md-icon-button"
                style="background-color: transparent !important"
                md-menu-trigger
              >
                <md-badge :md-content="memberinfo.news" style="margin-top: -7px;">
                  <div @click="newsfeed(memberinfo.news)">
                    <i class="fas fa-bell" style="margin-top: 10px; margin-right: 10px"></i>
                  </div>
                </md-badge>
              </div>
            </div>
            <md-menu-content style="margin-top:76px; margin-right:30px">
              <div v-if="articleinfo">
                <div
                  class="author-title"
                  style="margin-left:10px; margin-bottom:10px; font-size:1rem"
                >
                  <strong>&#x23F0;알림보기</strong>
                </div>
                <md-divider class="md-inset"></md-divider>
                <div v-for="(article, index) in articleinfo" :key="'index_' + index">
<<<<<<< HEAD
                  <div
                    v-if="index < memberinfo.news"
                    class="author-card"
                    @click="gocat(article)"
                    style="background-color: #ffffcc"
                  >
                    <md-avatar class="md-large">
                      <img :src="article.image" :alt="'Member_' + index" />
                    </md-avatar>

                    <div v-if="index%3==0" class="author-card-info">
                      <span>
                        <strong>{{article.member.nickname}}</strong>님이
                        <strong>{{article.cat.nickname}}</strong>와
                        <br />놀고있어요&#x1F638;
                      </span>
                    </div>
                    <div v-if="index%3==1" class="author-card-info">
                      <span>
                        <strong>{{article.member.nickname}}</strong>님이
                        <strong>{{article.cat.nickname}}</strong>와
                        <br />쌓은 소소한 일상&#x1F63B;
                      </span>
                    </div>
                    <div v-if="index%3==2" class="author-card-info">
                      <span>
                        <strong>{{article.member.nickname}}</strong>님이
                        <strong>{{article.cat.nickname}}</strong>랑
                        <br />함께하는 중입니다!
                      </span>
=======
                  <div v-if="index < memberinfo.news" class="author-card" @click="gocat(article)" style="background-color: #ffffcc">
                    <md-avatar class="md-large">
                      <img :src="article.image" :alt="'Member_' + index">
                    </md-avatar>

                    <div v-if="index%3==0" class="author-card-info">
                      <span><strong>{{article.member.nickname}}</strong>님이 <strong>{{article.cat.nickname}}</strong>와<br>놀고있어요&#x1F638;</span>
                    </div>
                    <div v-if="index%3==1" class="author-card-info">
                      <span><strong>{{article.member.nickname}}</strong>님이 <strong>{{article.cat.nickname}}</strong>와<br>쌓은 소소한 일상&#x1F63B;</span>
                    </div>
                    <div v-if="index%3==2" class="author-card-info">
                      <span><strong>{{article.member.nickname}}</strong>님이 <strong>{{article.cat.nickname}}</strong>랑<br>함께하는 중입니다!</span>
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
                    </div>
                  </div>

                  <div v-if="index >= memberinfo.news" class="author-card" @click="gocat(article)">
                    <md-avatar class="md-large">
                      <img :src="article.image" :alt="'Member_' + index" />
                    </md-avatar>

                    <div v-if="index%3==0" class="author-card-info">
                      <span>
                        <strong>{{article.member.nickname}}</strong>님이
                        <strong>{{article.cat.nickname}}</strong>와
                        <br />놀고있어요&#x1F638;
                      </span>
                    </div>
                    <div v-if="index%3==1" class="author-card-info">
                      <span>
                        <strong>{{article.member.nickname}}</strong>님이
                        <strong>{{article.cat.nickname}}</strong>와
                        <br />쌓은 소소한 일상&#x1F63B;
                      </span>
                    </div>
                    <div v-if="index%3==2" class="author-card-info">
                      <span>
                        <strong>{{article.member.nickname}}</strong>님이
                        <strong>{{article.cat.nickname}}</strong>랑
                        <br />함께하는 중입니다!
                      </span>
                    </div>
                  </div>
                  <md-divider class="md-inset"></md-divider>
                </div>
              </div>
            </md-menu-content>
          </md-menu>

          <!-- </div>

          <div class="md-toolbar-section-end">-->
          <md-button
            class="md-just-icon md-simple md-toolbar-toggle"
            :class="{ toggled: toggledClass }"
            @click="toggleNavbarMobile()"
          >
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </md-button>

          <div class="md-collapse">
            <div class="md-collapse-wrapper">
              <mobile-menu nav-mobile-section-start="false">
                <!-- Here you can add your items from the section-start of your toolbar -->
              </mobile-menu>

              <md-list @click="toggleNavbarWeb
              ()">
                <md-list-item v-if="!isLoggedIn">
                  <router-link to="/login">
                    <p>LOGIN</p>
                  </router-link>
                </md-list-item>

                <md-list-item v-if="!isLoggedIn">
                  <router-link to="/join">
                    <p>JOIN</p>
                  </router-link>
                </md-list-item>

                <md-list-item v-if="isLoggedIn" @click="logout">
                  <router-link to="/">
                    <p>LOGOUT</p>
                  </router-link>
                </md-list-item>

                <md-list-item v-if="isLoggedIn">
                  <router-link to="/profile">
                    <p>PROFILE</p>
                  </router-link>
                </md-list-item>

                <!-- <md-list-item>
                  <router-link to="/create">
                    <p>CREATE</p>
                  </router-link>
<<<<<<< HEAD
                </md-list-item>-->
=======
                </md-list-item> -->
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d

                <md-list-item>
                  <router-link to="/news">
                    <p>NEWS</p>
                  </router-link>
                </md-list-item>

                <md-list-item>
                  <router-link to="/donate">
                    <p>DONATE</p>
                  </router-link>
                </md-list-item>

                <md-list-item>
                  <router-link to="/faq">
                    <p>FAQ</p>
                  </router-link>
                </md-list-item>

              </md-list>
            </div>
          </div>
        </div>
      </div>
    </md-toolbar>
  </div>
</template>

<script>
let resizeTimeout;
function resizeThrottler(actualResizeHandler) {
  // ignore resize events as long as an actualResizeHandler execution is in the queue
  if (!resizeTimeout) {
    resizeTimeout = setTimeout(() => {
      resizeTimeout = null;
      actualResizeHandler();

      // The actualResizeHandler will execute at a rate of 15fps
    }, 66);
  }
}
import { Modal } from "@/components";
import MobileMenu from "@/layout/MobileMenu";
import axios from "axios";

export default {
  components: {
    Modal,
    MobileMenu
  },
  props: {
    type: {
      type: String,
      default: "white",
      validator(value) {
        return [
          "white",
          "default",
          "primary",
          "danger",
          "success",
          "warning",
          "info"
        ].includes(value);
      }
    },
    colorOnScroll: {
      type: Number,
      default: 0
    },
    isLoggedIn: Boolean,
    centerdong: String
  },
  data() {
    return {
      loadingId: null,
      loadingModal: false,
      fill: { gradient: ["red", "green", "blue"] },
      isFirst: true,
      snapshotModal: false,
      showSearchbar: true,
      image: null,
      extraNavClasses: "",
      toggledClass: false,
      search: "",
      imageInput: "",
      center: this.dong,
      updateCount: 0,
      memberinfo: {
        email: null,
        nickname: null,
        mid: null,
        image: null,
        news: null
      },
      articleinfo: []
    };
  },
  created() {},
  methods: {
    onChangeImages(e) {
<<<<<<< HEAD
=======
      console.log(e.target.files);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      const file = e.target.files[0];
      this.image = file;
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
          this.searchImage(res.data);
        })
        .catch(err => {
          console.log(err);
          this.loadingModal = !this.loadingModal;
          alert("고양이 이미지를 넣어 주세요.");
        });
    },
    bodyClick() {
      let bodyClick = document.getElementById("bodyClick");

      if (bodyClick === null) {
        let body = document.querySelector("body");
        let elem = document.createElement("div");
        elem.setAttribute("id", "bodyClick");
        body.appendChild(elem);

        let bodyClick = document.getElementById("bodyClick");
        bodyClick.addEventListener("click", this.toggleNavbarMobile);
      } else {
        bodyClick.remove();
      }
    },
    toggleNavbarMobile() {
      this.showSearchbar = !this.showSearchbar;
      this.NavbarStore.showNavbar = !this.NavbarStore.showNavbar;
      this.toggledClass = !this.toggledClass;
      this.bodyClick();
    },
    toggleNavbarWeb() {
      this.showSearchbar = true;
      this.NavbarStore.showNavbar = !this.NavbarStore.showNavbar;
      this.toggledClass = !this.toggledClass;
      this.bodyClick();
    },
    handleScroll() {
      let scrollValue =
        document.body.scrollTop || document.documentElement.scrollTop;
      let navbarColor = document.getElementById("toolbar");
      this.currentScrollValue = scrollValue;
      if (this.colorOnScroll > 0 && scrollValue > this.colorOnScroll) {
        this.extraNavClasses = `md-${this.type}`;
        navbarColor.classList.remove("md-transparent");
      } else {
        if (this.extraNavClasses) {
          this.extraNavClasses = "";
          navbarColor.classList.add("md-transparent");
        }
      }
    },
    scrollListener() {
      resizeThrottler(this.handleScroll);
    },
    logout() {
<<<<<<< HEAD
=======
      // console.log("로그아웃요청보냈다고했다");
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      this.$emit("submit-logout");
    },
    searchName() {
      axios
        .get(
          `${process.env.VUE_APP_SPRING_API_SERVER_URL}cat/search?nickname=${this.search}&location=${this.centerdong}`
        )
        .then(res => {
<<<<<<< HEAD
=======
          // console.log(res);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
          this.$emit("submit-search-data", res);
          this.$router.push("/search");
          this.search = null;
        })
        .catch(error => {
          alert("검색이 실패하였습니다.\n다시 시도해주세요. :(");
        });
    },
    searchImage(data) {
      const formData = new FormData();
      formData.append("breed", data);
      formData.append("location", this.centerdong);

      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "cat/searchImage",
          formData
        )
        .then(res => {
<<<<<<< HEAD
=======
          // console.log(res);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
          this.$emit("submit-search-data", res);
          this.Catimage = null;
          this.$router.push("/search");
        })
        .catch(error => {
          alert("검색이 실패하였습니다.\n다시 시도해주세요. :(");
        });
    },
    newsCount() {
<<<<<<< HEAD
=======
      // console.log("유저정보받아오라고했다");
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      const token = this.$cookies.get("auth-token");
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "member/info", null, {
          headers: { Authorization: `${token}` }
        })
        .then(res => {
<<<<<<< HEAD
=======
          // console.log("user정보 출력");
          // console.log(res.data);
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
          this.memberinfo.email = res.data.email;
          this.memberinfo.nickname = res.data.nickname;
          this.memberinfo.mid = res.data.mid;
          this.memberinfo.image = res.data.image;
          this.memberinfo.news = res.data.news;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 알림정보 불러옴
    newsfeed(count) {
      if (true) {
        axios
<<<<<<< HEAD
          .get(
            process.env.VUE_APP_SPRING_API_SERVER_URL +
              "article/newArticle?mid=" +
              this.memberinfo.mid
          )
          .then(res => {
            for (var i = 0; i < res.data.length; i++) {
              res.data[i].image =
                process.env.VUE_APP_IMAGE_SERVER + res.data[i].image;
            }
            this.articleinfo = res.data;
=======
          .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "article/newArticle?mid="+this.memberinfo.mid)        
          .then(res => {
            // console.log(res.data);
            for(var i=0; i<res.data.length; i++) {
              res.data[i].image = process.env.VUE_APP_IMAGE_SERVER + res.data[i].image;
            }
            this.articleinfo = res.data;
            // this.clearAlarmCount();
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
          })
          .catch(err => {
            console.log(err);
          })
          .finally(() => {
            // this.isFirst = false;
          });
        // 내가 팔로우한 고양이들의 게시글을
        // count 만큼 (대신 내가 쓴거 뺴고)
        // 불러온다.
      }
    },
    // 알람모달 숨기기
    snapshotModalHide() {
      this.snapshotModal = false;
      document.getElementById("searchImage").click();
    },
    // 알람 카운트 초기화
    clearAlarmCount() {
      const request = new FormData();
      request.append("news", 0);
      request.append("mid", this.memberinfo.mid);

      axios
        .put(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "member/clear",
          request
        )
        .then(res => {
          this.memberinfo.news = 0;
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {});
    },
<<<<<<< HEAD
    gocat(data) {
      this.clearAlarmCount();
      this.$router.push("/detail/" + data.cat.catid).catch(error => {
        this.$router.go("/detail/" + data.cat.catid);
=======
    gocat(data){
      // console.log(data);
      this.clearAlarmCount();
      this.$router.push("/detail/" + data.cat.catid).catch(error => {
        this.$router.go("/detail/" + data.cat.catid)
>>>>>>> c2e4a83b5814720ba46994196e2e4c6b3ba1d83d
      });
    }
  },
  mounted() {
    document.addEventListener("scroll", this.scrollListener);
    this.newsCount();
  },
  updated() {
    this.updateCount++;
    if (this.updateCount % 10 == 1) {
      this.newsCount();
    }
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scrollListener);
  }
};
</script>

<style lang="scss" scoped>
@media screen and (max-width: 352px) {
  #searchIcon {
    display: none;
  }
  .searchBar {
    width: 0px;
  }
}

@media screen and (min-width: 353px) and (max-width: 391px) {
  .searchBar {
    width: 40px;
  }
}

@media screen and (min-width: 392px) and (max-width: 411px) {
  .searchBar {
    width: 80px;
  }
}

@media screen and (min-width: 412px) and (max-width: 430px) {
  .searchBar {
    width: 100px;
  }
}

.md-menu {
  margin-top: 14px;
}

.author-card {
  padding: 8px 16px;
  display: flex;
  align-items: center;

  .md-avatar {
    margin-right: 16px;
  }

  .author-card-info {
    display: flex;
    flex-flow: column;
    flex: 1;
  }

  span {
    font-size: 16px;
  }

  .author-card-links {
    display: flex;

    a + a {
      margin-left: 8px;
    }
  }
}
</style> 
