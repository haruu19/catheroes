<template>
  <md-card class="md-card-login" :class="{ 'md-card-hidden': cardHidden }">
    <md-card-header :class="getClass(headerColor)">
      <slot name="title"></slot>
      <div class="social-line">
        <slot name="buttons"></slot>
      </div>
    </md-card-header>
    <md-card-content>
      <slot name="description"></slot>
      <slot name="inputs"></slot>
    </md-card-content>

    <div>
      <slot name="button"></slot>
    </div>
    <div>
      <slot name="footer"></slot>
    </div>
  </md-card>
</template>

<script>
export default {
  name: "login-card",
  props: {
    headerColor: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      cardHidden: true
    };
  },
  beforeMount() {
    setTimeout(this.showCard, 400);
  },
  methods: {
    showCard: function() {
      this.cardHidden = false;
    },
    getClass: function(headerColor) {
      return "md-card-header-" + headerColor + "";
    }
  }
};
</script>

<style lang="css"></style>
