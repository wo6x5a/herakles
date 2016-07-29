define(['jquery',
        'module/popup',
        'module/util',
        'jquery.pnotify', 
        'jquery.validate',
        'plugins'], 
function($, popup, util){
	
	$(document).ajaxError(function(event, xhr, settings, thrownError ) {
	    try{
		    var error = $.parseJSON(xhr.responseText);
		    if(error.code === 'SESSION_TIME_OUT'){
		    	popup({
			    	msg: error.message,
			    	title: 'Session Timeout',
			    	buttons: {
						'OK': {
							primary: true,
							callback: function(event){
								this.$element.modal('hide');
								window.location.reload();
							}
						}
					}
			    });
		    }else{
		    	popup({
			    	msg: error.message,
			    	detail: error.data
			    });
		    }
		}catch(Error){
			popup({
				title: 'JavaScript Error',
		    	msg:  Error || 'Unknown error occurred'
		    });
		}
	});
	
	$(document).ajaxSuccess(function(event, xhr, settings) {
		try{
			// Status 206 (Partial Content) indicates that the partial HTML string
			// are returned as the ajax result, we need avoid to parseJSON against
			// the HTML string.
			if(xhr.status != 206){
				var result = $.parseJSON(xhr.responseText);
				if(result && result.hasOwnProperty('code') &&
						result.hasOwnProperty('message') &&
						result.hasOwnProperty('data')){
					if( result.code === 'ACK'){
						$.pnotify({
						    text: result.message,
						    type: 'success'
						});
					}else if(result.code === 'NACK'){
						$.pnotify({
						    text: result.message,
						    type: 'error'
						});
					}else if(result.code === 'VALIDATION_ERROR'){
						$.pnotify({
						    text: result.message,
						    type: 'error'
						});
						$.each(result.data, function(i, obj){
							if(obj && obj.formId){
								var errors = {};
								if((obj.generalError || []).length !=0){
									$('#' + obj.formId).find('.general-error').show().text(
											(obj.generalError || []).join(','));
								}
								if(obj.fieldErrors){
									$('#' + obj.formId).find('input,textarea').each(function(){
										var name = $(this).attr('data-prop') || $(this).attr('name');
										if(name && obj.fieldErrors.hasOwnProperty(name)){
											errors[name] = obj.fieldErrors[name];
										}
									});
									$('#' + obj.formId).validate().showErrors(errors);
								}
							}
						});
					}else if(result.code === 'REDIRECT'){
						util.redirect(result.data);
					}
				}
			}
		}catch(Error){
			popup({
				title: 'JavaScript Error',
		    	msg:  Error || 'Unknown error occurred'
		    });
		}
	});
	
	return {};
});