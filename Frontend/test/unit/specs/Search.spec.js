import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Search from '@/components/Search'
import Vue from 'vue';
import Vuetify from 'vuetify'

Vue.use(Vuetify);
const localVue = createLocalVue()
let vuetify
let wrapper

describe('Navigation', () => {

  beforeEach(() => {
      vuetify = new Vuetify()
      wrapper = shallowMount(Search, {
        localVue,
        vuetify,
        propsData: {
          city: 'verlin'
        }
    })
  })

  it('should contain search bar element', () => {
    expect(wrapper.find('#search-bar-container').exists()).toBe(true)
  })
  

  it('should emit city', async() => {
    wrapper.setData({city:"Berlin"})
    wrapper.vm.getCity()
    expect(wrapper.emitted().change[0]).toEqual(["Berlin"])
})

  afterEach(() => {
    wrapper.destroy()
  })

})
