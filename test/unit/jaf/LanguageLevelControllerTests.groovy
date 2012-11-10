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

@TestFor(LanguageLevelController)
@Mock(LanguageLevel)
class LanguageLevelControllerTests {


    def populateValidParams(params) {
      assert params != null
      params["person"] = new Person()
      params["language"] = 'no'
      params["level"] = 'GOOD' 
    }

    void testIndex() {
        controller.index()
        assert "/languageLevel/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.languageLevelInstanceList.size() == 0
        assert model.languageLevelInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.languageLevelInstance != null
    }

    void testSave() {
        controller.save()

        assert model.languageLevelInstance != null
        assert view == '/languageLevel/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/languageLevel/show/1'
        assert controller.flash.message != null
        assert LanguageLevel.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/languageLevel/list'


        populateValidParams(params)
        def languageLevel = new LanguageLevel(params)

        assert languageLevel.save() != null

        params.id = languageLevel.id

        def model = controller.show()

        assert model.languageLevelInstance == languageLevel
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/languageLevel/list'


        populateValidParams(params)
        def languageLevel = new LanguageLevel(params)

        assert languageLevel.save() != null

        params.id = languageLevel.id

        def model = controller.edit()

        assert model.languageLevelInstance == languageLevel
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/languageLevel/list'

        response.reset()


        populateValidParams(params)
        def languageLevel = new LanguageLevel(params)

        assert languageLevel.save() != null

        // test invalid parameters in update
        params.id = languageLevel.id
        params.level = 'IMPOSSIBLE'

        controller.update()

        assert view == "/languageLevel/edit"
        assert model.languageLevelInstance != null

        languageLevel.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/languageLevel/show/$languageLevel.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        languageLevel.clearErrors()

        populateValidParams(params)
        params.id = languageLevel.id
        params.version = -1
        controller.update()

        assert view == "/languageLevel/edit"
        assert model.languageLevelInstance != null
        assert model.languageLevelInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/languageLevel/list'

        response.reset()

        populateValidParams(params)
        def languageLevel = new LanguageLevel(params)

        assert languageLevel.save() != null
        assert LanguageLevel.count() == 1

        params.id = languageLevel.id

        controller.delete()

        assert LanguageLevel.count() == 0
        assert LanguageLevel.get(languageLevel.id) == null
        assert response.redirectedUrl == '/languageLevel/list'
    }
}
