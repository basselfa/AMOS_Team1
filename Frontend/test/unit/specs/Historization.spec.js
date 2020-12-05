import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Historization from '@/views/Historization'
import Vue from 'vue'
import Vuetify from 'vuetify'
import moxios from 'moxios'
import * as Vue2Leaflet from 'vue2-leaflet'; 

Vue.use(Vuetify)
Vue.use(Vue2Leaflet)
let vuetify
let wrapper
vuetify = new Vuetify()
const localVue = createLocalVue()

describe('Historization', () => {

  beforeEach(() => {

    moxios.install();
    wrapper = shallowMount(Historization, {
        localVue,
        vuetify
      });
  })

  it('should contain historization table', () => {

      expect(wrapper.find('#historization-table').exists()).toBe(true)

})

  afterEach(() => {
    moxios.uninstall();
    wrapper.destroy()
  })
})
