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
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<section id="container" >
    <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="t_action">
                <span>产品</span>
                <span>评审</span>
                <span>新建</span>
            </div>
            <div class="row">
                <div class="col-lg-3 main-chart"></div>
                <div class="col-lg-9 ds">
                    <f:table collection="${tea.T_case.list()}" />
                </div>
            </div><! --/row -->
        </section>
    </section>

    <!--main content end-->
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

<script type="application/javascript">
    $(document).ready(function () {
        var dp = $("#date-popover");
        dp.popover({html: true, trigger: "manual"});
        dp.hide();
        dp.click(function (e) {
            $(this).hide();
        });

        $("#my-calendar").zabuto_calendar({
            action: function () {
                return myDateFunction(this.id, false);
            },
            action_nav: function () {
                return myNavFunction(this.id);
            },
            ajax: {
//                url: "show_data.php?action=1",    //这里设置日历事件 , get该url , 取得事件信息
                url: "",
                modal: true
            },
            legend: [
                {type: "text", label: "Special event", badge: "00"},
                {type: "block", label: "Regular event", }
            ]
        });
    });


    function myNavFunction(id) {
        $("#date-popover").hide();
        var nav = $("#" + id).data("navigation");
        var to = $("#" + id).data("to");
        console.log('nav ' + nav + ' to: ' + to.month + '/' + to.year);
    }
</script>


</body>
</html>