import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from "axios";
import VueRouter from "vue-router";
import router from "./router";

Vue.prototype.$axios=axios;
Vue.prototype.$httpUrl='http://localhost:8070'

Vue.config.productionTip = false;

Vue.use(VueRouter)
Vue.use(ElementUI);

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
