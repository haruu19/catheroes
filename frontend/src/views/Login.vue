<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">로그인</h4>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>email</md-icon>
                <label>Email...</label>
                <md-input v-model="loginData.email" type="email"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password...</label>
                <md-input @keydown.enter="login" v-model="loginData.password" type="password"></md-input>
              </md-field>
              <md-button
                style="display:block; margin:auto;"
                @click="login"
                slot="footer"
                class="md-simple md-success md-lg"
              >
                <img src="@/assets/img/login/EmailLogin.png" />
              </md-button>
              <md-button
                style="display:block; margin:auto;"
                @click="loginFormWithKakao"
                slot="footer"
                class="md-simple md-success md-lg"
              >
                <img src="@/assets/img/login/KakaoLogin.png" />
              </md-button>
              <md-button @click="gotoJoin" slot="footer" class="md-simple md-success md-lg">회원가입</md-button>
              <md-button
                @click="gotoFind"
                slot="footer"
                class="md-simple md-success md-lg"
                style="float:right"
              >비밀번호 찾기</md-button>
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
export default {
  components: {
    LoginCard
  },
  bodyClass: "login-page",
  data() {
    return {
      loginData: {
        email: null,
        password: null
      }
    };
  },
  created() {},
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
    login() {
      let err = true;
      let msg = "";
      err &&
        !this.loginData.email &&
        ((msg = "이메일을 입력해주세요."), (err = false));
      err &&
        !this.verifyEmail() &&
        ((msg = "이메일 양식을 확인해주세요."), (err = false));
      err &&
        !this.loginData.password &&
        ((msg = "비밀번호를 입력해주세요."), (err = false));

      if (!err) alert(msg);
      else {
        this.$emit("submit-login-data", this.loginData);
      }
    },
    verifyEmail() {
      var emailVal = this.loginData.email;
      var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      if (emailVal.match(regExp) != null) {
        return true;
      } else {
        return false;
      }
    },
    loginFormWithKakao() {
      Kakao.Auth.loginForm({
        success: res => {
          this.$emit("kakao-login", res);
        },
        fail: function(err) {
          alert(JSON.stringify(err));
        }
      });
    },
    gotoJoin() {
      this.$router.push("/join");
    },
    gotoFind() {
      this.$router.push("/findpassword");
    }
  }
};
</script>


<style lang="css"></style>