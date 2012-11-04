package jaf



import org.junit.*
import grails.test.mixin.*

@TestFor(StaffController)
@Mock(Staff)
class StaffControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/staff/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.staffInstanceList.size() == 0
        assert model.staffInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.staffInstance != null
    }

    void testSave() {
        controller.save()

        assert model.staffInstance != null
        assert view == '/staff/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/staff/show/1'
        assert controller.flash.message != null
        assert Staff.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/staff/list'


        populateValidParams(params)
        def staff = new Staff(params)

        assert staff.save() != null

        params.id = staff.id

        def model = controller.show()

        assert model.staffInstance == staff
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/staff/list'


        populateValidParams(params)
        def staff = new Staff(params)

        assert staff.save() != null

        params.id = staff.id

        def model = controller.edit()

        assert model.staffInstance == staff
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/staff/list'

        response.reset()


        populateValidParams(params)
        def staff = new Staff(params)

        assert staff.save() != null

        // test invalid parameters in update
        params.id = staff.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/staff/edit"
        assert model.staffInstance != null

        staff.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/staff/show/$staff.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        staff.clearErrors()

        populateValidParams(params)
        params.id = staff.id
        params.version = -1
        controller.update()

        assert view == "/staff/edit"
        assert model.staffInstance != null
        assert model.staffInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/staff/list'

        response.reset()

        populateValidParams(params)
        def staff = new Staff(params)

        assert staff.save() != null
        assert Staff.count() == 1

        params.id = staff.id

        controller.delete()

        assert Staff.count() == 0
        assert Staff.get(staff.id) == null
        assert response.redirectedUrl == '/staff/list'
    }
}
