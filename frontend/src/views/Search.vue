<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div class="md-layout-item md-size-50 md-small-size-70 md-xsmall-size-100">
            <h1 class="title">길냥이 검색</h1>
            <h4>검색결과는 다음과 같습니다.</h4>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section">
        <div class="container">
          <div class="features text-left">
            <div class="md-layout">
              <!-- <h1>test</h1> -->
              <!-- <p>{{ searchData }}</p> -->

              <div v-for="(cat, index) in searchData.data" :key="index">
                <div class="md-card-new" @click="toDetail(cat.catid)">
                  <md-card-header>
                    <md-card-media style="transform: translateY(50%);">
                      <md-avatar class="md-avatar-icon md-large">
                        <img :src="imageUrl + cat.image" alt="CatImage" />
                      </md-avatar>
                    </md-card-media>
                    <md-card-header-text>
                      <div class="md-title">이름: {{ cat.nickname }}</div>
                      <div class="md-title">위치: {{ cat.location }}</div>
                      <div class="md-title">종류: {{ cat.breed }}</div>
                    </md-card-header-text>
                  </md-card-header>
                </div>
              </div>
            </div>
              <div style="text-align:center;">
                <div>찾으시는 고양이가 아직 등록되지 않았나요?</div>
                <md-button class="md-success" @click="create">고양이 등록!</md-button>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Search",
  bodyClass: "landing-page",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/cat-1543541_1920.jpg")
    },
    searchData: Object
  },
  data() {
    return {
      items: [],
      imageUrl: process.env.VUE_APP_IMAGE_SERVER
    };
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    }
  },
  methods: {
    toDetail(catid) {
      this.$router.push(`/detail/${catid}`);
    },
    create() {
      this.$router.push(`/create`);
    }
  },
  created() {}
};
</script>

<style lang="scss" scoped>
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
.md-card-new {
  background: transparent !important;
  box-shadow: 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.14),
    0 1px 5px 0 rgba(0, 0, 0, 0.12);
  margin: 10px;
  display: inline-block;

  .md-card-header {
    margin-left: 0;
    margin-right: 0;
    display: block;
  }
  .md-card-header-text {
    padding-left: 5px;
    padding-right: 5px;
  }
  .md-title {
    margin-bottom: 8px;
  }
  .md-card-media {
    margin: 0;
    border-radius: 3px;
    float: right;

    img {
      border-radius: 3px;
    }
  }
}
</style>