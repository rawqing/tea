<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Test Engineer Arsenal"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:link rel="icon" href="tea32X32.ico" type="image/x-ico" />
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <asset:stylesheet src="application.css"/>   <!-- 这是必须的 -->
    <asset:stylesheet src="custom.css"/>

    <script src="https://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <asset:javascript src="myjs/bootstrap.min.js"/>

    <g:layoutHead/>
</head>

<body>

    <g:pageProperty name="page.header" default="${render(template: "/layouts/header1")}"/>
    <g:pageProperty name="page.menus" default="${render(template: "/layouts/menus")}"/>
    <g:layoutBody/>
    <g:pageProperty name="page.footer" default="${render(template: "/layouts/footer")}"/>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    %{--<asset:javascript src="application.js"/>--}%
    <!-- js placed at the end of the document so the pages load faster -->
    <asset:javascript src="myjs/jquery.dcjqaccordion.2.7.js"/>
    <asset:javascript src="myjs/jquery.scrollTo.min.js"/>
    <asset:javascript src="myjs/jquery.nicescroll.js" />
    <asset:javascript src="myjs/jquery.sparkline.js"/>


    <!--common script for all pages-->
    <asset:javascript src="myjs/common-scripts.js"/>
    <asset:javascript src="myjs/gritter/js/jquery.gritter.js"/>
    <asset:javascript src="myjs/gritter-conf.js"/>

    <!--script for this page-->
    <asset:javascript src="myjs/sparkline-chart.js"/>
    <asset:javascript src="myjs/zabuto_calendar.js"/>

</body>
</html>
