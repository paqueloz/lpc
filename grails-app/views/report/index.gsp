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

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main"/>
    <title>LPC - <g:message code="general.reports" default="Create reports"/></title>
    <script type="text/javascript">
    </script>
        <style type="text/css" media="screen">
            #page-body {
                margin: 2em 1em 1.25em 18em;
            }
        </style>
  </head>
  <body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
             </ul>
        </div>
  
  <div id="header">
  </div>
  <div id="page-body" role="main">
  <ul>
  <li><g:link action="younger"><g:message code="report.younger" default="Younger"/></g:link></li>
  <li><g:link action="fourteen"><g:message code="report.fourteen" default="14th year old"/></g:link></li>
  <li><g:link action="older"><g:message code="report.older" default="Older"/></g:link></li>
  <li><g:link action="counselor"><g:message code="report.counselor" default="Counselor"/></g:link></li>
    <sec:ifAllGranted roles="ROLE_EXPORT">
      <li><g:link action="appliedForNextYear" params="[format:'excel',ext:'xls']">
        <g:message code="report.applied.for.next.year" default="Labels for people who applied for next year"/>
      </g:link></li>
      <li><g:link action="notSelectedAtCC" params="[format:'excel',ext:'xls']">
        <g:message code="report.not.selected.at.cc" default="Not selected at CC"/>
      </g:link></li>
    </sec:ifAllGranted>
  
  </ul>
  </div>
  </body>
</html>
