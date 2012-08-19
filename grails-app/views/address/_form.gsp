<%@ page import="jaf.Address" %>



<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'street1', 'error')} ">
	<label for="street1">
		<g:message code="address.street1.label" default="Street1" />
		
	</label>
	<g:textField name="street1" value="${addressInstance?.street1}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'street2', 'error')} ">
	<label for="street2">
		<g:message code="address.street2.label" default="Street2" />
		
	</label>
	<g:textField name="street2" value="${addressInstance?.street2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'zipCode', 'error')} ">
	<label for="zipCode">
		<g:message code="address.zipCode.label" default="Zip Code" />
		
	</label>
	<g:textField name="zipCode" value="${addressInstance?.zipCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'city', 'error')} ">
	<label for="city">
		<g:message code="address.city.label" default="City" />
		
	</label>
	<g:textField name="city" value="${addressInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="address.country.label" default="Country" />
		
	</label>
<%--	<g:select id="country" name="country.id" from="${grails.plugins.countries.Country.list()}" --%>
<%--    optionKey="id" value="${addressInstance?.country?.id}" class="many-to-one" --%>
<%--    noSelection="['null': '']"/>--%>
<country:select name="country.id" value="${addressInstance?.country?.id}" 
noSelection="['':'select please:']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'active', 'error')} ">
	<label for="active">
		<g:message code="address.active.label" default="Active" />
		
	</label>
	<g:checkBox name="active" value="${addressInstance?.active}" />
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="address.person.label" default="Person" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="person" name="person.id" from="${jaf.Person.list()}" optionKey="id" required="" value="${addressInstance?.person?.id}" class="many-to-one"/>
</div>

