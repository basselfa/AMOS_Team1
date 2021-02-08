import { shallowMount, createLocalVue } from '@vue/test-utils'
import About from '@/views/About'
import Vue from 'vue';
import Vuetify from 'vuetify'

Vue.use(Vuetify);

const localVue = createLocalVue()
let vuetify
let wrapper

describe('About', () => {

  beforeEach(() => {
    vuetify = new Vuetify()
    wrapper = shallowMount(About, {
      localVue,
      vuetify
    })
  })

  it('should contain about container', () => {
    expect(wrapper.find('.about-container').exists()).toBe(true)
  })

  it('should contain about card', () => {
    expect(wrapper.find('.about-card').exists()).toBe(true)
  })

  afterEach(() => {
    wrapper.destroy()
  })
})
