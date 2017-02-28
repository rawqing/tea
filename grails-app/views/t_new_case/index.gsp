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
    <asset:javascript src="hdt/handsontable.full.min.js"/>
    <asset:javascript src="hdt/common.js"/>
    <asset:javascript src="hdt/highlight.pack.js"/>

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
            <div id="edit_case"></div>
            <p><button name="save" id="save">Save</button></p>

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
 /*   var
        $$ = function(id) {
            return document.getElementById(id);
        },
        container = $$('edit_case'),
        exampleConsole = $$('example1console'),
        autosave = $$('autosave'),
        load = $$('load'),
        save = $$('save'),
        autosaveNotification,
        hot;

    hot = new Handsontable(container, {
        startRows: 1,
        startCols: 7,
        rowHeaders: true,
        colHeaders: ['NO.','module','name','step','expect','result','actual'],
        minSpareRows: 1,
        contextMenu: true,
        afterChange: function (change, source) {
            if (source === 'loadData') {
                return; //don't save this change
            }
            if (!autosave.checked) {
                return;
            }
            clearTimeout(autosaveNotification);
            ajax('modified', 'POST', {"data":JSON.stringify( change)}, function (data) {
                exampleConsole.innerText  = 'Autosaved (' + change.length + ' ' + 'cell' + (change.length > 1 ? 's' : '') + ')';
                autosaveNotification = setTimeout(function() {
                    exampleConsole.innerText ='Changes will be autosaved';
                }, 1000);
            });
        }
    });*/

    jQuery().ready(function () {
        var container = jQuery("#edit_case"),
            autosave = jQuery("#autosave"),
            save = jQuery("save"),
            hot,
            cols = 7;

        var settings = {
            startRows: 1,
            startCols: cols,
            rowHeaders: true,
            colHeaders: ['NO.','module','name','step','expect','result','actual'],
            minSpareRows: 1,
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
            jQuery.post("ajax",{"case":JSON.stringify(hot.getData())},function (data) {
                alert("Data Loaded: " + data);
            });
        });
    })
</script>

</body>
</html>