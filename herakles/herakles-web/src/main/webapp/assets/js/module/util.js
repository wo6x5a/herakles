define(
		[ 'jquery' ],
		function($) {

			var Util = function() {
			};

			Util.prototype = {

				stringify_aoData : function(aoData) {
					var o = {};
					var modifiers = [ 'mDataProp_', 'sSearch_', 'iSortCol_',
							'bSortable_', 'bRegex_', 'bSearchable_',
							'sSortDir_' ];
					$
							.each(
									aoData,
									function(idx, obj) {
										if (obj.name) {
											for (var i = 0; i < modifiers.length; i++) {
												if (obj.name.substring(0,
														modifiers[i].length) == modifiers[i]) {
													var index = parseInt(obj.name
															.substring(modifiers[i].length));
													var key = 'a'
															+ modifiers[i]
																	.substring(
																			0,
																			modifiers[i].length - 1);
													if (!o[key]) {
														o[key] = [];
													}
													// console.log('index=' +
													// index);
													o[key][index] = obj.value;
													// console.log(key +
													// ".push(" + obj.value +
													// ")");
													return;
												}
											}
											// console.log(obj.name+"=" +
											// obj.value);
											o[obj.name] = obj.value;
										} else {
											o[idx] = obj;
										}
									});
					return JSON.stringify(o);
				},

				set_menu : function(item) {
					$('.navbar-fixed-top .navbar-nav li').removeClass('active');
					$('.navbar-fixed-top .navbar-nav li').each(function(i) {
						var menuItem = $(this).attr('data-menu-item');
						if (menuItem == item) {
							$(this).addClass('active');
						}
					});
				},

				ajax_submit : function(form) {
					var o = {};
					$(form).find('input,textarea,select').each(function() {
						var key = $(this).attr('name');
						if (key) {
							o[key] = $(this).val();
						}
					});
					return $.ajax({
						url : $(form).attr('action'),
						type : $(form).attr('method'),
						dataType : 'json',
						headers : {
							'x-form-id' : $(form).attr('id')
						},
						contentType : 'application/json; charset=UTF-8',
						data : JSON.stringify(o)
					});
				},

				redirect : function(url) {
					// Similar behavior as an HTTP redirect
					window.location.replace(url);
				},

				getSearchData : function(containerId) {
					var result = [];
					$(containerId)
							.find('input,textarea,select')
							.each(
									function() {
										var o = {};
										var key;
										if ($(this).attr("data-ignore") == "true") {
											return true;
										}
										if ($(this)
												.hasClass(
														"select2-focusser select2-offscreen")
												|| $(this).hasClass(
														"select2-input")) {
											return true;
										}
										key = $(this).attr('name');
										if (key) {
											if ($(this).attr("Type") == "checkbox") {
												o["name"] = key;
												if ($(this).val() == "true") {
													o["value"] = true;
												} else {
													o["value"] = false;
												}
											} else if ($(this).attr("Type") == "radio") {
												if ($(this).is(":checked")) {
													o["name"] = key;
													o["value"] = $(this).val();
												} else {
													return;
												}
											} else {
												o["name"] = key;
												o["value"] = $(this).val();
											}
											result.push(o);
										}
									});
					return result;
				}
			};

			return new Util();
		});