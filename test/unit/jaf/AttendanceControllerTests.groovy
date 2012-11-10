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

@TestFor(AttendanceController)
@Mock([Attendance,Person,CampYear])
class AttendanceControllerTests {


    def populateValidParams(params) {
      assert params != null
      Person p = new Person(firstName:"Victor",lastName:"Hugo",gender:jaf.Gender.MALE,birthDay:new Date(),status:jaf.PersonStatus.CAmper).save()
      assert p != null
      params["person"] = p
      CampYear c = new CampYear(year : 2000, camp : new Camp(location:"Venezia")).save()
      assert c != null
      params["camp"] = c
      params["status"] = jaf.PersonStatus.CAmper
      params["person_id"] = p.id
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

        assert response.redirectedUrl == '/campYear/show/1'
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
        // add invalid values to params object
        params.camp = null

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
