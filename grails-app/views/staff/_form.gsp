<%@ page import="jaf.Staff" %>



<%-- BEGIN MANUAL EDIT --%>
<div class="fieldcontain ${hasErrors(bean: staffInstance, field: 'camp', 'error')} required">
    <label for="camp">
        <g:message code="staff.camp.label" default="Camp" />
    </label>
    <g:link controller="camp" action="show" id="${staffInstance?.camp?.id}">${staffInstance?.camp?.encodeAsHTML()}</g:link>
    <g:hiddenField name="camp.id" value="${staffInstance?.camp?.id}" />
</div>

<div class="fieldcontain ${hasErrors(bean: staffInstance, field: 'other', 'error')} required">
    <label for="person">
        <g:message code="staff.person.label" default="Person" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="person_id_old" value="${staffInstance?.person?.id}" />
    <gui:autoComplete id="person"
        value="${staffInstance?.person?.toStringForSearch()}" 
        resultName="result" labelField="name" idField="id" 
        controller="person" action="autoCompleteJSON"
        minQueryLength='1' queryDelay='0.3'
        queryAppend="*" maxResultsDisplayed='20'
    />
</div>
<%-- END MANUAL EDIT --%>

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

