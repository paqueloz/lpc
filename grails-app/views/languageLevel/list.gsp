
<%@ page import="jaf.LanguageLevel" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'languageLevel.label', default: 'LanguageLevel')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-languageLevel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
<%--				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="list-languageLevel" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="language" title="${message(code: 'languageLevel.language.label', default: 'Language')}" />
					
						<g:sortableColumn property="level" title="${message(code: 'languageLevel.level.label', default: 'Level')}" />
					
						<th><g:message code="languageLevel.person.label" default="Person" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${languageLevelInstanceList}" status="i" var="languageLevelInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${languageLevelInstance.id}">${fieldValue(bean: languageLevelInstance, field: "language")}</g:link></td>
					
						<td>${fieldValue(bean: languageLevelInstance, field: "level")}</td>
					
						<td>${fieldValue(bean: languageLevelInstance, field: "person")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${languageLevelInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
