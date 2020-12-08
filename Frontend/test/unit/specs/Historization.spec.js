import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Historization from '@/views/Historization'
import Vue from 'vue'
import Vuetify from 'vuetify'
import moxios from 'moxios'
import * as Vue2Leaflet from 'vue2-leaflet'
import flushPromises from 'flush-promises'

Vue.use(Vuetify)
Vue.use(Vue2Leaflet)
let vuetify
let wrapper
vuetify = new Vuetify()
const localVue = createLocalVue()

describe('Historization', () => {
    beforeEach(() => {
        moxios.install()
    })

    it('should contain historization table', () => {
        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })

        expect(wrapper.find('#historization-table').exists()).toBe(true)
    })

    it('should call fetchData() when created', done => {
        const testMethod = jest.spyOn(Historization.methods, 'fetchData')

        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })

        expect(testMethod).toHaveBeenCalled()
        done()
    })

    it('should get historization data from request', async () => {
        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })
        await flushPromises()

        // todo: structure needs to be changed after incident is refactored in backend
        moxios.stubRequest('http://localhost:8082/demo/historization/', {
            status: 200,
            response: {
                incidents: [
                    {
                        provider: 1,
                        type: 'traffic jam',
                        size: 'major',
                        startPositionStreet: 'bismarkstraße',
                        endPositionStreet: 'bergmanstraße',
                        city: 'berlin',
                    },
                ],
            },
        })

        wrapper.vm.fetchData()

        moxios.wait(() => {
            expect(wrapper.vm.historizationData).toEqual({
                provider: 1,
                type: 'traffic jam',
                size: 'major',
                startPositionStreet: 'bismarkstraße',
                endPositionStreet: 'bergmanstraße',
                city: 'berlin',
            })
            done()
        })
    })

    it('should get an error from invalid request for historization data', async () => {
        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })
        await flushPromises()
        const error = new Error('Error: Request failed with status code 500')
        moxios.stubRequest('http://localhost:8082/demo/historization/', {
            error,
        })

        wrapper.vm.fetchData()

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
