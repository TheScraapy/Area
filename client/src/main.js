import Vue from 'vue'
import App from './App'
import router from './router'
import store from '@/store/store'
import Vuetify from 'vuetify'
import FBSignInButton from 'vue-facebook-signin-button'
import ToggleButton from 'vue-js-toggle-button'
import { sync } from 'vuex-router-sync'
import 'vuetify/dist/vuetify.min.css'

Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(FBSignInButton)
Vue.use(ToggleButton)

sync(store, router)

new Vue({
    el: '#app',
    router,
    store,
    components: { App },
    template: '<App/>'
})
