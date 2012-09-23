package jaf



import org.junit.*
import grails.test.mixin.*

@TestFor(AttendanceController)
@Mock(Attendance)
class AttendanceControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/attendance/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.attendanceInstanceList.size() == 0
        assert model.attendanceInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.attendanceInstance != null
    }

    void testSave() {
        controller.save()

        assert model.attendanceInstance != null
        assert view == '/attendance/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/attendance/show/1'
        assert controller.flash.message != null
        assert Attendance.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/attendance/list'


        populateValidParams(params)
        def attendance = new Attendance(params)

        assert attendance.save() != null

        params.id = attendance.id

        def model = controller.show()

        assert model.attendanceInstance == attendance
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/attendance/list'


        populateValidParams(params)
        def attendance = new Attendance(params)

        assert attendance.save() != null

        params.id = attendance.id

        def model = controller.edit()

        assert model.attendanceInstance == attendance
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/attendance/list'

        response.reset()


        populateValidParams(params)
        def attendance = new Attendance(params)

        assert attendance.save() != null

        // test invalid parameters in update
        params.id = attendance.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/attendance/edit"
        assert model.attendanceInstance != null

        attendance.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/attendance/show/$attendance.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        attendance.clearErrors()

        populateValidParams(params)
        params.id = attendance.id
        params.version = -1
        controller.update()

        assert view == "/attendance/edit"
        assert model.attendanceInstance != null
        assert model.attendanceInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/attendance/list'

        response.reset()

        populateValidParams(params)
        def attendance = new Attendance(params)

        assert attendance.save() != null
        assert Attendance.count() == 1

        params.id = attendance.id

        controller.delete()

        assert Attendance.count() == 0
        assert Attendance.get(attendance.id) == null
        assert response.redirectedUrl == '/attendance/list'
    }
}
