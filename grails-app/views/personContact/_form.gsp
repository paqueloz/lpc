<%@ page import="jaf.Contact" %>



<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'person', 'error')} required">
    <label for="person">
        <g:message code="contact.person.label" default="Person" />
    </label>
    <g:link controller="person" action="show" id="${contactInstance?.person?.id}">${contactInstance?.person?.encodeAsHTML()}</g:link>
    <g:hiddenField name="person.id" value="${contactInstance?.person?.id}" />
</div>

<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'type', 'error')} required">
    <label for="type">
        <g:message code="contact.type.label" default="Type" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="type" from="${jaf.ContactType?.values()}" keys="${jaf.ContactType.values()*.name()}" required="" value="${contactInstance?.type?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'mode', 'error')} required">
    <label for="mode">
        <g:message code="contact.mode.label" default="Mode" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="mode" from="${jaf.ContactMode?.values()}" keys="${jaf.ContactMode.values()*.name()}" required="" value="${contactInstance?.mode?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'value', 'error')} required">
	<label for="value">
		<g:message code="contact.value.label" default="Value" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="value" required="" value="${contactInstance?.value}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'active', 'error')} ">
	<label for="active">
		<g:message code="contact.active.label" default="Active" />
		
	</label>
    <g:checkBox name="active" value="${(contactInstance?.id)?(contactInstance?.active):true}" />
</div>


