
<%@ page import="jaf.Contact" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-contact" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
<%--				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--%>
			</ul>
		</div>
		<div id="show-contact" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list contact">
			
                <g:if test="${contactInstance?.camp}">
                <li class="fieldcontain">
                    <span id="camp-label" class="property-label"><g:message code="contact.camp.label" default="Camp" /></span>
                    
                        <span class="property-value" aria-labelledby="camp-label"><g:link controller="camp" action="show" id="${contactInstance?.camp?.id}">${contactInstance?.camp?.encodeAsHTML()}</g:link></span>
                    
                </li>
                </g:if>
            
                <g:if test="${contactInstance?.type}">
                <li class="fieldcontain">
                    <span id="type-label" class="property-label"><g:message code="contact.type.label" default="Type" /></span>
                    
                        <span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${contactInstance}" field="type"/></span>
                    
                </li>
                </g:if>
            
                <g:if test="${contactInstance?.mode}">
                <li class="fieldcontain">
                    <span id="mode-label" class="property-label"><g:message code="contact.mode.label" default="Mode" /></span>
                    
                        <span class="property-value" aria-labelledby="mode-label"><g:fieldValue bean="${contactInstance}" field="mode"/></span>
                    
                </li>
                </g:if>
            
				<g:if test="${contactInstance?.value}">
				<li class="fieldcontain">
					<span id="value-label" class="property-label"><g:message code="contact.value.label" default="Value" /></span>
					
						<span class="property-value" aria-labelledby="value-label"><g:fieldValue bean="${contactInstance}" field="value"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${contactInstance?.active}">
				<li class="fieldcontain">
					<span id="active-label" class="property-label"><g:message code="contact.active.label" default="Active" /></span>
					
						<span class="property-value" aria-labelledby="active-label"><g:formatBoolean boolean="${contactInstance?.active}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${contactInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="contact.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${contactInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${contactInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="contact.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${contactInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			

    			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${contactInstance?.id}" />
					<g:link class="edit" action="edit" id="${contactInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:link class="delete" action="delete" id="${contactInstance?.id}" 
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                            <g:message code="default.button.delete.label" default="Delete" />
                    </g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
