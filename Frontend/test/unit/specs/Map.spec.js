import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Map from '@/views/Map'
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

describe('Map', () => {

  beforeEach(() => {

    moxios.install();
    moxios.stubRequest('http://localhost:8082/demo/incidents?city=Berlin', {
      status: 200,
      response: {
        "incidents": [
            { "shape" : [{
              "latitude" : "68.99999",
              "longitude" : "67.99999"
            }]
            }
          ]
        }
    })

    wrapper = mount(Map, {
        localVue,
        vuetify
      });
  
  })

  it('should contain osm map and search bar', (done) => {
    
    expect(wrapper.find('#osm-map').exists()).toBe(true)
    expect(wrapper.find('#search-bar-container').exists()).toBe(true)
  })

  afterEach(() => {
    moxios.uninstall();
    wrapper.destroy()
  })
})
