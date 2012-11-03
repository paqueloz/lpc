package jaf



import org.junit.*
import grails.test.mixin.*

@TestFor(PersonRelationController)
@Mock([PersonRelation,Person])
class PersonRelationControllerTests {


    def populateValidParams(params) {
      assert params != null
      Person p = new Person(firstName:"Paul",lastName:"Henri",gender:jaf.Gender.MALE,birthDay:new Date(),status:jaf.PersonStatus.CAmper).save()
      assert p != null
      params.person = p
      params.relationship = 'parentOf'
      p = new Person(firstName:"Jean",lastName:"François",gender:jaf.Gender.MALE,birthDay:new Date(),status:jaf.PersonStatus.CAmper).save()
      params.other = p
      params.other_id = p.id
      params.other_id_old = p.id
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
        // test empty params
        controller.save()
        assert model.personRelationInstance != null
        assert view == '/personRelation/create'

        // test valid params
        response.reset()
        populateValidParams(params)
        controller.save()
        assert response.redirectedUrl == '/personRelation/show/1'
        assert controller.flash.message != null
        assert PersonRelation.count() == 2
    }
    
    void testSaveMissingPerson() {
        response.reset()
        populateValidParams(params)
        params.person = [ id : "999" ]
        controller.save()
        assert model.personRelationInstance != null
        assert view == '/personRelation/create'
        assert PersonRelation.count() == 0
    }

    void testSaveInvalidRelation() {
        response.reset()
        populateValidParams(params)
        params.relationship = "asdfg"
        controller.save()
        assert model.personRelationInstance != null
        assert view == '/personRelation/create'
        assert PersonRelation.count() == 0
    }

    void testSaveInvalidOther() {
        response.reset()
        populateValidParams(params)
        params.other_id = "${params.person.id}"
        controller.save()
        assert view == '/personRelation/create'
        assert model.personRelationInstance != null
        assert PersonRelation.count() == 0
    }
    
    void testSaveLivesWith() {
        response.reset()
        populateValidParams(params)
        params.relationship = "livesWith"
        controller.save()
        assert response.redirectedUrl == '/personRelation/show/1'
        assert controller.flash.message != null
        assert PersonRelation.count() == 1
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
        params.other_id = null
        params.other_id_old = null
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
    
    void testUpdateOtherId() {
        response.reset()
        populateValidParams(params)
        def personRelation = new PersonRelation(params)
        assert personRelation.save() != null
        params.id = personRelation.id
        params.other_id = personRelation.person.id
        controller.update()
        assert view == "/personRelation/edit"
        assert model.personRelationInstance != null
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
