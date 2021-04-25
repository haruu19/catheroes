<template>
    <div id="header" v-if="isHeader">
        <h1>
            <router-link v-bind:to="{name:constants.URL_TYPE.POST.MAIN}">
                SS_log
            </router-link>
        </h1>
        <div class="right">
            <div class="search-input">
                <i class="fas fa-search"></i>
                <input v-model="keyword" type="text"/>
            </div> 
            
            <router-link v-if="!isLoggedIn" v-bind:to="{name:constants.URL_TYPE.USER.LOGIN}" class="login-btn">
                로그인 
            </router-link>  
            <router-link :to="{name:constants.URL_TYPE.USER.INFO}" v-if="isLoggedIn" @click.native="retrieveUserInfo" style="padding: 10px;">
                사용자정보
            </router-link> 
            <button v-if="isLoggedIn" @click="logout">
                로그아웃
            </button>
        </div>  
        
    </div>
</template>   

<script> 
    import constants from '../../lib/constants'

    export default {
        name: 'Header',
        components: { 

        },
        props: ['isHeader', 'isLoggedIn'],
        computed:{
        },
        watch: {
        },
        created() {
        },
        methods : {
            retrieveUserInfo() {
                this.$emit('retrieveUserInfo')
            },
            logout() {
                this.$emit('submit-logout')
            }
        },
        data: function() {
           return {
               constants,
               keyword : "",
           }
        },

    }
</script>

