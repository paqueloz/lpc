%{--
  - This program is intended to help the Luethi-Peterson Camps association
  -     to help them store and manage their users
  -
  -     Copyright (C) 2012 Jacques Fontignie
  -
  -     This program is free software: you can redistribute it and/or modify
  -     it under the terms of the GNU General Public License as published by
  -     the Free Software Foundation, either version 3 of the License, or
  -     (at your option) any later version.
  -
  -     This program is distributed in the hope that it will be useful,
  -     but WITHOUT ANY WARRANTY; without even the implied warranty of
  -     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  -     GNU General Public License for more details.
  -
  -     You should have received a copy of the GNU General Public License
  -     along with this program.  If not, see <http://www.gnu.org/licenses/>.
  --}%
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Grails</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}
            
			#status li {
				line-height: 1.3;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 18em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				line-height: 1.5;
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				line-height: 1.3;
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <%--
		<div id="status" role="complementary">
			<h1>Application Status</h1>
			<ul>
				<li>App version: <g:meta name="app.version"/></li>
				<li>Grails version: <g:meta name="app.grails.version"/></li>
				<li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
				<li>JVM version: ${System.getProperty('java.version')}</li>
				<li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
				<li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
				<li>Domains: ${grailsApplication.domainClasses.size()}</li>
				<li>Services: ${grailsApplication.serviceClasses.size()}</li>
				<li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
			</ul>
			<h1>Installed Plugins</h1>
			<ul>
				<g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
					<li>${plugin.name} - ${plugin.version}</li>
				</g:each>
			</ul>
		</div>
		--%>
        <div id="page-body" role="main"><%--
			<h1>Welcome to Grails</h1>
			<p>Welcome to the admin site of Luethi-Peterson Camps</p>

			--%><div id="controller-list" role="navigation">
				<h2>Actions:</h2><%--
				<ul>
					<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
						<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
					</g:each>
				</ul>
            --%>
              <ul>
                <li class="controller"><g:link controller="person"><g:message code="general.manage.people" default="Manage people" /></g:link></li>
                <li class="controller"><g:link controller="camp"><g:message code="general.manage.camps" default="Manage camps" /></g:link></li>
                <li class="controller"><g:link controller="search"><g:message code="general.search" default="Search" /></g:link></li>
                <li class="controller"><g:link controller="report" action="index"><g:message code="general.reports" default="Create reports" /></g:link></li>
              </ul>
              
              <sec:ifAnyGranted roles="ROLE_ADMIN">
                  <h2>Actions (admin):</h2>
                  <ul>
                    <g:if env="development">
                        <li class="controller"><g:link controller="testData">Generate test data</g:link></li>
                    </g:if>
                    <li class="controller"><g:link controller="secUser">Manage users</g:link></li>
                  </ul>
               </sec:ifAnyGranted>
            </div>
		</div>
	</body>
</html>
