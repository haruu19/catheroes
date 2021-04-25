<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">비밀번호 찾기</h4>

              <md-field class="md-form-group" slot="inputs" :class="EmessageClass">
                <md-icon>email</md-icon>
                <label>Email...</label>
                <md-input v-model="email" id="email" ref="email" type="email" @blur="verifyEmail"></md-input>
                <md-button
                  v-if="useEmail == '이메일중복'"
                  @click="emailAuth"
                  style="background-color:#4CAF50 !important; min-width:65px;"
                  class="md-icon-button"
                >이메일 인증</md-button>
              </md-field>

              <md-field
                class="md-form-group"
                slot="inputs"
                :class="EmessageClass"
                v-if="useEmail == '이메일중복'"
              >
                <md-icon>email</md-icon>
                <label>인증번호...</label>
                <md-input v-model="emailcode"></md-input>
                <md-button
                  @click="emailConfirm"
                  style="background-color:#4CAF50 !important; min-width:65px;"
                  class="md-icon-button"
                >인증번호 확인</md-button>
              </md-field>

              <md-field class="md-form-group" slot="inputs" v-if="emailCodeConfirm">
                <md-icon>lock_outline</md-icon>
                <label>Password...</label>
                <md-input
                  v-model="password"
                  id="password"
                  ref="password"
                  :type="passwordType"
                  v-on:keyup="fn_compare_pwd"
                ></md-input>
                <span :class="{ active: passwordConfirmType === 'text' }" style="display:None">
                  <i id="pwd_compare" ref="pwd_compare" class="fas fa-eye">불일치</i>
                </span>
              </md-field>
              <md-field class="md-form-group" slot="inputs" v-if="emailCodeConfirm">
                <md-icon>lock_outline</md-icon>
                <label>Password check...</label>
                <md-input
                  v-model="passwordConfirm"
                  id="passwordConfirm"
                  ref="passwordConfirm"
                  :type="passwordConfirmType"
                  v-on:keyup="fn_compare_pwd"
                ></md-input>
                <span :class="{ active: passwordConfirmType === 'text' }" style="display:None">
                  <i class="fas fa-eye"></i>
                </span>
              </md-field>

              <md-button
                style="display:block; margin:auto;"
                @click="checkHandler"
                slot="footer"
                class="md-simple md-success md-lg"
              >
                <!-- Get Started -->
                비밀번호 변경
              </md-button>
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
import KakaoLogin from "vue-kakao-login";

export default {
  components: {
    LoginCard,
    KakaoLogin
  },
  bodyClass: "login-page",
  data() {
    return {
      email: "", //이메일
      nickName: "", //닉네임
      uid: "", //회원번호
      password: "", //패스워드
      passwordConfirm: "", //패스워드 확인
      isTerm: true,
      passwordType: "password",
      passwordConfirmType: "password",
      useNickname: "", //유저 닉네임 중복체크 여부
      useEmail: "", //이메일 중복체크 확인 여부
      authcode: "", //백엔드에서 보낸 이메일 인증 코드
      emailcode: "", //사용자의 이메일 인증코드
      emailCodeConfirm: false //이메일 인증 확인 여부
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/profile_city-1.jpg")
    }
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`
      };
    },
    EmessageClass() {
      if (this.useEmail == "이메일중복" || this.useEmail == "잘못된형식") {
        return {
          "md-invalid": true
        };
      } else {
        return {
          "md-invalid": false
        };
      }
    },
    NmessageClass() {
      if (this.useNickname == "중복") {
        return {
          "md-invalid": true
        };
      } else {
        return {
          "md-invalid": false
        };
      }
    }
  },
  methods: {
    //이메일 인증메일 보내기
    emailAuth() {
      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "member/email/", {
          params: {
            userEmail: this.email,
            subject:
              "길냥이 히어로즈 비밀번호 찾기 인증 코드 발급 안내 입니다.",
            text: "비밀번호 찾기 안내입니다."
          }
        })
        .then(response => {
          alert("인증번호가 발송되었습니다 !");
          if (response.data != null) {
            this.authcode = response.data;
            this.emailCodeConfirm = false;
          }
        })
        .catch();
    },
    //이메일 인증코드 확인
    emailConfirm() {
      if (this.authcode == this.emailcode) {
        alert("인증되었습니다.");
        this.emailCodeConfirm = true;
      } else {
        alert("인증번호를 다시 적어주세요");
        this.emailCodeConfirm = false;
      }
    },
    //회원가입전 유효성 체크
    checkHandler() {
      // alert('123');
      let err = true;
      let msg = "";
      err &&
        this.useEmail != "이메일중복" &&
        ((msg = "이메일을 확인해주세요."), (err = false));
      err &&
        !this.emailCodeConfirm &&
        ((msg = "인증코드를 확인해 주세요."), (err = false));
      err &&
        !this.password &&
        ((msg = "비밀번호를 입력해주세요."), (err = false));
      err &&
        !this.passwordConfirm &&
        ((msg = "비밀번호확인을 입력해주세요."), (err = false));
      err &&
        !this.emailCodeConfirm &&
        ((msg = "인증코드를 확인해 주세요."), (err = false));
      err &&
        this.password != this.passwordConfirm &&
        ((msg = "비밀번호가 다릅니다."), (err = false));

      if (!err) alert(msg);
      else this.createHandler();
    },
    //닉네임 중복체크
    isNickname() {
      if (this.nickName) {
        const request = new FormData();
        request.append("nickname", this.nickName);
        axios
          .post(
            process.env.VUE_APP_SPRING_API_SERVER_URL + "member/nickname/",
            request
          )
          .then(response => {
            if (response.data === true) {
              this.useNickname = "중복";
            } else {
              this.useNickname = "가능";
            }
          })
          .catch(error => {
            this.error = error;
          });
      }
    },
    //이메일 중복체크
    isEmail() {
      if (this.email) {
        const request = new FormData();
        request.append("email", this.email);
        axios
          .post(
            process.env.VUE_APP_SPRING_API_SERVER_URL + "member/email/",
            request
          )
          .then(response => {
            if (response.data === true) {
              this.useEmail = "이메일중복";
            } else {
              this.useEmail = "가능";
            }
          })
          .catch(error => {
            this.error = error;
          });
      }
    },
    //회원수정
    createHandler() {
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "member/findemail", {
          email: this.email,
          password: this.password
        })
        .then(response => {
          alert("수정이 완료되었습니다.");
          this.moveList();
        })
        .catch(error => {
          this.error = error;
        })
        .finally(() => {});
    },
    //로그인창 이동
    moveList() {
      this.$router.push("/login");
    },
    //패스워드 유효성 확인
    fn_compare_pwd() {
      var pwd1 = this.password;
      var pwd2 = this.passwordConfirm;
      var regExpPwd = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/i;
      var result = document.getElementById("pwd_compare");

      if (pwd1.match(regExpPwd) != null) {
        if (pwd1 == pwd2) {
          result.innerText = "일치";
        } else {
          result.innerText = "불일치";
        }
      } else {
        result.innerText = "영어/한글 포함 8자 이상";
      }
    },
    //이메일 유효성 확인
    verifyEmail() {
      var emailVal = this.email;
      var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      var result = document.getElementById("email_compare");
      if (emailVal.match(regExp) != null) {
        this.isEmail();
      } else {
        this.useEmail = "잘못된형식";
      }
    }
  }
};
</script>

<style lang="css">
.md-field .md-error {
  left: unset !important;
}
.md-field .md-count,
.md-field .md-error,
.md-field .md-helper-text {
  right: 0px;
}
</style>
