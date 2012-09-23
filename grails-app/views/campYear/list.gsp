
<%@ page import="jaf.CampYear" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'campYear.label', default: 'CampYear')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-campYear" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-campYear" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="campYear.camp.label" default="Camp" /></th>
					
						<g:sortableColumn property="year" title="${message(code: 'campYear.year.label', default: 'Year')}" />
					
						<g:sortableColumn property="beginDate" title="${message(code: 'campYear.beginDate.label', default: 'Begin Date')}" />
					
						<g:sortableColumn property="endDate" title="${message(code: 'campYear.endDate.label', default: 'End Date')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${campYearInstanceList}" status="i" var="campYearInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${campYearInstance.id}">${fieldValue(bean: campYearInstance, field: "camp")}</g:link></td>
					
						<td>${fieldValue(bean: campYearInstance, field: "year")}</td>
					
						<td><g:formatDate date="${campYearInstance.beginDate}" /></td>
					
						<td><g:formatDate date="${campYearInstance.endDate}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${campYearInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
