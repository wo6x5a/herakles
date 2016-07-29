require(['jquery',
         'global',
         'module/util',
         'module/datatables',
         'module/ajax',
         'bootstrap',
         'requirejs/domready!'],
function($,
		global, util, datatables) {
	var table = null;

	var _rendarTable = function() {
		var options = {};
		options.tableId = '#main-table';
		options.aaSorting = [ [ 1, "asc" ] ];
		options.sAjaxSource = global.context + "/web/system/log/list";
		table = datatables.init(options);
		table.setParams(util.getSearchData("#search-area"));
		return table.create();
	};

	var bindEvent = function() {
		$("#log-search-btn").on("click", function() {
			table.setParams(util.getSearchData('#search-area'));
			table.invoke("fnDraw");
		});

		$("#search-area").on("keydown", function(e) {
			if (e.which == 13) {
				$("#log-search-btn").trigger("click");
				return false;
			}
		});
	};

	var init = function() {
		_rendarTable();
		bindEvent();
	};

	init();
});