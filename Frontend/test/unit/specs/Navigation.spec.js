import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Navigation from '@/components/Navigation'
import Vue from 'vue';
import Vuetify from 'vuetify'
import router from '@/router'

Vue.use(Vuetify);
const localVue = createLocalVue()
let vuetify
let wrapper

describe('Navigation', () => {

  beforeEach(() => {
      vuetify = new Vuetify()
      wrapper = shallowMount(Navigation, {
        localVue,
        router,
        vuetify
    })
  })

  it('should contain navigation element', () => {
    expect(wrapper.find('#navigation').exists()).toBe(true)
  })

  it('should contain 4 navigation items', () => {
    expect(wrapper.findAll('v-list-item-stub').length).toEqual(4)
  })

  afterEach(() => {
    wrapper.destroy()
  })

})
