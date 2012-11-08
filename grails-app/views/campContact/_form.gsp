<%@ page import="jaf.Contact" %>



<div class="fieldcontain ${hasErrors(bean: contactInstance, field: 'camp', 'error')} required">
    <label for="camp">
        <g:message code="contact.camp.label" default="Camp" />
    </label>
    <g:link controller="camp" action="show" id="${contactInstance?.camp?.id}">${contactInstance?.camp?.encodeAsHTML()}</g:link>
    <g:hiddenField name="camp.id" value="${contactInstance?.camp?.id}" />
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


