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
         * 按产品名称查找模块名 , 并update 表格设置
         */
        sel.find('select').change(function () {
            selectedProduct = $.trim($(this).val());
            if(isEmpty(selectedProduct)){
                return;
            }
            loadMenu(selectedProduct);
        });
        /**        功能菜单 End       **/
        
        /**        selected event start     **/
        var eventSelected = function (event, node) {
            if(node.parentId == null){
                // 1 级节点被选择
                $treeview.treeview('toggleNodeExpanded',[node]);
            }else{
                var parent = $treeview.treeview('getParent',node);
                var pText = parent.text;
                alert(pText)
            }
        };

    });

</script>

</body>
</html>