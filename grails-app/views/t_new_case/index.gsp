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

    <asset:stylesheet src="hdt/handsontable.full.min.css"/>
    <asset:stylesheet src="hdt/tables.css"/>
    <asset:javascript src="hdt/common.js"/>
    <asset:javascript src="hdt/highlight.pack.js"/>
    <asset:javascript src="hdt/handsontable.full.min.js"/>

</head>
<body>
<section id="container" >
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="t_action">
                <span>产品</span>
                <span>评审</span>
                <span>新建</span>
            </div>
            <p><label><input type="checkbox" name="autosave" id="autosave" checked="checked" autocomplete="off"> Autosave</label></p>
            <pre id="example1console" class="console">Click "Load" to load data from server</pre>

            <g:set var="cc" value="${col}"/>
            <div id="edit_case" class="handsontable"></div>

            <p><button name="save" id="save">Save</button></p>
            <p><button name="te" id="te">test</button></p>

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
<script>
    jQuery().ready(function () {
        var container = jQuery("#edit_case"),
            autosave = jQuery("#autosave"),
            save = jQuery("save"),
            colt = <%= title%>,
            cols = colt.length,

            w = container.width() - 50,
            hot;



        var settings = {
            startRows: 1,
            startCols: cols,
            rowHeaders: true,
            colHeaders: colt,
            minSpareRows: 1,
            // 允许调整行高列宽
            manualRowResize: true,
            manualColumnResize: true,
            autoWrapRow: true,  //If true, pressing TAB or right arrow in the last column will move to first column in next row

//            stretchH: 'all',
            colWidths: calc(w,[10,20,10,40,30,5,20,10]),
//            width: 1260,
//            autoColumnSize: false,
            autoRowSize: true,
            wordWrap:true,

            afterChange: function (change, source) {
                if (source === 'loadData') {
                    return; //don't save this change
                }
                if (!autosave.attr('checked')) {
                    return;
                }
                jQuery.post("modified",{"data":JSON.stringify( change)},function (data) {
//                    alert("Data Loaded: " + data);

                });
            }};




        hot = container.handsontable(settings);

        hot.handsontable("updateSettings",{
            comments: true, //启用标注功能
            // 设置右键菜单
            contextMenu: ['row_above', 'row_below', 'remove_row',"----------",
                'make_read_only','commentsAddEdit','commentsRemove']
        });

        jQuery("#save").click(function () {
            jQuery.post("ajax",{"case":JSON.stringify(hot.handsontable("getData"))},function (data) {
                alert("Data Loaded: " + data);
            });
        });
        jQuery("#te").click(function () {
            alert(w);
            alert(calc(w,[10,20,10,40,30,5,20,10]));
//            alert(calc1(w,[10,10,10,10,10,20,20,10,10]));
        });
    })
</script>

</body>
</html>