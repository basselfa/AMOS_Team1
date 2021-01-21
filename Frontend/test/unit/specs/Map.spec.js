import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Map from '@/views/Map'
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

describe('Map', () => {
    beforeEach(() => {
        moxios.install()
        wrapper = shallowMount(Map, {
            localVue,
            vuetify,
        })
        flushPromises()
    })

    it('should get city data from request', async () => {
        await wrapper.vm.getSearchValue({
            city: 'Berlin',
            timestamp: '2020-12-19 13:00',
            type: ['Accident'],
        })
        let request = moxios.requests.mostRecent()
        request
            .respondWith({
                status: 200,
                response: [
                    {
                        type: 'Accident',
                        edges:
                            '52.51784:13.28016,52.51771:13.28021,52.51765:13.28024',
                    },
                ],
            })
            .then(function() {
                expect(wrapper.vm.polylines[0].latlngs).toEqual([
                    ['52.51784', '13.28016'],
                    ['52.51771', '13.28021'],
                    ['52.51765', '13.28024'],
                ])
                done()
            })
    })

    it('should get an error from invalid request for city data', async () => {
        const error = new Error('Error: Request failed with status code 500')
        moxios.stubRequest('http://localhost:8082/demo/incidents?city=Berlin', {
            error,
        })

        wrapper.vm.getSearchValue({
            city: 'Berlin',
            timestamp: '2020-12-19 13:00',
            type: ['Accident'],
        })

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
