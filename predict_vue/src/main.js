// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Predict from './components/Predict'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import VueRouter from "vue-router";
import router from './router'

Vue.prototype.$http=axios;

Vue.config.productionTip = false


Vue.use(ElementUI);
Vue.use(VueRouter);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { Predict },
  render: h => h(Predict)
})
