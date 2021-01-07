import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Search from '@/components/Search'
import Vue from 'vue'
import Vuetify from 'vuetify'
import moxios from 'moxios'
import flushPromises from 'flush-promises'

Vue.use(Vuetify)
const localVue = createLocalVue()
let vuetify
let wrapper

describe('Search', () => {
    beforeEach(() => {
        moxios.install()
        vuetify = new Vuetify()
        wrapper = shallowMount(Search, {
            localVue,
            vuetify,
            propsData: {
                city: 'verlin',
            },
        })
    })

    it('should contain search bar element', () => {
        expect(wrapper.find('#search-bar-container').exists()).toBe(true)
    })

    it('should emit city and latest timestamp', async () => {
        wrapper.setData({ city: 'Berlin' })
        await flushPromises()

        moxios.stubRequest(
            'http://localhost:8082/demo/timestamps?city=Berlin',
            {
                status: 200,
                response: ['2020-12-19 12:00', '2020-12-19 13:00'],
            }
        )

        wrapper.vm.getCity()
        moxios.wait(() => {
            expect(wrapper.emitted().change[0]).toEqual([
                { city: 'Berlin', timestamp: '2020-12-19 13:00' },
            ])
            done()
        })
    })

    afterEach(() => {
        moxios.uninstall()
        wrapper.destroy()
    })
})
