
<%@ page import="jaf.Camp" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'camp.label', default: 'Camp')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-camp" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-camp" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list camp">
			
				<g:if test="${campInstance?.location}">
				<li class="fieldcontain">
					<span id="location-label" class="property-label"><g:message code="camp.location.label" default="Location" /></span>
					
						<span class="property-value" aria-labelledby="location-label"><g:fieldValue bean="${campInstance}" field="location"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.street1}">
				<li class="fieldcontain">
					<span id="street1-label" class="property-label"><g:message code="camp.street1.label" default="Street1" /></span>
					
						<span class="property-value" aria-labelledby="street1-label"><g:fieldValue bean="${campInstance}" field="street1"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.street2}">
				<li class="fieldcontain">
					<span id="street2-label" class="property-label"><g:message code="camp.street2.label" default="Street2" /></span>
					
						<span class="property-value" aria-labelledby="street2-label"><g:fieldValue bean="${campInstance}" field="street2"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.zipCode}">
				<li class="fieldcontain">
					<span id="zipCode-label" class="property-label"><g:message code="camp.zipCode.label" default="Zip Code" /></span>
					
						<span class="property-value" aria-labelledby="zipCode-label"><g:fieldValue bean="${campInstance}" field="zipCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.city}">
				<li class="fieldcontain">
					<span id="city-label" class="property-label"><g:message code="camp.city.label" default="City" /></span>
					
						<span class="property-value" aria-labelledby="city-label"><g:fieldValue bean="${campInstance}" field="city"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.country}">
				<li class="fieldcontain">
					<span id="country-label" class="property-label"><g:message code="camp.country.label" default="Country" /></span>
					
						<span class="property-value" aria-labelledby="country-label">
                        <%--
                        <g:link controller="country" action="show" id="${campInstance?.country?.id}">
                        ${campInstance?.country?.encodeAsHTML()}
                        $</g:link>
                        --%>
                        <country:name object="${campInstance?.country}"/>
                        </span>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.contact}">
				<li class="fieldcontain">
					<span id="contact-label" class="property-label"><g:message code="camp.contact.label" default="Contact" /></span>
					
						<span class="property-value" aria-labelledby="contact-label"><g:link controller="contact" action="show" id="${campInstance?.contact?.id}">${campInstance?.contact?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.staff}">
				<li class="fieldcontain">
					<span id="staff-label" class="property-label"><g:message code="camp.staff.label" default="Staff" /></span>
					
						<g:each in="${campInstance.staff}" var="s">
						<span class="property-value" aria-labelledby="staff-label"><g:link controller="staff" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${campInstance?.years}">
				<li class="fieldcontain">
					<span id="years-label" class="property-label"><g:message code="camp.years.label" default="Years" /></span>
					
						<g:each in="${campInstance.years}" var="y">
						<span class="property-value" aria-labelledby="years-label"><g:link controller="campYear" action="show" id="${y.id}">${y?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${campInstance?.id}" />
					<g:link class="edit" action="edit" id="${campInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
