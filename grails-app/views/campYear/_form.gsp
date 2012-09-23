<%@ page import="jaf.CampYear" %>



<div class="fieldcontain ${hasErrors(bean: campYearInstance, field: 'camp', 'error')} required">
	<label for="camp">
		<g:message code="campYear.camp.label" default="Camp" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="camp" name="camp.id" from="${jaf.Camp.list()}" optionKey="id" required="" value="${campYearInstance?.camp?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campYearInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="campYear.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" min="1900" required="" value="${campYearInstance.year}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campYearInstance, field: 'beginDate', 'error')} ">
	<label for="beginDate">
		<g:message code="campYear.beginDate.label" default="Begin Date" />
		
	</label>
	<g:datePicker name="beginDate" precision="day"  value="${campYearInstance?.beginDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: campYearInstance, field: 'endDate', 'error')} ">
	<label for="endDate">
		<g:message code="campYear.endDate.label" default="End Date" />
		
	</label>
	<g:datePicker name="endDate" precision="day"  value="${campYearInstance?.endDate}" default="none" noSelection="['': '']" />
</div>


<%-- Attendances: hide this area until campYear is in the database --%>
<g:if test="${campYearInstance?.id && false}">
    <div
        class="fieldcontain ${hasErrors(bean: campYearInstance, field: 'attendances', 'error')} ">
        <label for="attendances"> <g:message
                code="campYear.attendances.label" default="Attendances" />

        </label>
        <ul class="one-to-many">
            <g:each in="${campYearInstance?.attendances?}" var="a">
                <li><g:link controller="attendance" action="show"
                        id="${a.id}">
                        ${a?.encodeAsHTML()}
                    </g:link></li>
            </g:each>
            <li class="add"><g:link controller="attendance"
                    action="create"
                    params="['campYear.id': campYearInstance?.id]">
                    ${message(code: 'default.add.label', args: [message(code: 'attendance.label', default: 'Attendance')])}
                </g:link></li>
        </ul>
</g:if>

</div>

