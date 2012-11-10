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



import jaf.Contact;

import org.junit.*

import grails.plugins.countries.Country;
import grails.test.mixin.*

@TestFor(CampController)
@Mock(Camp)
class CampControllerTests {


    def populateValidParams(params) {
      assert params != null
      params["location"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/camp/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.campInstanceList.size() == 0
        assert model.campInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.campInstance != null
    }

    void testSave() {
        controller.save()

        assert model.campInstance != null
        assert view == '/camp/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/camp/show/1'
        assert controller.flash.message != null
        assert Camp.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/camp/list'


        populateValidParams(params)
        def camp = new Camp(params)

        assert camp.save() != null

        params.id = camp.id

        def model = controller.show()

        assert model.campInstance == camp
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/camp/list'


        populateValidParams(params)
        def camp = new Camp(params)

        assert camp.save() != null

        params.id = camp.id

        def model = controller.edit()

        assert model.campInstance == camp
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/camp/list'

        response.reset()


        populateValidParams(params)
        def camp = new Camp(params)

        assert camp.save() != null

        // test invalid parameters in update
        params.id = camp.id
        params.location = null

        controller.update()

        assert view == "/camp/edit"
        assert model.campInstance != null

        camp.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/camp/show/$camp.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        camp.clearErrors()

        populateValidParams(params)
        params.id = camp.id
        params.version = -1
        controller.update()

        assert view == "/camp/edit"
        assert model.campInstance != null
        assert model.campInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/camp/list'

        response.reset()

        populateValidParams(params)
        def camp = new Camp(params)

        assert camp.save() != null
        assert Camp.count() == 1

        params.id = camp.id

        controller.delete()

        assert Camp.count() == 0
        assert Camp.get(camp.id) == null
        assert response.redirectedUrl == '/camp/list'
    }
}
