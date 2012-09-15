package jaf



import org.junit.*
import grails.test.mixin.*

@TestFor(PersonRelationController)
@Mock([PersonRelation,Person])
class PersonRelationControllerTests {


    def populateValidParams(params) {
      assert params != null
      params.person = new Person()
      params.relationship = 'father'
      params.other = new Person()
    }

    void testIndex() {
        controller.index()
        assert "/personRelation/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.personRelationInstanceList.size() == 0
        assert model.personRelationInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.personRelationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.personRelationInstance != null
        assert view == '/personRelation/create'

        response.reset()

//      TODO : find the way to really create the person, otherwise findById cannot work        
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/personRelation/show/1'
//        assert controller.flash.message != null
//        assert PersonRelation.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/personRelation/list'


        populateValidParams(params)
        def personRelation = new PersonRelation(params)

        assert personRelation.save() != null

        params.id = personRelation.id

        def model = controller.show()

        assert model.personRelationInstance == personRelation
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/personRelation/list'


        populateValidParams(params)
        def personRelation = new PersonRelation(params)

        assert personRelation.save() != null

        params.id = personRelation.id

        def model = controller.edit()

        assert model.personRelationInstance == personRelation
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/personRelation/list'

        response.reset()


        populateValidParams(params)
        def personRelation = new PersonRelation(params)

        assert personRelation.save() != null

        // test invalid parameters in update
        params.id = personRelation.id
        params.other = null

        controller.update()

        assert view == "/personRelation/edit"
        assert model.personRelationInstance != null

        personRelation.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/personRelation/show/$personRelation.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        personRelation.clearErrors()

        populateValidParams(params)
        params.id = personRelation.id
        params.version = -1
        controller.update()

        assert view == "/personRelation/edit"
        assert model.personRelationInstance != null
        assert model.personRelationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/personRelation/list'

        response.reset()

        populateValidParams(params)
        def personRelation = new PersonRelation(params)

        assert personRelation.save() != null
        assert PersonRelation.count() == 1

        params.id = personRelation.id

        controller.delete()

        assert PersonRelation.count() == 0
        assert PersonRelation.get(personRelation.id) == null
        assert response.redirectedUrl == '/personRelation/list'
    }
}
