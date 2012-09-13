
<%@ page import="jaf.Person"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
    value="${message(code: 'person.label', default: 'Person')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
    <a href="#show-person" class="skip" tabindex="-1"><g:message
            code="default.link.skip.label"
            default="Skip to content&hellip;" /></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message
                        code="default.home.label" /></a></li>
            <li><g:link class="list" action="list">
                    <g:message code="default.list.label"
                        args="[entityName]" />
                </g:link></li>
            <li><g:link class="create" action="create">
                    <g:message code="default.new.label"
                        args="[entityName]" />
                </g:link></li>
        </ul>
    </div>
    <div id="show-person" class="content scaffold-show" role="main">
        <h1>
            <g:message code="default.show.label" args="[entityName]" />
        </h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">
                ${flash.message}
            </div>
        </g:if>
        <ol class="property-list person">

            <g:if test="${personInstance?.firstName}">
                <li class="fieldcontain"><span id="firstName-label"
                    class="property-label"><g:message
                            code="person.firstName.label"
                            default="First Name" /></span> <span
                    class="property-value"
                    aria-labelledby="firstName-label"><g:fieldValue
                            bean="${personInstance}" field="firstName" /></span>

                </li>
            </g:if>

            <g:if test="${personInstance?.lastName}">
                <li class="fieldcontain"><span id="lastName-label"
                    class="property-label"><g:message
                            code="person.lastName.label"
                            default="Last Name" /></span> <span
                    class="property-value"
                    aria-labelledby="lastName-label"><g:fieldValue
                            bean="${personInstance}" field="lastName" /></span>

                </li>
            </g:if>

            <g:if test="${personInstance?.gender}">
                <li class="fieldcontain"><span id="gender-label"
                    class="property-label"><g:message
                            code="person.gender.label" default="Gender" /></span>

                    <span class="property-value"
                    aria-labelledby="gender-label"><g:fieldValue
                            bean="${personInstance}" field="gender" /></span></li>
            </g:if>

            <g:if test="${personInstance?.birthDay}">
                <li class="fieldcontain"><span id="birthDay-label"
                    class="property-label"><g:message
                            code="person.birthDay.label"
                            default="Birth Day" /></span> <span
                    class="property-value"
                    aria-labelledby="birthDay-label"><g:formatDate
                            date="${personInstance?.birthDay}"
                            type="date" style="MEDIUM" /></span></li>
            </g:if>

            <g:if test="${personInstance?.deathDay}">
                <li class="fieldcontain"><span id="deathDay-label"
                    class="property-label"><g:message
                            code="person.deathDay.label"
                            default="Death Day" /></span> <span
                    class="property-value"
                    aria-labelledby="deathDay-label"><g:formatDate
                            date="${personInstance?.deathDay}"
                            type="date" style="MEDIUM" /></span></li>
            </g:if>

            <g:if test="${personInstance?.address}">
                <li class="fieldcontain"><span id="address-label"
                    class="property-label"><g:message
                            code="person.address.label"
                            default="Address" /></span> <%--<span class="property-value" aria-labelledby="address-label"><g:link controller="address" action="show" id="${personInstance?.address?.id}">${personInstance?.address?.encodeAsHTML()}</g:link></span>
					        --%> <g:each in="${personInstance.address}" var="c">
                        <span class="property-value"
                            aria-labelledby="address-label"><g:link
                                controller="address" action="show"
                                id="${c.id}">
                                ${c?.encodeAsHTML()}
                            </g:link></span>
                    </g:each></li>
            </g:if>

            <g:if test="${personInstance?.contacts}">
                <li class="fieldcontain"><span id="contacts-label"
                    class="property-label"><g:message
                            code="person.contacts.label"
                            default="Contacts" /></span> <g:each
                        in="${personInstance.contacts}" var="c">
                        <span class="property-value"
                            aria-labelledby="contacts-label"><g:link
                                controller="contact" action="show"
                                id="${c.id}">
                                ${c?.encodeAsHTML()}
                            </g:link></span>
                    </g:each></li>
            </g:if>

            <g:if test="${personInstance?.nationalities}">
                <li class="fieldcontain"><span
                    id="nationalities-label" class="property-label"><g:message
                            code="person.nationalities.label"
                            default="Nationalities" /></span> <g:each
                        in="${personInstance.nationalities}" var="n">
                        <span class="property-value"
                            aria-labelledby="nationalities-label"><g:link
                                controller="nationality" action="show"
                                id="${n.id}">
                                ${n?.encodeAsHTML()}
                            </g:link></span>
                    </g:each></li>
            </g:if>

            <g:if test="${personInstance?.languages}">
                <li class="fieldcontain"><span id="languages-label"
                    class="property-label"><g:message
                            code="person.languages.label"
                            default="Languages" /></span> <g:each
                        in="${personInstance.languages}" var="l">
                        <span class="property-value"
                            aria-labelledby="languages-label"><g:link
                                controller="languageLevel" action="show"
                                id="${l.id}">
                                ${l?.encodeAsHTML()}
                            </g:link></span>
                    </g:each></li>
            </g:if>

            <g:if test="${personInstance?.relationships}">
                <li class="fieldcontain"><span
                    id="relationships-label" class="property-label"><g:message
                            code="person.relationships.label"
                            default="Relationships" /></span> <g:each
                        in="${personInstance.relationships}" var="a">
                        <span class="property-value"
                            aria-labelledby="relationships-label"><g:link
                                controller="personRelation" action="show"
                                id="${a.id}">
                                ${a?.encodeAsHTML()}
                            </g:link></span>
                    </g:each></li>
            </g:if>

            <g:if test="${personInstance?.attendances}">
                <li class="fieldcontain"><span
                    id="attendances-label" class="property-label"><g:message
                            code="person.attendances.label"
                            default="Attendances" /></span> <g:each
                        in="${personInstance.attendances}" var="a">
                        <span class="property-value"
                            aria-labelledby="attendances-label"><g:link
                                controller="attendance" action="show"
                                id="${a.id}">
                                ${a?.encodeAsHTML()}
                            </g:link></span>
                    </g:each></li>
            </g:if>

            <g:if test="${personInstance?.appliedForNextYear}">
                <li class="fieldcontain"><span
                    id="appliedForNextYear-label" class="property-label"><g:message
                            code="person.appliedForNextYear.label"
                            default="Applied For Next Year" /></span> <span
                    class="property-value"
                    aria-labelledby="appliedForNextYear-label"><g:formatBoolean
                            boolean="${personInstance?.appliedForNextYear}" /></span>

                </li>
            </g:if>

            <g:if test="${personInstance?.newToLpc}">
                <li class="fieldcontain"><span id="newToLpc-label"
                    class="property-label"><g:message
                            code="person.newToLpc.label"
                            default="New To Lpc" /></span> <span
                    class="property-value"
                    aria-labelledby="newToLpc-label"><g:formatBoolean
                            boolean="${personInstance?.newToLpc}" /></span></li>
            </g:if>

            <g:if test="${personInstance?.preferences}">
                <li class="fieldcontain"><span
                    id="preferences-label" class="property-label"><g:message
                            code="person.preferences.label"
                            default="Preferences" /></span> <span
                    class="property-value"
                    aria-labelledby="preferences-label"><g:fieldValue
                            bean="${personInstance}" field="preferences" /></span>

                </li>
            </g:if>

            <g:if test="${personInstance?.status}">
                <li class="fieldcontain"><span id="status-label"
                    class="property-label"><g:message
                            code="person.status.label" default="Status" /></span>

                    <span class="property-value"
                    aria-labelledby="status-label"><g:fieldValue
                            bean="${personInstance}" field="status" /></span></li>
            </g:if>

            <g:if test="${personInstance?.dateCreated}">
                <li class="fieldcontain"><span
                    id="dateCreated-label" class="property-label"><g:message
                            code="person.dateCreated.label"
                            default="Date Created" /></span> <span
                    class="property-value"
                    aria-labelledby="dateCreated-label"><g:formatDate
                            date="${personInstance?.dateCreated}" /></span></li>
            </g:if>

            <g:if test="${personInstance?.lastUpdated}">
                <li class="fieldcontain"><span
                    id="lastUpdated-label" class="property-label"><g:message
                            code="person.lastUpdated.label"
                            default="Last Updated" /></span> <span
                    class="property-value"
                    aria-labelledby="lastUpdated-label"><g:formatDate
                            date="${personInstance?.lastUpdated}" /></span></li>
            </g:if>

            <%-- Involvements: hide this area to keep things simple, will be managed through Camp
				<g:if test="${personInstance?.involvements}">
				<li class="fieldcontain">
					<span id="involvements-label" class="property-label"><g:message code="person.involvements.label" default="Involvements" /></span>
					
						<g:each in="${personInstance.involvements}" var="i">
						<span class="property-value" aria-labelledby="involvements-label"><g:link controller="staff" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
						</g:each>

				</li>
				</g:if>
			    --%>
        </ol>
        <g:form>
            <fieldset class="buttons">
                <g:hiddenField name="id" value="${personInstance?.id}" />
                <g:link class="edit" action="edit"
                    id="${personInstance?.id}">
                    <g:message code="default.button.edit.label"
                        default="Edit" />
                </g:link>
                <g:actionSubmit class="delete" action="delete"
                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </fieldset>
        </g:form>
    </div>
</body>
</html>
