import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import OpenStreetMap from '@/components/OpenStreetMap'
import Vue from 'vue'
import Vuetify from 'vuetify'

Vue.use(Vuetify)
const localVue = createLocalVue()
let vuetify
let wrapper

describe('OpenStreetMap', () => {
    beforeEach(() => {
        vuetify = new Vuetify()
        wrapper = shallowMount(OpenStreetMap, {
            localVue,
            vuetify,
            propsData: {
                polyline: { latlngs: [54.6785, 55.6748] },
            },
        })
    })

    it('should contain OSM map element', () => {
        expect(wrapper.find('#osm-map').exists()).toBe(true)
    })

    afterEach(() => {
        wrapper.destroy()
    })
})
