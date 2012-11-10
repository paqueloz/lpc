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

@TestFor(SecUserController)
@Mock(SecUser)
class SecUserControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/secUser/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.secUserInstanceList.size() == 0
        assert model.secUserInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.secUserInstance != null
    }

    void testSave() {
        controller.save()

        assert model.secUserInstance != null
        assert view == '/secUser/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/secUser/show/1'
        assert controller.flash.message != null
        assert SecUser.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/secUser/list'


        populateValidParams(params)
        def secUser = new SecUser(params)

        assert secUser.save() != null

        params.id = secUser.id

        def model = controller.show()

        assert model.secUserInstance == secUser
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/secUser/list'


        populateValidParams(params)
        def secUser = new SecUser(params)

        assert secUser.save() != null

        params.id = secUser.id

        def model = controller.edit()

        assert model.secUserInstance == secUser
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/secUser/list'

        response.reset()


        populateValidParams(params)
        def secUser = new SecUser(params)

        assert secUser.save() != null

        // test invalid parameters in update
        params.id = secUser.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/secUser/edit"
        assert model.secUserInstance != null

        secUser.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/secUser/show/$secUser.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        secUser.clearErrors()

        populateValidParams(params)
        params.id = secUser.id
        params.version = -1
        controller.update()

        assert view == "/secUser/edit"
        assert model.secUserInstance != null
        assert model.secUserInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/secUser/list'

        response.reset()

        populateValidParams(params)
        def secUser = new SecUser(params)

        assert secUser.save() != null
        assert SecUser.count() == 1

        params.id = secUser.id

        controller.delete()

        assert SecUser.count() == 0
        assert SecUser.get(secUser.id) == null
        assert response.redirectedUrl == '/secUser/list'
    }
}
