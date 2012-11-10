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

@TestFor(PersonController)
@Mock(Person)
class PersonControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/person/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.personInstanceList.size() == 0
        assert model.personInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.personInstance != null
    }

//    void testSave() {
//        controller.save()
//
//        assert model.personInstance != null
//        assert view == '/person/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/person/show/1'
//        assert controller.flash.message != null
//        assert Person.count() == 1
//    }

//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/person/list'
//
//
//        populateValidParams(params)
//        def person = new Person(params)
//
//        assert person.save() != null
//
//        params.id = person.id
//
//        def model = controller.show()
//
//        assert model.personInstance == person
//    }

//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/person/list'
//
//
//        populateValidParams(params)
//        def person = new Person(params)
//
//        assert person.save() != null
//
//        params.id = person.id
//
//        def model = controller.edit()
//
//        assert model.personInstance == person
//    }

//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/person/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def person = new Person(params)
//
//        assert person.save() != null
//
//        // test invalid parameters in update
//        params.id = person.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/person/edit"
//        assert model.personInstance != null
//
//        person.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/person/show/$person.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        person.clearErrors()
//
//        populateValidParams(params)
//        params.id = person.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/person/edit"
//        assert model.personInstance != null
//        assert model.personInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }

//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/person/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def person = new Person(params)
//
//        assert person.save() != null
//        assert Person.count() == 1
//
//        params.id = person.id
//
//        controller.delete()
//
//        assert Person.count() == 0
//        assert Person.get(person.id) == null
//        assert response.redirectedUrl == '/person/list'
//    }
}
