<%@ page import="tea.T_case" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
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

    <asset:stylesheet src="widget/select.css"/>
    <asset:javascript src="widget/select.js"/>
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
            <div>
                <g:uploadForm action="upload" method="post">
                    请选择文件 :<input type="file" id="cases_file" name="cases_file"/>
                    <input type="submit" value="上传">
                <p>
                </g:uploadForm>
            </div>
            <div id="product_select">
                <select>
                    <option value="" disabled>- 请选择所属产品 -</option>

                </select>
            </div>
            <p><label><input type="checkbox" name="autosave" id="autosave" checked="checked" autocomplete="off"> Autosave</label></p>
            <p>请选一个产品</p>

            <g:set var="cc" value="${col}"/>
            <div id="edit_case" class="handsontable"></div>
            <p>注: step 和 expectation 填写时,多个步骤请用 alt + enter 换行分开 ; step 和 expectation 必须相对应 , 如:</p>
            <table class="hint_table">
                <tr><td>step</td><td>expectation</td></tr>
                <tr><td>1. 输入"xxx" <br /> 2. 点击[xxx]按钮 </td><td>1. 可正常输入 <br /> 2. 跳转至"xxx"页面 </td></tr>
            </table>

            <p><button name="save" id="save">Save</button></p>
            <p><button name="te" id="te">test</button></p>

        </section>
    </section>

    <!--main content end-->
</section>

<script>
    jQuery().ready(function () {
        var products = <%= products %>,
            sel = jQuery('#product_select'),
            selectedProduct = "";

        sel.find('select').append(function () {
            var selects ="";
            for(var i=0;i< products.length;i++){
                selects += "<option value='"+products[i]+"'>"+products[i]+"</option>";
            }
            return selects;
        });
        jQuery('select').comboSelect();

        var container = jQuery("#edit_case"),
            autosave = jQuery("#autosave"),
            save = jQuery("save"),
            colt = <%= title%>,
            nullableCol = <%= nullable %>,
            cols = colt.length,
            w = container.width() - 50,
            column_settings ,
            hot;



        var settings = {
            startRows: 1,
            startCols: cols,
            rowHeaders: true,
            colHeaders: colt,
            //最小剩余行必须最小为1 , 否则将不能继续下一行输入
            minSpareRows: 1,
            // 允许调整行高列宽
            manualRowResize: true,
            manualColumnResize: true,
            autoWrapRow: true,  //If true, pressing TAB or right arrow in the last column will move to first column in next row
            manualColumnFreeze: true,
            manualRowMove: true,
            colWidths: calc(w,[10,20,10,40,30,5,20,10]),
            autoRowSize: true,
            wordWrap:true,
            readOnly:true,

            afterChange: function (change, source) {
                if (source === 'loadData') {
                    return; //don't save this change
                }
                if (!autosave.attr('checked')) {
                    return;
                }
//                var aaa = isQualifiedRows(hot,getChangeRows(change),nullableCol);
//                alert(aaa);
                %{--jQuery.post("${createLink(action: "modified")}",{"data":JSON.stringify( change)},function (data) {--}%
%{--//                    alert("Data Loaded: " + data);--}%

                %{--});--}%
            }};




        hot = container.handsontable(settings);
        updateHandsontableWithModule(sel.find('select'));




        var hint = ["m1","hello"];
        /**
         * 更新handsontable设置
         */
        hot.handsontable("updateSettings",{
            comments: true, //启用标注功能
            // 设置右键菜单
            contextMenu: ['row_above', 'row_below', 'remove_row',"----------",
                'make_read_only','commentsAddEdit','commentsRemove'],
            customBorders: [
                {row: 0, col: 0, left: {width: 2, color: 'red'},
                    right: {width: 1, color: 'green'}, top: '', bottom: ''}
            ]

        });


        /**
         * 按产品名称查找模块名 , 并update 表格设置
         */
        sel.find('select').change(function () {
            updateHandsontableWithModule(jQuery(this));
        });
        /**
         * 按产品名称加载模块名
         * @param $element
         */
        function updateHandsontableWithModule($element) {
            selectedProduct = jQuery.trim($element.val());
            if(isEmpty(selectedProduct)){
                return;
            }

            jQuery.post("${createLink(action: "productChange")}",{"product":selectedProduct},function (data,status) {
                if(status == "success"){
                    column_settings = data;
                    hot.handsontable("updateSettings",{
                        //从后台数据更新列设置
                        columns:column_settings,
                        readOnly:false
                    });
                }
            });
        }

        jQuery("#save").click(function () {
            var hotData = hot.handsontable("getData");
            if(isEmpty(selectedProduct) || hotData.length < 2){
                return;
            }
            var eCell = qualifiedData(hot,nullableCol);
            if(eCell[0] >= 0 && eCell[1] >= 0) {
                hot.handsontable("updateSettings", {
                    cells: function (row, col, prop) {
                        var cellProperties = {};
                        if (row === eCell[0] && col === eCell[1]) {
                            cellProperties.renderer = backgroundRenderer;
                        }
                        return cellProperties;
                    }
                });
                return;
            }
            jQuery.post("${createLink(action: "saveCases")}",{"case":JSON.stringify(hotData),"product":selectedProduct},function (data) {
                alert("Data Loaded: " + data);
            });
        });
        jQuery("#te").click(function () {
            jQuery.post("${createLink(action: "modified")}",{"data":"hello"},function (data) {
//                    alert("Data Loaded: " + data);

            });
        });

    });

</script>

</body>
</html>