
<%@ page import="jaf.CampYear" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'campYear.label', default: 'CampYear')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <gui:resources components="['toolTip','autoComplete']" />
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"  type="text/css"/>
	</head>
	<body>
		<a href="#show-campYear" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-campYear" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list campYear">
			
				<g:if test="${campYearInstance?.camp}">
				<li class="fieldcontain">
					<span id="camp-label" class="property-label"><g:message code="campYear.camp.label" default="Camp" /></span>
					
						<span class="property-value" aria-labelledby="camp-label"><g:link controller="camp" action="show" id="${campYearInstance?.camp?.id}">${campYearInstance?.camp?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${campYearInstance?.year}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="campYear.year.label" default="Year" /></span>
					
						<span class="property-value" aria-labelledby="year-label"><g:fieldValue bean="${campYearInstance}" field="year"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${campYearInstance?.beginDate}">
				<li class="fieldcontain">
					<span id="beginDate-label" class="property-label"><g:message code="campYear.beginDate.label" default="Begin Date" /></span>
                    <%--BEGIN MANUAL EDIT --%>
                    <span class="property-value" aria-labelledby="beginDate-label">
                        <g:formatDate date="${campYearInstance?.beginDate}" type="date" style="MEDIUM" />
                    </span>
                    <%--END MANUAL EDIT --%>
				</li>
				</g:if>
			
				<g:if test="${campYearInstance?.endDate}">
				<li class="fieldcontain">
					<span id="endDate-label" class="property-label"><g:message code="campYear.endDate.label" default="End Date" /></span>
                    <%--BEGIN MANUAL EDIT --%>
                    <span class="property-value" aria-labelledby="endDate-label">
                        <g:formatDate date="${campYearInstance?.endDate}" type="date" style="MEDIUM" />
                    </span>
                    <%--END MANUAL EDIT --%>
				</li>
				</g:if>
			
				<g:if test="${true || campYearInstance?.attendances}">
				<li class="fieldcontain">
					<span id="attendances-label" class="property-label"><g:message code="campYear.attendances.label" default="Attendances" /></span>
					
						<g:each in="${campYearInstance.attendances}" var="a">
						<span class="property-value" aria-labelledby="attendances-label"><g:link controller="attendance" action="show" id="${a.id}">
                            ${a?.nameAndStatus().encodeAsHTML()}
                        </g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
            
    <g:form controller="attendance" action="save">
        <g:hiddenField name="camp.id" value="${campYearInstance?.id}" />
        <table>
            <tr>
                <%--<td>${message(code: 'search.quick.access', default: 'Quick access')}</td><div style="width: 300px">
                --%>
                <td style="text-align: right"><g:submitButton
                        name="show" class="show"
                        value="${message(code: 'default.add.label', args: [message(code: 'attendance.label', default: 'Attendance')])}" /></td>
                <td><gui:autoComplete id="person"
                        value="${personRelationInstance?.other?.toStringForSearch()}"
                        resultName="result" labelField="name"
                        idField="id" controller="person"
                        action="autoCompleteJSON" minQueryLength='1'
                        queryDelay='0.3' queryAppend="*"
                        maxResultsDisplayed='20' /></td>
                <td><g:select name="status" from="${jaf.PersonStatus?.values()}" keys="${jaf.PersonStatus.values()*.name()}" required="" value="CAmper"/>
                </td>
            </tr>
        </table>
    </g:form>
 
    			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${campYearInstance?.id}" />
					<g:link class="edit" action="edit" id="${campYearInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:link class="delete" action="delete" id="${campYearInstance?.id}" 
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                            <g:message code="default.button.delete.label" default="Delete" />
                    </g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
