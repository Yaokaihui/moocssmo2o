$(function() {
	getlist();
	function getlist(e) {
		$.ajax({
			url : "/o2o/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.shopList);//渲染店铺列表
					handleUser(data.user);
				}
			}
		});
	}
	function handleUser(data) {   //获取店铺创建人
		$('#user-name').text(data.name);
	}

	function handleList(data) {  //获取店铺列表
		var html = '';
		data.map(function(item, index) {
			html += '<div class="row row-shop"><div class="col-40">'
				+ item.shopName + '</div><div class="col-40">'
				+ shopStatus(item.enableStatus)
				+ '</div><div class="col-20">'
				+ goShop(item.enableStatus, item.shopId) + '</div></div>';//goShop方法用来生成链接便于操作店铺具体信息

		});
		$('.shop-wrap').html(html);
	}

	function shopStatus(status) {
		if (status == 0) {
			return '审核中';
		} else if (status == -1) {
			return '店铺非法';
		} else if (status == 1) {
			return '审核通过';
		}
	}

	function goShop(status, id) { //goShop方法用来生成链接便于操作店铺具体信息
		if (status == 1) {  //判断店铺状态是否为审核通过，通过则可显示“进入”，并且能通过点击进行超链接页面跳转
			return '<a href="/o2o/shopadmin/shopmanagement?shopId=' + id
				+ '">进入</a>';
		} else {
			return '';
		}
	}
});