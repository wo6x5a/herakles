// Avoid `console` errors in browsers that lack a console.
(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }
}());

// Set jQuery pnotify plugin globally.
$.pnotify.defaults.history = false;
$.pnotify.defaults.width = '350px';
$.pnotify.defaults.styling = 'fontawesome';
$.pnotify.defaults.addclass = 'custom stack-bottomright';
$.pnotify.defaults.sticker = false;
$.pnotify.defaults.hide = true;
$.pnotify.defaults.stack = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25, "spacing1": 5};

// Set jQuery Validation plugin globally.
$.validator.setDefaults({
	debug: true,
	errorClass: 'invalid',
	validClass: 'valid'
});