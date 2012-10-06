<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Report for younger</title>
<style type="text/css">
.cellTag {
    font-weight: bold;
    font-style: oblique;
    font-size: 80%;
    text-align: right;
}

table.tableCamper {
    table-layout: fixed;
}

.cellPref,.cellYear,.cellStatusA {
    border: 1px solid #A0A0A0;
}

.cellFirstName {
    background-color: #D0D0D0;
}

.cellLastName {
    background-color: #A0A0A0;
}
</style>
</head>
<body>
    <div class="title">
        <h1>CAMPER APPLICATIONS - 14TH YEAR OLD</h1>
        <h2>Campers between 14 and 25 years old</h2>
        <h3>Sorted by Nationality, Sex, Given Name</h3>
    </div>
    <div class="stat">
        Number of records:
        ${result?.size()}
    </div>
    <p>
    <table class="tableCamper" width="95%">
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
            <table class="tableCamper" width="95%">
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

<%--
def rec = [ nationality : it.nationality,
                        gender : it.gender,
                        firstName : it.first_name,
                        lastName : it.last_name,
                        newToLpc : it.new_to_lpc,
                        preferences : it.preferences,
                        status : it.Status,
                        location : it.location,
                        year : it.year,
                        statusA : it.status,
                        age : it.Age ]
                        --%>