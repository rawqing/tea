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
                    <div id="treeview7" class=""></div>
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
        var products = <%= products %>,
            sel = $('#product_select'),
            selectedProduct,
            modules = [];

        // 设置产品下拉选项
        sel.find('select').append(function () {
            var selects ="";
            for(var i=0;i< products.length;i++){
                //将提示语 disabled 后第一条则为默认selected
//                if(i == 0){
//                    selects += "<option value='"+products[i]+"' selected>"+products[i]+"</option>";
//                    continue;
//                }
                selects += "<option value='"+products[i]+"'>"+products[i]+"</option>";
            }
            return selects;
        });
        $('select').comboSelect();
        selectedProduct = $.trim($(sel.find('select')).val());

        var loadModules = function (){
            $.post("<g:createLink action='loadModule'/>",{"product":selectedProduct},function (data,status) {
                if(status == "success"){
                    return data;
                }
            });
        };
        var loadPlans = function (){
            $.post("<g:createLink action='loadModule'/>",{"product":selectedProduct},function (data,status) {
                if(status == "success"){
                    return data;
                }
            });
        };
        var loadSuites = function (){
            $.post("<g:createLink action='loadModule'/>",{"product":selectedProduct},function (data,status) {
                if(status == "success"){
                    return data;
                }
            });
        };
        /**
         * 按产品名称查找模块名 , 并update 表格设置
         */
        sel.find('select').change(function () {
            selectedProduct = $.trim($(this).val());
            if(isEmpty(selectedProduct)){
                return;
            }
            loadModule();
        });
//        loadModule();
        var newData = changeData(defaultData ,{"module":["m1","M2"],"suite":["s1","s2"],"plan":["p1","p2"]});
        treeRefresh($('#treeview7'),newData);
//        $('#treeview7').treeview({
//            color: "#428bca",
//            showBorder: false,
//            data: newData
//        });

        function addElements($ele ,datas) {
            $ele.append(function () {
                var lis ="";
                for(var i=0;i< datas.length;i++){
                    lis += "<li >"+datas[i]+"</li>";
                }
                return lis;
            });
        }



    });

</script>

</body>
</html>