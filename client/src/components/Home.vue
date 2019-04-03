<template>

  <v-container>

    <v-flex xs12 sm10 offset-sm1 v-if="!$store.state.isUserLoggedIn">
      <v-card flat class="welcome-card text-xs-left Welcome">
        <v-card-text class="card-title white--text font-weight-black display-1 welcome-card-text">
          Welcome to the Area,
          <br>
          Please log in or register to have access to our functionalities
        </v-card-text>
        <v-card-text class="card-title white--text font-weight-black subheading welcome-card-text">
          Area is a school project which allows you to automate simple tasks in order to ease your everyday life.
          <br>
          For example, our services will permit the sending of a email via Gmail when someone subscribes to your Youtube channel (don't forget to subscribe to Pewdiepie)
          <br>
          You will be able to choose between numerous services such as Youtube, Facebook, Gmail, ... and bind any reaction to any available trigger, isn't it FABULOUS ?
          <br>
          Start using Area today and get a full access to our functionalities for FREE, no credit card required !
        </v-card-text>
      </v-card>
    </v-flex>

    <v-layout row wrap v-if="$store.state.isUserLoggedIn">
      <v-flex xs12 sm6 md4 lg4 class="testclass my-3" v-for="service in area" :key="service.feature"> 
        <v-card flat class="card text-xs-center ma-3 container" :class="service.serviceIn">
          <img v-if="service.serviceIn == 'Youtube'" src="../assets/youtube_logo.png" class="logo">
          <img v-if="service.serviceIn == 'Twitter'" src="../assets/twitter_logo.png" class="logo">
          <img v-if="service.serviceIn == 'Facebook'" src="../assets/facebook_logo.png" class="logo">
          <img v-if="service.serviceIn == 'Clock'" src="../assets/clock_logo.png" class="logo">
          <img v-if="service.serviceIn == 'Gmail'" src="../assets/gmail_logo.png" class="logo">
          <img v-if="service.serviceIn == 'Calendar'" src="../assets/calendar_logo.png" class="logo">
          <img src="../assets/close_logo.png" class="closecard" @click="deleteWidget(service.areaid)">
          <v-card-text class="card-title white--text font-weight-black display-1">
            {{ service.feature }}
          </v-card-text>
            <toggle-button
            v-on:change="toggleHandler(service.areaid, service.active)"
            class="togglebutton"
            :width="100"
            :height="40"
            :labels="{checked: 'ON', unchecked: 'OFF'}" 
            :color="{checked: '#b5b5b5', unchecked: '#595959'}"
            :id="service.areaid"
            v-model="service.active">
            </toggle-button>
          <v-card-text class="card-bottom white--text text-xs-right subheading">
            service(s) involved : {{ service.serviceIn }} {{ service.serviceOut }}
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    <div class="text-xs-center" v-if="$store.state.isUserLoggedIn && area.length == 0">
      <v-card-text class="text-xs-center font-weight-black subheading">
        You don't have any widget set up
      </v-card-text>
      <v-btn flat round color="primary" dark @click="navigateTo({name: 'appletcreator'})">
        Configure new widget
      </v-btn>
    </div>
  </v-container>

</template>

<script>
import WidgetService from '@/services/WidgetService'
export default {
  data() {
    return {
      area: [
      ]
    }
  },
  async mounted() {
    var logged = this.$store.state.isUserLoggedIn;
    if (this.$store.state.isUserLoggedIn) {
      const response = await WidgetService.getWidgets(this.$store.state.token);
      this.area = response.data.widgets
    }
  },
  methods: {
    navigateTo (route) {
      this.$router.push(route);
    },
    async refreshWidgets () {
      var logged = this.$store.state.isUserLoggedIn;
      if (this.$store.state.isUserLoggedIn) {
        const response = await WidgetService.getWidgets(this.$store.state.token);
        this.area = response.data.widgets
      }
    },
    async toggleHandler(id, active) {
      var widgettoggler = {
        areaid: id,
        state: active 
      }
      const response = await WidgetService.toggleWidget(widgettoggler, this.$store.state.token);
    },
    async deleteWidget(areaid) {
      var widgetdeleted = {
        areaid: areaid
      }
      const response = await WidgetService.deleteWidget(widgetdeleted, this.$store.state.token);
      this.refreshWidgets();
    }
  }
}
</script>

<style scoped>
.testclass {
  padding: 10px !important;
}
.welcome-card {
  border-radius: .5em;
}
.card {
  border-radius: .5em;
  height: 275px !important; 
}
.card-title {
  padding-top: 63px;
  padding-left: 35px;
  padding-right: 35px;
  padding-bottom: 16px;
}
.card-bottom {
  padding-right: 30px;
  padding-bottom: 0px;
}
#container {
  position: relative;
}
.container {
  margin: 10px !important;
  width: 100% !important;
}
.logo {
  position: absolute;
  top: 25px;
  left: 25px;
  height: 45px;
  width: auto;
}
.Youtube {
  background-color: red !important;
}
.Google {
  background-color: rgb(45,45,45) !important;
}
.Clock {
  background-color: rgb(164,198,57) !important;
}
.Twitter {
  background-color: rgb(56,161,243) !important;
}
.Facebook {
  background-color: rgb(59, 89, 152) !important;
}
.Gmail {
  background-color: rgb(35, 68, 139) !important;
}
.Calendar {
    background-color: rgb(223, 120, 35) !important;
}
.togglebutton {
  font-size: 16px !important;
  justify-content: center;
}
.Welcome {
  background-color: rgb(35, 68, 139) !important;
}
.welcome-card-text {
  padding-top: 35px;
  padding-left: 35px;
  padding-right: 35px;
  padding-bottom: 35px;
}
.closecard {
  position: absolute;
  top: 25px;
  right: 25px;
  height: 20px;
  width: auto;
  cursor: pointer;
}
</style>
