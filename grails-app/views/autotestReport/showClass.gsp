<!doctype html>
<html lang="en" class="no-js">
<head>

</head>
<body>
<h3>${className}</h3>
<p>Test duration ${data.spendTime /1000}s</p>
<div>
    <h4 class="failed_title">Failed Tests</h4>
    <ul>
        <g:each var="ft" in="${cases.FAILED}">
            <table>
                <tr>
                    <td>${ft.caseName}</td> <td>${ft.spendTime / 1000}s</td>
                    <td class="toggle_msg error_msg">${ft.localizedMessage.substring(0 ,100)}... <a>[More]</a></td>
                </tr>
                <tr class="toggle_details">
                    <td><g:img dir="analyzer/autotest/android" file="${ft.imageName}" width="300" /></td>
                    <td></td>
                    <td>
                        <p><span>use data {</span>
                            <g:each var="ud" in="${ft.useData}">
                                <span class="data_title">${ud.getKey()} : </span>
                                <span>${ud.getValue()} , </span>
                            </g:each>
                            }</p>
                        <p>${ft.errorMsg}</p>
                        <div>
                            <g:each var="tk" in="${ft.stackTraces}">
                                <p>${tk}</p>
                            </g:each>
                            <a>[Hide]</a>
                        </div>
                    </td>
                </tr>
            </table>
        </g:each>
    </ul>
    <h4 class="uncalibrated_title">Uncalibrated Tests</h4>
    <ul>
        <g:each var="ft" in="${cases.UNCALIBRATED}">
            <li>
                <span>${ft.caseName}</span>
                <span>${ft.spendTime / 1000}s</span>
                <span class="toggle_msg error_msg">${ft.localizedMessage.substring(0 ,100)}... <a>[More]</a></span>
                <div class="toggle_details">
                    <div class="imgs">
                        <g:each var="im" in="${ft.compareImageNames}">
                            <g:img dir="analyzer/autotest/android" file="${im}" width="40" />
                        </g:each>
                    </div>
                    <div>
                        <p><span>use data {</span>
                            <g:each var="ud" in="${ft.useData}">
                                <span class="data_title">${ud.getKey()} : </span>
                                <span>${ud.getValue()} , </span>
                            </g:each>
                            }</p>
                        <p>${ft.errorMsg}</p>
                        <div>
                            <g:each var="tk" in="${ft.stackTraces}">
                                <p>${tk}</p>
                            </g:each>
                        </div>
                    </div>
                </div>
            </li>
        </g:each>
    </ul>
    <h4 class="skipped_title">Skipped Tests</h4>
    <ul>
        <g:each var="ft" in="${cases.SKIPPED}">
            <li>
                <span>${ft.caseName}</span>
                <span>${ft.spendTime / 1000}s</span>
                <span class="toggle_msg error_msg">${ft.errorMsg.substring(0 ,100)}... <a>[More]</a></span>
                <div class="toggle_details">
                    <g:img dir="analyzer/autotest/android" file="${ft.imageName}" width="400" />
                    <div>
                        <p><span>use data {</span>
                            <g:each var="ud" in="${ft.useData}">
                                <span class="data_title">${ud.getKey()} : </span>
                                <span>${ud.getValue()} , </span>
                            </g:each>
                            }</p>
                        <p>${ft.errorMsg}</p>
                        <div>
                            <g:each var="tk" in="${ft.stackTraces}">
                                <p>${tk}</p>
                            </g:each>
                        </div>
                    </div>
                </div>
            </li>
        </g:each>
    </ul>
    <h4 class="passed_title">Passed Tests</h4>
    <ul>
        <g:each var="ft" in="${cases.SUCCEEDED}">
            <li>
                <span>${ft.caseName}</span>
                <span>${ft.spendTime / 1000}s</span>
                <p class="toggle_msg"><span>use data {</span>
                    <g:each var="ud" in="${ft.useData}">
                        <span class="data_title">${ud.getKey()} : </span>
                        <span>${ud.getValue()} , </span>
                    </g:each>
                }</p>
                <div class="toggle_details">

                </div>
            </li>
        </g:each>
    </ul>

</div>
<script type="text/javascript">
$(function () {

    $('.toggle_msg').click(function () {
       $(this).parent().siblings('.toggle_details').toggle();
    });

    $('.toggle_details > td > div > a').click(function () {
        var to_d = $(this).parents('.toggle_details');
        to_d.toggle();
        $("html,body").animate({scrollTop:to_d.prev().offset().top},300);
    })
});
</script>
</body>
</html>