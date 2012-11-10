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
import grails.test.mixin.*

@TestFor(CampYearController)
@Mock([CampYear,Camp])
class CampYearControllerTests {


    def populateValidParams(params) {
      assert params != null
      // Populate valid properties like...
      //params["name"] = 'someValidName'
      params["camp"] = new Camp()
      params["year"] = '2012'
    }

    void testIndex() {
        controller.index()
        assert "/campYear/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.campYearInstanceList.size() == 0
        assert model.campYearInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.campYearInstance != null
    }

    void testSave() {
        controller.save()

        assert model.campYearInstance != null
        assert view == '/campYear/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/campYear/show/1'
        assert controller.flash.message != null
        assert CampYear.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/campYear/list'


        populateValidParams(params)
        def campYear = new CampYear(params)

        assert campYear.save() != null

        params.id = campYear.id

        def model = controller.show()

        assert model.campYearInstance == campYear
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/campYear/list'


        populateValidParams(params)
        def campYear = new CampYear(params)

        assert campYear.save() != null

        params.id = campYear.id

        def model = controller.edit()

        assert model.campYearInstance == campYear
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/campYear/list'

        response.reset()


        populateValidParams(params)
        def campYear = new CampYear(params)

        assert campYear.save() != null

        // test invalid parameters in update
        params.id = campYear.id
        // add invalid values to params object
        params.camp = null

        controller.update()

        assert view == "/campYear/edit"
        assert model.campYearInstance != null

        campYear.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/campYear/show/$campYear.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        campYear.clearErrors()

        populateValidParams(params)
        params.id = campYear.id
        params.version = -1
        controller.update()

        assert view == "/campYear/edit"
        assert model.campYearInstance != null
        assert model.campYearInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/campYear/list'

        response.reset()

        populateValidParams(params)
        def campYear = new CampYear(params)

        assert campYear.save() != null
        assert CampYear.count() == 1

        params.id = campYear.id

        controller.delete()

        assert CampYear.count() == 0
        assert CampYear.get(campYear.id) == null
        assert response.redirectedUrl == '/campYear/list'
    }
}
