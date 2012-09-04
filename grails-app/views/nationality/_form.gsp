<%@ page import="jaf.Nationality" %>



<div class="fieldcontain ${hasErrors(bean: nationalityInstance, field: 'country', 'error')} required">
	<label for="country">
		<g:message code="nationality.country.label" default="Country" />
		<span class="required-indicator">*</span>
	</label>
<%--	<g:select id="country" name="country.id" from="${grails.plugins.countries.Country.list()}" optionKey="id" required="" value="${nationalityInstance?.country?.id}" class="many-to-one"/>--%>
<country:select name="country.id" value="${nationalityInstance?.country?.id}" 
noSelection="['':'select please:']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nationalityInstance, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="nationality.person.label" default="Person" />
	</label>
    <g:link controller="person" action="show" id="${nationalityInstance?.person?.id}">${nationalityInstance?.person?.encodeAsHTML()}</g:link>
    <g:hiddenField name="person.id" value="${nationalityInstance?.person?.id}" />
</div>

