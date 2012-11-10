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
import grails.plugins.countries.Country

@TestFor(NationalityController)
@Mock(Nationality)
class NationalityControllerTests {


    def populateValidParams(params) {
      assert params != null
      params["country"] = new Country()
      params["person"] = new Person()
    }

    void testIndex() {
        controller.index()
        assert "/nationality/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.nationalityInstanceList.size() == 0
        assert model.nationalityInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.nationalityInstance != null
    }

    void testSave() {
        controller.save()

        assert model.nationalityInstance != null
        assert view == '/nationality/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/nationality/show/1'
        assert controller.flash.message != null
        assert Nationality.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/nationality/list'


        populateValidParams(params)
        def nationality = new Nationality(params)

        assert nationality.save() != null

        params.id = nationality.id

        def model = controller.show()

        assert model.nationalityInstance == nationality
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/nationality/list'


        populateValidParams(params)
        def nationality = new Nationality(params)

        assert nationality.save() != null

        params.id = nationality.id

        def model = controller.edit()

        assert model.nationalityInstance == nationality
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/nationality/list'

        response.reset()


        populateValidParams(params)
        def nationality = new Nationality(params)

        assert nationality.save() != null

        // test invalid parameters in update
        params.id = nationality.id
        params.country = null

        controller.update()

        assert view == "/nationality/edit"
        assert model.nationalityInstance != null

        nationality.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/nationality/show/$nationality.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        nationality.clearErrors()

        populateValidParams(params)
        params.id = nationality.id
        params.version = -1
        controller.update()

        assert view == "/nationality/edit"
        assert model.nationalityInstance != null
        assert model.nationalityInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/nationality/list'

        response.reset()

        populateValidParams(params)
        def nationality = new Nationality(params)

        assert nationality.save() != null
        assert Nationality.count() == 1

        params.id = nationality.id

        controller.delete()

        assert Nationality.count() == 0
        assert Nationality.get(nationality.id) == null
        assert response.redirectedUrl == '/nationality/list'
    }
}
