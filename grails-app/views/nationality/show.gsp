
<%@ page import="jaf.Nationality" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'nationality.label', default: 'Nationality')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-nationality" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
<%--				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="show-nationality" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list nationality">
			
				<g:if test="${nationalityInstance?.country}">
				<li class="fieldcontain">
					<span id="country-label" class="property-label"><g:message code="nationality.country.label" default="Country" /></span>
					
					<span class="property-value" aria-labelledby="country-label">
<%--					<g:link controller="country" action="show" id="${nationalityInstance?.country?.id}">${nationalityInstance?.country?.encodeAsHTML()}</g:link>--%>
<country:name object="${nationalityInstance?.country}"/>
					</span>
					
				</li>
				</g:if>
			
				<g:if test="${nationalityInstance?.person}">
				<li class="fieldcontain">
					<span id="person-label" class="property-label"><g:message code="nationality.person.label" default="Person" /></span>
					
						<span class="property-value" aria-labelledby="person-label"><g:link controller="person" action="show" id="${nationalityInstance?.person?.id}">${nationalityInstance?.person?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${nationalityInstance?.id}" />
					<g:link class="edit" action="edit" id="${nationalityInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:link class="delete" action="delete" id="${nationalityInstance?.id}" 
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                            <g:message code="default.button.delete.label" default="Delete" />
                    </g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
