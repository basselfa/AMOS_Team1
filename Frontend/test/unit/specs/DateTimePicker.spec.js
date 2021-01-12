import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import DateTimePicker from '@/components/DateTimePicker'
import Vue from 'vue'
import Vuetify from 'vuetify'

Vue.use(Vuetify)

const localVue = createLocalVue()
let vuetify
let wrapper

describe('DateTimePicker', () => {
    beforeEach(() => {
        vuetify = new Vuetify()
        wrapper = mount(DateTimePicker, {
            localVue,
            vuetify,
            propsData: { label: 'start', value: '20-01-06 13:13' },
        })
    })

    it('is instantiated', () => {
        expect(wrapper).toBeTruthy()
    })

    it('should close datepicker after confirmation', () => {
        wrapper.vm.confirm()
        expect(wrapper.vm.open).toEqual(false)
    })

    it('should emit change on click confirm', () => {
        wrapper.setData({ timeModel: '12:13', dateModel: '24-01-20' })

        expect(wrapper.vm.currentSelection).toEqual('Feb 20, 24 12:13')
    })

    afterEach(() => {
        wrapper.destroy()
    })
})
