<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle"></parallax>
    <div class="main main-raised">
      <div class="section profile-content">
        <div class="container">
          <div class="md-layout">
            <div class="md-layout-item md-size-50 mx-auto">
              <div class="profile">
                <div class="avatar">
                  <img
                    :src="this.catinfo.image"
                    alt="Circle Image"
                    class="img-raised rounded-circle img-fluid"
                  />
                </div>
                <div class="name">
                  <h3 class="title">{{this.catinfo.nickname}}</h3>
                  <md-button
                    v-if="isFollow"
                    @click="follow()"
                    class="md-just-icon md-simple md-fa-heart"
                  >
                    <i class="fa fa-heart"></i>
                  </md-button>
                  <md-button
                    v-if="!isFollow"
                    @click="follow()"
                    class="md-just-icon md-simple md-fa-heart"
                  >
                    <i class="far fa-heart"></i>
                  </md-button>
                </div>
              </div>
            </div>
          </div>
          <div class="profile-tabs">
            <tabs
              :tab-name="['상세정보', '게시글', '팔로워']"
              :tab-icon="['camera', 'palette', 'favorite']"
              plain
              nav-pills-icons
              color-button="success"
            >
              <!-- 고양이 상세정보 -->
              <template slot="tab-pane-1">
                <div class="description text-center">
                  <h4>&#x1F476; 품종</h4>
                  <div
                    class="button"
                    id="breed"
                    @click="breedUpdateModal = true"
                  >{{this.catinfo.breed}}</div>
                  <modal v-if="breedUpdateModal" @close="breedUpdateModalHide">
                    <template slot="header">
                      <h4 class="modal-title">품종을 선택하세요.</h4>
                      <md-button
                        class="md-simple md-just-icon md-round modal-default-button"
                        @click="breedUpdateModalHide"
                      >
                        <md-icon>clear</md-icon>
                      </md-button>
                    </template>

                    <template slot="body">
                      <input type="radio" id="one" value="고등어태비" v-model="catinfo.breed" />
                      <label for="one">고등어태비</label>
                      <br />
                      <input type="radio" id="two" value="치즈태비" v-model="catinfo.breed" />
                      <label for="two">치즈태비</label>
                      <br />
                      <input type="radio" id="three" value="삼색이" v-model="catinfo.breed" />
                      <label for="three">삼색이</label>
                      <br />
                      <input type="radio" id="four" value="턱시도" v-model="catinfo.breed" />
                      <label for="four">턱시도</label>
                      <br />
                      <input type="radio" id="five" value="흰고양이" v-model="catinfo.breed" />
                      <label for="five">흰고양이</label>
                      <br />
                      <input type="radio" id="six" value="검은고양이" v-model="catinfo.breed" />
                      <label for="six">검은고양이</label>
                      <br />
                    </template>

                    <template slot="footer">
                      <md-button class="md-danger md-simple" @click="breedUpdateModalHide">닫기</md-button>
                    </template>
                  </modal>

                  <h4>&#128008; 성별</h4>
                  <div
                    class="button"
                    id="gender"
                    @click="genderUpdateModal = true"
                  >{{this.catinfo.neutered}}</div>
                  <modal v-if="genderUpdateModal" @close="genderUpdateModalHide">
                    <template slot="header">
                      <h4 class="modal-title">성별을 선택하세요.</h4>
                      <md-button
                        class="md-simple md-just-icon md-round modal-default-button"
                        @click="genderUpdateModalHide"
                      >
                        <md-icon>clear</md-icon>
                      </md-button>
                    </template>

                    <template slot="body">
                      <input type="radio" id="one" value="몰라요" v-model="catinfo.neutered" />
                      <label for="one">몰라요</label>
                      <br />
                      <input type="radio" id="two" value="수컷(중성화 O)" v-model="catinfo.neutered" />
                      <label for="two">수컷(중성화 O)</label>
                      <br />
                      <input type="radio" id="three" value="암컷(중성화 O)" v-model="catinfo.neutered" />
                      <label for="three">암컷(중성화 O)</label>
                      <br />
                      <input type="radio" id="four" value="수컷(중성화 X)" v-model="catinfo.neutered" />
                      <label for="four">수컷(중성화 X)</label>
                      <br />
                      <input type="radio" id="five" value="암컷(중성화 X)" v-model="catinfo.neutered" />
                      <label for="five">암컷(중성화 X)</label>
                      <br />
                    </template>

                    <template slot="footer">
                      <md-button class="md-danger md-simple" @click="genderUpdateModalHide">닫기</md-button>
                    </template>
                  </modal>

                  <h4>&#x1F43E; 사는곳</h4>
                  <div class="button" id="location">{{this.catinfo.location}}</div>
                  <h4>&#x1F48A; 오늘 {{this.catinfo.nickname}}의 건강상태</h4>
                  <div class="md-layout-item" style="width:180px; margin:0 auto;">
                    <md-field>
                      <md-select
                        v-model="catinfo.conditions"
                        name="conditions"
                        id="conditions"
                        placeholder="오늘의 건강 상태 선택하기"
                        @click="isUpdated = true"
                      >
                        <md-option value="1">&#x1F63C; 기운 넘쳐요</md-option>
                        <md-option value="2">&#x1F63A; 튼튼해요</md-option>
                        <md-option value="3">&#x1F63B; 사랑스러워요</md-option>
                        <md-option value="4">&#x1F63E; 가까이 가지 마세요</md-option>
                        <md-option value="5">&#x1F63F; 기운이 없어요</md-option>
                        <md-option value="6">&#x1F640; 아파요</md-option>
                      </md-select>
                    </md-field>
                  </div>
                  <h4>&#x1F372; {{this.catinfo.nickname }} 밥먹은 시간</h4>
                  <div class="button" id="attr" @click="getTime">{{ this.catinfo.food}}</div>

                  <h4>&#x1F4AC; {{this.catinfo.nickname}} 고양이를 소개해요!</h4>
                  <div
                    class="button"
                    id="attr"
                    @click="attrUpdateModal = true"
                  >{{this.catinfo.attr}}</div>
                  <modal v-if="attrUpdateModal" @close="attrUpdateModalHide">
                    <template slot="header">
                      <h4 class="modal-title">{{this.catinfo.nickname}}를 소개해주세요</h4>
                      <md-button
                        class="md-simple md-just-icon md-round modal-default-button"
                        @click="attrUpdateModalHide"
                      >
                        <md-icon>clear</md-icon>
                      </md-button>
                    </template>

                    <template slot="body">
                      <md-field>
                        <md-input style="text-align:center;" v-model="catinfo.attr" placeholder></md-input>
                      </md-field>
                    </template>

                    <template slot="footer">
                      <md-button class="md-danger md-simple" @click="attrUpdateModalHide">닫기</md-button>
                    </template>
                  </modal>
                  <br />
                  <br />
                  <md-button
                    v-if="isUpdated"
                    style="width:60px; margin:0 auto;"
                    class="md-success md-block"
                    @click="updateCatinfo"
                  >수정하기!</md-button>
                </div>
              </template>
              <template slot="tab-pane-2">
                <!-- -->
                <div class="md-layout">
                  <div class="md-layout-item md-size-35">
                    <md-button class="md-success md-block" @click="postCreateModal = true">글쓰기</md-button>
                    <modal v-if="postCreateModal" @close="postCreateModalHide">
                      <template slot="header">
                        <h4 class="modal-title">포스트 작성</h4>
                        <md-button
                          class="md-simple md-just-icon md-round modal-default-button"
                          @click="postCreateModalHide"
                        >
                          <md-icon>clear</md-icon>
                        </md-button>
                      </template>

                      <template slot="body">
                        <!-- -->
                        <md-field>
                          <label>제 목</label>
                          <md-input v-model="title"></md-input>
                        </md-field>

                        <md-field>
                          <label>내 용</label>
                          <md-textarea v-model="content"></md-textarea>
                        </md-field>

                        <md-field slot="inputs">
                          <md-input ref="imageInput" type="file" hidden @change="onChangeImages"></md-input>
                        </md-field>

                        <md-field slot="inputs" v-if="imgpreview">
                          <img :src="imgpreview" />
                        </md-field>
                        <!-- -->
                      </template>

                      <template slot="footer">
                        <md-button class="md-simple" @click="checkHandler">등록</md-button>
                        <md-button class="md-danger md-simple" @click="postCreateModalHide">닫기</md-button>
                      </template>
                    </modal>
                  </div>
                </div>
                <!-- -->
                <div v-for="(post, index) in posts" :key="index + '_posts'">
                  <div class="md-layout" style="max-width:500px; margin: 0 auto">
                    <md-card>
                      <md-card-header style="margin:0">
                        <md-avatar style="margin-left: 10px;">
                          <img :src="post.member.image" style="margin-bottom: 0px;" />
                        </md-avatar>
                        <!-- start closeBtn -->
                        <md-button
                          class="md-simple md-just-icon md-round modal-default-button"
                          v-if="post.member.nickname==memberinfo.nickname"
                          @click="callDeleteConfirmModal(post, index)"
                        >
                          <md-icon>clear</md-icon>
                        </md-button>
                        <!-- end closeBtn -->
                        <!-- start deleteConfirm modal -->
                        <modal
                          :id="'modal_'+index"
                          v-if="deleteConfirmModal"
                          @close="deleteConfirmModalHide"
                          :data="postModalData"
                        >
                          <template slot="header">
                            <h4 class="modal-title">포스트 삭제</h4>
                            <md-button
                              class="md-simple md-just-icon md-round modal-default-button"
                              @click="deleteConfirmModalHide"
                            >
                              <md-icon>clear</md-icon>
                            </md-button>
                          </template>

                          <template slot="body">
                            <p>정말 삭제하시겠습니까?</p>
                          </template>

                          <template slot="footer">
                            <md-button
                              style="margin: 0 auto"
                              class="md-danger md-simple"
                              @click="deletePost(postModalData.articleid, postModalData.index)"
                            >삭제</md-button>
                          </template>
                        </modal>
                        <!-- end deleteConfirm modal -->
                        <div class="md-title">{{post.title}}</div>
                        <div class="md-subhead">{{post.member.nickname}}</div>
                      </md-card-header>

                      <md-card-media>
                        <!-- <img src="@/assets/img/examples/mariya-georgieva.jpg" /> -->
                        <img :src="post.image" />
                      </md-card-media>

                      <md-card-content>{{post.content}}</md-card-content>
                      <!-- 댓글 더 보기 -->
                      <md-button
                        v-if="!post.isclick"
                        @click="commentTest(index); test2(index)"
                        style="margin-left:5px; height:4vh; background-color:#4CAF50 !important; font-size:9px;"
                        class="md-icon-button md-layout-item md-size-20"
                      >댓글보기</md-button>
                      <md-button
                        v-if="post.isclick"
                        @click="commentTest(index); test2(index)"
                        style="margin-left:5px; height:4vh; background-color:#4CAF50 !important; font-size:9px;"
                        class="md-icon-button md-layout-item md-size-20"
                      >댓글닫기</md-button>
                      <div v-if="post.isclick">
                        <div
                          v-for="(comment, index2) in comments[index]"
                          :key="index2 + '_comments'"
                        >
                          <md-card-content style="margin-left:5px; padding:0; display:inline-block">
                            <strong>{{comment.writer}}</strong>
                            {{comment.comment}}
                          </md-card-content>
                          <md-button
                            v-if="comment.writer==memberinfo.nickname"
                            @click="deleteComment(comment.commentid, index, index2)"
                            class="md-simple md-just-icon"
                            style="margin: 0; margin-right:10px; height:20px; width:20px; min-width:20px; float:right"
                          >
                            <md-icon>clear</md-icon>
                          </md-button>
                        </div>
                        <div>
                          <textarea
                            class="md-layout-item md-size-80"
                            style="margin-left:5px;"
                            v-model="comment"
                            placeholder="댓글을 입력해주세요."
                          ></textarea>
                          <md-button
                            @click="saveComment(post.articleid, comment, memberinfo.nickname, index)"
                            style="margin: 7px 0; margin-right:10px; background-color:#4CAF50 !important; float:right"
                            class="md-icon-button"
                          >등록</md-button>
                        </div>
                      </div>
                    </md-card>
                  </div>
                  <!--  -->
                  <!-- <md-card  style="width: 80vw;" >
                    <md-card-header>
                      <md-avatar style="margin-left: 10px;">
                        <img src="@/assets/img/faces/avatar.jpg" style="margin-bottom: 0px;"/>
                      </md-avatar>

                      <div class="md-title">Article Title</div>
                      <div class="md-subhead">Writter</div>
                    </md-card-header>

                    <md-card-media>
                      <img src="@/assets/img/examples/mariya-georgieva.jpg"/>
                    </md-card-media>

                    <md-card-content>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio itaque ea nostrum.</md-card-content>
                  </md-card>-->
                  <!--  -->
                </div>
                <infinite-loading @infinite="CatPost"></infinite-loading>
              </template>
              <template slot="tab-pane-3">
                <!-- <div class="wrapper"> -->
                <!-- <div class="main main-raised"> -->
                <!-- <div class="section"> -->
                <div class="container">
                  <div class="features text-center">
                    <div class="md-layout">
                      <!-- start print follow list -->
                      <md-list class="md-follow-list" v-if="items.length" style="padding:0">
                        <div v-for="(member, index) in items" :key="index + '_items'">
                          <md-list-item>
                            <md-avatar>
                              <img
                                style="margin-bottom:0; float:left"
                                :src="member.member.image"
                                alt="People"
                              />
                            </md-avatar>

                            <span
                              style="position:relative"
                              class="md-list-item-text"
                            >{{member.member.nickname}}</span>

                            <md-button
                              style="background-color:#4B89DC !important"
                              class="md-icon-button"
                            >팔로우</md-button>
                          </md-list-item>
                          <md-divider class="md-inset"></md-divider>
                        </div>
                      </md-list>
                      <!-- end print follow list -->
                    </div>
                  </div>
                </div>
                <!-- </div> -->
                <!-- </div> -->
                <!-- </div> -->
              </template>
            </tabs>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Pagination } from "@/components";
import { LoginCard } from "@/components";
import { Modal } from "@/components";
import { Tabs } from "@/components";
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";

export default {
  components: {
    Pagination,
    LoginCard,
    Modal,
    Tabs,
    InfiniteLoading
  },
  bodyClass: "profile-page",
  data() {
    return {
      postModalData: null,
      limit: -1,
      deleteConfirmModal: false,
      isUpdated: false,
      attrUpdateModal: false,
      breedUpdateModal: false,
      genderUpdateModal: false,
      title: "",
      content: "",
      image: null,
      imgpreview: null,
      postCreateModal: false,
      isFollow: false,
      isclick: false,
      isclicks: [],
      items: [],
      posts: [],
      writers: [],
      comments: [],
      comment: "",
      tabPane1: [],
      tabPane2: [
        { image: require("@/assets/img/bg.jpg") },
        { image: require("@/assets/img/bg2.jpg") },
        { image: require("@/assets/img/bg3.jpg") },
        { image: require("@/assets/img/bg7.jpg") },
        { image: require("@/assets/img/profile_city.jpg") },
        { image: require("@/assets/img/profile_city.jpg") },
        { image: require("@/assets/img/profile_city.jpg") }
      ],
      tabPane3: [
        { image: require("@/assets/img/examples/mariya-georgieva.jpg") },
        { image: require("@/assets/img/examples/studio-3.jpg") },
        { image: require("@/assets/img/examples/clem-onojeghuo.jpg") },
        { image: require("@/assets/img/examples/olu-eletu.jpg") },
        { image: require("@/assets/img/examples/studio-1.jpg") }
      ],
      memberinfo: {
        email: null,
        nickname: null,
        mid: null,
        image: null,
        news: null
      },
      catinfo: {
        nickname: null,
        age: null,
        breed: null,
        location: null,
        attr: null,
        food: null,
        hospital: null,
        family: null,
        gender: null,
        neutered: null,
        conditions: null,
        image: null,
        lat: null,
        lng: null,
        catid: null
      }
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/profile_city.jpg")
    },
    img: {
      type: String,
      default: require("@/assets/img/vue-mk-header.jpg")
    },
    isLoggedIn: Boolean
  },
  methods: {
    getTime() {
      const nowtime = new Date();
      const month = nowtime.getMonth();
      const date = nowtime.getDate();
      const hour = nowtime.getHours();
      const minute = nowtime.getMinutes();
      this.catinfo.food =
        month + "월" + date + "일 " + hour + "시" + minute + "분";
      this.isUpdated = true;
    },
    //삭제 창 닫기
    deleteConfirmModalHide() {
      this.deleteConfirmModal = false;
    },
    // 포스트 삭제
    deletePost(articleid, index) {
      axios
        .delete(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "article/" + articleid
        )
        .then(res => {
          this.posts.splice(index, 1);
          this.deleteConfirmModalHide();
        })
        .catch(err => {
          this.posts.splice(index, 1);
          this.deleteConfirmModalHide();
        });
    },
    // 고양이 정보 수정 좀더 생각해보자
    updateCatinfo() {
      const catid = this.$route.params.catid;

      const request = new FormData();
      request.append("catid", catid);
      request.append("breed", this.catinfo.breed);
      request.append("attr", this.catinfo.attr);
      request.append("conditions", this.catinfo.conditions);
      request.append("food", this.catinfo.food);
      request.append("neutered", this.catinfo.neutered);
      axios
        .patch(process.env.VUE_APP_SPRING_API_SERVER_URL + "cat", request)
        .then(res => {
          this.catinfo.breed = res.data.breed;
          this.catinfo.attr = res.data.attr;
          this.catinfo.conditions = res.data.conditions;
          this.catinfo.food = res.data.food;
          this.catinfo.neutered = res.data.neutered;
          this.isUpdated = false;
          alert("저장되었습니다. \n주변을 꺠끗하게 정리해주세요 :)");
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 고양이 소개 창 닫기
    attrUpdateModalHide() {
      this.attrUpdateModal = false;
      this.isUpdated = true;
    },
    // 품종 선택창 닫기
    breedUpdateModalHide() {
      this.breedUpdateModal = false;
      this.isUpdated = true;
    },
    // 품종 선택창 닫기
    genderUpdateModalHide() {
      this.genderUpdateModal = false;
      this.isUpdated = true;
    },
    // 파일 업로드 이미지 변경
    onChangeImages(e) {
      const file = e.target.files[0];
      this.image = file;
      this.imgpreview = URL.createObjectURL(file);
    },
    // 글쓰기 창 닫기
    postCreateModalHide() {
      this.imgpreview = null;
      this.postCreateModal = false;
    },
    // 글쓰기 버튼 클릭시 유효성 검사
    checkHandler() {
      let err = true;
      let msg = "";
      err && !this.title && ((msg = "제목을 입력해주세요."), (err = false));
      err && !this.content && ((msg = "내용을 입력해주세요."), (err = false));
      err && !this.image && ((msg = "사진을 올려주세요."), (err = false));

      if (!err) alert(msg);
      else this.createHandler();
    },
    // 글쓰기
    createHandler() {
      const request = new FormData();
      // let article = {
      //   catid: this.catinfo.catid,
      //   mid: this.memberinfo.mid,
      //   title: this.title,
      //   content: this.content
      //   // file : this.image
      // };
      request.append("catid", this.catinfo.catid);
      request.append("mid", this.memberinfo.mid);
      request.append("title", this.title);
      request.append("content", this.content);
      request.append("file", this.image);
      // request.append("articleid",0);
      // request.append("image","0");

      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "article/", request, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          alert("등록이 완료되었습니다.");
          this.posts.unshift({
            articleid: res.data.articleid,
            title: this.title,
            content: this.content,
            image: process.env.VUE_APP_IMAGE_SERVER + res.data.image,
            member: {
              image: process.env.VUE_APP_IMAGE_SERVER + this.memberinfo.image,
              nickname: this.memberinfo.nickname
            },
            isclick: false
          });
          this.writers.unshift({
            nickname: this.memberinfo.nickname,
            image: this.memberinfo.image
          });
          this.comments.unshift([]);
          this.title = "";
          this.content = "";
          this.image = null;
          this.imgpreview = null;
          this.postCreateModalHide();

          // 게시글 등록이 완료되었음
          // 해당 고양이 id 로 follow 조회해서 팔로우중인 모든 member 찾아서 news ++
          this.Newsfeed(this.catinfo.catid, this.memberinfo.mid);
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {});
    },
    // 댓글달기
    saveComment(articleid, comment, writer, index) {
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "article/comment", {
          articleid,
          comment,
          writer
        })
        .then(res => {
          alert("댓글 등록이 완료되었습니다.");
          var commentid = res.data.commentid;
          this.comments[index].push({ commentid, articleid, comment, writer });
        })
        .catch(err => {
          console.log(err);
        })
        .finally(() => {
          this.comment = "";
        });
    },
    //댓글 삭제
    deleteComment(commentid, index, index2) {
      axios
        .delete(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "comment/" + commentid
        )
        .then(res => {
          this.comments[index].splice(index2, 1);
        })
        .catch(err => {});
    },

    //유저정보 찾기
    retrieveMemberInfo() {
      const token = this.$cookies.get("auth-token");
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "member/info", null, {
          headers: { Authorization: `${token}` }
        })
        .then(res => {
          this.memberinfo.email = res.data.email;
          this.memberinfo.nickname = res.data.nickname;
          this.memberinfo.mid = res.data.mid;
          this.memberinfo.image = res.data.image;
          this.followCheck(res.data.mid);
        })
        .catch(error => {
          console.log(error);
        });
    },
    //  고양이 정보 조회
    retrieveCatInfo() {
      const catid = this.$route.params.catid;
      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "cat/detail/" + catid)
        .then(res => {
          this.catinfo.nickname = res.data.nickname;
          this.catinfo.age = res.data.age;
          this.catinfo.breed = res.data.breed;
          this.catinfo.location = res.data.location;
          this.catinfo.attr = res.data.attr;
          this.catinfo.conditions = res.data.conditions;
          this.catinfo.neutered = res.data.neutered;
          this.catinfo.food = res.data.food;
          this.catinfo.lat = res.data.lat;
          this.catinfo.lng = res.data.lng;
          this.catinfo.catid = this.$route.params.catid;
          this.catinfo.image =
            process.env.VUE_APP_IMAGE_SERVER + res.data.image;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 고양이 팔로워 찾기
    CatFollow() {
      const catid = this.$route.params.catid;

      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "follow/cat/" + catid)
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].member.image =
              process.env.VUE_APP_IMAGE_SERVER + res.data[i].member.image;
            this.items.push(res.data[i]);
          }
          Array.from(new Set(this.items));
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 고양이 게시글 조회
    CatPost($state) {
      const catid = this.$route.params.catid;
      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "article/cat/" + catid)
        .then(res => {
          if (this.limit < res.data.length) {
            for (
              var i = this.limit;
              i < this.limit + 1 && i < res.data.length;
              i++
            ) {
              // 댓글불러오기
              axios
                .get(
                  process.env.VUE_APP_SPRING_API_SERVER_URL +
                    "article/comment/" +
                    res.data[i].articleid
                )
                .then(res2 => {
                  this.comments.push(res2.data);
                })
                .catch(error => {
                  console.log(error);
                });
              res.data[i].isclick = false;
              res.data[i].member.image =
                process.env.VUE_APP_IMAGE_SERVER + res.data[i].member.image;
              res.data[i].image =
                process.env.VUE_APP_IMAGE_SERVER + res.data[i].image;
              this.posts.push(res.data[i]);
              $state.loaded();
            }
          } else {
            $state.complete();
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.limit += 1;
    },
    // 팔로우 추가/제거
    follow() {
      this.items = [];

      const mid = this.memberinfo.mid;
      const catid = this.$route.params.catid;

      const formData = new FormData();
      formData.append("catid", catid);
      formData.append("mid", mid);

      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "follow", formData)
        .then(res => {
          this.isFollow = res.data;
        })
        .catch(error => {
          console.log(error);
        });

      window.setTimeout(this.CatFollow, 1000);
    },

    // 팔로우 중인지 아닌지 체크 (하트색깔 지정)
    followCheck(mid) {
      const catid = this.$route.params.catid;

      const formData = new FormData();
      formData.append("catid", catid);
      formData.append("mid", mid);

      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "follow/check",
          formData
        )
        .then(res => {
          this.isFollow = res.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    commentTest(index) {
      this.posts[index].isclick
        ? (this.posts[index].isclick = false)
        : (this.posts[index].isclick = true);
    },
    // 뉴스피드
    Newsfeed(cid, mid) {
      const formData = new FormData();
      formData.append("cid", cid);
      formData.append("mid", mid);
      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "member/newsfeed",
          formData
        )
        .then(res => {})
        .catch(error => {
          console.log(error);
        });
    },
    // 포스트 삭제 모달창 호출
    callDeleteConfirmModal(post, index) {
      this.postModalData = post;
      this.postModalData.index = index;
      this.deleteConfirmModal = true;
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  mounted() {
    // this.retrieveCatInfo();
    // this.followCheck();
  },
  updated() {},
  created() {
    this.posts = [];
    this.retrieveMemberInfo();
    this.retrieveCatInfo();
    this.CatFollow();
    // this.Newsfeed(38);
    this.CatPost($state);
    // this.followCheck();
  }
};
</script>

<style lang="scss" scoped>
.button {
  display: inline-block;
  //  position: absolute;
  //  top: 50%;
  //  left: 50%;
  //  transform: translate(-50, -50);
}

.button {
  background: none;
  color: rgb(143, 134, 134);
  width: 180px;
  height: 25px;
  border: 1px solid #ffad7e;
  font-size: 16px;
  border-radius: 4px;
  transition: 0.6s;
  overflow: hidden;
}

.button:focus {
  outline: none;
}

.button:before {
  content: "";
  display: block;
  position: absolute;
  background: rgba(255, 255, 255, 0.5);
  width: 60px;
  height: 100%;
  left: 0;
  top: 0;
  opacity: 0.5;
  filter: blur(30px);
  transform: translateX(-130px) skewX(-15deg);
}

.button:after {
  content: "";
  display: block;
  position: absolute;
  background: rgba(255, 255, 255, 0.2);
  width: 30px;
  height: 100%;
  left: 30px;
  top: 0;
  opacity: 0;
  filter: blur(30px);
  transform: translateX(-100px) scaleX(-15deg);
}

.button:hover {
  background: #ffc192;
  cursor: pointer;
}

.button:hover:before {
  transform: translateX(300px) skewX(-15deg);
  opacity: 0.6;
  transition: 0.7s;
}

.button:hover:after {
  transform: translateX(300px) skewX(-15deg);
  opacity: 1;
  transition: 0.7s;
}

.section {
  padding: 0;
}

.profile-tabs::v-deep {
  .md-card-tabs .md-list {
    justify-content: center;
  }

  [class*="tab-pane-"] {
    margin-top: 3.213rem;
    padding-bottom: 50px;

    // img {
    //   margin-bottom: 2.142rem;
    // }
  }
  .md-card-actions.text-center {
    display: flex;
    justify-content: center !important;
  }
  .contact-form {
    margin-top: 30px;
  }

  .md-has-textarea + .md-layout {
    margin-top: 15px;
  }

  .md-card-header {
    margin: 0;
  }

  .md-follow-list {
    width: 320px;
    max-width: 100%;
    display: inline-block;
    vertical-align: top;
    border: 1px solid rgba(#000, 0.12);
  }
}
</style>
