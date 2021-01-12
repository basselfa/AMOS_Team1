import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Chart from '@/components/Chart'
import Vue from 'vue'
import Vuetify from 'vuetify'

Vue.use(Vuetify)

const localVue = createLocalVue()
let vuetify
let wrapper

describe('Chart', () => {
    beforeEach(() => {
        vuetify = new Vuetify()
    })

    it('is instantiated', () => {
        wrapper = shallowMount(Chart, {
            localVue,
            vuetify,
            propsData: {
                chartDataCollection: null,
                city: 'Berlin',
            },
        })
        expect(wrapper).toBeTruthy()
    })

    afterEach(() => {
        wrapper.destroy()
    })
})
