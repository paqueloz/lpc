<%@ page import="jaf.Person" %>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'firstName', 'error')} required">
	<label for="firstName">
		<g:message code="person.firstName.label" default="First Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="firstName" required="" value="${personInstance?.firstName}" autofocus="autofocus"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'lastName', 'error')} required">
	<label for="lastName">
		<g:message code="person.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lastName" required="" value="${personInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'gender', 'error')} required">
	<label for="gender">
		<g:message code="person.gender.label" default="Gender" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="gender" from="${jaf.Gender?.values()}" keys="${jaf.Gender.values()*.name()}" required="" value="${personInstance?.gender?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'birthDay', 'error')} required">
	<label for="birthDay">
		<g:message code="person.birthDay.label" default="Birth Day" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="birthDay" precision="day"  value="${personInstance?.birthDay}"  />
</div>

<%-- Death day: hide this area until Person is in the database --%>
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'deathDay', 'error')} ">
        <label for="deathDay"> <g:message
                code="person.deathDay.label" default="Death Day" />
        </label>
        <g:datePicker name="deathDay" precision="day"
            value="${personInstance?.deathDay}" default="none"
            noSelection="['': '']" />
    </div>
</g:if>

<%-- Address: hide this area until Person is in the database --%>
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'address', 'error')} ">
        <label for="address"> <g:message
                code="person.address.label" default="Address" />
        </label>
        <%--<g:select id="address" name="address.id"
            from="${jaf.Address.list()}" optionKey="id"
            value="${personInstance?.address?.id}" class="many-to-one"
            noSelection="['null': '']" />--%>
        <%--<g:if test="${personInstance?.address?.id}">
            <g:link controller="address" action="show" id="${personInstance?.address?.id}">
                ${personInstance?.address?.encodeAsHTML()}
            </g:link>
        </g:if>
        <g:else>
            <g:link controller="address" action="create"
                params="['person.id': personInstance?.id]">
                ${message(code: 'default.add.label', args: [message(code: 'person.address.label', default: 'Address')])}
            </g:link>
        </g:else>
        --%>
        <ul class="one-to-many">
            <g:each in="${personInstance?.address?}" var="c">
                <li><g:link controller="address" action="show"
                        id="${c.id}">
                        ${c?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <g:if test="${personInstance?.address?.size()==0}">
                <li class="add"><g:link controller="address"
                        action="create"
                        params="['person.id': personInstance?.id]">
                        ${message(code: 'default.add.label', args: [message(code: 'person.address.label', default: 'Address')])}
                    </g:link></li>
            </g:if>
        </ul>
    </div>
</g:if>

<%-- Contacts: hide this area until Person is in the database --%>
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'contacts', 'error')} ">
        <label for="contacts"> <g:message
                code="person.contacts.label" default="Contacts" />
        </label>
        <ul class="one-to-many">
            <g:each in="${personInstance?.contacts?}" var="c">
                <li><g:link controller="contact" action="show"
                        id="${c.id}">
                        ${c?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <li class="add"><g:link controller="contact"
                    action="create"
                    params="['person.id': personInstance?.id]">
                    ${message(code: 'default.add.label', args: [message(code: 'contact.label', default: 'Contact')])}
                </g:link></li>
        </ul>
    </div>
</g:if>

<%-- Nationalities: hide this area until Person is in the database --%>
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'nationalities', 'error')} ">
        <label for="nationalities"> <g:message
                code="person.nationalities.label"
                default="Nationalities" />
        </label>
        <ul class="one-to-many">
            <g:each in="${personInstance?.nationalities?}" var="n">
                <li><g:link controller="nationality" action="show"
                        id="${n.id}">
                        ${n?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <li class="add"><g:link controller="nationality"
                    action="create"
                    params="['person.id': personInstance?.id]">
                    ${message(code: 'default.add.label', args: [message(code: 'nationality.label', default: 'Nationality')])}
                </g:link></li>
        </ul>
    </div>
</g:if>

<%-- Languages: hide this area until Person is in the database --%>
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'languages', 'error')} ">
        <label for="languages"> <g:message
                code="person.languages.label" default="Languages" />
        </label>
        <ul class="one-to-many">
            <g:each in="${personInstance?.languages?}" var="l">
                <li><g:link controller="languageLevel"
                        action="show" id="${l.id}">
                        ${l?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <li class="add"><g:link controller="languageLevel"
                    action="create"
                    params="['person.id': personInstance?.id]">
                    ${message(code: 'default.add.label', args: [message(code: 'languageLevel.label', default: 'LanguageLevel')])}
                </g:link></li>
        </ul>
    </div>
</g:if>

<%-- Relationships: hide this area until Person is in the database --%>
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'relationships', 'error')} ">
        <label for="relationships"> <g:message
                code="person.relationships.label" default="Relationships" />
        </label>
        <ul class="one-to-many">
            <g:each in="${personInstance?.relationships?}" var="a">
                <li><g:link controller="personRelation" action="show"
                        id="${a.id}">
                        ${a?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <li class="add"><g:link controller="personRelation"
                    action="create"
                    params="['person.id': personInstance?.id]">
                    ${message(code: 'default.add.label', args: [message(code: 'person.relationships.label', default: 'Relationships')])}
                </g:link></li>
        </ul>
    </div>
</g:if>

<%-- Attendances: hide this area until Person is in the database --%>
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'attendances', 'error')} ">
        <label for="attendances"> <g:message
                code="person.attendances.label" default="Attendances" />
        </label>
        <ul class="one-to-many">
            <g:each in="${personInstance?.attendances?}" var="a">
                <li><g:link controller="attendance" action="show"
                        id="${a.id}">
                        ${a?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <li class="add"><g:link controller="attendance"
                    action="create"
                    params="['person.id': personInstance?.id]">
                    ${message(code: 'default.add.label', args: [message(code: 'attendance.label', default: 'Attendance')])}
                </g:link></li>
        </ul>
    </div>
</g:if>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'appliedForNextYear', 'error')} ">
	<label for="appliedForNextYear">
		<g:message code="person.appliedForNextYear.label" default="Applied For Next Year" />
		
	</label>
	<g:checkBox name="appliedForNextYear" value="${personInstance?.appliedForNextYear}" />
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'newToLpc', 'error')} ">
	<label for="newToLpc">
		<g:message code="person.newToLpc.label" default="New To Lpc" />
		
	</label>
	<g:checkBox name="newToLpc" value="${personInstance?.newToLpc}" />
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'preferences', 'error')} ">
	<label for="preferences">
		<g:message code="person.preferences.label" default="Preferences" />
		
	</label>
	<g:textField name="preferences" value="${personInstance?.preferences}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="person.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${jaf.PersonStatus?.values()}" keys="${jaf.PersonStatus.values()*.name()}" required="" value="${personInstance?.status?.name()}"/>
</div>


<%-- Involvements: 
  -- hide this area until Person is in the database
  -- hide this area to keep things simple (manage through Camp)
<g:if test="${personInstance?.id}">
    <div
        class="fieldcontain ${hasErrors(bean: personInstance, field: 'involvements', 'error')} ">
        <label for="involvements"> <g:message
                code="person.involvements.label" default="Involvements" />
        </label>
        <ul class="one-to-many">
            <g:each in="${personInstance?.involvements?}" var="i">
                <li><g:link controller="staff" action="show"
                        id="${i.id}">
                        ${i?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <li class="add"><g:link controller="staff"
                    action="create"
                    params="['person.id': personInstance?.id]">
                    ${message(code: 'default.add.label', args: [message(code: 'staff.label', default: 'Staff')])}
                </g:link></li>
        </ul>
    </div>
</g:if>
--%>