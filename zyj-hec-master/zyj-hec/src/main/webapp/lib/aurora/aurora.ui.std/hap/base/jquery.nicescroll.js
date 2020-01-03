(function(factory) {
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory)
    } else if (typeof exports === 'object') {
        module.exports = factory(require('jquery'))
    } else {
        factory(jQuery)
    }
} (function(jQuery) {
    "use strict";
    var domfocus = false,
    mousefocus = false,
    tabindexcounter = 0,
    ascrailcounter = 2000,
    globalmaxzindex = 0;
    var $ = jQuery,
    _doc = document,
    _win = window,
    $window = $(_win);
    var delegatevents = [];
    function getScriptPath() {
        var scripts = _doc.currentScript || (function() {
            var s = _doc.getElementsByTagName('script');
            return (s.length) ? s[s.length - 1] : false
        })();
        var path = scripts ? scripts.src.split('?')[0] : '';
        return (path.split('/').length > 0) ? path.split('/').slice(0, -1).join('/') + '/': ''
    }
    var setAnimationFrame = _win.requestAnimationFrame || _win.webkitRequestAnimationFrame || _win.mozRequestAnimationFrame || false;
    var clearAnimationFrame = _win.cancelAnimationFrame || _win.webkitCancelAnimationFrame || _win.mozCancelAnimationFrame || false;
    if (!setAnimationFrame) {
        var anilasttime = 0;
        setAnimationFrame = function(callback, element) {
            var currTime = new Date().getTime();
            var timeToCall = Math.max(0, 16 - (currTime - anilasttime));
            var id = _win.setTimeout(function() {
                callback(currTime + timeToCall)
            },
            timeToCall);
            anilasttime = currTime + timeToCall;
            return id
        };
        clearAnimationFrame = function(id) {
            _win.clearTimeout(id)
        }
    } else {
        if (!_win.cancelAnimationFrame) clearAnimationFrame = function(id) {}
    }
    var ClsMutationObserver = _win.MutationObserver || _win.WebKitMutationObserver || false;
    var now = Date.now ||
    function() {
        return new Date().getTime()
    };
    var _globaloptions = {
        zindex: "auto",
        cursoropacitymin: 0,
        cursoropacitymax: 1,
        cursorcolor: "#ccc",
        cursorwidth: "6px",
        cursorborder: "1px solid #fff",
        cursorborderradius: "5px",
        scrollspeed: 40,
        mousescrollstep: 9 * 3,
        touchbehavior: false,
        emulatetouch: false,
        hwacceleration: true,
        usetransition: true,
        boxzoom: false,
        dblclickzoom: true,
        gesturezoom: true,
        grabcursorenabled: true,
        autohidemode: true,
        background: "",
        iframeautoresize: true,
        cursorminheight: 32,
        preservenativescrolling: true,
        railoffset: false,
        railhoffset: false,
        bouncescroll: true,
        spacebarenabled: true,
        railpadding: {
            top: 0,
            right: 0,
            left: 0,
            bottom: 0
        },
        disableoutline: true,
        horizrailenabled: true,
        railalign: "right",
        railvalign: "bottom",
        enabletranslate3d: true,
        enablemousewheel: true,
        enablekeyboard: true,
        smoothscroll: true,
        sensitiverail: true,
        enablemouselockapi: true,
        cursorfixedheight: false,
        directionlockdeadzone: 6,
        hidecursordelay: 400,
        nativeparentscrolling: true,
        enablescrollonselection: true,
        overflowx: true,
        overflowy: true,
        cursordragspeed: 0.3,
        rtlmode: "auto",
        cursordragontouch: false,
        oneaxismousemode: "auto",
        scriptpath: getScriptPath(),
        preventmultitouchscrolling: true,
        disablemutationobserver: false,
        enableobserver: true,
        scrollbarid: false
    };
    var browserdetected = false;
    var getBrowserDetection = function() {
        if (browserdetected) return browserdetected;
        var _el = _doc.createElement('DIV'),
        _style = _el.style,
        _agent = navigator.userAgent,
        _platform = navigator.platform,
        d = {};
        d.haspointerlock = "pointerLockElement" in _doc || "webkitPointerLockElement" in _doc || "mozPointerLockElement" in _doc;
        d.isopera = ("opera" in _win);
        d.isopera12 = (d.isopera && ("getUserMedia" in navigator));
        d.isoperamini = (Object.prototype.toString.call(_win.operamini) === "[object OperaMini]");
        d.isie = (("all" in _doc) && ("attachEvent" in _el) && !d.isopera);
        d.isieold = (d.isie && !("msInterpolationMode" in _style));
        d.isie7 = d.isie && !d.isieold && (!("documentMode" in _doc) || (_doc.documentMode === 7));
        d.isie8 = d.isie && ("documentMode" in _doc) && (_doc.documentMode === 8);
        d.isie9 = d.isie && ("performance" in _win) && (_doc.documentMode === 9);
        d.isie10 = d.isie && ("performance" in _win) && (_doc.documentMode === 10);
        d.isie11 = ("msRequestFullscreen" in _el) && (_doc.documentMode >= 11);
        d.ismsedge = ("msCredentials" in _win);
        d.ismozilla = ("MozAppearance" in _style);
        d.iswebkit = !d.ismsedge && ("WebkitAppearance" in _style);
        d.ischrome = d.iswebkit && ("chrome" in _win);
        d.ischrome38 = (d.ischrome && ("touchAction" in _style));
        d.ischrome22 = (!d.ischrome38) && (d.ischrome && d.haspointerlock);
        d.ischrome26 = (!d.ischrome38) && (d.ischrome && ("transition" in _style));
        d.cantouch = ("ontouchstart" in _doc.documentElement) || ("ontouchstart" in _win);
        d.hasw3ctouch = (_win.PointerEvent || false) && ((navigator.maxTouchPoints > 0) || (navigator.msMaxTouchPoints > 0));
        d.hasmstouch = (!d.hasw3ctouch) && (_win.MSPointerEvent || false);
        d.ismac = /^mac$/i.test(_platform);
        d.isios = d.cantouch && /iphone|ipad|ipod/i.test(_platform);
        d.isios4 = d.isios && !("seal" in Object);
        d.isios7 = d.isios && ("webkitHidden" in _doc);
        d.isios8 = d.isios && ("hidden" in _doc);
        d.isios10 = d.isios && _win.Proxy;
        d.isandroid = (/android/i.test(_agent));
        d.haseventlistener = ("addEventListener" in _el);
        d.trstyle = false;
        d.hastransform = false;
        d.hastranslate3d = false;
        d.transitionstyle = false;
        d.hastransition = false;
        d.transitionend = false;
        d.trstyle = "transform";
        d.hastransform = ("transform" in _style) || (function() {
            var check = ['msTransform', 'webkitTransform', 'MozTransform', 'OTransform'];
            for (var a = 0,
            c = check.length; a < c; a++) {
                if (_style[check[a]] !== undefined) {
                    d.trstyle = check[a];
                    break
                }
            }
            d.hastransform = ( !! d.trstyle)
        })();
        if (d.hastransform) {
            _style[d.trstyle] = "translate3d(1px,2px,3px)";
            d.hastranslate3d = /translate3d/.test(_style[d.trstyle])
        }
        d.transitionstyle = "transition";
        d.prefixstyle = '';
        d.transitionend = "transitionend";
        d.hastransition = ("transition" in _style) || (function() {
            d.transitionend = false;
            var check = ['webkitTransition', 'msTransition', 'MozTransition', 'OTransition', 'OTransition', 'KhtmlTransition'];
            var prefix = ['-webkit-', '-ms-', '-moz-', '-o-', '-o', '-khtml-'];
            var evs = ['webkitTransitionEnd', 'msTransitionEnd', 'transitionend', 'otransitionend', 'oTransitionEnd', 'KhtmlTransitionEnd'];
            for (var a = 0,
            c = check.length; a < c; a++) {
                if (check[a] in _style) {
                    d.transitionstyle = check[a];
                    d.prefixstyle = prefix[a];
                    d.transitionend = evs[a];
                    break
                }
            }
            if (d.ischrome26) d.prefixstyle = prefix[1];
            d.hastransition = (d.transitionstyle)
        })();
        function detectCursorGrab() {
//            var lst = ['grab', '-webkit-grab', '-moz-grab'];
//            if ((d.ischrome && !d.ischrome38) || d.isie) lst = [];
////            for (var a = 0,
////            l = lst.length; a < l; a++) {
////                var p = lst[a];
//////                _style.cursor = p;
//////                if (_style.cursor == p) return p
////            }
//            return 'url(https://cdnjs.cloudflare.com/ajax/libs/slider-pro/1.3.0/css/images/openhand.cur),n-resize'
        }
        d.cursorgrabvalue = detectCursorGrab();
        d.hasmousecapture = ("setCapture" in _el);
        d.hasMutationObserver = (ClsMutationObserver !== false);
        _el = null;
        browserdetected = d;
        return d
    };
    var NiceScrollClass = function(myopt, me) {
        var self = this;
        this.version = '3.7.6';
        this.name = 'nicescroll';
        this.me = me;
        var $body = $("body");
        var opt = this.opt = {
            doc: $body,
            win: false
        };
        $.extend(opt, _globaloptions);
        opt.snapbackspeed = 80;
        if (myopt || false) {
            for (var a in opt) {
                if (myopt[a] !== undefined) opt[a] = myopt[a]
            }
        }
        if (opt.disablemutationobserver) ClsMutationObserver = false;
        this.doc = opt.doc;
        this.iddoc = (this.doc && this.doc[0]) ? this.doc[0].id || '': '';
        this.ispage = /^BODY|HTML/.test((opt.win) ? opt.win[0].nodeName: this.doc[0].nodeName);
        this.haswrapper = (opt.win !== false);
        this.win = opt.win || (this.ispage ? $window: this.doc);
        this.docscroll = (this.ispage && !this.haswrapper) ? $window: this.win;
        this.body = $body;
        this.viewport = false;
        this.isfixed = false;
        this.iframe = false;
        this.isiframe = ((this.doc[0].nodeName == 'IFRAME') && (this.win[0].nodeName == 'IFRAME'));
        this.istextarea = (this.win[0].nodeName == 'TEXTAREA');
        this.forcescreen = false;
        this.canshowonmouseevent = (opt.autohidemode != "scroll");
        this.onmousedown = false;
        this.onmouseup = false;
        this.onmousemove = false;
        this.onmousewheel = false;
        this.onkeypress = false;
        this.ongesturezoom = false;
        this.onclick = false;
        this.onscrollstart = false;
        this.onscrollend = false;
        this.onscrollcancel = false;
        this.onzoomin = false;
        this.onzoomout = false;
        this.view = false;
        this.page = false;
        this.scroll = {
            x: 0,
            y: 0
        };
        this.scrollratio = {
            x: 0,
            y: 0
        };
        this.cursorheight = 20;
        this.scrollvaluemax = 0;
        if (opt.rtlmode == "auto") {
            var target = this.win[0] == _win ? this.body: this.win;
            var writingMode = target.css("writing-mode") || target.css("-webkit-writing-mode") || target.css("-ms-writing-mode") || target.css("-moz-writing-mode");
            if (writingMode == "horizontal-tb" || writingMode == "lr-tb" || writingMode === "") {
                this.isrtlmode = (target.css("direction") == "rtl");
                this.isvertical = false
            } else {
                this.isrtlmode = (writingMode == "vertical-rl" || writingMode == "tb" || writingMode == "tb-rl" || writingMode == "rl-tb");
                this.isvertical = (writingMode == "vertical-rl" || writingMode == "tb" || writingMode == "tb-rl")
            }
        } else {
            this.isrtlmode = (opt.rtlmode === true);
            this.isvertical = false
        }
        this.scrollrunning = false;
        this.scrollmom = false;
        this.observer = false;
        this.observerremover = false;
        this.observerbody = false;
        if (opt.scrollbarid !== false) {
            this.id = opt.scrollbarid
        } else {
            do {
                this.id = "ascrail" + (ascrailcounter++)
            } while ( _doc . getElementById ( this . id ))
        }
        this.rail = false;
        this.cursor = false;
        this.cursorfreezed = false;
        this.selectiondrag = false;
        this.zoom = false;
        this.zoomactive = false;
        this.hasfocus = false;
        this.hasmousefocus = false;
        this.railslocked = false;
        this.locked = false;
        this.hidden = false;
        this.cursoractive = true;
        this.wheelprevented = false;
        this.overflowx = opt.overflowx;
        this.overflowy = opt.overflowy;
        this.nativescrollingarea = false;
        this.checkarea = 0;
        this.events = [];
        this.saved = {};
        this.delaylist = {};
        this.synclist = {};
        this.lastdeltax = 0;
        this.lastdeltay = 0;
        this.detected = getBrowserDetection();
        var cap = $.extend({},
        this.detected);
        this.canhwscroll = (cap.hastransform && opt.hwacceleration);
        this.ishwscroll = (this.canhwscroll && self.haswrapper);
        if (!this.isrtlmode) {
            this.hasreversehr = false
        } else if (this.isvertical) {
            this.hasreversehr = !(cap.iswebkit || cap.isie || cap.isie11)
        } else {
            this.hasreversehr = !(cap.iswebkit || (cap.isie && !cap.isie10 && !cap.isie11))
        }
        this.istouchcapable = false;
        if (!cap.cantouch && (cap.hasw3ctouch || cap.hasmstouch)) {
            this.istouchcapable = true
        } else if (cap.cantouch && !cap.isios && !cap.isandroid && (cap.iswebkit || cap.ismozilla)) {
            this.istouchcapable = true
        }
        if (!opt.enablemouselockapi) {
            cap.hasmousecapture = false;
            cap.haspointerlock = false
        }
        this.debounced = function(name, fn, tm) {
            if (!self) return;
            var dd = self.delaylist[name] || false;
            if (!dd) {
                self.delaylist[name] = {
                    h: setAnimationFrame(function() {
                        self.delaylist[name].fn.call(self);
                        self.delaylist[name] = false
                    },
                    tm)
                };
                fn.call(self)
            }
            self.delaylist[name].fn = fn
        };
        this.synched = function(name, fn) {
            if (self.synclist[name]) self.synclist[name] = fn;
            else {
                self.synclist[name] = fn;
                setAnimationFrame(function() {
                    if (!self) return;
                    self.synclist[name] && self.synclist[name].call(self);
                    self.synclist[name] = null
                })
            }
        };
        this.unsynched = function(name) {
            if (self.synclist[name]) self.synclist[name] = false
        };
        this.css = function(el, pars) {
            for (var n in pars) {
                self.saved.css.push([el, n, el.css(n)]);
                el.css(n, pars[n])
            }
        };
        this.scrollTop = function(val) {
            return (val === undefined) ? self.getScrollTop() : self.setScrollTop(val)
        };
        this.scrollLeft = function(val) {
            return (val === undefined) ? self.getScrollLeft() : self.setScrollLeft(val)
        };
        var BezierClass = function(st, ed, spd, p1, p2, p3, p4) {
            this.st = st;
            this.ed = ed;
            this.spd = spd;
            this.p1 = p1 || 0;
            this.p2 = p2 || 1;
            this.p3 = p3 || 0;
            this.p4 = p4 || 1;
            this.ts = now();
            this.df = ed - st
        };
        BezierClass.prototype = {
            B2: function(t) {
                return 3 * (1 - t) * (1 - t) * t
            },
            B3: function(t) {
                return 3 * (1 - t) * t * t
            },
            B4: function(t) {
                return t * t * t
            },
            getPos: function() {
                return (now() - this.ts) / this.spd
            },
            getNow: function() {
                var pc = (now() - this.ts) / this.spd;
                var bz = this.B2(pc) + this.B3(pc) + this.B4(pc);
                return (pc >= 1) ? this.ed: this.st + (this.df * bz) | 0
            },
            update: function(ed, spd) {
                this.st = this.getNow();
                this.ed = ed;
                this.spd = spd;
                this.ts = now();
                this.df = this.ed - this.st;
                return this
            }
        };
        function getMatrixValues() {
            var tr = self.doc.css(cap.trstyle);
            if (tr && (tr.substr(0, 6) == "matrix")) {
                return tr.replace(/^.*\((.*)\)$/g, "$1").replace(/px/g, '').split(/, +/)
            }
            return false
        }
        if (this.ishwscroll) {
            this.doc.translate = {
                x: 0,
                y: 0,
                tx: "0px",
                ty: "0px"
            };
            if (cap.hastranslate3d && cap.isios) this.doc.css("-webkit-backface-visibility", "hidden");
            this.getScrollTop = function(last) {
                if (!last) {
                    var mtx = getMatrixValues();
                    if (mtx) return (mtx.length == 16) ? -mtx[13] : -mtx[5];
                    if (self.timerscroll && self.timerscroll.bz) return self.timerscroll.bz.getNow()
                }
                return self.doc.translate.y
            };
            this.getScrollLeft = function(last) {
                if (!last) {
                    var mtx = getMatrixValues();
                    if (mtx) return (mtx.length == 16) ? -mtx[12] : -mtx[4];
                    if (self.timerscroll && self.timerscroll.bh) return self.timerscroll.bh.getNow()
                }
                return self.doc.translate.x
            };
            this.notifyScrollEvent = function(el) {
                var e = _doc.createEvent("UIEvents");
                e.initUIEvent("scroll", false, false, _win, 1);
                e.niceevent = true;
                el.dispatchEvent(e)
            };
            var cxscrollleft = (this.isrtlmode) ? 1 : -1;
            if (cap.hastranslate3d && opt.enabletranslate3d) {
                this.setScrollTop = function(val, silent) {
                    self.doc.translate.y = val;
                    self.doc.translate.ty = (val * -1) + "px";
                    self.doc.css(cap.trstyle, "translate3d(" + self.doc.translate.tx + "," + self.doc.translate.ty + ",0)");
                    if (!silent) self.notifyScrollEvent(self.win[0])
                };
                this.setScrollLeft = function(val, silent) {
                    self.doc.translate.x = val;
                    self.doc.translate.tx = (val * cxscrollleft) + "px";
                    self.doc.css(cap.trstyle, "translate3d(" + self.doc.translate.tx + "," + self.doc.translate.ty + ",0)");
                    if (!silent) self.notifyScrollEvent(self.win[0])
                }
            } else {
                this.setScrollTop = function(val, silent) {
                    self.doc.translate.y = val;
                    self.doc.translate.ty = (val * -1) + "px";
                    self.doc.css(cap.trstyle, "translate(" + self.doc.translate.tx + "," + self.doc.translate.ty + ")");
                    if (!silent) self.notifyScrollEvent(self.win[0])
                };
                this.setScrollLeft = function(val, silent) {
                    self.doc.translate.x = val;
                    self.doc.translate.tx = (val * cxscrollleft) + "px";
                    self.doc.css(cap.trstyle, "translate(" + self.doc.translate.tx + "," + self.doc.translate.ty + ")");
                    if (!silent) self.notifyScrollEvent(self.win[0])
                }
            }
        } else {
            this.getScrollTop = function() {
                return self.docscroll.scrollTop()
            };
            this.setScrollTop = function(val) {
                self.docscroll.scrollTop(val)
            };
            this.getScrollLeft = function() {
                var val;
                if (!self.hasreversehr) {
                    val = self.docscroll.scrollLeft()
                } else if (self.detected.ismozilla) {
                    val = self.page.maxw - Math.abs(self.docscroll.scrollLeft())
                } else {
                    val = self.page.maxw - self.docscroll.scrollLeft()
                }
                return val
            };
            this.setScrollLeft = function(val) {
                return setTimeout(function() {
                    if (!self) return;
                    if (self.hasreversehr) {
                        if (self.detected.ismozilla) {
                            val = -(self.page.maxw - val)
                        } else {
                            val = self.page.maxw - val
                        }
                    }
                    return self.docscroll.scrollLeft(val)
                },
                1)
            }
        }
        this.getTarget = function(e) {
            if (!e) return false;
            if (e.target) return e.target;
            if (e.srcElement) return e.srcElement;
            return false
        };
        this.hasParent = function(e, id) {
            if (!e) return false;
            var el = e.target || e.srcElement || e || false;
            while (el && el.id != id) {
                el = el.parentNode || false
            }
            return (el !== false)
        };
        function getZIndex() {
            var dom = self.win;
            if ("zIndex" in dom) return dom.zIndex();
            while (dom.length > 0) {
                if (dom[0].nodeType == 9) return false;
                var zi = dom.css('zIndex');
                if (!isNaN(zi) && zi !== 0) return parseInt(zi);
                dom = dom.parent()
            }
            return false
        }
        var _convertBorderWidth = {
            "thin": 1,
            "medium": 3,
            "thick": 5
        };
        function getWidthToPixel(dom, prop, chkheight) {
            var wd = dom.css(prop);
            var px = parseFloat(wd);
            if (isNaN(px)) {
                px = _convertBorderWidth[wd] || 0;
                var brd = (px == 3) ? ((chkheight) ? (self.win.outerHeight() - self.win.innerHeight()) : (self.win.outerWidth() - self.win.innerWidth())) : 1;
                if (self.isie8 && px) px += 1;
                return (brd) ? px: 0
            }
            return px
        }
        this.getDocumentScrollOffset = function() {
            return {
                top: _win.pageYOffset || _doc.documentElement.scrollTop,
                left: _win.pageXOffset || _doc.documentElement.scrollLeft
            }
        };
        this.getOffset = function() {
            if (self.isfixed) {
                var ofs = self.win.offset();
                var scrl = self.getDocumentScrollOffset();
                ofs.top -= scrl.top;
                ofs.left -= scrl.left;
                return ofs
            }
            var ww = self.win.offset();
            if (!self.viewport) return ww;
            var vp = self.viewport.offset();
            return {
                top: ww.top - vp.top,
                left: ww.left - vp.left
            }
        };
        this.updateScrollBar = function(len) {
            var pos, off;
            if (self.ishwscroll) {
                self.rail.css({
                    height: self.win.innerHeight() - (opt.railpadding.top + opt.railpadding.bottom)
                });
                if (self.railh) self.railh.css({
                    width: self.win.innerWidth() - (opt.railpadding.left + opt.railpadding.right)
                })
            } else {
                var wpos = self.getOffset();
                pos = {
                    top: wpos.top,
                    left: wpos.left - (opt.railpadding.left + opt.railpadding.right)
                };
                pos.top += getWidthToPixel(self.win, 'border-top-width', true);
                pos.left += (self.rail.align) ? self.win.outerWidth() - getWidthToPixel(self.win, 'border-right-width') - self.rail.width: getWidthToPixel(self.win, 'border-left-width');
                off = opt.railoffset;
                if (off) {
                    if (off.top) pos.top += off.top;
                    if (off.left) pos.left += off.left
                }
                if (!self.railslocked) self.rail.css({
                    top: pos.top,
                    left: pos.left,
                    height: ((len) ? len.h: self.win.innerHeight()) - (opt.railpadding.top + opt.railpadding.bottom)
                });
                if (self.zoom) {
                    self.zoom.css({
                        top: pos.top + 1,
                        left: (self.rail.align == 1) ? pos.left - 20 : pos.left + self.rail.width + 4
                    })
                }
                if (self.railh && !self.railslocked) {
                    pos = {
                        top: wpos.top,
                        left: wpos.left
                    };
                    off = opt.railhoffset;
                    if (off) {
                        if (off.top) pos.top += off.top;
                        if (off.left) pos.left += off.left
                    }
                    var y = (self.railh.align) ? pos.top + getWidthToPixel(self.win, 'border-top-width', true) + self.win.innerHeight() - self.railh.height: pos.top + getWidthToPixel(self.win, 'border-top-width', true);
                    var x = pos.left + getWidthToPixel(self.win, 'border-left-width');
                    self.railh.css({
                        top: y - (opt.railpadding.top + opt.railpadding.bottom),
                        left: x,
                        width: self.railh.width
                    })
                }
            }
        };
        this.doRailClick = function(e, dbl, hr) {
            var fn, pg, cur, pos;
            if (self.railslocked) return;
            self.cancelEvent(e);
            if (! ("pageY" in e)) {
                e.pageX = e.clientX + _doc.documentElement.scrollLeft;
                e.pageY = e.clientY + _doc.documentElement.scrollTop
            }
            if (dbl) {
                fn = (hr) ? self.doScrollLeft: self.doScrollTop;
                cur = (hr) ? ((e.pageX - self.railh.offset().left - (self.cursorwidth / 2)) * self.scrollratio.x) : ((e.pageY - self.rail.offset().top - (self.cursorheight / 2)) * self.scrollratio.y);
                self.unsynched("relativexy");
                fn(cur | 0)
            } else {
                fn = (hr) ? self.doScrollLeftBy: self.doScrollBy;
                cur = (hr) ? self.scroll.x: self.scroll.y;
                pos = (hr) ? e.pageX - self.railh.offset().left: e.pageY - self.rail.offset().top;
                pg = (hr) ? self.view.w: self.view.h;
                fn((cur >= pos) ? pg: -pg)
            }
        };
        self.newscrolly = self.newscrollx = 0;
        self.hasanimationframe = ("requestAnimationFrame" in _win);
        self.hascancelanimationframe = ("cancelAnimationFrame" in _win);
        self.hasborderbox = false;
        this.init = function() {
            self.saved.css = [];
            if (cap.isoperamini) return true;
            if (cap.isandroid && !("hidden" in _doc)) return true;
            opt.emulatetouch = opt.emulatetouch || opt.touchbehavior;
            self.hasborderbox = _win.getComputedStyle && (_win.getComputedStyle(_doc.body)['box-sizing'] === "border-box");
            var _scrollyhidden = {
                'overflow-y': 'hidden'
            };
            if (cap.isie11 || cap.isie10) _scrollyhidden['-ms-overflow-style'] = 'none';
            if (self.ishwscroll) {
                this.doc.css(cap.transitionstyle, cap.prefixstyle + 'transform 0ms ease-out');
                if (cap.transitionend) self.bind(self.doc, cap.transitionend, self.onScrollTransitionEnd, false)
            }
            self.zindex = "auto";
            if (!self.ispage && opt.zindex == "auto") {
                self.zindex = getZIndex() || "auto"
            } else {
                self.zindex = opt.zindex
            }
            if (!self.ispage && self.zindex != "auto" && self.zindex > globalmaxzindex) {
                globalmaxzindex = self.zindex
            }
            if (self.isie && self.zindex === 0 && opt.zindex == "auto") {
                self.zindex = "auto"
            }
            if (!self.ispage || !cap.isieold) {
                var cont = self.docscroll;
                if (self.ispage) cont = (self.haswrapper) ? self.win: self.doc;
                self.css(cont, _scrollyhidden);
                if (self.ispage && (cap.isie11 || cap.isie)) {
                    self.css($("html"), _scrollyhidden)
                }
                if (cap.isios && !self.ispage && !self.haswrapper) self.css($body, {
                    "-webkit-overflow-scrolling": "touch"
                });
                var cursor = $(_doc.createElement('div'));
                cursor.css({
                    position: "relative",
                    top: 0,
                    "float": "right",
                    width: opt.cursorwidth,
                    height: 0,
                    'background-color': opt.cursorcolor,
                    border: opt.cursorborder,
                    'background-clip': 'padding-box',
                    '-webkit-border-radius': opt.cursorborderradius,
                    '-moz-border-radius': opt.cursorborderradius,
                    'border-radius': opt.cursorborderradius
                });
                cursor.addClass('nicescroll-cursors');
                self.cursor = cursor;
                var rail = $(_doc.createElement('div'));
                rail.attr('id', self.id);
                rail.addClass('nicescroll-rails nicescroll-rails-vr');
                var v, a, kp = ["left", "right", "top", "bottom"];
                for (var n in kp) {
                    a = kp[n];
                    v = opt.railpadding[a] || 0;
                    v && rail.css("padding-" + a, v + "px")
                }
                rail.append(cursor);
                rail.width = Math.max(parseFloat(opt.cursorwidth), cursor.outerWidth());
                rail.css({
                    width: rail.width + "px",
                    zIndex: self.zindex,
                    background: opt.background,
                    cursor: "default"
                });
                rail.visibility = true;
                rail.scrollable = true;
                rail.align = (opt.railalign == "left") ? 0 : 1;
                self.rail = rail;
                self.rail.drag = false;
                var zoom = false;
                if (opt.boxzoom && !self.ispage && !cap.isieold) {
                    zoom = _doc.createElement('div');
                    self.bind(zoom, "click", self.doZoom);
                    self.bind(zoom, "mouseenter",
                    function() {
                        self.zoom.css('opacity', opt.cursoropacitymax)
                    });
                    self.bind(zoom, "mouseleave",
                    function() {
                        self.zoom.css('opacity', opt.cursoropacitymin)
                    });
                    self.zoom = $(zoom);
                    self.zoom.css({
                        cursor: "pointer",
                        zIndex: self.zindex,
                        backgroundImage: 'url(' + opt.scriptpath + 'zoomico.png)',
                        height: 18,
                        width: 18,
                        backgroundPosition: '0 0'
                    });
                    if (opt.dblclickzoom) self.bind(self.win, "dblclick", self.doZoom);
                    if (cap.cantouch && opt.gesturezoom) {
                        self.ongesturezoom = function(e) {
                            if (e.scale > 1.5) self.doZoomIn(e);
                            if (e.scale < 0.8) self.doZoomOut(e);
                            return self.cancelEvent(e)
                        };
                        self.bind(self.win, "gestureend", self.ongesturezoom)
                    }
                }
                self.railh = false;
                var railh;
                if (opt.horizrailenabled) {
                    self.css(cont, {
                        overflowX: 'hidden'
                    });
                    cursor = $(_doc.createElement('div'));
                    cursor.css({
                        position: "absolute",
                        top: 0,
                        height: opt.cursorwidth,
                        width: 0,
                        backgroundColor: opt.cursorcolor,
                        border: opt.cursorborder,
                        backgroundClip: 'padding-box',
                        '-webkit-border-radius': opt.cursorborderradius,
                        '-moz-border-radius': opt.cursorborderradius,
                        'border-radius': opt.cursorborderradius
                    });
                    if (cap.isieold) cursor.css('overflow', 'hidden');
                    cursor.addClass('nicescroll-cursors');
                    self.cursorh = cursor;
                    railh = $(_doc.createElement('div'));
                    railh.attr('id', self.id + '-hr');
                    railh.addClass('nicescroll-rails nicescroll-rails-hr');
                    railh.height = Math.max(parseFloat(opt.cursorwidth), cursor.outerHeight());
                    railh.css({
                        height: railh.height + "px",
                        'zIndex': self.zindex,
                        "background": opt.background
                    });
                    railh.append(cursor);
                    railh.visibility = true;
                    railh.scrollable = true;
                    railh.align = (opt.railvalign == "top") ? 0 : 1;
                    self.railh = railh;
                    self.railh.drag = false
                }
                if (self.ispage) {
                    rail.css({
                        position: "fixed",
                        top: 0,
                        height: "100%"
                    });
                    rail.css((rail.align) ? {
                        right: 0
                    }: {
                        left: 0
                    });
                    self.body.append(rail);
                    if (self.railh) {
                        railh.css({
                            position: "fixed",
                            left: 0,
                            width: "100%"
                        });
                        railh.css((railh.align) ? {
                            bottom: 0
                        }: {
                            top: 0
                        });
                        self.body.append(railh)
                    }
                } else {
                    if (self.ishwscroll) {
                        if (self.win.css('position') == 'static') self.css(self.win, {
                            'position': 'relative'
                        });
                        var bd = (self.win[0].nodeName == 'HTML') ? self.body: self.win;
                        $(bd).scrollTop(0).scrollLeft(0);
                        if (self.zoom) {
                            self.zoom.css({
                                position: "absolute",
                                top: 1,
                                right: 0,
                                "margin-right": rail.width + 4
                            });
                            bd.append(self.zoom)
                        }
                        rail.css({
                            position: "absolute",
                            top: 0
                        });
                        rail.css((rail.align) ? {
                            right: 0
                        }: {
                            left: 0
                        });
                        bd.append(rail);
                        if (railh) {
                            railh.css({
                                position: "absolute",
                                left: 0,
                                bottom: 0
                            });
                            railh.css((railh.align) ? {
                                bottom: 0
                            }: {
                                top: 0
                            });
                            bd.append(railh)
                        }
                    } else {
                        self.isfixed = (self.win.css("position") == "fixed");
                        var rlpos = (self.isfixed) ? "fixed": "absolute";
                        if (!self.isfixed) self.viewport = self.getViewport(self.win[0]);
                        if (self.viewport) {
                            self.body = self.viewport;
                            if (! (/fixed|absolute/.test(self.viewport.css("position")))) self.css(self.viewport, {
                                "position": "relative"
                            })
                        }
                        rail.css({
                            position: rlpos
                        });
                        if (self.zoom) self.zoom.css({
                            position: rlpos
                        });
                        self.updateScrollBar();
                        self.body.append(rail);
                        if (self.zoom) self.body.append(self.zoom);
                        if (self.railh) {
                            railh.css({
                                position: rlpos
                            });
                            self.body.append(railh)
                        }
                    }
                    if (cap.isios) self.css(self.win, {
                        '-webkit-tap-highlight-color': 'rgba(0,0,0,0)',
                        '-webkit-touch-callout': 'none'
                    });
                    if (opt.disableoutline) {
                        if (cap.isie) self.win.attr("hideFocus", "true");
                        if (cap.iswebkit) self.win.css('outline', 'none')
                    }
                }
                if (opt.autohidemode === false) {
                    self.autohidedom = false;
                    self.rail.css({
                        opacity: opt.cursoropacitymax
                    });
                    if (self.railh) self.railh.css({
                        opacity: opt.cursoropacitymax
                    })
                } else if ((opt.autohidemode === true) || (opt.autohidemode === "leave")) {
                    self.autohidedom = $().add(self.rail);
                    if (cap.isie8) self.autohidedom = self.autohidedom.add(self.cursor);
                    if (self.railh) self.autohidedom = self.autohidedom.add(self.railh);
                    if (self.railh && cap.isie8) self.autohidedom = self.autohidedom.add(self.cursorh)
                } else if (opt.autohidemode == "scroll") {
                    self.autohidedom = $().add(self.rail);
                    if (self.railh) self.autohidedom = self.autohidedom.add(self.railh)
                } else if (opt.autohidemode == "cursor") {
                    self.autohidedom = $().add(self.cursor);
                    if (self.railh) self.autohidedom = self.autohidedom.add(self.cursorh)
                } else if (opt.autohidemode == "hidden") {
                    self.autohidedom = false;
                    self.hide();
                    self.railslocked = false
                }
                if (cap.cantouch || self.istouchcapable || opt.emulatetouch || cap.hasmstouch) {
                    self.scrollmom = new ScrollMomentumClass2D(self);
                    var delayedclick = null;
                    self.ontouchstart = function(e) {
                        if (self.locked) return false;
                        if (e.pointerType && (e.pointerType === 'mouse' || e.pointerType === e.MSPOINTER_TYPE_MOUSE)) return false;
                        self.hasmoving = false;
                        if (self.scrollmom.timer) {
                            self.triggerScrollEnd();
                            self.scrollmom.stop()
                        }
                        if (!self.railslocked) {
                            var tg = self.getTarget(e);
                            if (tg) {
                                var skp = (/INPUT/i.test(tg.nodeName)) && (/range/i.test(tg.type));
                                if (skp) return self.stopPropagation(e)
                            }
                            var ismouse = (e.type === "mousedown");
                            if (! ("clientX" in e) && ("changedTouches" in e)) {
                                e.clientX = e.changedTouches[0].clientX;
                                e.clientY = e.changedTouches[0].clientY
                            }
                            if (self.forcescreen) {
                                var le = e;
                                e = {
                                    "original": (e.original) ? e.original: e
                                };
                                e.clientX = le.screenX;
                                e.clientY = le.screenY
                            }
                            self.rail.drag = {
                                x: e.clientX,
                                y: e.clientY,
                                sx: self.scroll.x,
                                sy: self.scroll.y,
                                st: self.getScrollTop(),
                                sl: self.getScrollLeft(),
                                pt: 2,
                                dl: false,
                                tg: tg
                            };
                            if (self.ispage || !opt.directionlockdeadzone) {
                                self.rail.drag.dl = "f"
                            } else {
                                var view = {
                                    w: $window.width(),
                                    h: $window.height()
                                };
                                var page = self.getContentSize();
                                var maxh = page.h - view.h;
                                var maxw = page.w - view.w;
                                if (self.rail.scrollable && !self.railh.scrollable) self.rail.drag.ck = (maxh > 0) ? "v": false;
                                else if (!self.rail.scrollable && self.railh.scrollable) self.rail.drag.ck = (maxw > 0) ? "h": false;
                                else self.rail.drag.ck = false
                            }
                            if (opt.emulatetouch && self.isiframe && cap.isie) {
                                var wp = self.win.position();
                                self.rail.drag.x += wp.left;
                                self.rail.drag.y += wp.top
                            }
                            self.hasmoving = false;
                            self.lastmouseup = false;
                            self.scrollmom.reset(e.clientX, e.clientY);
                            if (tg && ismouse) {
                                var ip = /INPUT|SELECT|BUTTON|TEXTAREA/i.test(tg.nodeName);
                                if (!ip) {
                                    if (cap.hasmousecapture) tg.setCapture();
                                    if (opt.emulatetouch) {
                                        if (tg.onclick && !(tg._onclick || false)) {
                                            tg._onclick = tg.onclick;
                                            tg.onclick = function(e) {
                                                if (self.hasmoving) return false;
                                                tg._onclick.call(this, e)
                                            }
                                        }
                                        return self.cancelEvent(e)
                                    }
                                    return self.stopPropagation(e)
                                }
                                if (/SUBMIT|CANCEL|BUTTON/i.test($(tg).attr('type'))) {
                                    self.preventclick = {
                                        "tg": tg,
                                        "click": false
                                    }
                                }
                            }
                        }
                    };
                    self.ontouchend = function(e) {
                        if (!self.rail.drag) return true;
                        if (self.rail.drag.pt == 2) {
                            if (e.pointerType && (e.pointerType === 'mouse' || e.pointerType === e.MSPOINTER_TYPE_MOUSE)) return false;
                            self.rail.drag = false;
                            var ismouse = (e.type === "mouseup");
                            if (self.hasmoving) {
                                self.scrollmom.doMomentum();
                                self.lastmouseup = true;
                                self.hideCursor();
                                if (cap.hasmousecapture) _doc.releaseCapture();
                                if (ismouse) return self.cancelEvent(e)
                            }
                        } else if (self.rail.drag.pt == 1) {
                            return self.onmouseup(e)
                        }
                    };
                    var moveneedoffset = (opt.emulatetouch && self.isiframe && !cap.hasmousecapture);
                    var locktollerance = opt.directionlockdeadzone * 0.3 | 0;
                    self.ontouchmove = function(e, byiframe) {
                        if (e.targetTouches && opt.preventmultitouchscrolling) {
                            if (e.targetTouches.length > 1) return true
                        }
                        if (e.pointerType && (e.pointerType === 'mouse' || e.pointerType === e.MSPOINTER_TYPE_MOUSE)) return true;
                        if (self.rail.drag.pt == 2) {
                            if (("changedTouches" in e)) {
                                e.clientX = e.changedTouches[0].clientX;
                                e.clientY = e.changedTouches[0].clientY
                            }
                            var ofy, ofx;
                            ofx = ofy = 0;
                            if (moveneedoffset && !byiframe) {
                                var wp = self.win.position();
                                ofx = -wp.left;
                                ofy = -wp.top
                            }
                            var fy = e.clientY + ofy;
                            var my = (fy - self.rail.drag.y);
                            var fx = e.clientX + ofx;
                            var mx = (fx - self.rail.drag.x);
                            var ny = self.rail.drag.st - my;
                            if (self.ishwscroll && opt.bouncescroll) {
                                if (ny < 0) {
                                    ny = Math.round(ny / 2)
                                } else if (ny > self.page.maxh) {
                                    ny = self.page.maxh + Math.round((ny - self.page.maxh) / 2)
                                }
                            } else {
                                if (ny < 0) {
                                    ny = 0;
                                    fy = 0
                                } else if (ny > self.page.maxh) {
                                    ny = self.page.maxh;
                                    fy = 0
                                }
                                if (fy === 0 && !self.hasmoving) {
                                    if (!self.ispage) self.rail.drag = false;
                                    return true
                                }
                            }
                            var nx = self.getScrollLeft();
                            if (self.railh && self.railh.scrollable) {
                                nx = (self.isrtlmode) ? mx - self.rail.drag.sl: self.rail.drag.sl - mx;
                                if (self.ishwscroll && opt.bouncescroll) {
                                    if (nx < 0) {
                                        nx = Math.round(nx / 2)
                                    } else if (nx > self.page.maxw) {
                                        nx = self.page.maxw + Math.round((nx - self.page.maxw) / 2)
                                    }
                                } else {
                                    if (nx < 0) {
                                        nx = 0;
                                        fx = 0
                                    }
                                    if (nx > self.page.maxw) {
                                        nx = self.page.maxw;
                                        fx = 0
                                    }
                                }
                            }
                            if (!self.hasmoving) {
                                if (self.rail.drag.y === e.clientY && self.rail.drag.x === e.clientX) return self.cancelEvent(e);
                                var ay = Math.abs(my);
                                var ax = Math.abs(mx);
                                var dz = opt.directionlockdeadzone;
                                if (!self.rail.drag.ck) {
                                    if (ay > dz && ax > dz) self.rail.drag.dl = "f";
                                    else if (ay > dz) self.rail.drag.dl = (ax > locktollerance) ? "f": "v";
                                    else if (ax > dz) self.rail.drag.dl = (ay > locktollerance) ? "f": "h"
                                } else if (self.rail.drag.ck == "v") {
                                    if (ax > dz && ay <= locktollerance) {
                                        self.rail.drag = false
                                    } else if (ay > dz) self.rail.drag.dl = "v"
                                } else if (self.rail.drag.ck == "h") {
                                    if (ay > dz && ax <= locktollerance) {
                                        self.rail.drag = false
                                    } else if (ax > dz) self.rail.drag.dl = "h"
                                }
                                if (!self.rail.drag.dl) return self.cancelEvent(e);
                                self.triggerScrollStart(e.clientX, e.clientY, 0, 0, 0);
                                self.hasmoving = true
                            }
                            if (self.preventclick && !self.preventclick.click) {
                                self.preventclick.click = self.preventclick.tg.onclick || false;
                                self.preventclick.tg.onclick = self.onpreventclick
                            }
                            if (self.rail.drag.dl) {
                                if (self.rail.drag.dl == "v") nx = self.rail.drag.sl;
                                else if (self.rail.drag.dl == "h") ny = self.rail.drag.st
                            }
                            self.synched("touchmove",
                            function() {
                                if (self.rail.drag && (self.rail.drag.pt == 2)) {
                                    if (self.prepareTransition) self.resetTransition();
                                    if (self.rail.scrollable) self.setScrollTop(ny);
                                    self.scrollmom.update(fx, fy);
                                    if (self.railh && self.railh.scrollable) {
                                        self.setScrollLeft(nx);
                                        self.showCursor(ny, nx)
                                    } else {
                                        self.showCursor(ny)
                                    }
                                    if (cap.isie10) _doc.selection.clear()
                                }
                            });
                            return self.cancelEvent(e)
                        } else if (self.rail.drag.pt == 1) {
                            return self.onmousemove(e)
                        }
                    };
                    self.ontouchstartCursor = function(e, hronly) {
                        if (self.rail.drag && self.rail.drag.pt != 3) return;
                        if (self.locked) return self.cancelEvent(e);
                        self.cancelScroll();
                        self.rail.drag = {
                            x: e.touches[0].clientX,
                            y: e.touches[0].clientY,
                            sx: self.scroll.x,
                            sy: self.scroll.y,
                            pt: 3,
                            hr: ( !! hronly)
                        };
                        var tg = self.getTarget(e);
                        if (!self.ispage && cap.hasmousecapture) tg.setCapture();
                        if (self.isiframe && !cap.hasmousecapture) {
                            self.saved.csspointerevents = self.doc.css("pointer-events");
                            self.css(self.doc, {
                                "pointer-events": "none"
                            })
                        }
                        return self.cancelEvent(e)
                    };
                    self.ontouchendCursor = function(e) {
                        if (self.rail.drag) {
                            if (cap.hasmousecapture) _doc.releaseCapture();
                            if (self.isiframe && !cap.hasmousecapture) self.doc.css("pointer-events", self.saved.csspointerevents);
                            if (self.rail.drag.pt != 3) return;
                            self.rail.drag = false;
                            return self.cancelEvent(e)
                        }
                    };
                    self.ontouchmoveCursor = function(e) {
                        if (self.rail.drag) {
                            if (self.rail.drag.pt != 3) return;
                            self.cursorfreezed = true;
                            if (self.rail.drag.hr) {
                                self.scroll.x = self.rail.drag.sx + (e.touches[0].clientX - self.rail.drag.x);
                                if (self.scroll.x < 0) self.scroll.x = 0;
                                var mw = self.scrollvaluemaxw;
                                if (self.scroll.x > mw) self.scroll.x = mw
                            } else {
                                self.scroll.y = self.rail.drag.sy + (e.touches[0].clientY - self.rail.drag.y);
                                if (self.scroll.y < 0) self.scroll.y = 0;
                                var my = self.scrollvaluemax;
                                if (self.scroll.y > my) self.scroll.y = my
                            }
                            self.synched('touchmove',
                            function() {
                                if (self.rail.drag && (self.rail.drag.pt == 3)) {
                                    self.showCursor();
                                    if (self.rail.drag.hr) self.doScrollLeft(Math.round(self.scroll.x * self.scrollratio.x), opt.cursordragspeed);
                                    else self.doScrollTop(Math.round(self.scroll.y * self.scrollratio.y), opt.cursordragspeed)
                                }
                            });
                            return self.cancelEvent(e)
                        }
                    }
                }
                self.onmousedown = function(e, hronly) {
                    if (self.rail.drag && self.rail.drag.pt != 1) return;
                    if (self.railslocked) return self.cancelEvent(e);
                    self.cancelScroll();
                    self.rail.drag = {
                        x: e.clientX,
                        y: e.clientY,
                        sx: self.scroll.x,
                        sy: self.scroll.y,
                        pt: 1,
                        hr: hronly || false
                    };
                    var tg = self.getTarget(e);
                    if (cap.hasmousecapture) tg.setCapture();
                    if (self.isiframe && !cap.hasmousecapture) {
                        self.saved.csspointerevents = self.doc.css("pointer-events");
                        self.css(self.doc, {
                            "pointer-events": "none"
                        })
                    }
                    self.hasmoving = false;
                    return self.cancelEvent(e)
                };
                self.onmouseup = function(e) {
                    if (self.rail.drag) {
                        if (self.rail.drag.pt != 1) return true;
                        if (cap.hasmousecapture) _doc.releaseCapture();
                        if (self.isiframe && !cap.hasmousecapture) self.doc.css("pointer-events", self.saved.csspointerevents);
                        self.rail.drag = false;
                        self.cursorfreezed = false;
                        if (self.hasmoving) self.triggerScrollEnd();
                        return self.cancelEvent(e)
                    }
                };
                self.onmousemove = function(e) {
                    if (self.rail.drag) {
                        if (self.rail.drag.pt !== 1) return;
                        if (cap.ischrome && e.which === 0) return self.onmouseup(e);
                        self.cursorfreezed = true;
                        if (!self.hasmoving) self.triggerScrollStart(e.clientX, e.clientY, 0, 0, 0);
                        self.hasmoving = true;
                        if (self.rail.drag.hr) {
                            self.scroll.x = self.rail.drag.sx + (e.clientX - self.rail.drag.x);
                            if (self.scroll.x < 0) self.scroll.x = 0;
                            var mw = self.scrollvaluemaxw;
                            if (self.scroll.x > mw) self.scroll.x = mw
                        } else {
                            self.scroll.y = self.rail.drag.sy + (e.clientY - self.rail.drag.y);
                            if (self.scroll.y < 0) self.scroll.y = 0;
                            var my = self.scrollvaluemax;
                            if (self.scroll.y > my) self.scroll.y = my
                        }
                        self.synched('mousemove',
                        function() {
                            if (self.cursorfreezed) {
                                self.showCursor();
                                if (self.rail.drag.hr) {
                                    self.scrollLeft(Math.round(self.scroll.x * self.scrollratio.x))
                                } else {
                                    self.scrollTop(Math.round(self.scroll.y * self.scrollratio.y))
                                }
                            }
                        });
                        return self.cancelEvent(e)
                    } else {
                        self.checkarea = 0
                    }
                };
                if (cap.cantouch || opt.emulatetouch) {
                    self.onpreventclick = function(e) {
                        if (self.preventclick) {
                            self.preventclick.tg.onclick = self.preventclick.click;
                            self.preventclick = false;
                            return self.cancelEvent(e)
                        }
                    };
                    self.onclick = (cap.isios) ? false: function(e) {
                        if (self.lastmouseup) {
                            self.lastmouseup = false;
                            return self.cancelEvent(e)
                        } else {
                            return true
                        }
                    };
                    if (opt.grabcursorenabled && cap.cursorgrabvalue) {
                        self.css((self.ispage) ? self.doc: self.win, {
                            'cursor': cap.cursorgrabvalue
                        });
                        self.css(self.rail, {
                            'cursor': cap.cursorgrabvalue
                        })
                    }
                } else {
                    var checkSelectionScroll = function(e) {
                        if (!self.selectiondrag) return;
                        if (e) {
                            var ww = self.win.outerHeight();
                            var df = (e.pageY - self.selectiondrag.top);
                            if (df > 0 && df < ww) df = 0;
                            if (df >= ww) df -= ww;
                            self.selectiondrag.df = df
                        }
                        if (self.selectiondrag.df === 0) return;
                        var rt = -(self.selectiondrag.df * 2 / 6) | 0;
                        self.doScrollBy(rt);
                        self.debounced("doselectionscroll",
                        function() {
                            checkSelectionScroll()
                        },
                        50)
                    };
                    if ("getSelection" in _doc) {
                        self.hasTextSelected = function() {
                            return (_doc.getSelection().rangeCount > 0)
                        }
                    } else if ("selection" in _doc) {
                        self.hasTextSelected = function() {
                            return (_doc.selection.type != "None")
                        }
                    } else {
                        self.hasTextSelected = function() {
                            return false
                        }
                    }
                    self.onselectionstart = function(e) {
                        if (self.ispage) return;
                        self.selectiondrag = self.win.offset()
                    };
                    self.onselectionend = function(e) {
                        self.selectiondrag = false
                    };
                    self.onselectiondrag = function(e) {
                        if (!self.selectiondrag) return;
                        if (self.hasTextSelected()) self.debounced("selectionscroll",
                        function() {
                            checkSelectionScroll(e)
                        },
                        250)
                    }
                }
                if (cap.hasw3ctouch) {
                    self.css((self.ispage) ? $("html") : self.win, {
                        'touch-action': 'none'
                    });
                    self.css(self.rail, {
                        'touch-action': 'none'
                    });
                    self.css(self.cursor, {
                        'touch-action': 'none'
                    });
                    self.bind(self.win, "pointerdown", self.ontouchstart);
                    self.bind(_doc, "pointerup", self.ontouchend);
                    self.delegate(_doc, "pointermove", self.ontouchmove)
                } else if (cap.hasmstouch) {
                    self.css((self.ispage) ? $("html") : self.win, {
                        '-ms-touch-action': 'none'
                    });
                    self.css(self.rail, {
                        '-ms-touch-action': 'none'
                    });
                    self.css(self.cursor, {
                        '-ms-touch-action': 'none'
                    });
                    self.bind(self.win, "MSPointerDown", self.ontouchstart);
                    self.bind(_doc, "MSPointerUp", self.ontouchend);
                    self.delegate(_doc, "MSPointerMove", self.ontouchmove);
                    self.bind(self.cursor, "MSGestureHold",
                    function(e) {
                        e.preventDefault()
                    });
                    self.bind(self.cursor, "contextmenu",
                    function(e) {
                        e.preventDefault()
                    })
                } else if (cap.cantouch) {
                    self.bind(self.win, "touchstart", self.ontouchstart, false, true);
                    self.bind(_doc, "touchend", self.ontouchend, false, true);
                    self.bind(_doc, "touchcancel", self.ontouchend, false, true);
                    self.delegate(_doc, "touchmove", self.ontouchmove, false, true)
                }
                if (opt.emulatetouch) {
                    self.bind(self.win, "mousedown", self.ontouchstart, false, true);
                    self.bind(_doc, "mouseup", self.ontouchend, false, true);
                    self.bind(_doc, "mousemove", self.ontouchmove, false, true)
                }
                if (opt.cursordragontouch || (!cap.cantouch && !opt.emulatetouch)) {
                    self.rail.css({
                        cursor: "default"
                    });
                    self.railh && self.railh.css({
                        cursor: "default"
                    });
                    self.jqbind(self.rail, "mouseenter",
                    function() {
                        if (!self.ispage && !self.win.is(":visible")) return false;
                        if (self.canshowonmouseevent) self.showCursor();
                        self.rail.active = true
                    });
                    self.jqbind(self.rail, "mouseleave",
                    function() {
                        self.rail.active = false;
                        if (!self.rail.drag) self.hideCursor()
                    });
                    if (opt.sensitiverail) {
                        self.bind(self.rail, "click",
                        function(e) {
                            self.doRailClick(e, false, false)
                        });
                        self.bind(self.rail, "dblclick",
                        function(e) {
                            self.doRailClick(e, true, false)
                        });
                        self.bind(self.cursor, "click",
                        function(e) {
                            self.cancelEvent(e)
                        });
                        self.bind(self.cursor, "dblclick",
                        function(e) {
                            self.cancelEvent(e)
                        })
                    }
                    if (self.railh) {
                        self.jqbind(self.railh, "mouseenter",
                        function() {
                            if (!self.ispage && !self.win.is(":visible")) return false;
                            if (self.canshowonmouseevent) self.showCursor();
                            self.rail.active = true
                        });
                        self.jqbind(self.railh, "mouseleave",
                        function() {
                            self.rail.active = false;
                            if (!self.rail.drag) self.hideCursor()
                        });
                        if (opt.sensitiverail) {
                            self.bind(self.railh, "click",
                            function(e) {
                                self.doRailClick(e, false, true)
                            });
                            self.bind(self.railh, "dblclick",
                            function(e) {
                                self.doRailClick(e, true, true)
                            });
                            self.bind(self.cursorh, "click",
                            function(e) {
                                self.cancelEvent(e)
                            });
                            self.bind(self.cursorh, "dblclick",
                            function(e) {
                                self.cancelEvent(e)
                            })
                        }
                    }
                }
                if (opt.cursordragontouch && (this.istouchcapable || cap.cantouch)) {
                    self.bind(self.cursor, "touchstart", self.ontouchstartCursor);
                    self.bind(self.cursor, "touchmove", self.ontouchmoveCursor);
                    self.bind(self.cursor, "touchend", self.ontouchendCursor);
                    self.cursorh && self.bind(self.cursorh, "touchstart",
                    function(e) {
                        self.ontouchstartCursor(e, true)
                    });
                    self.cursorh && self.bind(self.cursorh, "touchmove", self.ontouchmoveCursor);
                    self.cursorh && self.bind(self.cursorh, "touchend", self.ontouchendCursor)
                }
                if (!opt.emulatetouch && !cap.isandroid && !cap.isios) {
                    self.bind((cap.hasmousecapture) ? self.win: _doc, "mouseup", self.onmouseup);
                    self.bind(_doc, "mousemove", self.onmousemove);
                    if (self.onclick) self.bind(_doc, "click", self.onclick);
                    self.bind(self.cursor, "mousedown", self.onmousedown);
                    self.bind(self.cursor, "mouseup", self.onmouseup);
                    if (self.railh) {
                        self.bind(self.cursorh, "mousedown",
                        function(e) {
                            self.onmousedown(e, true)
                        });
                        self.bind(self.cursorh, "mouseup", self.onmouseup)
                    }
                    if (!self.ispage && opt.enablescrollonselection) {
                        self.bind(self.win[0], "mousedown", self.onselectionstart);
                        self.bind(_doc, "mouseup", self.onselectionend);
                        self.bind(self.cursor, "mouseup", self.onselectionend);
                        if (self.cursorh) self.bind(self.cursorh, "mouseup", self.onselectionend);
                        self.bind(_doc, "mousemove", self.onselectiondrag)
                    }
                    if (self.zoom) {
                        self.jqbind(self.zoom, "mouseenter",
                        function() {
                            if (self.canshowonmouseevent) self.showCursor();
                            self.rail.active = true
                        });
                        self.jqbind(self.zoom, "mouseleave",
                        function() {
                            self.rail.active = false;
                            if (!self.rail.drag) self.hideCursor()
                        })
                    }
                } else {
                    self.bind((cap.hasmousecapture) ? self.win: _doc, "mouseup", self.ontouchend);
                    if (self.onclick) self.bind(_doc, "click", self.onclick);
                    if (opt.cursordragontouch) {
                        self.bind(self.cursor, "mousedown", self.onmousedown);
                        self.bind(self.cursor, "mouseup", self.onmouseup);
                        self.cursorh && self.bind(self.cursorh, "mousedown",
                        function(e) {
                            self.onmousedown(e, true)
                        });
                        self.cursorh && self.bind(self.cursorh, "mouseup", self.onmouseup)
                    } else {
                        self.bind(self.rail, "mousedown",
                        function(e) {
                            e.preventDefault()
                        });
                        self.railh && self.bind(self.railh, "mousedown",
                        function(e) {
                            e.preventDefault()
                        })
                    }
                }
                if (opt.enablemousewheel) {
                    if (!self.isiframe) self.mousewheel((cap.isie && self.ispage) ? _doc: self.win, self.onmousewheel);
                    self.mousewheel(self.rail, self.onmousewheel);
                    if (self.railh) self.mousewheel(self.railh, self.onmousewheelhr)
                }
                if (!self.ispage && !cap.cantouch && !(/HTML|^BODY/.test(self.win[0].nodeName))) {
                    if (!self.win.attr("tabindex")) self.win.attr({
                        "tabindex": ++tabindexcounter
                    });
                    self.bind(self.win, "focus",
                    function(e) {
                        domfocus = (self.getTarget(e)).id || self.getTarget(e) || false;
                        self.hasfocus = true;
                        if (self.canshowonmouseevent) self.noticeCursor()
                    });
                    self.bind(self.win, "blur",
                    function(e) {
                        domfocus = false;
                        self.hasfocus = false
                    });
                    self.bind(self.win, "mouseenter",
                    function(e) {
                        mousefocus = (self.getTarget(e)).id || self.getTarget(e) || false;
                        self.hasmousefocus = true;
                        if (self.canshowonmouseevent) self.noticeCursor()
                    });
                    self.bind(self.win, "mouseleave",
                    function(e) {
                        mousefocus = false;
                        self.hasmousefocus = false;
                        if (!self.rail.drag) self.hideCursor()
                    })
                }
                self.onkeypress = function(e) {
                    if (self.railslocked && self.page.maxh === 0) return true;
                    e = e || _win.event;
                    var tg = self.getTarget(e);
                    if (tg && /INPUT|TEXTAREA|SELECT|OPTION/.test(tg.nodeName)) {
                        var tp = tg.getAttribute('type') || tg.type || false;
                        if ((!tp) || !(/submit|button|cancel/i.tp)) return true
                    }
                    if ($(tg).attr('contenteditable')) return true;
                    if (self.hasfocus || (self.hasmousefocus && !domfocus) || (self.ispage && !domfocus && !mousefocus)) {
                        var key = e.keyCode;
                        if (self.railslocked && key != 27) return self.cancelEvent(e);
                        var ctrl = e.ctrlKey || false;
                        var shift = e.shiftKey || false;
                        var ret = false;
                        switch (key) {
                        case 38:
                        case 63233:
                            self.doScrollBy(24 * 3);
                            ret = true;
                            break;
                        case 40:
                        case 63235:
                            self.doScrollBy( - 24 * 3);
                            ret = true;
                            break;
                        case 37:
                        case 63232:
                            if (self.railh) { (ctrl) ? self.doScrollLeft(0) : self.doScrollLeftBy(24 * 3);
                                ret = true
                            }
                            break;
                        case 39:
                        case 63234:
                            if (self.railh) { (ctrl) ? self.doScrollLeft(self.page.maxw) : self.doScrollLeftBy( - 24 * 3);
                                ret = true
                            }
                            break;
                        case 33:
                        case 63276:
                            self.doScrollBy(self.view.h);
                            ret = true;
                            break;
                        case 34:
                        case 63277:
                            self.doScrollBy( - self.view.h);
                            ret = true;
                            break;
                        case 36:
                        case 63273:
                            (self.railh && ctrl) ? self.doScrollPos(0, 0) : self.doScrollTo(0);
                            ret = true;
                            break;
                        case 35:
                        case 63275:
                            (self.railh && ctrl) ? self.doScrollPos(self.page.maxw, self.page.maxh) : self.doScrollTo(self.page.maxh);
                            ret = true;
                            break;
                        case 32:
                            if (opt.spacebarenabled) { (shift) ? self.doScrollBy(self.view.h) : self.doScrollBy( - self.view.h);
                                ret = true
                            }
                            break;
                        case 27:
                            if (self.zoomactive) {
                                self.doZoom();
                                ret = true
                            }
                            break
                        }
                        if (ret) return self.cancelEvent(e)
                    }
                };
                if (opt.enablekeyboard) self.bind(_doc, (cap.isopera && !cap.isopera12) ? "keypress": "keydown", self.onkeypress);
                self.bind(_doc, "keydown",
                function(e) {
                    var ctrl = e.ctrlKey || false;
                    if (ctrl) self.wheelprevented = true
                });
                self.bind(_doc, "keyup",
                function(e) {
                    var ctrl = e.ctrlKey || false;
                    if (!ctrl) self.wheelprevented = false
                });
                self.bind(_win, "blur",
                function(e) {
                    self.wheelprevented = false
                });
                self.bind(_win, 'resize', self.onscreenresize);
                self.bind(_win, 'orientationchange', self.onscreenresize);
                self.bind(_win, "load", self.lazyResize);
                if (cap.ischrome && !self.ispage && !self.haswrapper) {
                    var tmp = self.win.attr("style");
                    var ww = parseFloat(self.win.css("width")) + 1;
                    self.win.css('width', ww);
                    self.synched("chromefix",
                    function() {
                        self.win.attr("style", tmp)
                    })
                }
                self.onAttributeChange = function(e) {
                    self.lazyResize(self.isieold ? 250 : 30)
                };
                if (opt.enableobserver) {
                    if ((!self.isie11) && (ClsMutationObserver !== false)) {
                        self.observerbody = new ClsMutationObserver(function(mutations) {
                            mutations.forEach(function(mut) {
                                if (mut.type == "attributes") {
                                    return ($body.hasClass("modal-open") && $body.hasClass("modal-dialog") && !$.contains($('.modal-dialog')[0], self.doc[0])) ? self.hide() : self.show()
                                }
                            });
                            if (self.me.clientWidth != self.page.width || self.me.clientHeight != self.page.height) return self.lazyResize(30)
                        });
                        self.observerbody.observe(_doc.body, {
                            childList: true,
                            subtree: true,
                            characterData: false,
                            attributes: true,
                            attributeFilter: ['class']
                        })
                    }
                    if (!self.ispage && !self.haswrapper) {
                        var _dom = self.win[0];
                        if (ClsMutationObserver !== false) {
                            self.observer = new ClsMutationObserver(function(mutations) {
                                mutations.forEach(self.onAttributeChange)
                            });
                            self.observer.observe(_dom, {
                                childList: true,
                                characterData: false,
                                attributes: true,
                                subtree: false
                            });
                            self.observerremover = new ClsMutationObserver(function(mutations) {
                                mutations.forEach(function(mo) {
                                    if (mo.removedNodes.length > 0) {
                                        for (var dd in mo.removedNodes) {
                                            if ( !! self && (mo.removedNodes[dd] === _dom)) return self.remove()
                                        }
                                    }
                                })
                            });
                            self.observerremover.observe(_dom.parentNode, {
                                childList: true,
                                characterData: false,
                                attributes: false,
                                subtree: false
                            })
                        } else {
                            self.bind(_dom, (cap.isie && !cap.isie9) ? "propertychange": "DOMAttrModified", self.onAttributeChange);
                            if (cap.isie9) _dom.attachEvent("onpropertychange", self.onAttributeChange);
                            self.bind(_dom, "DOMNodeRemoved",
                            function(e) {
                                if (e.target === _dom) self.remove()
                            })
                        }
                    }
                }
                if (!self.ispage && opt.boxzoom) self.bind(_win, "resize", self.resizeZoom);
                if (self.istextarea) {
                    self.bind(self.win, "keydown", self.lazyResize);
                    self.bind(self.win, "mouseup", self.lazyResize)
                }
                self.lazyResize(30)
            }
            if (this.doc[0].nodeName == 'IFRAME') {
                var oniframeload = function() {
                    self.iframexd = false;
                    var doc;
                    try {
                        doc = 'contentDocument' in this ? this.contentDocument: this.contentWindow._doc;
                        var a = doc.domain
                    } catch(e) {
                        self.iframexd = true;
                        doc = false
                    }
                    if (self.iframexd) {
                        if ("console" in _win) console.log('NiceScroll error: policy restriced iframe');
                        return true
                    }
                    self.forcescreen = true;
                    if (self.isiframe) {
                        self.iframe = {
                            "doc": $(doc),
                            "html": self.doc.contents().find('html')[0],
                            "body": self.doc.contents().find('body')[0]
                        };
                        self.getContentSize = function() {
                            return {
                                w: Math.max(self.iframe.html.scrollWidth, self.iframe.body.scrollWidth),
                                h: Math.max(self.iframe.html.scrollHeight, self.iframe.body.scrollHeight)
                            }
                        };
                        self.docscroll = $(self.iframe.body)
                    }
                    if (!cap.isios && opt.iframeautoresize && !self.isiframe) {
                        self.win.scrollTop(0);
                        self.doc.height("");
                        var hh = Math.max(doc.getElementsByTagName('html')[0].scrollHeight, doc.body.scrollHeight);
                        self.doc.height(hh)
                    }
                    self.lazyResize(30);
                    self.css($(self.iframe.body), _scrollyhidden);
                    if (cap.isios && self.haswrapper) {
                        self.css($(doc.body), {
                            '-webkit-transform': 'translate3d(0,0,0)'
                        })
                    }
                    if ('contentWindow' in this) {
                        self.bind(this.contentWindow, "scroll", self.onscroll)
                    } else {
                        self.bind(doc, "scroll", self.onscroll)
                    }
                    if (opt.enablemousewheel) {
                        self.mousewheel(doc, self.onmousewheel)
                    }
                    if (opt.enablekeyboard) self.bind(doc, (cap.isopera) ? "keypress": "keydown", self.onkeypress);
                    if (cap.cantouch) {
                        self.bind(doc, "touchstart", self.ontouchstart);
                        self.bind(doc, "touchmove", self.ontouchmove)
                    } else if (opt.emulatetouch) {
                        self.bind(doc, "mousedown", self.ontouchstart);
                        self.bind(doc, "mousemove",
                        function(e) {
                            return self.ontouchmove(e, true)
                        });
                        if (opt.grabcursorenabled && cap.cursorgrabvalue) self.css($(doc.body), {
                            'cursor': cap.cursorgrabvalue
                        })
                    }
                    self.bind(doc, "mouseup", self.ontouchend);
                    if (self.zoom) {
                        if (opt.dblclickzoom) self.bind(doc, 'dblclick', self.doZoom);
                        if (self.ongesturezoom) self.bind(doc, "gestureend", self.ongesturezoom)
                    }
                };
                if (this.doc[0].readyState && this.doc[0].readyState === "complete") {
                    setTimeout(function() {
                        oniframeload.call(self.doc[0], false)
                    },
                    500)
                }
                self.bind(this.doc, "load", oniframeload)
            }
        };
        this.showCursor = function(py, px) {
            if (self.cursortimeout) {
                clearTimeout(self.cursortimeout);
                self.cursortimeout = 0
            }
            if (!self.rail) return;
            if (self.autohidedom) {
                self.autohidedom.stop().css({
                    opacity: opt.cursoropacitymax
                });
                self.cursoractive = true
            }
            if (!self.rail.drag || self.rail.drag.pt != 1) {
                if (py !== undefined && py !== false) {
                    self.scroll.y = (py / self.scrollratio.y) | 0
                }
                if (px !== undefined) {
                    self.scroll.x = (px / self.scrollratio.x) | 0
                }
            }
            self.cursor.css({
                height: self.cursorheight,
                top: self.scroll.y
            });
            if (self.cursorh) {
                var lx = (self.hasreversehr) ? self.scrollvaluemaxw - self.scroll.x: self.scroll.x;
                self.cursorh.css({
                    width: self.cursorwidth,
                    left: (!self.rail.align && self.rail.visibility) ? lx + self.rail.width: lx
                });
                self.cursoractive = true
            }
            if (self.zoom) self.zoom.stop().css({
                opacity: opt.cursoropacitymax
            })
        };
        this.hideCursor = function(tm) {
            if (self.cursortimeout) return;
            if (!self.rail) return;
            if (!self.autohidedom) return;
            if (self.hasmousefocus && opt.autohidemode === "leave") return;
            self.cursortimeout = setTimeout(function() {
            	if(self){
            		if (!self.rail.active || !self.showonmouseevent) {
                        self.autohidedom.stop().animate({
                            opacity: opt.cursoropacitymin
                        });
                        if (self.zoom) self.zoom.stop().animate({
                            opacity: opt.cursoropacitymin
                        });
                        self.cursoractive = false
                    }
            		self.cursortimeout = 0
            	}
            },
            tm || opt.hidecursordelay)
        };
        this.noticeCursor = function(tm, py, px) {
            self.showCursor(py, px);
            if (!self.rail.active) self.hideCursor(tm)
        };
        this.getContentSize = (self.ispage) ?
        function() {
            return {
                w: Math.max(_doc.body.scrollWidth, _doc.documentElement.scrollWidth),
                h: Math.max(_doc.body.scrollHeight, _doc.documentElement.scrollHeight)
            }
        }: (self.haswrapper) ?
        function() {
            return {
                w: self.doc[0].offsetWidth,
                h: self.doc[0].offsetHeight
            }
        }: function() {
            return {
                w: self.docscroll[0].scrollWidth,
                h: self.docscroll[0].scrollHeight
            }
        };
        this.onResize = function(e, page) {
            if (!self || !self.win) return false;
            var premaxh = self.page.maxh,
            premaxw = self.page.maxw,
            previewh = self.view.h,
            previeww = self.view.w;
            self.view = {
                w: (self.ispage) ? self.win.width() : self.win[0].clientWidth,
                h: (self.ispage) ? self.win.height() : self.win[0].clientHeight
            };
            self.page = (page) ? page: self.getContentSize();
            self.page.maxh = Math.max(0, self.page.h - self.view.h);
            self.page.maxw = Math.max(0, self.page.w - self.view.w);
            if ((self.page.maxh == premaxh) && (self.page.maxw == premaxw) && (self.view.w == previeww) && (self.view.h == previewh)) {
                if (!self.ispage) {
                    var pos = self.win.offset();
                    if (self.lastposition) {
                        var lst = self.lastposition;
                        if ((lst.top == pos.top) && (lst.left == pos.left)) return self
                    }
                    self.lastposition = pos
                } else {
                    return self
                }
            }
            if (self.page.maxh === 0) {
                self.hideRail();
                self.scrollvaluemax = 0;
                self.scroll.y = 0;
                self.scrollratio.y = 0;
                self.cursorheight = 0;
                self.setScrollTop(0);
                if (self.rail) self.rail.scrollable = false
            } else {
                self.page.maxh -= (opt.railpadding.top + opt.railpadding.bottom);
                self.rail.scrollable = true
            }
            if (self.page.maxw === 0) {
                self.hideRailHr();
                self.scrollvaluemaxw = 0;
                self.scroll.x = 0;
                self.scrollratio.x = 0;
                self.cursorwidth = 0;
                self.setScrollLeft(0);
                if (self.railh) {
                    self.railh.scrollable = false
                }
            } else {
                self.page.maxw -= (opt.railpadding.left + opt.railpadding.right);
                if (self.railh) self.railh.scrollable = (opt.horizrailenabled)
            }
            self.railslocked = (self.locked) || ((self.page.maxh === 0) && (self.page.maxw === 0));
            if (self.railslocked) {
                if (!self.ispage) self.updateScrollBar(self.view);
                return false
            }
            if (!self.hidden) {
                if (!self.rail.visibility) self.showRail();
                if (self.railh && !self.railh.visibility) self.showRailHr()
            }
            if (self.istextarea && self.win.css('resize') && self.win.css('resize') != 'none') self.view.h -= 20;
            self.cursorheight = Math.min(self.view.h, Math.round(self.view.h * (self.view.h / self.page.h)));
            self.cursorheight = (opt.cursorfixedheight) ? opt.cursorfixedheight: Math.max(opt.cursorminheight, self.cursorheight);
            self.cursorwidth = Math.min(self.view.w, Math.round(self.view.w * (self.view.w / self.page.w)));
            self.cursorwidth = (opt.cursorfixedheight) ? opt.cursorfixedheight: Math.max(opt.cursorminheight, self.cursorwidth);
            self.scrollvaluemax = self.view.h - self.cursorheight - (opt.railpadding.top + opt.railpadding.bottom);
            if (!self.hasborderbox) self.scrollvaluemax -= self.cursor[0].offsetHeight - self.cursor[0].clientHeight;
            if (self.railh) {
                self.railh.width = (self.page.maxh > 0) ? (self.view.w - self.rail.width) : self.view.w;
                self.scrollvaluemaxw = self.railh.width - self.cursorwidth - (opt.railpadding.left + opt.railpadding.right)
            }
            if (!self.ispage) self.updateScrollBar(self.view);
            self.scrollratio = {
                x: (self.page.maxw / self.scrollvaluemaxw),
                y: (self.page.maxh / self.scrollvaluemax)
            };
            var sy = self.getScrollTop();
            if (sy > self.page.maxh) {
                self.doScrollTop(self.page.maxh)
            } else {
                self.scroll.y = (self.getScrollTop() / self.scrollratio.y) | 0;
                self.scroll.x = (self.getScrollLeft() / self.scrollratio.x) | 0;
                if (self.cursoractive) self.noticeCursor()
            }
            if (self.scroll.y && (self.getScrollTop() === 0)) self.doScrollTo((self.scroll.y * self.scrollratio.y) | 0);
            return self
        };
        this.resize = self.onResize;
        var hlazyresize = 0;
        this.onscreenresize = function(e) {
            clearTimeout(hlazyresize);
            var hiderails = (!self.ispage && !self.haswrapper);
            if (hiderails) self.hideRails();
            hlazyresize = setTimeout(function() {
                if (self) {
                    if (hiderails) self.showRails();
                    self.resize()
                }
                hlazyresize = 0
            },
            120)
        };
        this.lazyResize = function(tm) {
            clearTimeout(hlazyresize);
            tm = isNaN(tm) ? 240 : tm;
            hlazyresize = setTimeout(function() {
                self && self.resize();
                hlazyresize = 0
            },
            tm);
            return self
        };
        function _modernWheelEvent(dom, name, fn, bubble) {
            self._bind(dom, name,
            function(e) {
                e = e || _win.event;
                var event = {
                    original: e,
                    target: e.target || e.srcElement,
                    type: "wheel",
                    deltaMode: e.type == "MozMousePixelScroll" ? 0 : 1,
                    deltaX: 0,
                    deltaZ: 0,
                    preventDefault: function() {
                        e.preventDefault ? e.preventDefault() : e.returnValue = false;
                        return false
                    },
                    stopImmediatePropagation: function() { (e.stopImmediatePropagation) ? e.stopImmediatePropagation() : e.cancelBubble = true
                    }
                };
                if (name == "mousewheel") {
                    e.wheelDeltaX && (event.deltaX = -1 / 40 * e.wheelDeltaX);
                    e.wheelDeltaY && (event.deltaY = -1 / 40 * e.wheelDeltaY); ! event.deltaY && !event.deltaX && (event.deltaY = -1 / 40 * e.wheelDelta)
                } else {
                    event.deltaY = e.detail
                }
                return fn.call(dom, event)
            },
            bubble)
        }
        this.jqbind = function(dom, name, fn) {
            self.events.push({
                e: dom,
                n: name,
                f: fn,
                q: true
            });
            $(dom).on(name, fn)
        };
        this.mousewheel = function(dom, fn, bubble) {
            var el = ("jquery" in dom) ? dom[0] : dom;
            if ("onwheel" in _doc.createElement("div")) {
                self._bind(el, "wheel", fn, bubble || false)
            } else {
                var wname = (_doc.onmousewheel !== undefined) ? "mousewheel": "DOMMouseScroll";
                _modernWheelEvent(el, wname, fn, bubble || false);
                if (wname == "DOMMouseScroll") _modernWheelEvent(el, "MozMousePixelScroll", fn, bubble || false)
            }
        };
        var passiveSupported = false;
        if (cap.haseventlistener) {
            try {
                var options = Object.defineProperty({},
                "passive", {
                    get: function() {
                        passiveSupported = !0
                    }
                });
                _win.addEventListener("test", null, options)
            } catch(err) {}
            this.stopPropagation = function(e) {
                if (!e) return false;
                e = (e.original) ? e.original: e;
                e.stopPropagation();
                return false
            };
            this.cancelEvent = function(e) {
                if (e.cancelable) e.preventDefault();
                e.stopImmediatePropagation();
                if (e.preventManipulation) e.preventManipulation();
                return false
            }
        } else {
            Event.prototype.preventDefault = function() {
                this.returnValue = false
            };
            Event.prototype.stopPropagation = function() {
                this.cancelBubble = true
            };
            _win.constructor.prototype.addEventListener = _doc.constructor.prototype.addEventListener = Element.prototype.addEventListener = function(type, listener, useCapture) {
                this.attachEvent("on" + type, listener)
            };
            _win.constructor.prototype.removeEventListener = _doc.constructor.prototype.removeEventListener = Element.prototype.removeEventListener = function(type, listener, useCapture) {
                this.detachEvent("on" + type, listener)
            };
            this.cancelEvent = function(e) {
                e = e || _win.event;
                if (e) {
                    e.cancelBubble = true;
                    e.cancel = true;
                    e.returnValue = false
                }
                return false
            };
            this.stopPropagation = function(e) {
                e = e || _win.event;
                if (e) e.cancelBubble = true;
                return false
            }
        }
        this.delegate = function(dom, name, fn, bubble, active) {
            var de = delegatevents[name] || false;
            if (!de) {
                de = {
                    a: [],
                    l: [],
                    f: function(e) {
                        var lst = de.l,
                        l = lst.length - 1;
                        var r = false;
                        for (var a = l; a >= 0; a--) {
                            r = lst[a].call(e.target, e);
                            if (r === false) return false
                        }
                        return r
                    }
                };
                self.bind(dom, name, de.f, bubble, active);
                delegatevents[name] = de
            }
            if (self.ispage) {
                de.a = [self.id].concat(de.a);
                de.l = [fn].concat(de.l)
            } else {
                de.a.push(self.id);
                de.l.push(fn)
            }
        };
        this.undelegate = function(dom, name, fn, bubble, active) {
            var de = delegatevents[name] || false;
            if (de && de.l) {
                for (var a = 0,
                l = de.l.length; a < l; a++) {
                    if (de.a[a] === self.id) {
                        de.a.splice(a);
                        de.l.splice(a);
                        if (de.a.length === 0) {
                            self._unbind(dom, name, de.l.f);
                            delegatevents[name] = null
                        }
                    }
                }
            }
        };
        this.bind = function(dom, name, fn, bubble, active) {
            var el = ("jquery" in dom) ? dom[0] : dom;
            self._bind(el, name, fn, bubble || false, active || false)
        };
        this._bind = function(el, name, fn, bubble, active) {
            self.events.push({
                e: el,
                n: name,
                f: fn,
                b: bubble,
                q: false
            }); (passiveSupported && active) ? el.addEventListener(name, fn, {
                passive: false,
                capture: bubble
            }) : el.addEventListener(name, fn, bubble || false)
        };
        this._unbind = function(el, name, fn, bub) {
            if (delegatevents[name]) self.undelegate(el, name, fn, bub);
            else el.removeEventListener(name, fn, bub)
        };
        this.unbindAll = function() {
            for (var a = 0; a < self.events.length; a++) {
                var r = self.events[a]; (r.q) ? r.e.unbind(r.n, r.f) : self._unbind(r.e, r.n, r.f, r.b)
            }
        };
        this.showRails = function() {
            return self.showRail().showRailHr()
        };
        this.showRail = function() {
            if ((self.page.maxh !== 0) && (self.ispage || self.win.css('display') != 'none')) {
                self.rail.visibility = true;
                self.rail.css('display', 'block')
            }
            return self
        };
        this.showRailHr = function() {
            if (self.railh) {
                if ((self.page.maxw !== 0) && (self.ispage || self.win.css('display') != 'none')) {
                    self.railh.visibility = true;
                    self.railh.css('display', 'block')
                }
            }
            return self
        };
        this.hideRails = function() {
            return self.hideRail().hideRailHr()
        };
        this.hideRail = function() {
            self.rail.visibility = false;
            self.rail.css('display', 'none');
            return self
        };
        this.hideRailHr = function() {
            if (self.railh) {
                self.railh.visibility = false;
                self.railh.css('display', 'none')
            }
            return self
        };
        this.show = function() {
            self.hidden = false;
            self.railslocked = false;
            return self.showRails()
        };
        this.hide = function() {
            self.hidden = true;
            self.railslocked = true;
            return self.hideRails()
        };
        this.toggle = function() {
            return (self.hidden) ? self.show() : self.hide()
        };
        this.remove = function() {
            self.stop();
            if (self.cursortimeout) clearTimeout(self.cursortimeout);
            for (var n in self.delaylist) if (self.delaylist[n]) clearAnimationFrame(self.delaylist[n].h);
            self.doZoomOut();
            self.unbindAll();
            if (cap.isie9) self.win[0].detachEvent("onpropertychange", self.onAttributeChange);
            if (self.observer !== false) self.observer.disconnect();
            if (self.observerremover !== false) self.observerremover.disconnect();
            if (self.observerbody !== false) self.observerbody.disconnect();
            self.events = null;
            if (self.cursor) {
                self.cursor.remove()
            }
            if (self.cursorh) {
                self.cursorh.remove()
            }
            if (self.rail) {
                self.rail.remove()
            }
            if (self.railh) {
                self.railh.remove()
            }
            if (self.zoom) {
                self.zoom.remove()
            }
            for (var a = 0; a < self.saved.css.length; a++) {
                var d = self.saved.css[a];
                d[0].css(d[1], (d[2] === undefined) ? '': d[2])
            }
            self.saved = false;
            self.me.data('__nicescroll', '');
            var lst = $.nicescroll;
            lst.each(function(i) {
                if (!this) return;
                if (this.id === self.id) {
                    delete lst[i];
                    for (var b = ++i; b < lst.length; b++, i++) lst[i] = lst[b];
                    lst.length--;
                    if (lst.length) delete lst[lst.length]
                }
            });
            for (var i in self) {
                self[i] = null;
                delete self[i]
            }
            self = null
        };
        this.scrollstart = function(fn) {
            this.onscrollstart = fn;
            return self
        };
        this.scrollend = function(fn) {
            this.onscrollend = fn;
            return self
        };
        this.scrollcancel = function(fn) {
            this.onscrollcancel = fn;
            return self
        };
        this.zoomin = function(fn) {
            this.onzoomin = fn;
            return self
        };
        this.zoomout = function(fn) {
            this.onzoomout = fn;
            return self
        };
        this.isScrollable = function(e) {
            var dom = (e.target) ? e.target: e;
            if (dom.nodeName == 'OPTION') return true;
            while (dom && (dom.nodeType == 1) && (dom !== this.me[0]) && !(/^BODY|HTML/.test(dom.nodeName))) {
                var dd = $(dom);
                var ov = dd.css('overflowY') || dd.css('overflowX') || dd.css('overflow') || '';
                if (/scroll|auto/.test(ov)) return (dom.clientHeight != dom.scrollHeight);
                dom = (dom.parentNode) ? dom.parentNode: false
            }
            return false
        };
        this.getViewport = function(me) {
            var dom = (me && me.parentNode) ? me.parentNode: false;
            while (dom && (dom.nodeType == 1) && !(/^BODY|HTML/.test(dom.nodeName))) {
                var dd = $(dom);
                if (/fixed|absolute/.test(dd.css("position"))) return dd;
                var ov = dd.css('overflowY') || dd.css('overflowX') || dd.css('overflow') || '';
                if ((/scroll|auto/.test(ov)) && (dom.clientHeight != dom.scrollHeight)) return dd;
                if (dd.getNiceScroll().length > 0) return dd;
                dom = (dom.parentNode) ? dom.parentNode: false
            }
            return false
        };
        this.triggerScrollStart = function(cx, cy, rx, ry, ms) {
            if (self.onscrollstart) {
                var info = {
                    type: "scrollstart",
                    current: {
                        x: cx,
                        y: cy
                    },
                    request: {
                        x: rx,
                        y: ry
                    },
                    end: {
                        x: self.newscrollx,
                        y: self.newscrolly
                    },
                    speed: ms
                };
                self.onscrollstart.call(self, info)
            }
        };
        this.triggerScrollEnd = function() {
            if (self.onscrollend) {
                var px = self.getScrollLeft();
                var py = self.getScrollTop();
                var info = {
                    type: "scrollend",
                    current: {
                        x: px,
                        y: py
                    },
                    end: {
                        x: px,
                        y: py
                    }
                };
                self.onscrollend.call(self, info)
            }
        };
        var scrolldiry = 0,
        scrolldirx = 0,
        scrolltmr = 0,
        scrollspd = 1;
        function doScrollRelative(px, py, chkscroll, iswheel) {
            if (!self.scrollrunning) {
                self.newscrolly = self.getScrollTop();
                self.newscrollx = self.getScrollLeft();
                scrolltmr = now()
            }
            var gap = (now() - scrolltmr);
            scrolltmr = now();
            if (gap > 350) {
                scrollspd = 1
            } else {
                scrollspd += (2 - scrollspd) / 10
            }
            px = px * scrollspd | 0;
            py = py * scrollspd | 0;
            if (px) {
                if (iswheel) {
                    if (px < 0) {
                        if (self.getScrollLeft() >= self.page.maxw) return true
                    } else {
                        if (self.getScrollLeft() <= 0) return true
                    }
                }
                var dx = px > 0 ? 1 : -1;
                if (scrolldirx !== dx) {
                    if (self.scrollmom) self.scrollmom.stop();
                    self.newscrollx = self.getScrollLeft();
                    scrolldirx = dx
                }
                self.lastdeltax -= px
            }
            if (py) {
                var chk = (function() {
                    var top = self.getScrollTop();
                    if (py < 0) {
                        if (top >= self.page.maxh) return true
                    } else {
                        if (top <= 0) return true
                    }
                })();
                if (chk) {
                    if (opt.nativeparentscrolling && chkscroll && !self.ispage && !self.zoomactive) return true;
                    var ny = self.view.h >> 1;
                    if (self.newscrolly < -ny) {
                        self.newscrolly = -ny;
                        py = -1
                    } else if (self.newscrolly > self.page.maxh + ny) {
                        self.newscrolly = self.page.maxh + ny;
                        py = 1
                    } else py = 0
                }
                var dy = py > 0 ? 1 : -1;
                if (scrolldiry !== dy) {
                    if (self.scrollmom) self.scrollmom.stop();
                    self.newscrolly = self.getScrollTop();
                    scrolldiry = dy
                }
                self.lastdeltay -= py
            }
            if (py || px) {
                self.synched("relativexy",
                function() {
                    var dty = self.lastdeltay + self.newscrolly;
                    self.lastdeltay = 0;
                    var dtx = self.lastdeltax + self.newscrollx;
                    self.lastdeltax = 0;
                    if (!self.rail.drag) self.doScrollPos(dtx, dty)
                })
            }
        }
        var hasparentscrollingphase = false;
        function execScrollWheel(e, hr, chkscroll) {
            var px, py;
            if (!chkscroll && hasparentscrollingphase) return true;
            if (e.deltaMode === 0) {
                px = -(e.deltaX * (opt.mousescrollstep / (18 * 3))) | 0;
                py = -(e.deltaY * (opt.mousescrollstep / (18 * 3))) | 0
            } else if (e.deltaMode === 1) {
                px = -(e.deltaX * opt.mousescrollstep * 50 / 80) | 0;
                py = -(e.deltaY * opt.mousescrollstep * 50 / 80) | 0
            }
            if (hr && opt.oneaxismousemode && (px === 0) && py) {
                px = py;
                py = 0;
                if (chkscroll) {
                    var hrend = (px < 0) ? (self.getScrollLeft() >= self.page.maxw) : (self.getScrollLeft() <= 0);
                    if (hrend) {
                        py = px;
                        px = 0
                    }
                }
            }
            if (self.isrtlmode) px = -px;
            var chk = doScrollRelative(px, py, chkscroll, true);
            if (chk) {
                if (chkscroll) hasparentscrollingphase = true
            } else {
                hasparentscrollingphase = false;
                e.stopImmediatePropagation();
                return e.preventDefault()
            }
        }
        this.onmousewheel = function(e) {
            if (self.wheelprevented || self.locked) return false;
            if (self.railslocked) {
                self.debounced("checkunlock", self.resize, 250);
                return false
            }
            if (self.rail.drag) return self.cancelEvent(e);
            if (opt.oneaxismousemode === "auto" && e.deltaX !== 0) opt.oneaxismousemode = false;
            if (opt.oneaxismousemode && e.deltaX === 0) {
                if (!self.rail.scrollable) {
                    if (self.railh && self.railh.scrollable) {
                        return self.onmousewheelhr(e)
                    } else {
                        return true
                    }
                }
            }
            var nw = now();
            var chk = false;
            if (opt.preservenativescrolling && ((self.checkarea + 600) < nw)) {
                self.nativescrollingarea = self.isScrollable(e);
                chk = true
            }
            self.checkarea = nw;
            if (self.nativescrollingarea) return true;
            var ret = execScrollWheel(e, false, chk);
            if (ret) self.checkarea = 0;
            return ret
        };
        this.onmousewheelhr = function(e) {
            if (self.wheelprevented) return;
            if (self.railslocked || !self.railh.scrollable) return true;
            if (self.rail.drag) return self.cancelEvent(e);
            var nw = now();
            var chk = false;
            if (opt.preservenativescrolling && ((self.checkarea + 600) < nw)) {
                self.nativescrollingarea = self.isScrollable(e);
                chk = true
            }
            self.checkarea = nw;
            if (self.nativescrollingarea) return true;
            if (self.railslocked) return self.cancelEvent(e);
            return execScrollWheel(e, true, chk)
        };
        this.stop = function() {
            self.cancelScroll();
            if (self.scrollmon) self.scrollmon.stop();
            self.cursorfreezed = false;
            self.scroll.y = Math.round(self.getScrollTop() * (1 / self.scrollratio.y));
            self.noticeCursor();
            return self
        };
        this.getTransitionSpeed = function(dif) {
            return 80 + (dif / 72) * opt.scrollspeed | 0
        };
        if (!opt.smoothscroll) {
            this.doScrollLeft = function(x, spd) {
                var y = self.getScrollTop();
                self.doScrollPos(x, y, spd)
            };
            this.doScrollTop = function(y, spd) {
                var x = self.getScrollLeft();
                self.doScrollPos(x, y, spd)
            };
            this.doScrollPos = function(x, y, spd) {
                var nx = (x > self.page.maxw) ? self.page.maxw: x;
                if (nx < 0) nx = 0;
                var ny = (y > self.page.maxh) ? self.page.maxh: y;
                if (ny < 0) ny = 0;
                self.synched('scroll',
                function() {
                    self.setScrollTop(ny);
                    self.setScrollLeft(nx)
                })
            };
            this.cancelScroll = function() {}
        } else if (self.ishwscroll && cap.hastransition && opt.usetransition && !!opt.smoothscroll) {
            var lasttransitionstyle = '';
            this.resetTransition = function() {
                lasttransitionstyle = '';
                self.doc.css(cap.prefixstyle + 'transition-duration', '0ms')
            };
            this.prepareTransition = function(dif, istime) {
                var ex = (istime) ? dif: self.getTransitionSpeed(dif);
                var trans = ex + 'ms';
                if (lasttransitionstyle !== trans) {
                    lasttransitionstyle = trans;
                    self.doc.css(cap.prefixstyle + 'transition-duration', trans)
                }
                return ex
            };
            this.doScrollLeft = function(x, spd) {
                var y = (self.scrollrunning) ? self.newscrolly: self.getScrollTop();
                self.doScrollPos(x, y, spd)
            };
            this.doScrollTop = function(y, spd) {
                var x = (self.scrollrunning) ? self.newscrollx: self.getScrollLeft();
                self.doScrollPos(x, y, spd)
            };
            this.cursorupdate = {
                running: false,
                start: function() {
                    var m = this;
                    if (m.running) return;
                    m.running = true;
                    var loop = function() {
                        if (m.running) setAnimationFrame(loop);
                        self.showCursor(self.getScrollTop(), self.getScrollLeft());
                        self.notifyScrollEvent(self.win[0])
                    };
                    setAnimationFrame(loop)
                },
                stop: function() {
                    this.running = false
                }
            };
            this.doScrollPos = function(x, y, spd) {
                var py = self.getScrollTop();
                var px = self.getScrollLeft();
                if (((self.newscrolly - py) * (y - py) < 0) || ((self.newscrollx - px) * (x - px) < 0)) self.cancelScroll();
                if (!opt.bouncescroll) {
                    if (y < 0) y = 0;
                    else if (y > self.page.maxh) y = self.page.maxh;
                    if (x < 0) x = 0;
                    else if (x > self.page.maxw) x = self.page.maxw
                } else {
                    if (y < 0) y = y / 2 | 0;
                    else if (y > self.page.maxh) y = self.page.maxh + (y - self.page.maxh) / 2 | 0;
                    if (x < 0) x = x / 2 | 0;
                    else if (x > self.page.maxw) x = self.page.maxw + (x - self.page.maxw) / 2 | 0
                }
                if (self.scrollrunning && x == self.newscrollx && y == self.newscrolly) return false;
                self.newscrolly = y;
                self.newscrollx = x;
                var top = self.getScrollTop();
                var lft = self.getScrollLeft();
                var dst = {};
                dst.x = x - lft;
                dst.y = y - top;
                var dd = Math.sqrt((dst.x * dst.x) + (dst.y * dst.y)) | 0;
                var ms = self.prepareTransition(dd);
                if (!self.scrollrunning) {
                    self.scrollrunning = true;
                    self.triggerScrollStart(lft, top, x, y, ms);
                    self.cursorupdate.start()
                }
                self.scrollendtrapped = true;
                if (!cap.transitionend) {
                    if (self.scrollendtrapped) clearTimeout(self.scrollendtrapped);
                    self.scrollendtrapped = setTimeout(self.onScrollTransitionEnd, ms)
                }
                self.setScrollTop(self.newscrolly);
                self.setScrollLeft(self.newscrollx)
            };
            this.cancelScroll = function() {
                if (!self.scrollendtrapped) return true;
                var py = self.getScrollTop();
                var px = self.getScrollLeft();
                self.scrollrunning = false;
                if (!cap.transitionend) clearTimeout(cap.transitionend);
                self.scrollendtrapped = false;
                self.resetTransition();
                self.setScrollTop(py);
                if (self.railh) self.setScrollLeft(px);
                if (self.timerscroll && self.timerscroll.tm) clearInterval(self.timerscroll.tm);
                self.timerscroll = false;
                self.cursorfreezed = false;
                self.cursorupdate.stop();
                self.showCursor(py, px);
                return self
            };
            this.onScrollTransitionEnd = function() {
                if (!self.scrollendtrapped) return;
                var py = self.getScrollTop();
                var px = self.getScrollLeft();
                if (py < 0) py = 0;
                else if (py > self.page.maxh) py = self.page.maxh;
                if (px < 0) px = 0;
                else if (px > self.page.maxw) px = self.page.maxw;
                if ((py != self.newscrolly) || (px != self.newscrollx)) return self.doScrollPos(px, py, opt.snapbackspeed);
                if (self.scrollrunning) self.triggerScrollEnd();
                self.scrollrunning = false;
                self.scrollendtrapped = false;
                self.resetTransition();
                self.timerscroll = false;
                self.setScrollTop(py);
                if (self.railh) self.setScrollLeft(px);
                self.cursorupdate.stop();
                self.noticeCursor(false, py, px);
                self.cursorfreezed = false
            }
        } else {
            this.doScrollLeft = function(x, spd) {
                var y = (self.scrollrunning) ? self.newscrolly: self.getScrollTop();
                self.doScrollPos(x, y, spd)
            };
            this.doScrollTop = function(y, spd) {
                var x = (self.scrollrunning) ? self.newscrollx: self.getScrollLeft();
                self.doScrollPos(x, y, spd)
            };
            this.doScrollPos = function(x, y, spd) {
                var py = self.getScrollTop();
                var px = self.getScrollLeft();
                if (((self.newscrolly - py) * (y - py) < 0) || ((self.newscrollx - px) * (x - px) < 0)) self.cancelScroll();
                var clipped = false;
                if (!self.bouncescroll || !self.rail.visibility) {
                    if (y < 0) {
                        y = 0;
                        clipped = true
                    } else if (y > self.page.maxh) {
                        y = self.page.maxh;
                        clipped = true
                    }
                }
                if (!self.bouncescroll || !self.railh.visibility) {
                    if (x < 0) {
                        x = 0;
                        clipped = true
                    } else if (x > self.page.maxw) {
                        x = self.page.maxw;
                        clipped = true
                    }
                }
                if (self.scrollrunning && (self.newscrolly === y) && (self.newscrollx === x)) return true;
                self.newscrolly = y;
                self.newscrollx = x;
                self.dst = {};
                self.dst.x = x - px;
                self.dst.y = y - py;
                self.dst.px = px;
                self.dst.py = py;
                var dd = Math.sqrt((self.dst.x * self.dst.x) + (self.dst.y * self.dst.y)) | 0;
                var ms = self.getTransitionSpeed(dd);
                self.bzscroll = {};
                var p3 = (clipped) ? 1 : 0.58;
                self.bzscroll.x = new BezierClass(px, self.newscrollx, ms, 0, 0, p3, 1);
                self.bzscroll.y = new BezierClass(py, self.newscrolly, ms, 0, 0, p3, 1);
                var loopid = now();
                var loop = function() {
                    if (!self.scrollrunning) return;
                    var x = self.bzscroll.y.getPos();
                    self.setScrollLeft(self.bzscroll.x.getNow());
                    self.setScrollTop(self.bzscroll.y.getNow());
                    if (x <= 1) {
                        self.timer = setAnimationFrame(loop)
                    } else {
                        self.scrollrunning = false;
                        self.timer = 0;
                        self.triggerScrollEnd()
                    }
                };
                if (!self.scrollrunning) {
                    self.triggerScrollStart(px, py, x, y, ms);
                    self.scrollrunning = true;
                    self.timer = setAnimationFrame(loop)
                }
            };
            this.cancelScroll = function() {
                if (self.timer) clearAnimationFrame(self.timer);
                self.timer = 0;
                self.bzscroll = false;
                self.scrollrunning = false;
                return self
            }
        }
        this.doScrollBy = function(stp, relative) {
            doScrollRelative(0, stp)
        };
        this.doScrollLeftBy = function(stp, relative) {
            doScrollRelative(stp, 0)
        };
        this.doScrollTo = function(pos, relative) {
            var ny = (relative) ? Math.round(pos * self.scrollratio.y) : pos;
            if (ny < 0) ny = 0;
            else if (ny > self.page.maxh) ny = self.page.maxh;
            self.cursorfreezed = false;
            self.doScrollTop(pos)
        };
        this.checkContentSize = function() {
            var pg = self.getContentSize();
            if ((pg.h != self.page.h) || (pg.w != self.page.w)) self.resize(false, pg)
        };
        self.onscroll = function(e) {
            if (self.rail.drag) return;
            if (!self.cursorfreezed) {
                self.synched('scroll',
                function() {
                    self.scroll.y = Math.round(self.getScrollTop() / self.scrollratio.y);
                    if (self.railh) self.scroll.x = Math.round(self.getScrollLeft() / self.scrollratio.x);
                    self.noticeCursor()
                })
            }
        };
        self.bind(self.docscroll, "scroll", self.onscroll);
        this.doZoomIn = function(e) {
            if (self.zoomactive) return;
            self.zoomactive = true;
            self.zoomrestore = {
                style: {}
            };
            var lst = ['position', 'top', 'left', 'zIndex', 'backgroundColor', 'marginTop', 'marginBottom', 'marginLeft', 'marginRight'];
            var win = self.win[0].style;
            for (var a in lst) {
                var pp = lst[a];
                self.zoomrestore.style[pp] = (win[pp] !== undefined) ? win[pp] : ''
            }
            self.zoomrestore.style.width = self.win.css('width');
            self.zoomrestore.style.height = self.win.css('height');
            self.zoomrestore.padding = {
                w: self.win.outerWidth() - self.win.width(),
                h: self.win.outerHeight() - self.win.height()
            };
            if (cap.isios4) {
                self.zoomrestore.scrollTop = $window.scrollTop();
                $window.scrollTop(0)
            }
            self.win.css({
                position: (cap.isios4) ? "absolute": "fixed",
                top: 0,
                left: 0,
                zIndex: globalmaxzindex + 100,
                margin: 0
            });
            var bkg = self.win.css("backgroundColor");
            if ("" === bkg || /transparent|rgba\(0, 0, 0, 0\)|rgba\(0,0,0,0\)/.test(bkg)) self.win.css("backgroundColor", "#fff");
            self.rail.css({
                zIndex: globalmaxzindex + 101
            });
            self.zoom.css({
                zIndex: globalmaxzindex + 102
            });
            self.zoom.css('backgroundPosition', '0 -18px');
            self.resizeZoom();
            if (self.onzoomin) self.onzoomin.call(self);
            return self.cancelEvent(e)
        };
        this.doZoomOut = function(e) {
            if (!self.zoomactive) return;
            self.zoomactive = false;
            self.win.css("margin", "");
            self.win.css(self.zoomrestore.style);
            if (cap.isios4) {
                $window.scrollTop(self.zoomrestore.scrollTop)
            }
            self.rail.css({
                "z-index": self.zindex
            });
            self.zoom.css({
                "z-index": self.zindex
            });
            self.zoomrestore = false;
            self.zoom.css('backgroundPosition', '0 0');
            self.onResize();
            if (self.onzoomout) self.onzoomout.call(self);
            return self.cancelEvent(e)
        };
        this.doZoom = function(e) {
            return (self.zoomactive) ? self.doZoomOut(e) : self.doZoomIn(e)
        };
        this.resizeZoom = function() {
            if (!self.zoomactive) return;
            var py = self.getScrollTop();
            self.win.css({
                width: $window.width() - self.zoomrestore.padding.w + "px",
                height: $window.height() - self.zoomrestore.padding.h + "px"
            });
            self.onResize();
            self.setScrollTop(Math.min(self.page.maxh, py))
        };
        this.init();
        $.nicescroll.push(this)
    };
    var ScrollMomentumClass2D = function(nc) {
        var self = this;
        this.nc = nc;
        this.lastx = 0;
        this.lasty = 0;
        this.speedx = 0;
        this.speedy = 0;
        this.lasttime = 0;
        this.steptime = 0;
        this.snapx = false;
        this.snapy = false;
        this.demulx = 0;
        this.demuly = 0;
        this.lastscrollx = -1;
        this.lastscrolly = -1;
        this.chkx = 0;
        this.chky = 0;
        this.timer = 0;
        this.reset = function(px, py) {
            self.stop();
            self.steptime = 0;
            self.lasttime = now();
            self.speedx = 0;
            self.speedy = 0;
            self.lastx = px;
            self.lasty = py;
            self.lastscrollx = -1;
            self.lastscrolly = -1
        };
        this.update = function(px, py) {
            var tm = now();
            self.steptime = tm - self.lasttime;
            self.lasttime = tm;
            var dy = py - self.lasty;
            var dx = px - self.lastx;
            var sy = self.nc.getScrollTop();
            var sx = self.nc.getScrollLeft();
            var newy = sy + dy;
            var newx = sx + dx;
            self.snapx = (newx < 0) || (newx > self.nc.page.maxw);
            self.snapy = (newy < 0) || (newy > self.nc.page.maxh);
            self.speedx = dx;
            self.speedy = dy;
            self.lastx = px;
            self.lasty = py
        };
        this.stop = function() {
            self.nc.unsynched("domomentum2d");
            if (self.timer) clearTimeout(self.timer);
            self.timer = 0;
            self.lastscrollx = -1;
            self.lastscrolly = -1
        };
        this.doSnapy = function(nx, ny) {
            var snap = false;
            if (ny < 0) {
                ny = 0;
                snap = true
            } else if (ny > self.nc.page.maxh) {
                ny = self.nc.page.maxh;
                snap = true
            }
            if (nx < 0) {
                nx = 0;
                snap = true
            } else if (nx > self.nc.page.maxw) {
                nx = self.nc.page.maxw;
                snap = true
            } (snap) ? self.nc.doScrollPos(nx, ny, self.nc.opt.snapbackspeed) : self.nc.triggerScrollEnd()
        };
        this.doMomentum = function(gp) {
            var t = now();
            var l = (gp) ? t + gp: self.lasttime;
            var sl = self.nc.getScrollLeft();
            var st = self.nc.getScrollTop();
            var pageh = self.nc.page.maxh;
            var pagew = self.nc.page.maxw;
            self.speedx = (pagew > 0) ? Math.min(60, self.speedx) : 0;
            self.speedy = (pageh > 0) ? Math.min(60, self.speedy) : 0;
            var chk = l && (t - l) <= 60;
            if ((st < 0) || (st > pageh) || (sl < 0) || (sl > pagew)) chk = false;
            var sy = (self.speedy && chk) ? self.speedy: false;
            var sx = (self.speedx && chk) ? self.speedx: false;
            if (sy || sx) {
                var tm = Math.max(16, self.steptime);
                if (tm > 50) {
                    var xm = tm / 50;
                    self.speedx *= xm;
                    self.speedy *= xm;
                    tm = 50
                }
                self.demulxy = 0;
                self.lastscrollx = self.nc.getScrollLeft();
                self.chkx = self.lastscrollx;
                self.lastscrolly = self.nc.getScrollTop();
                self.chky = self.lastscrolly;
                var nx = self.lastscrollx;
                var ny = self.lastscrolly;
                var onscroll = function() {
                    var df = ((now() - t) > 600) ? 0.04 : 0.02;
                    if (self.speedx) {
                        nx = Math.floor(self.lastscrollx - (self.speedx * (1 - self.demulxy)));
                        self.lastscrollx = nx;
                        if ((nx < 0) || (nx > pagew)) df = 0.10
                    }
                    if (self.speedy) {
                        ny = Math.floor(self.lastscrolly - (self.speedy * (1 - self.demulxy)));
                        self.lastscrolly = ny;
                        if ((ny < 0) || (ny > pageh)) df = 0.10
                    }
                    self.demulxy = Math.min(1, self.demulxy + df);
                    self.nc.synched("domomentum2d",
                    function() {
                        if (self.speedx) {
                            var scx = self.nc.getScrollLeft();
                            self.chkx = nx;
                            self.nc.setScrollLeft(nx)
                        }
                        if (self.speedy) {
                            var scy = self.nc.getScrollTop();
                            self.chky = ny;
                            self.nc.setScrollTop(ny)
                        }
                        if (!self.timer) {
                            self.nc.hideCursor();
                            self.doSnapy(nx, ny)
                        }
                    });
                    if (self.demulxy < 1) {
                        self.timer = setTimeout(onscroll, tm)
                    } else {
                        self.stop();
                        self.nc.hideCursor();
                        self.doSnapy(nx, ny)
                    }
                };
                onscroll()
            } else {
                self.doSnapy(self.nc.getScrollLeft(), self.nc.getScrollTop())
            }
        }
    };
    var _scrollTop = jQuery.fn.scrollTop;
    jQuery.cssHooks.pageYOffset = {
        get: function(elem, computed, extra) {
            var nice = $.data(elem, '__nicescroll') || false;
            return (nice && nice.ishwscroll) ? nice.getScrollTop() : _scrollTop.call(elem)
        },
        set: function(elem, value) {
            var nice = $.data(elem, '__nicescroll') || false; (nice && nice.ishwscroll) ? nice.setScrollTop(parseInt(value)) : _scrollTop.call(elem, value);
            return this
        }
    };
    jQuery.fn.scrollTop = function(value) {
        if (value === undefined) {
            var nice = (this[0]) ? $.data(this[0], '__nicescroll') || false: false;
            return (nice && nice.ishwscroll) ? nice.getScrollTop() : _scrollTop.call(this)
        } else {
            return this.each(function() {
                var nice = $.data(this, '__nicescroll') || false; (nice && nice.ishwscroll) ? nice.setScrollTop(parseInt(value)) : _scrollTop.call($(this), value)
            })
        }
    };
    var _scrollLeft = jQuery.fn.scrollLeft;
    $.cssHooks.pageXOffset = {
        get: function(elem, computed, extra) {
            var nice = $.data(elem, '__nicescroll') || false;
            return (nice && nice.ishwscroll) ? nice.getScrollLeft() : _scrollLeft.call(elem)
        },
        set: function(elem, value) {
            var nice = $.data(elem, '__nicescroll') || false; (nice && nice.ishwscroll) ? nice.setScrollLeft(parseInt(value)) : _scrollLeft.call(elem, value);
            return this
        }
    };
    jQuery.fn.scrollLeft = function(value) {
        if (value === undefined) {
            var nice = (this[0]) ? $.data(this[0], '__nicescroll') || false: false;
            return (nice && nice.ishwscroll) ? nice.getScrollLeft() : _scrollLeft.call(this)
        } else {
            return this.each(function() {
                var nice = $.data(this, '__nicescroll') || false; (nice && nice.ishwscroll) ? nice.setScrollLeft(parseInt(value)) : _scrollLeft.call($(this), value)
            })
        }
    };
    var NiceScrollArray = function(doms) {
        var self = this;
        this.length = 0;
        this.name = "nicescrollarray";
        this.each = function(fn) {
            $.each(self, fn);
            return self
        };
        this.push = function(nice) {
            self[self.length] = nice;
            self.length++
        };
        this.eq = function(idx) {
            return self[idx]
        };
        if (doms) {
            for (var a = 0; a < doms.length; a++) {
                var nice = $.data(doms[a], '__nicescroll') || false;
                if (nice) {
                    this[this.length] = nice;
                    this.length++
                }
            }
        }
        return this
    };
    function mplex(el, lst, fn) {
        for (var a = 0,
        l = lst.length; a < l; a++) fn(el, lst[a])
    }
    mplex(NiceScrollArray.prototype, ['show', 'hide', 'toggle', 'onResize', 'resize', 'remove', 'stop', 'doScrollPos'],
    function(e, n) {
        e[n] = function() {
            var args = arguments;
            return this.each(function() {
                this[n].apply(this, args)
            })
        }
    });
    jQuery.fn.getNiceScroll = function(index) {
        if (index === undefined) {
            return new NiceScrollArray(this)
        } else {
            return this[index] && $.data(this[index], '__nicescroll') || false
        }
    };
    var pseudos = jQuery.expr.pseudos || jQuery.expr[':'];
    pseudos.nicescroll = function(a) {
        return $.data(a, '__nicescroll') !== undefined
    };
    $.fn.niceScroll = function(wrapper, _opt) {
        if (_opt === undefined && typeof wrapper == "object" && !("jquery" in wrapper)) {
            _opt = wrapper;
            wrapper = false
        }
        var ret = new NiceScrollArray();
        this.each(function() {
            var $this = $(this);
            var opt = $.extend({},
            _opt);
            if (wrapper || false) {
                var wrp = $(wrapper);
                opt.doc = (wrp.length > 1) ? $(wrapper, $this) : wrp;
                opt.win = $this
            }
            var docundef = !("doc" in opt);
            if (!docundef && !("win" in opt)) opt.win = $this;
            var nice = $this.data('__nicescroll') || false;
            if (!nice) {
                opt.doc = opt.doc || $this;
                nice = new NiceScrollClass(opt, $this);
                $this.data('__nicescroll', nice)
            }
            ret.push(nice)
        });
        return (ret.length === 1) ? ret[0] : ret
    };
    _win.NiceScroll = {
        getjQuery: function() {
            return jQuery
        }
    };
    if (!$.nicescroll) {
        $.nicescroll = new NiceScrollArray();
        $.nicescroll.options = _globaloptions
    }
}));