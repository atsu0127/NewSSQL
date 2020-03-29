console.log("makeTable Start");

// TODO この変数を可変に
var divID = [];
var xmlpath = [];

for(var i = 0; i < xmlFileName.length; i++){
	xmlpath[i] = "."+xmlFileName[i].slice(xmlFileName[i].search("GeneratedXML")-1);
	divID[i] = "ssqlResult" + (i+1);
}
console.log('divID = '+divID);
console.log('xmlpath = '+xmlpath);

// ルートノードの要素名を入れる変数
var root;
var table;
var tr;
var td;
var initFlag = true;
var formNum = 0;
var base = [];

// 属性の値をのせるテーブル
var attTable;

var num = 0;

// （仮）全体数
// var all = 23;
// var row = 3;
// var column = 2;

// 一番下へ移動 2019/7/9
// console.log("Before onload function ");
// window.onload = function(){
// 	console.log("onload function ");
// 	if(pageNum !=0 ){
// 		$(function() {
// 		    $("#paging").pagination({
// 		        items: pageNum, // ページボタンの数
// 		        displayedPages: 1,
// 		        cssStyle: 'light-theme',
// 		        prevText: '<<',
// 		        nextText: '>>',
// 		        onPageClick: function(pageNumber){ssqlshow(pageNumber)}
// 		    })
// 		});
// 		function ssqlshow(pageNumber){
// 			var page = "#page-"+pageNumber;
// 			$('.selection').hide()
// 			$(page).ssqlshow()
// 		}		
// 	}
// 
// 	readXML();
// }
// console.log("After onload function ");



var readXML = function(){
	console.log("readXML ");
	base[num] = document.getElementById(divID[num]);
  $.ajax({
      async: true,  // 非同期オプション(デフォルト true)
      dataType: 'xml',
      url: xmlpath[num],
      success: function(data) {
      	traverseXML(data.documentElement);
      	base[num].appendChild(root);
      	initFlag = true;
      }, 
		complete:function(data){
			num+=1;
			if(num < divID.length){
				readXML();
			}
		}
  });
}
//readXML();	// New

// var readXML = function(){
// 	base[num] = document.getElementById(divID[num]);
// 		var msec = (new Date()).getTime();

//     new Ajax.Request(xmlpath[num], {
// 			method: "get",
// 			parameters: "cache="+msec,
// 			onSuccess:function(httpObj){
// 				var xmlDoc = httpObj.responseXML.documentElement;
// 				traverseXML(xmlDoc);

// 				base[num].appendChild(root);
// 				initFlag = true;
// 			},
// 			onFailure:function(httpObj){
// 				$("tableData").innerHTML = "エラーで読み込めませんでした";
// 			}, 
// 			onComplete:function(httpObj){
// 				num+=1;
// 				if(num < divID.length){
// 					readXML();
// 				}
// 			}
// 		});
// }

// XMLDOMを再帰的に探索
var traverseXML = function (node) {
	if((node.tagName.indexOf("Grouper") !== -1) || (node.tagName.indexOf("Connector") !== -1)){
		switch (node.getAttribute("type")) {
			case 'G1':
				if (node.getAttribute("outType") == "div") {
					// ページング（[],3!4% -> @{row=3, column=4} -> 横に3つ出して改行、縦に4セット出してページング）
					if (node.getAttribute("row") && node.getAttribute("column")) {
						var row = Number(node.getAttribute("row"));
						var column = Number(node.getAttribute("column"));
						var div = document.createElement("div");
						div.classList.add("row");
						div.classList.add(node.getAttribute("class"));
						initProcess(div);
						setArg(arguments[1], div);
						var i = 0;
						var k = 1;
						// 4回入る i=0 -> i=6 -> i=12 -> i=18
						while(i < ((Math.ceil(itemCount / (row*column))) * (row*column))){
							var selectiondiv = document.createElement("div");
							selectiondiv.classList.add("selection");
							selectiondiv.id = "page-" + k;
							div.appendChild(selectiondiv);
							var columndiv = document.createElement("div");
							columndiv.classList.add("column");
							columndiv.classList.add(node.getAttribute("class"));
							selectiondiv.appendChild(columndiv);
							for(var j = i; j < (i+(row*column)); j+=column){
								var rowdiv = document.createElement("div");
								rowdiv.classList.add("row");
								rowdiv.classList.add(node.getAttribute("class"));
								columndiv.appendChild(rowdiv);
								for(var l = j; l < j+column; l++){
									if(l>=itemCount) break;
									traverseXML(node.children.item(l), rowdiv);
								}
							}
							i=(i+(row*column))|0;
							k=(k+1)|0;
						}
					} else if (node.getAttribute("column")) { // 複合反復子（[],3! -> @{column=3} -> 横に3つ出して縦に改行）
						var i = 0;
						// retNUm=3でnode.children.count=8のとき、3回処理
						var retdiv = document.createElement("div");
						while(i < ((Math.ceil(node.children.length / g1RetNum[num])) * g1RetNum[num])){
							retdiv.classList.add("column");
							retdiv.classList.add(node.getAttribute("class"));
							initProcess(retdiv);
							setArg(arguments[1], retdiv);
							var div = document.createElement("div");
							for(var j = i; j < g1RetNum[num]+i; j++){
								if(j>=node.children.length) break;
								div.classList.add("row");
								div.classList.add(node.getAttribute("class"));
								retdiv.appendChild(div);
								traverseXML(node.children.item(j), div);
							}
							i=(i+g1RetNum[num])|0;
						}
					} else { // 通常のG1
						var div = document.createElement("div");
						div.classList.add("row");
						div.classList.add(node.getAttribute("class"));
						initProcess(div);
						setArg(arguments[1], div);
						var i = 0;
						while(i < node.children.length){
							traverseXML(node.children.item(i), div);
							i=(i+1)|0;
						}
					}				
				} else if (node.getAttribute("outType") == "table") {
					table = makeVoidTable(table);
					table.classList.add(node.getAttribute("class"));
					initProcess(table);
					if(arguments[1])
					arguments[1].classList.add(node.getAttribute("class"));
					setArg(arguments[1], table);
					var tr = document.createElement("tr");
					table.appendChild(tr);
					var array = node.children;
					var i = 0;
					while(i < node.children.length){
						td = document.createElement("td");
						tr.appendChild(td);
						traverseXML(node.children.item(i), td);
						i=(i+1)|0;
					}
				}
				break;

			case 'G2':
				if (node.getAttribute("outType") == "div") {
					// ページング（[]!3,2% -> @{row=3, column=2} -> 縦に3つ出して改行、横に2セット出してページング）
					if (node.getAttribute("row") && node.getAttribute("column")) {
						var row = Number(node.getAttribute("row"));
						var column = Number(node.getAttribute("column"));
						var div = document.createElement("div");
						div.classList.add("column");
						div.classList.add(node.getAttribute("class"));
						initProcess(div);
						setArg(arguments[1], div);
						var i = 0;
						var k = 1;
						// 4回入る i=0 -> i=6 -> i=12 -> i=18
						while(i < ((Math.ceil(itemCount / (row*column))) * (row*column))){
							var selectiondiv = document.createElement("div");
							selectiondiv.classList.add("selection");
							selectiondiv.id = "page-" + k;
							div.appendChild(selectiondiv);
							var rowdiv = document.createElement("div");
							rowdiv.classList.add("row");
							rowdiv.classList.add(node.getAttribute("class"));
							selectiondiv.appendChild(rowdiv);
							for(var j = i; j < (i+(row*column)); j+=row){
								var coldiv = document.createElement("div");
								coldiv.classList.add("column");
								coldiv.classList.add(node.getAttribute("class"));
								rowdiv.appendChild(coldiv);
								for(var l = j; l < j+row; l++){
									if(l>=itemCount) break;
									traverseXML(node.children.item(l), coldiv);
								}
							}
							i=(i+(row*column))|0;
							k=(k+1)|0;
						}
					} else if (node.getAttribute("row")) { // 複合反復子（[]!3, -> @{row=3} -> 縦に3つ出して横に改行）
						var i = 0;
						// retNUm=3でnode.children.count=8のとき、3回処理
						var retdiv = document.createElement("div");
						while(i < ((Math.ceil(node.children.length / g2RetNum[num])) * g2RetNum[num])){
							retdiv.classList.add("row");
							retdiv.classList.add(node.getAttribute("class"));
							initProcess(retdiv);
							setArg(arguments[1], retdiv);
							var div = document.createElement("div");
							for(var j = i; j < g2RetNum[num]+i; j++){
								if(j>=node.children.length) break;
								div.classList.add("column");
								div.classList.add(node.getAttribute("class"));
								retdiv.appendChild(div);
								traverseXML(node.children.item(j), div);
							}
							i=(i+g2RetNum[num])|0;
						}
					} else { // 通常のG2
						var div = document.createElement("div");
						div.classList.add("column");
						div.classList.add(node.getAttribute("class"));
						initProcess(div);
						setArg(arguments[1], div);
						var i = 0;
						while(i < node.children.length){
							traverseXML(node.children.item(i), div);
							i=(i+1)|0;
						}
					}



					// var div = document.createElement("div");
					// div.classList.add("column");
					// div.classList.add(node.getAttribute("class"));
					// initProcess(div);
					// setArg(arguments[1], div);
					// var i = 0;
					// if(!node.getAttribute("row")){
					// 	while(i < node.children.length){
					// 		traverseXML(node.children.item(i), div);
					// 		i=(i+1)|0;
					// 	}
					// } else {
					// 	var Paginationdiv = document.createElement("div");
					// 	Paginationdiv.id = "paging";

					// 	div.appendChild(Paginationdiv);
					// 	var k = 1;
					// 	while(i < node.children.length){
					// 		var selectiondiv = document.createElement("div");
					// 		for(var j = i; j < (i+ItemNumPerPage); j++){
					// 			if(j==itemCount) break;
					// 			selectiondiv.id = "page-" + k;
					// 			selectiondiv.classList.add("selection");
					// 			div.appendChild(selectiondiv);
					// 			traverseXML(node.children.item(j), selectiondiv);
					// 		}
					// 		i=(i+ItemNumPerPage)|0;
					// 		k++;
					// 	}
					// }
				


				} else if (node.getAttribute("outType") == "table") {
					table = makeVoidTable(table);
					table.classList.add(node.getAttribute("class"));
					initProcess(table);
					if(arguments[1])
					arguments[1].classList.add(node.getAttribute("class"));
					setArg(arguments[1], table);
					var i = 0;
					while(i < node.children.length){
						tr = document.createElement("tr");
						td = document.createElement("td");
						table.appendChild(tr);
						tr.appendChild(td);
						traverseXML(node.children.item(i), td);
						i=(i+1)|0;
					}
				}
				break;

			case 'C1':
				if (node.getAttribute("outType") == "div") {
					var div = document.createElement("div");
					div.classList.add("row");
					div.classList.add(node.getAttribute("class"));
					initProcess(div);
					setArg(arguments[1], div);
					var i = 0;
					while(i < node.children.length){
						traverseXML(node.children.item(i), div);
						i=(i+1)|0;
					}
				} else if (node.getAttribute("outType") == "table") {
					table = makeVoidTable(table);
					table.classList.add(node.getAttribute("class"));
					initProcess(table);
					arguments[1].classList.add(node.getAttribute("class"));
					setArg(arguments[1], table);				
					tr = document.createElement("tr");
					table.appendChild(tr);
					var i = 0;
					while(i < node.children.length){
						td = document.createElement("td");
						tr.appendChild(td);
						traverseXML(node.children.item(i), td);
						i=(i+1)|0;
					}
				}
				break;

			case 'C2':
				if (node.getAttribute("outType") == "div") {
					var div = document.createElement("div");
					div.classList.add("column");
					div.classList.add(node.getAttribute("class"));
					initProcess(div);
					setArg(arguments[1], div);
					var i = 0;
					while(i < node.children.length){
						traverseXML(node.children.item(i), div);
						i=(i+1)|0;
					}
				} else if (node.getAttribute("outType") == "table") {
					var table = makeVoidTable(table);
					table.classList.add(node.getAttribute("class"));
					initProcess(table);
					arguments[1].classList.add(node.getAttribute("class"));
					setArg(arguments[1], table);
					var i = 0;
					while(i < node.children.length){
						tr = document.createElement("tr");
						td = document.createElement("td");
						table.appendChild(tr);
						tr.appendChild(td);
						traverseXML(node.children.item(i), td);
						i=(i+1)|0;
					}
				}
				break;
		}
	} else if (node.tagName=='anchor') {
		var anchor = document.createElement('a');
		anchor.setAttribute('href', node.getAttribute('url'));
		setArg(arguments[1], anchor);
		traverseXML(node.children.item(0), anchor);
	} else if (node.tagName=='img') {
		var image = document.createElement('img');
		image.classList.add(node.getAttribute('class'));
		image.setAttribute('src', node.getAttribute('src'));
		arguments[1].appendChild(image);
	} else if (node.tagName=='plink') {
		var anchor = document.createElement('a');
		anchor.setAttribute('href', '');
		anchor.setAttribute('onclick', 'document.form' + formNum + '.submit();return false;');
		setArg(arguments[1], anchor);
		var form = document.createElement('form');
		form.setAttribute('action', node.getAttribute('target'));
		form.setAttribute('method', 'POST');
		form.setAttribute('name', 'form'+formNum);
		// 受け渡す値の数だけ作る
		var i = 1;
		while(node.getAttribute('value'+i)){
			var input = document.createElement('input');
			input.setAttribute('type', 'hidden');
			input.setAttribute('value', node.getAttribute('value'+i));
			input.setAttribute('name', 'att'+i);
			form.appendChild(input);			
			i++;
		}
		setArg(arguments[1], form);
		formNum++;
		traverseXML(node.children.item(0), anchor);
	} else {
		if (node.getAttribute("outType") == "div") {
			var div = document.createElement("div");
			div.appendChild(document.createTextNode(node.textContent));
			div.classList.add('att');
			div.classList.add(node.tagName);
			if(arguments[1]){
				arguments[1].appendChild(div);
			}
		} else if (node.getAttribute("outType") == "table") {
			attTable = makeAttTable(node);
			if(arguments[1]){
				arguments[1].classList.add(node.tagName);
				arguments[1].appendChild(attTable);
			}
		}
	}

}

// Grouper、Connectorで生成されるテーブル
var makeVoidTable = function (table) {
	table = document.createElement("table");
	return table;
}

// 各属性値を載せるテーブル
var makeAttTable = function (node) {
	table = document.createElement("table");
	table.classList.add('att');
	table.classList.add(node.tagName);
	tr = document.createElement("tr");
	td = document.createElement('td');
	td.classList.add('att');
	td.classList.add(node.tagName);
	td.appendChild(document.createTextNode(node.textContent));
	tr.appendChild(td);
	table.appendChild(tr);
	
	return table;
};

var initProcess = function (rt) {
	if(initFlag){
		root = rt;
		initFlag = false;
	}
}

var setArg = function (arg, element) {
	if(arg){
		arg.appendChild(element);
	}
}



// Exec
// window.onload = function(){
	console.log("onload function ");
	if(pageNum !=0 ){
		$(function() {
		    $("#paging").pagination({
		        items: pageNum, // ページボタンの数
		        displayedPages: 1,
		        cssStyle: 'light-theme',
		        prevText: '<<',
		        nextText: '>>',
		        onPageClick: function(pageNumber){ssqlshow(pageNumber)}
		    })
		});
		function ssqlshow(pageNumber){
			var page = "#page-"+pageNumber;
			$('.selection').hide()
			$(page).ssqlshow()
		}		
	}

	readXML();
// }


console.log("makeTable End");
