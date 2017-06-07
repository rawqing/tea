<!doctype html>
<html lang="en" class="no-js" xmlns="">
<head>
    <meta name="layout" content="simple"/>

</head>
<body>
    <div class="row">
        <div class="col-lg-3 main-chart">
            <h3>Espresso Testing Report</h3>
            <div id="report_list">
                <ul>
                <g:each in="${data}">
                    <li class="report_li">
                        <a class="file_date" name="${it.fileName}">${it.strDate}</a>
                        %{--<g:link action="showDetails" params="${[fileName:it.fileName]}">${it.strDate}</g:link>--}%
                        <div class="test_suites">
                            <g:each var="su" in="${it.json.suiteBean.childSuites}">
                                <h4>${su.suiteName}</h4>
                                <g:each var="tc" in="${su.testClasses}">
                                    <a class="test_class">${tc.testClassName}</a>
                                </g:each>
                            </g:each>
                        </div>
                    </li>
                    <br/>
                </g:each>
                </ul>
            </div>
        </div>
        <div class="col-lg-9 ds">

            <a id = "test">test</a>
            <div id="report_details">
                <g:uploadForm action="upload" method="post" >
                    请选择文件 :<input type="file" id="files" name="files" multiple/>
                    <input type="submit" value="上传">
                    <p>
                    </p>
                    <br>
                </g:uploadForm>
            </div>
        </div>
    </div>
<asset:javascript src="widget/extension.js"/>
<script type="text/javascript">
    $(function () {

        var drawRight = function (name) {
            $.ajax({
                type: "POST",
                url:"<g:createLink action="loadJson"/>",
                data:{"fileName":name} ,
                async: false,
                dataType:"json",
                success:function (data) {
                    $('#report_details').html("1");

                }
            });
    };
    $('.report_li > a.file_date').click(function () {
       var fName = this.name;
       console.log(fName);
       $('#report_details').load("${createLink(action: "showDetails")}?fileName="+fName);
    });
    $('a.test_class').click(function () {
        var cName = this.text;
        var fName = $(this).parent().prev('.file_date').attr("name");
        console.log(fName);
        $('#report_details').load("${createLink(action: "showClass")}?fileName="+fName + "&className="+cName);
    });
    $('#test').click(function () {
//        $('#report_details').html("<h1>hello</h1>");
        $('#report_details').load("showDetails?fileName=jcd_json_report_170605_161219226.json");
    });
});


</script>
</body>
</html>