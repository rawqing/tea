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

    <asset:javascript src="widget/treeview.js"/>
    <asset:javascript src="widget/treeview.css"/>
    <asset:stylesheet src="widget/select.css"/>
    <asset:javascript src="widget/select.js"/>
    <asset:stylesheet src="widget/jquery.dataTables-1.10.13.css"/>
    <asset:javascript src="widget/jquery.dataTables-1.10.13.js"/>
</head>
<body>
<section id="container" >
    <section id="main-content">
        <section class="wrapper">
            <div class="t_action">
                <span>
                    <div id="product_select">
                        <select>
                            <option value="" disabled>- 请选择所属产品 -</option>
                        </select>
                    </div>
                </span>
                <span>评审</span>
                <span>新建</span>
            </div>
            <div class="row">
                <div class="col-lg-3 main-chart">
                    <div id="treeview" class=""></div>
                </div>
                <div class="col-lg-9 ds">
                    <table id="show_case" class="display" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>名称</th>
                            <th>评审结果</th>
                            <th>优先级</th>
                            <th>最后更新</th>
                            <th>Salary</th>
                        </tr>
                        </thead>
                    </table>
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
        /**** 设置产品下拉选项  ****/
        var products = <%= products %>,
            sel = $('#product_select');

        sel.find('select').append(function () {
            var selects ="";
            for(var i=0;i< products.length;i++){
                //将提示语 disabled 后第一条则为默认selected
                selects += "<option value='"+products[i]+"'>"+products[i]+"</option>";
            }
            return selects;
        });
        $('select').comboSelect();
        /**        下拉选框设置完毕       **/
        /**       初始化表格      **/
        var table = $('#show_case').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "loadCase?t_product="+$.trim($(sel.find('select')).val()),
                "dataSrc": "data"
//                "data": {
//                    "t_product":$.trim($(sel.find('select')).val())
//                }
            },
            "columns": [
                {"data": "id"},
                {"data": "c_name"},
                {"data": "judge"},
                {"data": "prio"},
                {"data": "lastUpdated"},
                {"data": ""}
            ],
            "columnDefs": [{
                "targets": -1,
                "data": null,
                "defaultContent": "<button>Edit!</button>"
            }]
        });
        /**        表格初始化完毕       **/
        /**        功能菜单 start       **/
        var $treeview = $('#treeview');
        var loadMenu = function (productName){
            $.post("<g:createLink action='loadByProduct'/>",{"product":productName},function (data, status) {
                if(status == "success"){
                    var newData = changeData(jQuery.extend(true, {}, defaultData) ,data);
                    treeRefresh($treeview,newData);
                }
            });
        };
        var treeRefresh = function($obj ,newData) {
            $obj.treeview({
                color: "#428bca",
                showBorder: false,
                data: newData,
                onNodeSelected:function(event, node) {
                    eventSelected(event,node);
                }
            });
        };
        var selectedProduct = $.trim($(sel.find('select')).val());
        loadMenu(selectedProduct);

        /**
         * 按产品名称查找模块名
         */
        sel.find('select').change(function () {
            selectedProduct = $.trim($(this).val());
            if(isEmpty(selectedProduct)){
                return;
            }
            loadMenu(selectedProduct);
            reloadCases(selectedProduct,"" ,"")
        });
        /**        功能菜单 End       **/
        
        /**        selected event start     **/
        var eventSelected = function (event, node) {
            var product = $.trim($(sel.find('select')).val()),
                action,
                cName;
            if(node.parentId == null){
                // 1 级节点被选择
                if(node.text == dataMapping[2][3]) {
                    action = findMapping(node.text)
                }
            }else{
                var parent = $treeview.treeview('getParent',node);
                action = findMapping(parent.text);
                cName = node.text;
            }
            if(! isEmpty(action)){
                reloadCases(product ,action ,cName)
            }
        };

        function reloadCases(product ,action ,cName) {
            table.ajax.url("loadCase?t_product="+product+"&t_action="+action+"&t_name="+cName).load();
        }


    });

</script>

</body>
</html>