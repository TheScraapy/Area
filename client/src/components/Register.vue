<template>
  <v-layout column>
    <v-flex>
      <div class="white elevation-2">
        <v-toolbar flat dense class="cyan white--text">
          <v-toolbar-title>Register</v-toolbar-title>
        </v-toolbar>

        <div class="pl-4 pr-4 pt-2 pb-2">
          <form name="area-form" autocomplete="off">
            <v-text-field label="Email" v-model="email"/>
            <v-text-field label="Password" type="password" v-model="password"/>
            <div class="error" v-html="error"/>
            <v-btn class="cyan" @click="register">Register</v-btn>
          </form>
        </div>
      </div>
    </v-flex>
    <v-btn class="red white--text" @click="checkAuth">Register with Google</v-btn>
  </v-layout>
</template>

<script>
import AuthenticationService from "@/services/AuthenticationService";
export default {
  data() {
    return {
      email: "",
      password: "",
      error: null
    };
  },
  methods: {
    async checkAuth() {
      gapi.auth.authorize(
        {
          client_id:
            "348333225838-43rcuddes9uftacun2drni5h3hpkbja9.apps.googleusercontent.com",
          scope: [
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile"
          ]
        },
        this.handleAuth
      );
    },
    async handleAuth(auth) {
      try {
        const response = await AuthenticationService.google({
          access_token: auth.access_token
        });
        this.$store.dispatch("setToken", response.data.token);
        this.$router.push({
          name: "root"
        });
      } catch (error) {
        this.error = "Login already in use";
      }
    },
    async register() {
      try {
        const response = await AuthenticationService.register({
          email: this.email,
          password: this.password
        });
        this.$store.dispatch("setToken", response.data.token);
        this.$router.push({
          name: "root"
        });
      } catch (error) {
        this.error = error.response.data.error;
      }
    }
  }
};
</script>

<style scoped>
</style>
