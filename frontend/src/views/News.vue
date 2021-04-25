<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div class="md-layout-item md-size-50 md-small-size-70 md-xsmall-size-100">
            <h1 class="title">길냥이 뉴스</h1>
            <h4>길고양이 관련 뉴스 정보들을 열람할 수 있어요.</h4>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section">
        <div class="container">
          <div class="features text-center">
            <div class="md-layout">
              <!-- test -->
              <div v-for="(news, index) in items" :key="index + '_items'">
                <div class="md-card-new" style="max-width:900px">
                  <md-card-header>
                    <md-card-media style="margin-right:10px; margin-bottom:5px">
                      <img
                        style="border: 1px solid #808080; border-radius: 5px;"
                        :src="news.img"
                        alt="NewsImage"
                        @click="PopNews(index)"
                      />
                    </md-card-media>
                    <md-card-header-text>
                      <div class="md-title" @click="PopNews(index)">
                        &#x1F63A;
                        <strong>{{news.title}} &#x1F63A;</strong>
                      </div>
                      <div class="md-subhead" @click="PopNews(index)">{{news.content}}</div>
                    </md-card-header-text>
                  </md-card-header>
                </div>
              </div>
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
  name: "Media",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/bg7.jpg")
    }
  },
  data() {
    return {
      items: []
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
    PopNews(index) {
      var winWidth = 1024;
      var winHeight = 768;
      var popupOption = "width=" + winWidth + ", height=" + winHeight;
      var win = window.open(this.items[index].url, "PopupWin", popupOption);
    },
    retrieveNewsInfo() {
      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "news")
        .then(res => {
          this.items = res.data;
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  created() {
    this.retrieveNewsInfo();
  }
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
    float: left;

    img {
      border-radius: 3px;
    }
  }
}
</style>