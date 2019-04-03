<template>
  <v-toolbar fixed class="cyan">
    <v-toolbar-title class="mr-4">
      <span class="home white--text" @click="navigateTo({name: 'root'})">
        Area
      </span>
    </v-toolbar-title>
    <v-btn flat round class="white--text" @click="navigateTo({name: 'root'})" v-if="$store.state.isUserLoggedIn">
        My widgets
    </v-btn>
    <v-btn flat round class="white--text" @click="navigateTo({name: 'appletcreator'})" v-if="$store.state.isUserLoggedIn">
        Create widgets
    </v-btn>

    <v-spacer></v-spacer>

    <v-toolbar-items>
			<v-btn flat class="white--text" href="../../static/client.apk" download="">
				Download the app
			</v-btn>
      
			<v-btn flat class="white--text" @click="navigateTo({name: 'login'})" v-if="!$store.state.isUserLoggedIn">
        Login
      </v-btn>

      <v-btn flat class="white--text" @click="navigateTo({name: 'register'})" v-if="!$store.state.isUserLoggedIn">
        Register
      </v-btn>

      <v-btn flat class="white--text" @click="logout" v-if="$store.state.isUserLoggedIn">
        Log out
      </v-btn>
    </v-toolbar-items>

  </v-toolbar>
</template>

<script>
export default {
  methods: {
    navigateTo (route) {
      this.$router.push(route)
    },
    logout () {
      this.$store.dispatch('setToken', null)
      this.$router.push({
        name: 'root'
      })
    }
  }
}
</script>

<style scoped>
.home {
  cursor: pointer;
}
</style>
