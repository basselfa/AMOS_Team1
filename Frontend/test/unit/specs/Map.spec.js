import { mount, shallowMount, createLocalVue } from '@vue/test-utils'
import Map from '@/views/Map'
import Vue from 'vue'
import Vuetify from 'vuetify'
import * as Vue2Leaflet from 'vue2-leaflet'
import axios from "axios";
const MockAdapter = require("axios-mock-adapter");

Vue.use(Vuetify)
Vue.use(Vue2Leaflet)
let vuetify
vuetify = new Vuetify()
const localVue = createLocalVue()

describe('Map', () => {
    let wrapper;
    let mock;

    beforeEach(() => {
        mock = new MockAdapter(axios);
        wrapper = shallowMount(Map, {
            localVue,
            vuetify,
        })
        
    })

  it("should get incidents data from request", async () => {
    const fakeData ={
        type: 'Accident',
        edges:
            '52.51784:13.28016,52.51771:13.28021,52.51765:13.28024',
    }

    mock.onGet("http://localhost:8082/withDatabase/incidents?city=Berlin&timestamp=2020-12-19 13:00&types=Accident").reply(200, fakeData);

    await wrapper.vm.getIncidents({city: 'Berlin',
    timestamp: '2020-12-19 13:00',
    type: ['Accident']});

    expect(wrapper.vm.incidentsData).toEqual({
        type: 'Accident',
        edges:
            '52.51784:13.28016,52.51771:13.28021,52.51765:13.28024',
    })

  })

  it("should get comparison data from request", async () => {
    const fakeData = {
        tomTomIncidentId: '22',
        hereIncidentId: '23'
    }

    mock.onGet("http://localhost:8082/withDatabase/comparison?city=Berlin&timestamp=2020-12-19 13:00").reply(200, fakeData);

    await wrapper.vm.getComparison({city: 'Berlin',
    timestamp: '2020-12-19 13:00'});

    expect(wrapper.vm.comparisonData).toEqual({
        tomTomIncidentId: '22',
        hereIncidentId: '23'
    })
  })

  it("should get error from incidents data request", async () => {
    let error = new Error()

    mock.onGet("http://localhost:8082/withDatabase/incidents?city=Berlin&timestamp=2020-12-19 13:00&types=Accident").reply(500, error)

    await wrapper.vm.getIncidents({city: 'Berlin',
    timestamp: '2020-12-19 13:00',type: ['Accident']});

    expect(wrapper.vm.errorMessage).toEqual(
        "Request failed with status code 500"
    )
  })

  it("should get error from comparison data request", async () => {
    let error = new Error()

    mock.onGet("http://localhost:8082/withDatabase/comparison?city=Berlin&timestamp=2020-12-19 13:00").reply(500, error)

    await wrapper.vm.getComparison({city: 'Berlin',
    timestamp: '2020-12-19 13:00'});

    expect(wrapper.vm.errorMessage).toEqual(
        "Request failed with status code 500"
    )
  })

  it("should call executeQuery()", async () => {

        wrapper.vm.executeQuery = jest.fn();

        wrapper.vm.getSearchValue({city: 'Berlin', timestamp: '2020-12-19 13:00'});
        expect(wrapper.vm.executeQuery).toHaveBeenCalled();
    })

    it("should not call passCoordinates()", async () => {

        wrapper.vm.passCoordinates = jest.fn();
        
        wrapper.vm.executeQuery({city: '', timestamp: ''});

        expect(wrapper.vm.passCoordinates).not.toHaveBeenCalled();
    })

    it("should get edges for incidents ", async () => {

        wrapper.vm.comparisonData = [{
            tomTomIncidentId: '22',
            hereIncidentId: '23'
        },
        {
            tomTomIncidentId: '32',
            hereIncidentId: '33'
        }] 

        let incidentDummyData = [{
            type: 'Accident',
            edges: '52.51784:13.28016,52.51771:13.28021,52.51765:13.28024',
            size:1,
            description:"description",
            lengthInMeter:55,
            provider:"1"
        }]

        wrapper.vm.passCoordinates(incidentDummyData);

        expect(wrapper.vm.polylines).toEqual([{
            "color": "rgb(255, 233, 66)", 
            "criticality": 1, 
            "description": "description", 
            "latlngs": [["52.51784", "13.28016"], ["52.51771", "13.28021"], ["52.51765", "13.28024"]], 
            "length": "55.00", 
            "type":  "Accident"}])

    })

    afterEach(() => {
        mock.restore();
    })
})
 