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
<title>Report for younger</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'reportA.css')}" type="text/css">
</head>
<body>
    <div class="title">
        <h1>CAMPER APPLICATIONS - Younger</h1>
        <h2>Campers less than 14 years old</h2>
        <h3>Sorted by Nationality, Sex, Given Name</h3>
    </div>
    <div class="stat">
        Number of records:
        ${result?.size()}
    </div>
    <p>
    <table class="tableCamper" width="97%">
        <tr>
            <td class="cell" width="9%">NATIONALITY</td>
            <td class="cell" width="3%">F=GIRLS<br>M=BOYS
            </td>
            <td class="cell"></td>
            <td class="cell">FAMILY NAME</td>
            <td class="cell">AGE IN CAMP</td>
            <td class="cell">NEW?</td>
        </tr>
    </table>
    <p>
    <g:each in="${result}" status="i" var="camper">
        <div class="divCamper">
            <p>
            <table class="tableCamper" width="97%">
                <tr>
                    <td class="cellNationality" width="9%">
                        ${camper.nationality}
                    </td>
                    <td class="cell" width="3%">
                        ${camper.gender}
                    </td>
                    <td class="cellFirstName">
                        ${camper.firstName}
                    </td>
                    <td class="cellLastName">
                        ${camper.lastName}
                    </td>
                    <td class="cell">
                        ${camper.age}
                    </td>
                    <td class="cell">
                        ${camper.newToLpc?"New to LPC":"Not new"}
                    </td>
                </tr>
                <tr>
                    <td width="9%"></td>
                    <td width="3%"></td>
                    <td class="cellTag">Preferences:</td>
                    <td class="cellPref" colspan="3">
                        ${camper.preferences}
                    </td>
                </tr>
                <tr>
                    <td width="9%"></td>
                    <td width="3%"></td>
                    <td class="cellTag">Camp:</td>
                    <td class="cell">
                        ${camper.location}
                    </td>
                    <td class="cellYear">
                        ${camper.year}
                    </td>
                    <td class="cellStatusA">
                        ${camper.statusA}
                    </td>
                </tr>
            </table>
        </div>
    </g:each>
</body>
</html>

