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
<title>Report for counselors</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'reportB.css')}" type="text/css">
</head>
<body>

<div id="title">
        <i><b>COUNSELOR APPLICATIONS</b> (sorted by experience, gender, age, nationality)</i>
    </div>
    <div class="stat">
        Number of records:
        ${result?.size()}
    </div>
    <p>
    <hr class="hr2">
    <table class="tableCamper" width="97%">
        <tr>
            <td class="hdr" width="15%">Sex&nbsp;&nbsp;Given name</td>
            <td class="hdr" width="15%">Family name</td>
            <td class="hdr" width="10%">Experience</td>
            <td class="hdr" width="6%">Age</td>
            <td class="hdr" width="9%">Nationality</td>
            <td class="hdr">Languages</td>
            <td class="hdr">Preferences</td>
        </tr>
    </table>
    <hr class="hr1" />
    <p>
    <g:set var="lastGender" value="" />
    <g:each in="${result}" status="i" var="camper">
        <div class="divCamper">
            <p>
            <table class="tableCamper" width="97%">
                <g:if test="${camper.gender!=lastGender}">
                <tr>
                    <td class="hdr">
                    ${camper.gender} 
                    </td>
                </tr>
                <g:set var="lastGender" value="${camper.gender}" />
                </g:if>
            </table>
            <table class="tableCamper" width="97%">
                <tr>
                    <td class="cell" width="15%">
                        ${camper.firstName}
                    </td>
                    <td class="cell" width="15%">
                        ${camper.lastName}
                    </td>
                    <td class="cell" width="10%">
                        exp??
                    </td>
                     <td class="cell" width="6%">
                        ${camper.age}
                    </td>
                    <td class="cell" width="9%">
                        ${camper.nationality}
                    </td>
                     <td class="cell">
                        ${camper.languages}
                    </td>
                    <td class="cell">
                        ${camper.preferences}
                    </td>               
                </tr>
            </table>
            <hr class="hr3" />
        </div>
    </g:each>
</body>
</html>
