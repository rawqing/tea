<%@ page import="constant.EDITION; constant.PLATFORM; grails.converters.JSON; tea.T_case" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="my"/>

    <!--external css-->
    <asset:stylesheet src="font-awesome/css/font-awesome.css"/>
    <asset:stylesheet src="zabuto_calendar.css"/>
    <asset:stylesheet src="myjs/gritter/css/jquery.gritter.css"/>
    <asset:stylesheet src="lineicons/style.css"/>
    <!-- Custom styles for this template -->
    <asset:stylesheet src="style.css"/>
    <asset:stylesheet src="style-responsive.css"/>
    <asset:javascript src="myjs/chart-master/Chart.js"/>

    <asset:stylesheet src="widget/select.css"/>
    <asset:javascript src="widget/select.js"/>
    <asset:stylesheet src="widget/jquery.dataTables-1.10.13.css"/>
    <asset:javascript src="widget/jquery.dataTables-1.10.13.js"/>
</head>
<body>
<section id="container" >
    <section id="main-content">
        <section class="wrapper">

            <div>
                <g:form action="create" method="post" name="newVer">
                    <g:set var="pr" bean="productService" />
                    <g:select name="pro" from="${pr.getEnabledProductNames()}" /><br>
                    <g:select name="platform" from="${PLATFORM}"/><br>
                    <g:select name="edition" from="${EDITION}"/><br>
                </g:form>
            </div>

        </section>
    </section>
</section>

<script>

</script>

</body>
</html>