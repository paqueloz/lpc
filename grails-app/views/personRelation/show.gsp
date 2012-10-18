
<%@ page import="jaf.PersonRelation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'personRelation.label', default: 'PersonRelation')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-personRelation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<%--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
                </ul>
		</div>
		<div id="show-personRelation" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list personRelation">
			
				<g:if test="${personRelationInstance?.person}">
				<li class="fieldcontain">
					<span id="person-label" class="property-label"><g:message code="personRelation.person.label" default="Person" /></span>
					
						<span class="property-value" aria-labelledby="person-label"><g:link controller="person" action="show" id="${personRelationInstance?.person?.id}">${personRelationInstance?.person?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${personRelationInstance?.relationship}">
				<li class="fieldcontain">
					<span id="relationship-label" class="property-label"><g:message code="personRelation.relationship.label" default="Relationship" /></span>
					
						<span class="property-value" aria-labelledby="relationship-label"><g:fieldValue bean="${personRelationInstance}" field="relationship"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${personRelationInstance?.other}">
				<li class="fieldcontain">
					<span id="other-label" class="property-label"><g:message code="personRelation.other.label" default="Other" /></span>
					
						<span class="property-value" aria-labelledby="other-label"><g:link controller="person" action="show" id="${personRelationInstance?.other?.id}">${personRelationInstance?.other?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${personRelationInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="personRelation.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${personRelationInstance}" field="comment"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${personRelationInstance?.id}" />
					<g:link class="edit" action="edit" id="${personRelationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:link class="delete" action="delete" id="${personRelationInstance?.id}" 
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                            <g:message code="default.button.delete.label" default="Delete" />
                    </g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
