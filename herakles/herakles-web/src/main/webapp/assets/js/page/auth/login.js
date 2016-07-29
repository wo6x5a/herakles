require(['jquery',
         'global',
         'module/util',
         'module/ajax',
         'jquery.validate',
         'bootstrap',
         'requirejs/domready!'], 
function($, global, util){
	
	// Add event handler for captcha image click
	$("#captcha-img").click(function() {
	    $(this).attr("src", global.context+ "/web/auth/captcha?r=" + Math.random());
	});
	
	// Add jQuery validate to sign-in form
	$('#signin-form').validate({
		submitHandler: function(form) {
			util.ajax_submit(form).complete(function(){
				$('#captcha-img').attr("src", 
						global.context+ "/web/auth/captcha?r=" + Math.random());
			});
	    },
	    highlight: function(element, errorClass, validClass) {
	    	$(element).addClass(errorClass).removeClass(validClass);
	    	$(element).parent().removeClass('has-success')
	    		.addClass('has-error').find('.fa').remove();
	    	$(element).parent().append('<i class="fa fa-exclamation-circle form-control-feedback"></i>');
	    },
	    unhighlight: function(element, errorClass, validClass) {
	    	$(element).removeClass(errorClass).addClass(validClass);
	    	$(element).parent().removeClass('has-error')
    			.addClass('has-success').find('.fa').remove();
	    	$(element).parent().append('<i class="fa fa-check form-control-feedback"></i>');
	    }
	});
	
});
