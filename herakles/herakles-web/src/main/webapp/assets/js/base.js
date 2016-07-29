require(['global'], function(global){
	require.config({
		baseUrl: global.context + '/assets/js',
		paths : {
			// the left side is the module ID, the right side is the path to
			// the jQuery file, relative to baseUrl. Also, the path should NOT include
			// the '.js' file extension. This example is using jQuery located at
			// vendor/jquery/jquery-1.11.0.js, relative to the HTML page.
			'jquery' : '../vendor/jquery/jquery-1.11.0',
			'bootstrap' : '../vendor/bootstrap/js/bootstrap',
			'jquery.ui' : '../vendor/jquery-ui/js/jquery-ui',
			'jquery.ui.widget':'../vendor/jquery-ui/js/jquery.ui.widget',
			'jquery.iframe-transport':'../vendor/jquery.iframe-transport/jquery.iframe-transport',
			'jquery.fileupload':'../vendor/jquery.fileupload/jquery.fileupload',
			'jquery.validate' : '../vendor/jquery-validation/jquery.validate',
			'jquery.spinner' : '../vendor/jquery.spinner/jquery.spinner',
			'jquery.pnotify' : '../vendor/pnotify/jquery.pnotify',
			'jquery-bridget/jquery.bridget': '../vendor/masonry/masonry.pkgd',
			'jquery.infinitescroll' : '../vendor/infinite-scroll/jquery.infinitescroll',
			'jquery.migrate':'../vendor/jquery.migrate/jquery.migrate',
			'holder' : '../vendor/holder/holder-2.3.1',
			'imagesloaded' : '../vendor/imagesloaded/imagesloaded.pkgd',
			'masonry' : '../vendor/masonry/masonry.pkgd',
			'modernizr' : '../vendor/modernizr/modernizr-2.7.1',
			'requirejs/text' : '../vendor/requirejs/plugins/text',
			'requirejs/i18n' : '../vendor/requirejs/plugins/i18n',
			'requirejs/domready' : '../vendor/requirejs/plugins/domReady',
			'requirejs/cs' : '../vendor/requirejs/plugins/cs',
			'datatables' : '../vendor/datatables/js/jquery.dataTables',
			'columnfilter': '../vendor/datatables/js/ColumnFilter',
			'fixedcolumns':'../vendor/dataTables.fixedColumns/js/dataTables.fixedColumns',
            'summernote' : '../vendor/summernote/js/summernote.min',
            'summernote-zh-CN' : '../vendor/summernote/js/summernote-zh-CN',
			'responsive.nav':'../vendor/responsive-nav/responsive-nav'
		},
		// Remember: only use shim config for non-AMD scripts,
		// scripts that do not already call define(). The shim
		// config will not work correctly if used on AMD scripts,
		// in particular, the exports and init config will not
		// be triggered, and the deps config will be confusing
		// for those cases.
		shim : {
			'modernizr': {
				// Once loaded, use the global 'Holder' as the
				// module value.
				exports: 'Modernizr'
			},
			'holder': {
				// Once loaded, use the global 'Holder' as the
				// module value.
				exports: 'Holder'
			},
			'bootstrap': {
				// These script dependencies should be loaded before loading
				// bootstrap modual
				deps : [ 'jquery' ]
			},
			'jquery.ui': {
				deps : [ 'jquery' ]
			},
			'jquery.iframe-transport' : {
				deps : [ 'jquery' ]
			},
			'jquery.fileupload' : {
				deps : ['jquery','jquery.ui','jquery.iframe-transport' ]
			},
			'jquery.validate': {
				deps : [ 'jquery' ]
			},
			'jquery.spinner' : {
				deps : [ 'jquery' ]
			},
			'jquery.pnotify' : {
				deps : [ 'jquery' ]
			},
			'jquery.infinitescroll' : {
				deps : [ 'jquery' ]
			},
			'plugins': {
				deps : [ 'jquery.pnotify' ]
			},
			'datatables': {
				// These script dependencies should be loaded before loading
				// bootstrap modual
				deps : [ 'jquery' ]
			},
			'jquery.migrate':{
	            deps : ['jquery']
	        },
			'fixedcolumns':{
                deps : ['jquery','datatables', 'jquery.migrate']
            },
            'summernote' : {
            	deps : ['jquery','bootstrap']
            },
            'summernote-zh-CN' : {
            	deps : ['summernote']
            },
	        'columnfilter':{
                deps : ['jquery','datatables']
            }
		}
	});
});