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
                city: 'Berlin',
            },
        })
    })

    it('should contain search bar element', () => {
        expect(wrapper.find('#search-bar-container').exists()).toBe(true)
    })

    it('should emit city and latest timestamp', async () => {
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })
        flushPromises()
        await wrapper.vm.getCity()
        moxios.requests
            .mostRecent()
            .respondWith({
                status: 200,
                response: ['2020-12-19 12:00', '2020-12-19 13:00'],
            })
            .then(function() {
                expect(wrapper.emitted().change[0]).toEqual([
                    { city: 'Berlin', timestamp: '2020-12-19 13:00' },
                ])
                done()
            })
    })

    it('should get error from request', async () => {
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })
        const error = new Error('Error: Request failed with status code 400')
        flushPromises()
        await wrapper.vm.getCity()
        moxios.requests
            .mostRecent()
            .respondWith({
                status: 400,
                response: error,
            })
            .then(function() {
                expect(wrapper.vm.errorMessage).toEqual(
                    'Error: Request failed with status code 400'
                )
                done()
            })
    })

    afterEach(() => {
        moxios.uninstall()
        wrapper.destroy()
    })
})
