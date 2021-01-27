import {
  mount,
  shallowMount,
  createLocalVue
} from '@vue/test-utils'
import Configuration from '@/views/Configuration'
import Vue from 'vue'
import Vuetify from 'vuetify'
import moxios from 'moxios'
import flushPromises from 'flush-promises'

Vue.use(Vuetify)
const localVue = createLocalVue()
let vuetify
let wrapper

describe('Configuration', () => {
  beforeEach(() => {
    moxios.install()
    vuetify = new Vuetify()
    wrapper = mount(Configuration, {
      localVue,
      vuetify,
      propsData: {
        city: 'Berlin',
      },
    })
  })

  it('should contain card element', () => {
    expect(wrapper.find('.config-card').exists()).toBe(true)
  })

  it('should contain form elements', async () => {
    await  wrapper.setData({ cities: ["Berlin", "Hamburg"] })
     
    expect(wrapper.find('.v-chip__content').exists()).toBe(true)
  })

  it('stub response for city get request', function (done) {
    moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation', {
      status: 200,
      response: [{
        name: "Berlin",
        centerLatitude: "51.55",
        centerLongitude: "51.55",
        searchRadiusInMeter: "50"
      }]
    })

    wrapper.vm.getRequestCityData()

    moxios.wait(() => {
      expect(wrapper.vm.cities).toEqual(
        [{
          name: "Berlin",
          centerLatitude: "51.55",
          centerLongitude: "51.55",
          searchRadiusInMeter: "50"
        }]
      )
      done()
    })
  })
  /*
    it('stub response for city get request', function (done) {
      const error = new Error('Error: Request failed with status code 500')
      moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation', {
        error,
      })


    wrapper.vm.getRequestCityData()
  })
    moxios.wait(() => {
      expect(wrapper.vm.errorMessage).toEqual(
        'Error: Request failed with status code 500'
      )
      done()
    })
  })
*/
    afterEach(() => {
      moxios.uninstall()
      wrapper.destroy()
    })
  })

  it('stub response for city deletion request', function (done) {
    moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation?id=8720', {
      status: 200,
      response: [{}]
    })

    wrapper.vm.removeCity("8720")

    moxios.wait(() => {
      expect(wrapper.vm.cities).toEqual(
        []
      )
      done()
    })
  })
    it('stub response for city get request', function (done) {
      const error = new Error('Error: Request failed with status code 500')
      moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation?id=1', {
        error,
      })


    wrapper.vm.removeCity("1")

    moxios.wait(() => {
      expect(wrapper.vm.errorMessage).toEqual(
        'Error: Request failed with status code 500'
      )
      done()
    })
  })

    afterEach(() => {
      moxios.uninstall()
      wrapper.destroy()
    })

})
