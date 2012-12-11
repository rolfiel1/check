// JavaScript Document
var isIe = (document.all) ? true : false;
// 设置select的可见状态
function setSelectState(state) {
	var objl = document.getElementsByTagName('select');
	for ( var i = 0; i < objl.length; i++) {
		objl[i].style.visibility = state;
	}
}
function mousePosition(ev) {
	if (ev.pageX || ev.pageY) {
		return {
			x : ev.pageX,
			y : ev.pageY
		};
	}
	return {
		x : ev.clientX + document.body.scrollLeft - document.body.clientLeft,
		y : ev.clientY + document.body.scrollTop - document.body.clientTop
	};
}
// 弹出方法
function showMessageBox(wTitle, content, pos, wWidth) {
	closeWindow();
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	if (isIe) {
		setSelectState('hidden');
	}
	var back = document.createElement("div");
	back.id = "back";
	var styleStr = "top:0px;left:0px;position:absolute;background:#666;width:"
			+ bWidth + "px;height:" + bHeight + "px;";
	styleStr += (isIe) ? "filter:alpha(opacity=0);" : "opacity:0;";
	back.style.cssText = styleStr;
	document.body.appendChild(back);
	showBackground(back, 50);
	var mesW = document.createElement("div");
	mesW.id = "mesWindow";
	mesW.className = "mesWindow";
	mesW.innerHTML = "<div class='mesWindowTop'><table width='100%' height='100%'><tr><td>"
			+ wTitle
			+ "</td><td style='width:1px;'><input type='button' onclick='closeWindow();' title='关闭窗口' class='close' value='关闭' /></td></tr></table></div><div class='mesWindowContent' id='mesWindowContent'>"
			+ content + "</div><div class='mesWindowBottom'></div>";
	styleStr = "left:" + (((pos.x - wWidth) > 0) ? (pos.x - wWidth) : pos.x)
			+ "px;top:" + (pos.y) + "px;position:absolute;width:" + wWidth
			+ "px;";
	mesW.style.cssText = styleStr;
	document.body.appendChild(mesW);
}
// 让背景渐渐变暗
function showBackground(obj, endInt) {
	if (isIe) {
		obj.filters.alpha.opacity += 1;
		if (obj.filters.alpha.opacity < endInt) {
			setTimeout(function() {
				showBackground(obj, endInt)
			}, 5);
		}
	} else {
		var al = parseFloat(obj.style.opacity);
		al += 0.01;
		obj.style.opacity = al;
		if (al < (endInt / 100)) {
			setTimeout(function() {
				showBackground(obj, endInt)
			}, 5);
		}
	}
}
// 关闭窗口
function closeWindow() {
	if (document.getElementById('back') != null) {
		document.getElementById('back').parentNode.removeChild(document
				.getElementById('back'));
	}
	if (document.getElementById('mesWindow') != null) {
		document.getElementById('mesWindow').parentNode.removeChild(document
				.getElementById('mesWindow'));
	}
	if (isIe) {
		setSelectState('');
	}
}
// 测试弹出
function testMessageBox(ev) {
	var objPos = mousePosition(ev);
	var a = document.getElementById("sz").value;
	var sum = Math.ceil(a.length / 2000);
	var date =new Date();
	if (a.length < 1) {
		alert('请输入检测内容!');
	} else {
		if (a.length > 0 && a.length < 2000) {
			alert('检测内容至少2000字!');
		} else if (a.length >= 100000) {
			alert('检测内容至多10万字!');
		} else {
			messContent = "<div style='background:#fff;width:757px;'><div class='motel_box'><div class='motel_bt'>请输入订单号</div><table width='100%' border='0' class='motel_info'><tr><td class='motel_red'>订单提示：</td><td colspan='2'>订单号可以填写一个，如果第一资金不够，可以添加第二个，系统会自动合并计算资金。</td></tr><tr><td>检测字数：</td><td><span class='motel_red'>"
					+ a.length
					+ "</span>字符，数量选择<span class='motel_red'>"
					+ sum
					+ "</span>件。</td><td>&nbsp;</td></tr><tr><td>订单号1：</td><td><input type='text' name='orderNo1' id='orderNo1' /></td><td>&nbsp;</td></tr><tr><td>订单号2：</td><td><input type='text' name='orderNo2' id='orderNo2' /></td><td>&nbsp;</td></tr><tr><td>验证码：</td><td><input type='text' name='validCode' id='validCode' size='10' /><img src='validcode!getValidcode.action?date='"+date+" alt='' align='' id='random' valign='absmiddle' /> <a href='javascript:void(0)' onclick='changeValidcode()' >换一个验证码</a></td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td><a href='javascript:void(0)' onclick='ajaxLogin()'  class='zdfh'>自动发货</a></td><td><span class='hddfh'><a href='http://item.taobao.com/item.htm?spm=a1z10.5.w4002.9.3ahAna&id=15000193626' target='_blank'>获得订单号的方法</a></span><span class='hddfh'><a href='#'>点击获得订单号</a></span></td></tr></table></div></div>";
			showMessageBox('', messContent, objPos, 800);
		}
	}
}

function changeValidcode(){
	//reload 验证码
	var timenow = new Date().getTime();
	document.getElementById("random").src="validcode!getValidcode.action?date="+timenow;
}

function ajaxLogin() {
	$.ajax({
		url : "user!checkLogin.action",
		data : {
			"orderNo1" : $('#orderNo1').val(),
			"orderNo2" : $('#orderNo2').val(),
			"validCode" : $('#validCode').val()
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			alert(data);
		}
	});
}
function login() {
	$.ajax({
		url : "user!login.action",
		data : {
			"orderNo1" : $('#orderNo1').val(),
			"validCode" : $('#validCode').val()
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data=='success'){
				window.location="paperpass!list.action";
			}else{
				alert('系统异常,请稍后再试!');
			}
			
		}
	});
}

function adminLogin() {
	$.ajax({
		url : "user!adminLogin.action",
		data : {
			"username" : $('#username').val(),
			"password" : $('#password').val(),
			"validCode" : $('#validCode').val()
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data=='success'){
				window.location="user!main.action";
			}else{
				alert('系统异常,请稍后再试!');
			}
			
		}
	});
}