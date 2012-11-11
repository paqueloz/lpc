
<%@ page import="jaf.Person"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
    value="${message(code: 'person.label', default: 'Person')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<gui:resources components="['toolTip','autoComplete']" />
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"  type="text/css"/>
</head>
<body>
    <a href="#list-person" class="skip" tabindex="-1"><g:message
            code="default.link.skip.label"
            default="Skip to content&hellip;" /></a>
    <g:form action="show">
        <table>
            <tr>
                <%--<td>${message(code: 'search.quick.access', default: 'Quick access')}</td><div style="width: 300px">
                --%>
                <td style="text-align: right"><g:submitButton
                        name="show" class="show"
                        value="${message(code: 'search.quick.access', default: 'Quick access')}" /></td>
                <td><gui:autoComplete id="person"
                        value="${personRelationInstance?.other?.toStringForSearch()}"
                        resultName="result" labelField="name"
                        idField="id" controller="person"
                        action="autoCompleteJSON" minQueryLength='1'
                        queryDelay='0.3' queryAppend="*"
                        maxResultsDisplayed='20' /></td>
            </tr>
        </table>
    </g:form>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message
                        code="default.home.label" /></a></li>
            <li><g:link class="create" action="create">
                    <g:message code="default.new.label"
                        args="[entityName]" />
                </g:link></li>
        </ul>

    </div>

    <div id="list-person" class="content scaffold-list" role="main">
        <h1>
            <g:message code="default.list.label" args="[entityName]" />
        </h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">
                ${flash.message}
            </div>
        </g:if>
        <table>
            <thead>
                <tr>

                    <g:sortableColumn property="firstName"
                        title="${message(code: 'person.firstName.label', default: 'First Name')}" />

                    <g:sortableColumn property="lastName"
                        title="${message(code: 'person.lastName.label', default: 'Last Name')}" />

                    <g:sortableColumn property="gender"
                        title="${message(code: 'person.gender.label', default: 'Gender')}" />

                    <g:sortableColumn property="birthDay"
                        title="${message(code: 'person.birthDay.label', default: 'Birth Day')}" />

                    <%-- <g:sortableColumn property="deathDay" title="${message(code: 'person.deathDay.label', default: 'Death Day')}" /> --%>

                    <th><g:message code="person.address.label"
                            default="Address" /></th>

                </tr>
            </thead>
            <tbody>
                <g:each in="${personInstanceList}" status="i"
                    var="personInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td><g:link action="show"
                                id="${personInstance.id}">
                                ${fieldValue(bean: personInstance, field: "firstName")}
                            </g:link></td>

                        <td>
                            ${fieldValue(bean: personInstance, field: "lastName")}
                        </td>

                        <td>
                            ${fieldValue(bean: personInstance, field: "gender")}
                        </td>

                        <td><g:formatDate
                                date="${personInstance.birthDay}"
                                type="date" style="MEDIUM" /></td>

                        <%-- <td><g:formatDate date="${personInstance.deathDay}" type="date" style="MEDIUM" /></td> --%>

                        <td>
                            ${fieldValue(bean: personInstance, field: "address")}
                        </td>

                    </tr>
                </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${personInstanceTotal}" />
        </div>
    </div>
</body>
</html>
