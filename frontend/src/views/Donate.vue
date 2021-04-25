<template>
  <div class="wrapper">
    <parallax class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div class="md-layout-item md-size-50 md-small-size-70 md-xsmall-size-100">
            <h1 class="title">길냥이 후원</h1>
            <h4>도움이 필요한 길고양이에게 도움의 손길을 내밀어주세요.</h4>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section">
        <div class="container">
          <div class="features text-center">
            <h2>전체보기</h2>
            <!-- start donate elements -->
            <!-- start modal page -->
            <modal v-if="donateModal" @close="donateModal=false" :data="donateModalData">
              <template slot="header">
                <h4 class="modal-title">&#x1F63A;결제하기&#x1F63A;</h4>
                <md-button
                  class="md-simple md-just-icon md-round modal-default-button"
                  @click="donateModal=false"
                >
                  <md-icon>clear</md-icon>
                </md-button>
              </template>

              <template slot="body" style="padding-top:0">
                <!-- paymentInfo.amount, buyer_tel 그외정보 자동출력-->
                <md-field>
                  <md-input
                    style="text-align:center; font-size:14px !important"
                    v-model="memberinfo.nickname"
                    placeholder="이름 입력"
                  ></md-input>
                </md-field>
                <md-field>
                  <md-input
                    style="text-align:center; font-size:14px !important"
                    v-model="memberinfo.email"
                    placeholder="이메일 입력"
                  ></md-input>
                </md-field>
                <md-field>
                  <md-select
                    v-model="paymentInfo.amount"
                    name="amount"
                    id="amount"
                    placeholder="결제금액 선택"
                    style="text-align-last:center; margin-left:24px;"
                  >
                    <md-option value="1000">&#x1F4B0; 1,000원</md-option>
                    <md-option value="3000">&#x1F4B4; 3,000원</md-option>
                    <md-option value="5000">&#x1F4B5; 5,000원</md-option>
                    <md-option value="10000">&#x1F4B6; 10,000원</md-option>
                    <md-option value="30000">&#x1F4B7; 30,000원</md-option>
                    <md-option value="50000">&#x1F4B8; 50,000원</md-option>
                  </md-select>
                </md-field>
                <md-field>
                  <md-input
                    style="text-align:center; font-size:14px !important"
                    v-model="paymentInfo.buyer_tel"
                    placeholder="전화번호 입력"
                  ></md-input>
                </md-field>
              </template>

              <template slot="footer">
                <md-button class="md-danger md-simple" @click="onPayment(donateModalData.did)">다 음</md-button>
              </template>
            </modal>
            <!-- end modal page -->
            <div v-if="donateInfo">
              <!-- <div v-for="(donate, index) in donateInfo" :key="'donate_' + index"> -->
              <md-card v-for="(donate, index) in donateInfo" :key="'donate_' + index">
                <md-card-media>
                  <img
                    @click="openDonateModal(donate)"
                    :src="donate.cat.image"
                    style="height:140px"
                    alt="People"
                  />
                </md-card-media>
                <md-card-header style="margin:0; padding:0; background-color:#ffffff">
                  <div
                    class="md-title"
                    style="font-size:14px"
                  >{{donate.cat.nickname}} {{donate.type}} 비용 후원</div>
                  <md-progress-bar
                    :id="'progress_'+index"
                    md-mode="determinate"
                    :md-value="donate.percentage"
                    style="width:80%;margin:0 auto;"
                  ></md-progress-bar>
                  <div class="md-subhead">
                    <strong :id="'percent_'+index">{{donate.percentage}}%</strong>
                    달성 - {{donate.current}}원
                  </div>
                </md-card-header>
              </md-card>
              <!-- </div> -->
            </div>
            <!-- end donate elements -->
            <br />
            <hr />
            <h4>
              &#x1F47C;후원 문의&#x1F47C;
              <br />kastori1990@gmail.com
            </h4>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Modal } from "@/components";
import axios from "axios";

export default {
  components: {
    Modal
  },
  name: "Media",
  bodyClass: "landing-page",
  props: {
    header: {
      type: String,
      default: require("@/assets/img/cat-2536662_1920.jpg")
    }
  },
  data() {
    return {
      donateModalData: null,
      donateModal: false,
      focusedDid: null,
      donateInfo: [],
      memberinfo: {
        email: null,
        nickname: null,
        mid: null,
        password: null,
        image: null
      },
      paymentInfo: {
        amount: 0, // 결제금액
        buyer_tel: "" // 결제자 전화번호
      }
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
    // 후원 결제
    onPayment(did) {
      // alert(did);
      /* 1. 가맹점 식별하기 */
      const { IMP } = window;
      IMP.init("imp80887593");

      /* 2. 결제 데이터 정의하기 */
      const data = {
        // pg: "html5_inicis", // PG사
        pg: "danal", // PG사
        pay_method: "card", // 결제수단
        merchant_uid: `mid_${new Date().getTime()}`, // 주문번호
        amount: this.paymentInfo.amount, // 결제금액
        name: "길냥이 펀딩:)", // 주문명
        buyer_name: this.memberinfo.nickname, // 결제자 이름
        buyer_tel: this.paymentInfo.buyer_tel, // 결제자 전화번호
        buyer_email: this.memberinfo.email // 결제자 이메일
        // m_redirect_url: "https://i3d206.p.ssafy.io/donate" // 모바일 결제시 복귀 URL
        // ...
      };

      /* 4. 결제 창 호출하기 */
      this.focusedDid = did;
      IMP.request_pay(data, this.callback);
    },
    callback(response) {
      /* 3. 콜백 함수 정의하기 */
      const {
        success,
        merchant_uid,
        error_msg,
        paid_amount
        // ...
      } = response;

      if (success) {
        alert("결제가 완료되었습니다:)");
        // alert(this.focusedDid);
        // axios 호출하여 donate db 수정 및 프론트 갱신해주는 부분
        const request = new FormData();
        request.append("did", this.focusedDid);
        request.append("amount", paid_amount);
        // alert(paid_amount);
        axios
          .put(process.env.VUE_APP_SPRING_API_SERVER_URL + "donate", request)
          .then(res => {
            this.donateInfo = [];
            for (var i = 0; i < res.data.length; i++) {
              res.data[i].cat.image =
                process.env.VUE_APP_IMAGE_SERVER + res.data[i].cat.image;
            }
            this.donateInfo = res.data;
            this.donateModal = false;
            this.$router.go("/funding");
          })
          .catch(err => {});
        /////////////////////////////////////////////////////////
      } else {
        alert(`결제 실패: ${error_msg}`);
      }
    },
    //도네이션 퍼센트 글씨 색변환
    updatePercentColor() {
      for (var i = 0; i < this.donateInfo.length; i++) {
        if (
          this.donateInfo[i].percentage >= 0 &&
          this.donateInfo[i].percentage <= 33
        ) {
          document.getElementById(`percent_${i}`).style.color = "#4CAF50";
        } else if (
          this.donateInfo[i].percentage >= 34 &&
          this.donateInfo[i].percentage <= 66
        ) {
          document.getElementById(`percent_${i}`).style.color = "#4CAF50";
        } else {
          document.getElementById(`percent_${i}`).style.color = "#4CAF50";
        }
      }
    },
    //유저 정보 확인
    retrievememberinfo() {
      const token = this.$cookies.get("auth-token");
      axios
        .post(process.env.VUE_APP_SPRING_API_SERVER_URL + "member/info", null, {
          headers: { Authorization: `${token}` }
        })
        .then(res => {
          this.memberinfo.email = res.data.email;
          this.memberinfo.nickname = res.data.nickname;
          this.memberinfo.mid = res.data.mid;
          this.memberinfo.password = res.data.password;
          this.memberinfo.image =
            process.env.VUE_APP_IMAGE_SERVER + res.data.image;
        })
        .catch(error => {
          console.log(error);
        });
    },

    // 펀딩 정보 조회
    retrieveDonateInfo() {
      axios
        .get(process.env.VUE_APP_SPRING_API_SERVER_URL + "donate")
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].cat.image =
              process.env.VUE_APP_IMAGE_SERVER + res.data[i].cat.image;
            res.data[i].percentage = Math.round(
              (res.data[i].current * 100) / res.data[i].goal
            );
          }
          this.donateInfo = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },

    openDonateModal(donate) {
      this.donateModalData = donate;
      this.donateModal = true;
    }
  },

  created() {
    this.retrievememberinfo();
    this.retrieveDonateInfo();
  },
  updated() {
    this.updatePercentColor();
  }
};
</script>

<style lang="scss" scoped>
.card-expansion {
  height: 480px;
}

.md-card {
  width: 280px;
  margin: 4px;
  display: inline-block;
  vertical-align: top;
}
</style>