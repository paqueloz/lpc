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



import jaf.ContactMode;
import jaf.ContactType;

import org.junit.*
import grails.test.mixin.*

@TestFor(ContactController)
@Mock(Contact)
class ContactControllerTests {


    def populateValidParams(params) {
      assert params != null
      params["person"] = new Person()
      params["type"] = 'PHONE'
      params["mode"] = 'HOME'
      params["value"] = '00 33 44 55'
      params["active"] = 'false'
    }

    void testIndex() {
        controller.index()
        assert "/contact/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.contactInstanceList.size() == 0
        assert model.contactInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.contactInstance != null
    }

    void testSave() {
        controller.save()

        assert model.contactInstance != null
        assert view == '/contact/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/contact/show/1'
        assert controller.flash.message != null
        assert Contact.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/contact/list'


        populateValidParams(params)
        def contact = new Contact(params)

        assert contact.save() != null

        params.id = contact.id

        def model = controller.show()

        assert model.contactInstance == contact
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/contact/list'


        populateValidParams(params)
        def contact = new Contact(params)

        assert contact.save() != null

        params.id = contact.id

        def model = controller.edit()

        assert model.contactInstance == contact
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/contact/list'

        response.reset()


        populateValidParams(params)
        def contact = new Contact(params)

        assert contact.save() != null

        // test invalid parameters in update
        params.id = contact.id
        params.type = 'NOT GOOD'

        controller.update()

        assert view == "/contact/edit"
        assert model.contactInstance != null

        contact.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/contact/show/$contact.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        contact.clearErrors()

        populateValidParams(params)
        params.id = contact.id
        params.version = -1
        controller.update()

        assert view == "/contact/edit"
        assert model.contactInstance != null
        assert model.contactInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/contact/list'

        response.reset()

        populateValidParams(params)
        def contact = new Contact(params)

        assert contact.save() != null
        assert Contact.count() == 1

        params.id = contact.id

        controller.delete()

        assert Contact.count() == 0
        assert Contact.get(contact.id) == null
        assert response.redirectedUrl == '/contact/list'
    }
}
