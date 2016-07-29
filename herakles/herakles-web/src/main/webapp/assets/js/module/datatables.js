
define(['jquery',
        'datatables',
        'module/util'],function($,datatables,util){
	var DataTable = function(){
		
	};
	
	DataTable.prototype = {
		init: function(options){
			var inner_table = new TableInner();
			inner_table.init(options);
			return inner_table;
		}
	};
	var TableInner = function(){
		this.tableId  = "";
		this.sAjaxSource = "";
		this.params = [];
		this.aaSorting=[];
		this.functionList = {};
		this.rawOptions = {};
		this.rawTable = {};
	};
	
	
	$.fn.dataTableExt.oApi.fnStandingRedraw = function(oSettings) {
	    oSettings.oApi._fnDraw(oSettings);
	};
	
	TableInner.prototype = {
			init: function(options){
				var that = this;
				options = $.extend({}, TableInner.prototype.defaults, typeof options == 'object'?  options : {});
				if(!options.tableId || options.tableId .length == 0 ||
				   !options.sAjaxSource || options.sAjaxSource.length == 0)
				{
					alert("tableId,sAjaxSource必须提供");
					return;
				}
				if(options.params && typeof options.params == "object")
				{
					this.params = options.params;
				}
				this.tableId = options.tableId;
				this.sAjaxSource = options.sAjaxSource;
				this.aaSorting = options.aaSorting;
				this.functionList = options.functionList;
				this.rawOptions = options.rawOptions;
				var cols = [{aTargets : [ '_all' ], "sDefaultContent" : "&nbsp;"}];
				var mData,bSortable,mRender,col;
				 
				$(this.tableId).find("th").each(function(index){
					if($(this).attr("data-ignore") == "true"){
						return;
					}
					       mData = $(this).attr("data-mData");
					       bSortable = $(this).attr("data-bSortable");
					       mRender = $(this).attr("data-mRender");
                           sFormatType = $(this).attr("data-sFormatType");
					       fnCreatedCell = $(this).attr("data-fnCreatedCell");
					       data_index = $(this).attr("data-index");
					col = {};
					if(mData){
						if(mData.match(/^_fn/i))
						{
							col.mData = that.functionList[mData];
						}else{
							col.mData = mData;
						}
					}
					
					if(bSortable){
						if(bSortable == "false")
						{
							bSortable = false;
						}	
						col.bSortable = bSortable;
					}
					
					if(mRender){
						col.mRender = that.functionList[mRender];
					}

                    if(sFormatType=="thousand"){
                        col.mRender = function(data){
                            return '<span class="table-align-right">'+numeral(data).format('0,0.00')+'</span>';
                        };
                    }
                    else if(sFormatType=="aprate"){
                        col.mRender = function(data){
                        	var num = parseFloat(data);
                            return !/\./.test(num) == true? num += ".0%":num +"%" ;
                        };
                    }
                    else if(sFormatType=="percent"){
                        col.mRender = function(data){
                            return numeral(data).format('0.0')+"%";
                        };
                    }
					if(fnCreatedCell){
						col.fnCreatedCell = that.functionList[fnCreatedCell];
					}
					if(data_index){
						index = data_index;
					}
					col.aTargets = [index];
					cols.push(col);
					
				});
			    this.aoColumns = cols;
				return this;
			},
			setParams: function(params){
				this.params=params;
			},
			getParams:function(){
				return this.params;
			},
			create: function(){
				var that = this;
				this.rawTable = $(that.tableId).dataTable($.extend({},{
					"bPaginate":true,
					"bFilter":false,
					"bSort" : true,
					"bInfo" : false,
					"bLengthChange": false,
					"iDisplayLength":10,
					"sPaginationType": "full_numbers",
			        "bServerSide": true,
			        "aaSorting":that.aaSorting,
			        "aoColumnDefs": that.aoColumns,
			        "sAjaxSource": that.sAjaxSource,
			        "fnServerData": function( sSource, aoData, fnCallback){
			        	var new_aoData = aoData.concat(that.params);
			     		$.ajax( {
			     			"type": "POST", 
			     			"url": sSource, 
			     			"contentType": 'application/json; charset=UTF-8',
			     			"dataType": "json",
			     			"data": util.stringify_aoData(new_aoData), 
			     			"success": function(resp) {
			     				fnCallback(resp); //服务器端返回的对象的returnObject部分是要求的格式
			     			}
			     		});
			         },    
			        "oLanguage" : {
						"sLengthMenu" : "每页显示 _MENU_ 条记录",
						"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条记录",
						"sInfoEmpty" : "没有数据",
						"oPaginate" : {
							"sFirst" : "首页",
							"sPrevious" : "前一页",
							"sNext" : "后一页",
							"sLast" : "尾页",
						},
						"sZeroRecords" : "暂无您要查询的数据！",
						"sProcessing": "loading..."
					},
				},that.rawOptions));
				return this;
			},
			invoke:function(method_name){
				var args =[];
				if(arguments.length>1)
				{
					for(var i=1;i<arguments.length;i++)
					{
						args.push(arguments[i]);
					}
				}else{
					args = null;
				}	 
				this.rawTable[method_name].apply(this.rawTable,args);
			}
	};
	
	TableInner.prototype.defaults = {
			sAjaxSource: "",
			tableId: "",
			params:[],
			aaSorting:[[0,"asc"]],
			functionList:{},
			rawTable:{}
	};
	return new DataTable();
});