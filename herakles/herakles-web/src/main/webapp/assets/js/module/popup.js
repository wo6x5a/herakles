
define(['jquery', 'bootstrap'], function($){
	
	var Popup = function(){
	};
	
	Popup.prototype = {
		
		init: function(){
			var $this = this;
			return function(options){
				return $this.popup.apply($this, [options]);
			};
		},
		
		popup: function(options){
			return (new Win()).init(options).create();
		}
	};
	
	var Win = function(){
		this.msg = '';
		this.level = '';
		this.title = '';
		this.detail = '';
		this.buttons = {};
	};
	
	Win.prototype = {
			
		constructor: Win,
		
		init: function(options){
			options = $.extend({}, Win.prototype.defaults, typeof options == 'object'?  options : {});
			this.id = new Date().getTime();
			this.level = options.level;
			this.title = options.title;
			this.msg = options.msg;
			this.detail = options.detail;
			this.buttons = options.buttons;
			this.$element = null;
			return this;
		},
			
		create: function(){
			var $this = this;
			var html = '<div id="'+this.id+'" class="modal fade error-modal" tabindex="-1" aria-hidden="true" role="dialog"><div class="modal-dialog modal-lg"><div class="modal-content">'+
				'<div class="modal-header">'+
					'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
					'<h4 class="modal-title">'+this.title+'</h4>'+
				'</div>'+
				'<div class="modal-body">'+
				
				'<div class="panel last-block panel-'+this.level+'">' +
				    '<div class="panel-heading">' +
				        '<h4 class="panel-title">' +
					        '<a data-toggle="collapse" data-parent="#'+this.id+' .modal-body" href="#'+this.id+'-error-detail">'+
					        	this.msg +
					        '</a>'+
				        '</h4>'+
				    '</div>'+
				    '<div id="'+this.id+'-error-detail" class="panel-collapse collapse">'+
				        '<div class="panel-body">'+
				            this.detail + 
				        '</div>' +
				    '</div>' +
			    '</div>' +
				
				'</div>'+
				'<div class="modal-footer">'+
					'<span class="loading-small hide pull-left"></span>';
			
			for(var prop in this.buttons){
				var btn = this.buttons[prop];
				if(typeof btn == 'object'){
					html += '<button class="btn ' + (btn.primary? 'btn-primary':'') +'">'+prop+'</button>';
				}else{
					html += '<button class="btn">'+prop+'</button>';
				}
			}
			
			html+='</div>'+
			'</div></div></div>';
			
			this.$element = $(html).appendTo('body')
			.on('click', '.btn', $.proxy($this.click, $this))
			.on('hidden.bs.modal', $.proxy($this.destroy, $this))
			.modal({
				keyboard: false,
				backdrop: 'static'
			});
			},
			
			destroy: function(event){
				this.$element.off('click', '.btn', this.click);
				this.$element.off('hidden.bs.modal', this.destroy);
				$('#'+this.id).remove();
			},
			
			click: function(event){
				var $btn = $(event.target);
				if(!$btn.is('button')){
					$btn = $btn.parents('button');
				}
				var label = $btn.text();
				var btn = this.buttons[label];
				var fn = null;
				if(typeof btn == 'object'){
					fn = btn.callback;
				}else{
					fn = btn;
				}
				fn.apply(this, [event]);
			}
		};
		
	Win.prototype.defaults = {
		msg : 'Server internal error',
		level : 'danger',
		title: 'Server Error',
		detail: 'No detail stack trace',
		buttons: {
			'OK': {
				primary: true,
				callback: function(event){
					this.$element.modal('hide');
				}
			}
		}
	};
		
	var popup = null;
	if(!popup){
		popup = (new Popup()).init();
	}
	
	return popup;
});