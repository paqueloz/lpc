<%@ page import="jaf.Nationality" %>



<div class="fieldcontain ${hasErrors(bean: nationalityInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="nationality.country.label" default="Country" />
		
	</label>
	<g:textField name="country" value="${nationalityInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nationalityInstance, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="nationality.person.label" default="Person" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="person" name="person.id" from="${jaf.Person.list()}" optionKey="id" required="" value="${nationalityInstance?.person?.id}" class="many-to-one"/>
</div>

