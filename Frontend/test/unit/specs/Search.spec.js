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

    it('should contain search bar element', (done) => {
        expect(wrapper.find('#search-bar-container').exists()).toBe(true)
        done()
    })

    it('should get cities', async () => {
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })

        moxios.requests
            .mostRecent()
            .respondWith({
                status: 200,
                response: [{ cityName: 'Berlin' }],
            })
            .then(function () {
                expect(wrapper.vm.cities).toEqual(['Berlin'])
                done()
            })
    })

    it('should get error from cities request', async () => {
        const error = new Error('Error: Request failed with status code 400')
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })

        moxios.requests
            .mostRecent()
            .respondWith({
                status: 400,
                response: error,
            })
            .then(function () {
                expect(wrapper.vm.errorMessage).toEqual(
                    'Error: Request failed with status code 400'
                )
                done()
            })
    })

    it('should emit city change', async () => {
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })
        flushPromises()
        await wrapper.vm.setCityChange()
        expect(wrapper.emitted('city-change')).toBeTruthy()
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
            .then(function () {
                expect(wrapper.emitted().change[0]).toEqual([
                    { city: 'Berlin', timestamp: '2020-12-19 13:00' },
                ])
                done()
            })
    })

    it('should get error from city request no timestamps', async () => {
        jest.spyOn(global.console, 'error')
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })
        flushPromises()
        await wrapper.vm.getCity()
        moxios.requests
            .mostRecent()
            .respondWith({
                status: 200,
                response: [],
            })
            .then(function () {
                expect(console.error).toHaveBeenCalled()
                done()
            })
    })

    it('should get error from city request', async () => {
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
            .then(function () {
                expect(wrapper.vm.errorMessage).toEqual(
                    'Error: Request failed with status code 400'
                )
                done()
            })
    })

    it('should refresh data', async () => {
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })
        flushPromises()

        moxios.stubRequest('http://localhost:8082/withDatabase/refresh/', {
            status: 200,
            response: [{}],
        })

        await wrapper.vm.refreshData()

        moxios.wait(() => {
            expect(wrapper.vm.refreshDisabled).toEqual(false)
            done()
        })
    })

    it('should get error from refresh', async () => {
        wrapper.setData({ city: 'Berlin', type: ['Accident', 'Lane closed'] })
        const error = new Error('Error: Request failed with status code 400')
        flushPromises()

        moxios.stubRequest('http://localhost:8082/withDatabase/refresh/', {
            status: 400,
            response: error,
        })

        await wrapper.vm.refreshData()

        moxios.wait(() => {
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
