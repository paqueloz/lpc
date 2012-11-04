<%@ page import="jaf.Staff" %>



<div class="fieldcontain ${hasErrors(bean: staffInstance, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="staff.person.label" default="Person" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="person" name="person.id" from="${jaf.Person.list()}" optionKey="id" required="" value="${staffInstance?.person?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: staffInstance, field: 'camp', 'error')} required">
	<label for="camp">
		<g:message code="staff.camp.label" default="Camp" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="camp" name="camp.id" from="${jaf.Camp.list()}" optionKey="id" required="" value="${staffInstance?.camp?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: staffInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="staff.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${staffInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: staffInstance, field: 'startDate', 'error')} ">
	<label for="startDate">
		<g:message code="staff.startDate.label" default="Start Date" />
		
	</label>
	<g:datePicker name="startDate" precision="day"  value="${staffInstance?.startDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: staffInstance, field: 'endDate', 'error')} ">
	<label for="endDate">
		<g:message code="staff.endDate.label" default="End Date" />
		
	</label>
	<g:datePicker name="endDate" precision="day"  value="${staffInstance?.endDate}" default="none" noSelection="['': '']" />
</div>

