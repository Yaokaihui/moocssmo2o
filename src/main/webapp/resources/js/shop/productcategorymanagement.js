$(function() {
	var listUrl = '/o2o/shopadmin/getproductcategorylist';
	var addUrl = '/o2o/shopadmin/addproductcategorys';
	var deleteUrl = '/o2o/shopadmin/removeproductcategory';
	getList();
	function getList() {
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								var dataList = data.data;
								$('.category-wrap').html('');
								var tempHtml = '';
								dataList
										.map(function(item, index) {
											tempHtml += ''
													+ '<div class="row row-product-category now">'
													+ '<div class="col-33 product-category-name">'
													+ item.productCategoryName
													+ '</div>'
													+ '<div class="col-33">'
													+ item.priority
													+ '</div>'
													+ '<div class="col-33"><a href="#" class="button delete" data-id="'
													+ item.productCategoryId
													+ '">删除</a></div>'
													+ '</div>';
										});
								$('.category-wrap').append(tempHtml);
							}
						});
	}
	$('#new')
			.click(  //点击事件，每点击一次新增一行，用来批量添加商品分类信息
					function() {
						var tempHtml = '<div class="row row-product-category temp">'
								+ '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
								+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
								+ '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
								+ '</div>';
						$('.category-wrap').append(tempHtml);
					});
	$('#submit').click(function() {
		var tempArr = $('.temp'); //通过查询“.temp”遍历新增的行来提交批量添加的商品分类信息
		var productCategoryList = [];
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.productCategoryName && tempObj.priority) {
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addUrl,
			type : 'POST',
			data : JSON.stringify(productCategoryList),//将前端添加的数据通过转换成字符串的显示传递到后台，并通过url:addUrl来调用后端方法进行数据处理并保存到数据库中去
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					getList();
				} else {
					$.toast('提交失败！');
				}
			}
		});
	});

	$('.category-wrap').on('click', '.row-product-category.temp .delete',//用来删除前端新增的还未提交到后台的商品分类列表信息
			function(e) {
				console.log($(this).parent().parent());
				$(this).parent().parent().remove();

			});
	$('.category-wrap').on('click', '.row-product-category.now .delete',  //用来删除数据库中存在的商品分类信息
			function(e) {
				var target = e.currentTarget;
				$.confirm('确定么?', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							productCategoryId : target.dataset.id
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$.toast('删除成功！');
								getList();
							} else {
								$.toast('删除失败！');
							}
						}
					});
				});
			});
});