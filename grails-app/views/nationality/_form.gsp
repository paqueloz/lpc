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
		<span class="required-indicator">*</span>
	</label>
	<g:select id="person" name="person.id" from="${jaf.Person.list()}" optionKey="id" required="" value="${nationalityInstance?.person?.id}" class="many-to-one"/>
</div>

