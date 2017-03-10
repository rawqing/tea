<%@ page import="tea.T_case" %>
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

    <asset:stylesheet src="widget/treemenu.css"/>
    <asset:javascript src="widget/treemenu.js"/>

</head>
<body>
<section id="container" >
    <section id="main-content">
        <section class="wrapper">
            <div class="t_action">
                <span>产品</span>
                <span>评审</span>
                <span>新建</span>
            </div>
            <div class="row">
                <div class="col-lg-3 main-chart">
                    <ul class="tree">
                        <li><a href="">全部</a></li>
                        <li><span>模块</span>
                            <ul>
                                <li><a href="#">jQuery</a></li>
                                <li><a href="#">JavaScript</a></li>
                                <li><a href="#">Golang</a></li>
                            </ul>
                        </li>
                        <li><a href="#about">计划</a>
                            <ul>
                                <li><a href="#">Contact</a></li>
                                <li><a href="#">Blog</a></li>
                                <li><a href="#">Jobs</a></li>
                            </ul>
                        </li>
                        <li><a href="#">suite</a>

                        </li>
                    </ul>
                </div>
                <div class="col-lg-9 ds">
                    <f:table collection="${tea.T_case.list()}" />
                </div>
            </div>
        </section>
    </section>
</section>

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

<script>
    $(function(){
        $(".tree").treemenu({delay:300});
    });
</script>

</body>
</html>