require(['jquery',
         'global',
         'module/util',
         'module/ajax',
         'bootstrap',
         'requirejs/domready!'], 
function($, global, util){
	
	$('#product-form').validate({
		submitHandler: function(form) {
			util.ajax_submit("#product-form").complete(function(xhr) {
				var result = $.parseJSON(xhr.responseText);
				// TODO
			});
	    }
	});
	
	$('#cancel-btn').on('click', function() {
		util.redirect(global.context + '/web/product/view');
	});

});
