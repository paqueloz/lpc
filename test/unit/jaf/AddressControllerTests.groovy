/*
 *   This program manages the database and supports the work processes
 *   of the Luethi-Peterson Camps association (http://luethipetersoncamps.org/).
 *
 *   Copyright (C) 2012 Pierre-Antoine Queloz
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jaf



import org.junit.*

import grails.plugins.countries.Country;
import grails.test.mixin.*

@TestFor(AddressController)
@Mock(Address)
class AddressControllerTests {


    def populateValidParams(params) {
      assert params != null
      params["person"] = new Person()
      params["street1"] = 'rue du levant'
      params["zipCode"] = '1220'
      params["city"] = 'Vett'
    }

    void testIndex() {
        controller.index()
        assert "/address/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.addressInstanceList.size() == 0
        assert model.addressInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.addressInstance != null
    }

    void testSave() {
        controller.save()

        assert model.addressInstance != null
        assert view == '/address/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/address/show/1'
        assert controller.flash.message != null
        assert Address.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/address/list'


        populateValidParams(params)
        def address = new Address(params)

        assert address.save() != null

        params.id = address.id

        def model = controller.show()

        assert model.addressInstance == address
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/address/list'


        populateValidParams(params)
        def address = new Address(params)

        assert address.save() != null

        params.id = address.id

        def model = controller.edit()

        assert model.addressInstance == address
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/address/list'

        response.reset()


        populateValidParams(params)
        def address = new Address(params)

        assert address.save() != null

        // test invalid parameters in update
        params.id = address.id
        params.person = null

        controller.update()

        assert view == "/address/edit"
        assert model.addressInstance != null

        address.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/address/show/$address.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        address.clearErrors()

        populateValidParams(params)
        params.id = address.id
        params.version = -1
        controller.update()

        assert view == "/address/edit"
        assert model.addressInstance != null
        assert model.addressInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/address/list'

        response.reset()

        populateValidParams(params)
        def address = new Address(params)

        assert address.save() != null
        assert Address.count() == 1

        params.id = address.id

        controller.delete()

        assert Address.count() == 0
        assert Address.get(address.id) == null
        assert response.redirectedUrl == '/address/list'
    }
}
