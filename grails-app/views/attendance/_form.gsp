<%@ page import="jaf.Attendance"%>



<div
    class="fieldcontain ${hasErrors(bean: attendanceInstance, field: 'person', 'error')} required">
    <label for="person"> <g:message
            code="attendance.person.label" default="Person" />
    </label> <span class="property-value" aria-labelledby="person-label"><g:link
            controller="person" action="show"
            id="${attendanceInstance?.person?.id}">
            ${attendanceInstance?.person?.encodeAsHTML()}
        </g:link></span>
</div>

<div
    class="fieldcontain ${hasErrors(bean: attendanceInstance, field: 'camp', 'error')} required">
    <label for="camp"> <g:message code="attendance.camp.label"
            default="Camp" />
    </label> <span class="property-value" aria-labelledby="camp-label"><g:link
            controller="campYear" action="show"
            id="${attendanceInstance?.camp?.id}">
            ${attendanceInstance?.camp?.encodeAsHTML()}
        </g:link></span>
</div>

<div
    class="fieldcontain ${hasErrors(bean: attendanceInstance, field: 'status', 'error')} required">
    <label for="status"> <g:message
            code="attendance.status.label" default="Status" /> <span
        class="required-indicator">*</span>
    </label>
    <g:select name="status" from="${jaf.PersonStatus?.values()}"
        keys="${jaf.PersonStatus.values()*.name()}" required=""
        value="${attendanceInstance?.status?.name()}" />
</div>

