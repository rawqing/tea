<!doctype html>
<html lang="en" class="no-js">
<head>

</head>
<body>
    <p>${reportName}</p>
    <div>
        <g:each var="sn" in="${data}">
            <p>${sn.suiteName}</p>
            <table >
                <tr>
                    <th></th>
                    <th>Duration</th>
                    <th>Passed</th>
                    <th>Uncalibrated</th>
                    <th>Skipped</th>
                    <th>Failed</th>
                    <th>Pass Rate</th>
                </tr>
                <g:each var="clz" in="${sn.classes}">
                    <tr>
                        <td><a class="test_class">${clz.className}</a></td>
                        <td>${clz.duration}</td>
                        <td>${clz.passed}</td>
                        <td>${clz.uncalibrated}</td>
                        <td>${clz.skipped}</td>
                        <td>${clz.failed}</td>
                        <td>${clz.passRate}</td>
                    </tr>
                </g:each>
                <tr>
                    <td></td>
                    <td></td>
                    <td>${sn.total.passed}</td>
                    <td>${sn.total.uncalibrated}</td>
                    <td>${sn.total.skipped}</td>
                    <td>${sn.total.failed}</td>
                    <td>${sn.total.passRate}</td>
                </tr>
            </table>
        </g:each>
    </div>
<p id="hidden_fileName" hidden="hidden">${fileName}</p>
<script type="text/javascript">
$(function () {
    $('a.test_class').click(function () {
        var cName = this.text;
        var fName = $('#hidden_fileName').text();
        console.log(cName);
        console.log(fName);
        $('#report_details').load("${createLink(action: "showClass")}?fileName="+fName + "&className="+cName);
    });


});

</script>
</body>
</html>