
<%@ page import="jaf.LanguageLevel" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'languageLevel.label', default: 'LanguageLevel')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-languageLevel" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
<%--				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="show-languageLevel" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list languageLevel">
			
				<g:if test="${languageLevelInstance?.language}">
				<li class="fieldcontain">
					<span id="language-label" class="property-label"><g:message code="languageLevel.language.label" default="Language" /></span>
					
						<span class="property-value" aria-labelledby="language-label"><g:fieldValue bean="${languageLevelInstance}" field="language"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${languageLevelInstance?.level}">
				<li class="fieldcontain">
					<span id="level-label" class="property-label"><g:message code="languageLevel.level.label" default="Level" /></span>
					
						<span class="property-value" aria-labelledby="level-label"><g:fieldValue bean="${languageLevelInstance}" field="level"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${languageLevelInstance?.person}">
				<li class="fieldcontain">
					<span id="person-label" class="property-label"><g:message code="languageLevel.person.label" default="Person" /></span>
					
						<span class="property-value" aria-labelledby="person-label"><g:link controller="person" action="show" id="${languageLevelInstance?.person?.id}">${languageLevelInstance?.person?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${languageLevelInstance?.id}" />
					<g:link class="edit" action="edit" id="${languageLevelInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
