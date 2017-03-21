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
                <span><g:link action="create">新建</g:link></span>
            </div>
            <div class="row">

                <div style=" margin-left: 18px;width: 98%;">
                    <table id="show_case" class="display" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>platform</th>
                            <th>edition</th>
                            <th>version</th>
                            <th>release time</th>
                            <th>operate</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </section>
    </section>
</section>

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
                "url": "${createLink(action: "loadVersioning")}?t_product="+$.trim($(sel.find('select')).val()),
                "dataSrc": "data"
//                "data": {
//                    "t_product":$.trim($(sel.find('select')).val())
//                }
            },
            "columns": [
                {"data": "id"},
                {"data": "platform"},
                {"data": "edition"},
                {"data": "version"},
                {"data": "releaseTime"},
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

        var selectedProduct = $.trim($(sel.find('select')).val());

        /**
         * 按产品名称查找模块名
         */
        sel.find('select').change(function () {
            selectedProduct = $.trim($(this).val());
            if(isEmpty(selectedProduct)){
                return;
            }
//            reloadCases(selectedProduct,"" ,"")
        });
        /**        功能菜单 End       **/


        function reloadCases(product ,plan) {
            table.ajax.url("${createLink(action: "loadVersioning")}?t_product="+product+"&t_plan="+plan).load();
        }


    });

</script>

</body>
</html>