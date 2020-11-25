// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import vuetify from '@/plugins/vuetify' // path to vuetify export
import 'leaflet/dist/leaflet.css';

Vue.config.productionTip = false

// Leaflet icon config
import { Icon } from 'leaflet'
import 'leaflet/dist/leaflet.css'
delete Icon.Default.prototype._getIconUrl;
Icon.Default.imagePath = '.';
Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png')
});

new Vue({
  el: '#app',
  vuetify,
  router,
  components: { App },
  template: '<App/>'
}).$mount('#app')
