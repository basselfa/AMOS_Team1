import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import App from '@/App'
import Vue from 'vue'
import Vuetify from 'vuetify'

Vue.use(Vuetify)

const localVue = createLocalVue()
let vuetify
let wrapper

describe('App', () => {

  beforeEach(() => {
    vuetify = new Vuetify()
    wrapper = shallowMount(App, {
      localVue,
      vuetify
    })
  })

  it('is instantiated', () => {
    expect(wrapper).toBeTruthy();
  })

  it('should contain app element', () => {
    expect(wrapper.find('#app').exists()).toBe(true)
  })

  afterEach(() => {
    wrapper.destroy()
  })
})
