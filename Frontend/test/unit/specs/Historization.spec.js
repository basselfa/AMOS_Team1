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

    it('should call fetchData() when created', done => {
        const testMethod = jest.spyOn(Historization.methods, 'fetchData')

        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })
        expect(testMethod).toHaveBeenCalled()
        done()
    })

    it('should get cities from request', async () => {
        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })
        flushPromises()
        //fetchData is called on created
        moxios.wait(function() {
            let request = moxios.requests.mostRecent()
            request
                .respondWith({
                    status: 200,
                    response: [{ city: 'Berlin' }, { city: 'Muenchen' }],
                })
                .then(function() {
                    expect(wrapper.vm.cities).toEqual(['Berlin', 'Muenchen'])
                    done()
                })
        })
    })

    it('should get an error from invalid request for cities', async () => {
        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })

        const error = new Error('Error: Request failed with status code 400')

        await wrapper.vm.fetchData()

        let request = moxios.requests.mostRecent()
        request
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

    it('should get comparison data from request', async () => {
        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })

        wrapper.setData({ city: 'Berlin' })
        wrapper.setData({ startTime: '2020-01-13 12:00' })
        wrapper.setData({ endTime: '2020-01-13 13:00' })
        flushPromises()

        moxios.stubRequest(
            'http://localhost:8082/withDatabase/comparisonEvaluationOverTime/?city=Berlin',
            {
                status: 200,
                response: [
                    {
                        sameIncidentAmount: 20,
                        date: '2021-01-11 10:50',
                        tomTomIncidentAmount: 55,
                        hereIncidentAmount: 40,
                    },
                ],
            }
        )

        wrapper.vm.fetchDataForCity()

        moxios.wait(() => {
            expect(wrapper.vm.comparisonData).toEqual({
                labels: ['2021-01-11 10:50'],
                tomtom: [55],
                here: [40],
                comparison: [20],
            })
            done()
        })
    })

    it('should get an error from invalid request for comparison data', async () => {
        wrapper = shallowMount(Historization, {
            localVue,
            vuetify,
        })
        const error = new Error('Error: Request failed with status code 400')

        await wrapper.vm.fetchDataForCity()

        let request = moxios.requests.mostRecent()
        request
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
