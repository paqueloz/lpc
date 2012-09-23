package jaf



import org.junit.*
import grails.test.mixin.*

@TestFor(CampYearController)
@Mock(CampYear)
class CampYearControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
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
        //TODO: add invalid values to params object

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
