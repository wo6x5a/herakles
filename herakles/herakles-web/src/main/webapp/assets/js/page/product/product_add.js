require(['jquery',
         'global',
         'module/util',
         'module/ajax',
	     'summernote',
	     'summernote-zh-CN',
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
	
	$('#summernote').summernote({
		lang: 'zh-CN',
		height: 150,
		focus: true,
		toolbar: [
		          ['style', ['style']],
		          ['style', ['bold', 'italic', 'underline', 'clear']],
		          ['fontsize', ['fontsize']],
		          ['color', ['color']],
		          ['para', ['ul', 'ol', 'paragraph']],
		          ['height', ['height']],
		          ['insert', ['picture', 'link']],
		          ['table', ['table']]
		        ],
		onInit: function(){
			$('.note-editor').attr({'data-error-prop':"true",'name':'body'});
		} 
	});
	
	
	$('#fileupload').fileupload({
    	url: global.context+"/web/product/pic/upload",
    	iframe: true,
    	autoUpload: true,
    	maxFileSize: 200000,
		dataType: 'json',
    	formData:null,
    	add: function(e, data) {
        	var maxFileSize = 2110000;
            if(data.originalFiles[0]['size'] && data.originalFiles[0]['size'] > maxFileSize) {
                $.pnotify({
                    text: "文件大小必须小于2M，请重新上传",
                    type: 'error'
                });
            }
            else{
                data.submit();
            }
        },
        success:function(resp){
    	if(resp.code == "ACK"){
    		var path = resp.message;
    		var objUrl= util.getObjectURL(this.files[0]);
    		$("#vcPicUrl").val(path);
    		$("#bannerPic").attr('src',objUrl); 
    	}
    	else if(resp.code == "NACK"){
    		$.pnotify({
                text: "文件大小必须小于2M，请重新上传",
                type: 'error'
            });
    	}
    }
    });

});
