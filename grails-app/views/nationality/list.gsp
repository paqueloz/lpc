
<%@ page import="jaf.Nationality" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'nationality.label', default: 'Nationality')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-nationality" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
<%--				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="list-nationality" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="nationality.country.label" default="Country" /></th>
					
						<th><g:message code="nationality.person.label" default="Person" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${nationalityInstanceList}" status="i" var="nationalityInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>
<%--                        <g:link action="show" id="${nationalityInstance.id}">${fieldValue(bean: nationalityInstance, field: "country")}</g:link>--%>
<country:name object="${nationalityInstance?.country}"/>
                        </td>
					
						<td>${fieldValue(bean: nationalityInstance, field: "person")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${nationalityInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
