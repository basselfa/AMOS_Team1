import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Map from '@/views/Map'
import Vue from 'vue'
import Vuetify from 'vuetify'
import moxios from 'moxios'
import * as Vue2Leaflet from 'vue2-leaflet'

Vue.use(Vuetify)
Vue.use(Vue2Leaflet)
let vuetify
let wrapper
vuetify = new Vuetify()
const localVue = createLocalVue()

describe('Map', () => {

  beforeEach(() => {

    moxios.install();
    wrapper = shallowMount(Map, {
        localVue,
        vuetify
      });
  })

  it('should get city data from request', (done) => {

    wrapper.vm.getSearchValue("Berlin")
    moxios.wait(function () {
      let request = moxios.requests.mostRecent()
      request.respondWith({
        status: 200,
        response: {incidents: [
          { shape : [{
            latitude : "68.99999",
            longitude : "67.99999"
            }]
          }
        ]}
      }).then(function() {
        expect(wrapper.vm.polyline.latlngs[0]).toEqual(["68.99999", "67.99999"])
        done()
      })
  })
})

  it("should get an error from invalid request for city data", async () => {

    const error = new Error("Error: Request failed with status code 500")
    moxios.stubRequest("http://localhost:8082/demo/incidents?city=Berlin", {
      error
    });

    wrapper.vm.getSearchValue("Berlin")

    moxios.wait(() => {
      expect(wrapper.vm.errorMessage).toEqual(
        "Error: Request failed with status code 500"
      )
      done()
    })
  })

  afterEach(() => {
    moxios.uninstall();
    wrapper.destroy()
  })
})
