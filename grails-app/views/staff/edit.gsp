<%@ page import="jaf.Staff" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'staff.label', default: 'Staff')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
        <%-- BEGIN MANUAL EDIT --%>
        <gui:resources components="['toolTip','autoComplete']"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"  type="text/css"/>
        <%-- END MANUAL EDIT --%>
	</head>
	<body>
		<a href="#edit-staff" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <%-- BEGIN MANUAL EDIT --%>
                <%-- 
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                --%>
                <%-- END MANUAL EDIT --%>
			</ul>
		</div>
		<div id="edit-staff" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${staffInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${staffInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" action="update" >
				<g:hiddenField name="id" value="${staffInstance?.id}" />
				<g:hiddenField name="version" value="${staffInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
                    <g:submitButton name="update" class="save" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                    <g:link class="delete" action="delete" id="${staffInstance?.id}" 
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                            <g:message code="default.button.delete.label" default="Delete" />
                    </g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
