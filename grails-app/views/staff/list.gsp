
<%@ page import="jaf.Staff" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'staff.label', default: 'Staff')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-staff" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <%-- BEGIN MANUAL EDIT --%>
                <%-- 
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                --%>
                <%-- BEGIN MANUAL EDIT --%>
			</ul>
		</div>
		<div id="list-staff" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="staff.person.label" default="Person" /></th>
					
						<th><g:message code="staff.camp.label" default="Camp" /></th>
					
						<g:sortableColumn property="comment" title="${message(code: 'staff.comment.label', default: 'Comment')}" />
					
						<g:sortableColumn property="startDate" title="${message(code: 'staff.startDate.label', default: 'Start Date')}" />
					
						<g:sortableColumn property="endDate" title="${message(code: 'staff.endDate.label', default: 'End Date')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${staffInstanceList}" status="i" var="staffInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${staffInstance.id}">${fieldValue(bean: staffInstance, field: "person")}</g:link></td>
					
						<td>${fieldValue(bean: staffInstance, field: "camp")}</td>
					
						<td>${fieldValue(bean: staffInstance, field: "comment")}</td>
					
						<td><g:formatDate date="${staffInstance.startDate}" /></td>
					
						<td><g:formatDate date="${staffInstance.endDate}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${staffInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
