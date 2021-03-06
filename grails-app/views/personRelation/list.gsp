
<%@ page import="jaf.PersonRelation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'personRelation.label', default: 'PersonRelation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-personRelation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <%--
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			    --%>
            </ul>
		</div>
		<div id="list-personRelation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="personRelation.person.label" default="Person" /></th>
					
						<g:sortableColumn property="relationship" title="${message(code: 'personRelation.relationship.label', default: 'Relationship')}" />
					
						<th><g:message code="personRelation.other.label" default="Other" /></th>
					
						<g:sortableColumn property="comment" title="${message(code: 'personRelation.comment.label', default: 'Comment')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${personRelationInstanceList}" status="i" var="personRelationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: personRelationInstance, field: "person")}</td>
					
						<td><g:link action="show" id="${personRelationInstance.id}">${fieldValue(bean: personRelationInstance, field: "relationship")}</g:link></td>
					
						<td>${fieldValue(bean: personRelationInstance, field: "other")}</td>
					
						<td>${fieldValue(bean: personRelationInstance, field: "comment")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${personRelationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
