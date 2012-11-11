<%@ page import="jaf.PersonRelation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'personRelation.label', default: 'PersonRelation')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <gui:resources components="['toolTip','autoComplete']"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"  type="text/css"/>
	</head>
	<body>
		<a href="#create-personRelation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li>
                    <%--<gui:toolTip text="Retour!">
                        --%><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
                    <%--</gui:toolTip>
                --%></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
        <%-- BEGIN MANUAL EDIT --%>
        <script type="text/javascript">
        function validateOther(msg) {
            var v = document.getElementById("other_id");
            var t = document.getElementById("other");
            if (v.value=="" && t.value!="") {
                return confirm(msg);
            }
        }
        </script>
        <%-- END MANUAL EDIT --%>
		<div id="create-personRelation" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${personRelationInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${personRelationInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
                    <%-- BEGIN MANUAL EDIT --%>
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" 
                        onclick="return validateOther('${message(code: 'personRelation.create.confirm.message', 
                            default: 'The person is not in the database, OK to create a new record?')}');"/>
                    <%-- END MANUAL EDIT --%>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
