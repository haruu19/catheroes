<template>
  <div id="material-kit">
    <div :class="{ 'nav-open': NavbarStore.showNavbar }">
      <router-view
        name="header"
        @submit-logout="logout"
        :isLoggedIn="isLoggedIn"
        :centerdong="centerdong"
        @submit-search-data="passsearch"
      />
      <div>
        <router-view
          @submit-login-data="login"
          @kakao-login="kakaoLogin"
          @submit-dong="dong"
          :searchData="searchData"
        />
      </div>
      <router-view name="footer" />
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  created() {},
  watch: {},
  methods: {
    passsearch(res) {
      this.searchData = res;
    },
    dong(dong) {
      this.centerdong = dong;
    },
    login(loginData) {
      const formData = new FormData();
      formData.append("email", loginData.email);
      formData.append("password", loginData.password);
      axios
        .post(
          process.env.VUE_APP_SPRING_API_SERVER_URL + "member/login/",
          formData
        )
        .then(res => {
          this.$cookies.set("auth-token", res.data); //토큰 날라오는거 설정해줘야함!!
          this.isLoggedIn = true;
          this.$router.push("/");
          alert("로그인 되었습니다.");
        })
        .catch(err => {
          alert(
            "존재하지 않는 계정입니다. 아이디와 비밀번호를 다시 확인해주세요"
          );
        });
    },
    logout() {
      Kakao.Auth.logout(function(data) {
        alert("로그아웃 되었습니다.");
      });
      this.$cookies.remove("auth-token");
      this.isLoggedIn = false;
    },
    kakaoLogin(res) {
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "auth/kakao/Login", {
          access_token: res.access_token,
          expires_in: res.expires_in,
          refresh_token: res.refresh_token,
          refresh_token_expires_in: res.refresh_token_expires_in,
          scope: res.scope,
          token_type: res.token_type
        })
        .then(res1 => {
          this.$cookies.set("auth-token", res1.data); //토큰 날라오는거 설정해줘야함!!
          this.isLoggedIn = true;
          this.$router.push("/");
          alert("로그인 되었습니다.");
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  data: function() {
    return {
      searchData: null,
      isLoggedIn: false,
      centerdong: null
    };
  },
  mounted() {
    //카카오 로그인 init
    // 웹 플랫폼 도메인 등 초기화한 앱의 설정이 그대로 적용됩니다.
    // 초기화한 앱에 현재 도메인이 등록되지 않은 경우 에러가 발생합니다.
    Kakao.init("06a0b8cad70afe30f83080c09516c797");
    this.isLoggedIn = this.$cookies.isKey("auth-token");
  },
  updated() {
    this.isLoggedIn = this.$cookies.isKey("auth-token");
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Poor+Story&display=swap");
*:not(img):not(.md-list):not(.nav-tabs):not(.md-theme-default):not(.fas):not(.fa-search):not(.far):not(.fa-heart) {
  font-family: "Poor Story", cursive !important;
}
</style>