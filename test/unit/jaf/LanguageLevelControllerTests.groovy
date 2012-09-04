package jaf



import org.junit.*
import grails.test.mixin.*

@TestFor(LanguageLevelController)
@Mock(LanguageLevel)
class LanguageLevelControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
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
        //TODO: add invalid values to params object

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
