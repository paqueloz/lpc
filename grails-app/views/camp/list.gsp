
<%@ page import="jaf.Camp" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'camp.label', default: 'Camp')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
        <gui:resources components="['toolTip','autoComplete']" />
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"  type="text/css"/>
	</head>
	<body>
		<a href="#list-camp" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

        <g:form action="show">
        <table>
            <tr>
                <%--<td>${message(code: 'search.quick.access', default: 'Quick access')}</td><div style="width: 300px">
                --%>
                <td style="text-align: right"><g:submitButton
                        name="show" class="show"
                        value="${message(code: 'search.quick.access', default: 'Quick access')}" /></td>
                <td><gui:autoComplete id="camp"
                        value="${personRelationInstance?.other?.toStringForSearch()}"
                        resultName="result" labelField="name"
                        idField="id" controller="camp"
                        action="autoCompleteJSON" minQueryLength='1'
                        queryDelay='0.3' queryAppend="*"
                        maxResultsDisplayed='20' /></td>
            </tr>
        </table>
        </g:form>

        <div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-camp" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="location" title="${message(code: 'camp.location.label', default: 'Location')}" />
					
						<g:sortableColumn property="street1" title="${message(code: 'camp.street1.label', default: 'Street1')}" />
					
						<g:sortableColumn property="street2" title="${message(code: 'camp.street2.label', default: 'Street2')}" />
					
						<g:sortableColumn property="zipCode" title="${message(code: 'camp.zipCode.label', default: 'Zip Code')}" />
					
						<g:sortableColumn property="city" title="${message(code: 'camp.city.label', default: 'City')}" />
					
						<th><g:message code="camp.country.label" default="Country" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${campInstanceList}" status="i" var="campInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${campInstance.id}">${fieldValue(bean: campInstance, field: "location")}</g:link></td>
					
						<td>${fieldValue(bean: campInstance, field: "street1")}</td>
					
						<td>${fieldValue(bean: campInstance, field: "street2")}</td>
					
						<td>${fieldValue(bean: campInstance, field: "zipCode")}</td>
					
						<td>${fieldValue(bean: campInstance, field: "city")}</td>
					
						<td>
                        <%--${fieldValue(bean: campInstance, field: "country")}
                        --%>
                        <country:name object="${campInstance?.country}"/>
                        </td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${campInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
