<%@ page import="jaf.Camp" %>



<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'location', 'error')} ">
	<label for="location">
		<g:message code="camp.location.label" default="Location" />
		
	</label>
	<g:textField name="location" value="${campInstance?.location}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'street1', 'error')} ">
	<label for="street1">
		<g:message code="camp.street1.label" default="Street1" />
		
	</label>
	<g:textField name="street1" value="${campInstance?.street1}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'street2', 'error')} ">
	<label for="street2">
		<g:message code="camp.street2.label" default="Street2" />
		
	</label>
	<g:textField name="street2" value="${campInstance?.street2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'zipCode', 'error')} ">
	<label for="zipCode">
		<g:message code="camp.zipCode.label" default="Zip Code" />
		
	</label>
	<g:textField name="zipCode" value="${campInstance?.zipCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'city', 'error')} ">
	<label for="city">
		<g:message code="camp.city.label" default="City" />
		
	</label>
	<g:textField name="city" value="${campInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="camp.country.label" default="Country" />		
	</label>
	<%--<g:select id="country" name="country.id" from="${grails.plugins.countries.Country.list()}" optionKey="id" value="${campInstance?.country?.id}" class="many-to-one" noSelection="['null': '']"/>
    --%>
    <country:select name="country.id" value="${campInstance?.country?.id}" noSelection="['':'select please:']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'contact', 'error')} ">
	<label for="contact">
		<g:message code="camp.contact.label" default="Contact" />
		
	</label>
	<g:select id="contact" name="contact.id" from="${jaf.Contact.list()}" optionKey="id" value="${campInstance?.contact?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'staff', 'error')} ">
	<label for="staff">
		<g:message code="camp.staff.label" default="Staff" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${campInstance?.staff?}" var="s">
    <li><g:link controller="staff" action="show" id="${s.id}">${s?.toStringForCamp().encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="staff" action="create" params="['camp.id': campInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'staff.label', default: 'Staff')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: campInstance, field: 'years', 'error')} ">
	<label for="years">
		<g:message code="camp.years.label" default="Years" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${campInstance?.years?}" var="y">
    <li><g:link controller="campYear" action="show" id="${y.id}">${y?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="campYear" action="create" params="['camp.id': campInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'campYear.label', default: 'CampYear')])}</g:link>
</li>
</ul>

</div>

