/*
 * https://developer.mozilla.org/en-US/docs/Web/Events/DOMContentLoaded
 *
 * contentloaded.js
 *
 * Author: Diego Perini (diego.perini at gmail.com)
 * Summary: cross-browser wrapper for DOMContentLoaded
 * Updated: 20101020
 * License: MIT
 * Version: 1.2
 *
 * URL:
 * http://javascript.nwbox.com/ContentLoaded/
 * http://javascript.nwbox.com/ContentLoaded/MIT-LICENSE
 *
 */

// @win window reference
// @fn function reference
function contentLoaded(win, fn) {

  var done = false, top = true,

    doc = win.document,
    root = doc.documentElement,
    modern = doc.addEventListener,

    add = modern ? 'addEventListener' : 'attachEvent',
    rem = modern ? 'removeEventListener' : 'detachEvent',
    pre = modern ? '' : 'on',

    init = function(e) {
      if (e.type == 'readystatechange' && doc.readyState != 'complete') return;
      (e.type == 'load' ? win : doc)[rem](pre + e.type, init, false);
      if (!done && (done = true)) fn.call(win, e.type || e);
    },

    poll = function() {
      try { root.doScroll('left'); } catch(e) { setTimeout(poll, 50); return; }
      init('poll');
    };

  if (doc.readyState == 'complete') fn.call(win, 'lazy');
  else {
    if (!modern && root.doScroll) {
      try { top = !win.frameElement; } catch(e) { }
      if (top) poll();
    }
    doc[add](pre + 'DOMContentLoaded', init, false);
    doc[add](pre + 'readystatechange', init, false);
    win[add](pre + 'load', init, false);
  }
}


function ajax(url, method, params, callback) {
  var obj;

  try {
    obj = new XMLHttpRequest();
  } catch (e) {
    try {
      obj = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
      try {
        obj = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (e) {
        alert("Your browser does not support Ajax.");
        return false;
      }
    }
  }
  obj.onreadystatechange = function () {
    if (obj.readyState == 4) {
      callback(obj);
    }
  };
  obj.open(method, url, true);
  obj.setRequestHeader("X-Requested-With", "XMLHttpRequest");
  obj.setRequestHeader("Content-type","application/x-www-form-urlencoded");
  obj.send(params);

  return obj;
}

(function () {


  function trimCodeBlock(code, pad) {
    var i, ilen;
    pad = pad || 0;
    code = code.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"); //escape html special chars
    code = code.split('\n');
    for (i = 0; i < 10; i++) {
      if (code[0].trim() === '') {
        code.splice(0, 1);
      }
    }
    var offset = 0;
    for (i = 0, ilen = code[0].length; i < ilen; i++) {
      if (code[0].charAt(i) != " ") {
        break;
      }
      offset++;
    }
    for (i = 0, ilen = code.length; i < ilen; i++) {
      code[i] = new Array(pad + 1).join(' ') + code[i].substring(offset);
    }
    return code;
  }



})();

/**
 * 根据指定的权重计算每列宽度
 * @param ori 最大宽度
 * @param weighted 权重
 * @returns {*}
 */
var calc = function (ori ,weighted) {
  var ol = [],
      sum = 0,
      avg = 0,
      max = 0,
      maxIndex = 0,
      olSum = 0,
      short = 0;
      len = weighted.length;
  weighted.forEach(function (value, index, array) {
      sum += value;
      if(max < value){
          max = value;
          maxIndex = index;
      }
  });
  if(len < 1) return ori;
  if(len > 1){
    avg = ori / sum;  //求出加权平均数
    for(var i=0;i< len ;i++){
      ol[i] = parseInt(avg * weighted[i]);
      olSum += ol[i];
    }
    short = ori - olSum;
    if(short != 0){
        ol[maxIndex] += short;
    }
    return ol;
  }else{
    return parseInt(avg * weighted);
  }
};
/**
 * 校验所有数据是否符合验证
 * @param instance
 * @param excludeColumns
 * @returns {[*]}
 */
function qualifiedData(instance,excludeColumns) {
    var invalid = [-1,-1];
    var data = instance.handsontable("getData");
    for(var r=0; r<data.length -1; r++){
        var rowData = data[r];
        for (var i = 0; i < rowData.length; i++) {
            if(excludeColumns.indexOf(i) != -1){
                continue;
            }
            if (rowData[i] === null || rowData[i] == "") {
                return invalid = [r,i];
            }
        }
    }
    return invalid;
}

function isEmpty(val) {
    return val == null || val == "" || val == " ";
}

var classRenderer = function(instance, td, row, col, prop, value, cellProperties) {
    Handsontable.renderers.TextRenderer.apply(this, arguments);
    td.className = 'error_border';
};
var backgroundRenderer = function(instance, td, row, col, prop, value, cellProperties) {
    Handsontable.renderers.TextRenderer.apply(this, arguments);
    td.style.background = '#ffc1c3';
};