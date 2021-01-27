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

  it('should get city information from request', function (done) {
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

  it('should get unaccptabe values for latitude&longitude in city information from request', function (done) {
    moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation', {
      status: 200,
      response: [{
        name: "Berlin",
        centerLatitude: "190",
        centerLongitude: "190",
        searchRadiusInMeter: "50"
      }]
    })

    wrapper.vm.getRequestCityData()

    moxios.wait(() => {
      expect(wrapper.vm.cities).toEqual(
        [{
          name: "Berlin",
          centerLatitude: "190",
          centerLongitude: "190",
          searchRadiusInMeter: "50"
        }]
      )
      done()
    })
  })
  
    it('should get error for city information from request', function (done) {
      const error = new Error('Request failed with status code 500')
      moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation', {
        status:500,
        response: error,
      })


    wrapper.vm.getRequestCityData()

    moxios.wait(() => {
      expect(wrapper.vm.errorMessage).toEqual(
        'Request failed with status code 500'
      )
      done()
    })
  })


  it('should remove a city', async () => {
    moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation?id=8720', {
      status: 200,
      response: [{}]
    })

    await wrapper.vm.removeCity("8720")

    moxios.wait(() => {
      expect(wrapper.vm.cities).toEqual(
        []
      )
      done()
    })
  })

    it('should get an error when removing city', async () => {
      const error = new Error('Error: Request failed with status code 500')
      moxios.stubRequest('http://localhost:8082/withDatabase/cityinformation?id=1', {
        error,
      })

    await wrapper.vm.removeCity("1")

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
