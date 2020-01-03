/**
 * Created by HASEE on 2017/3/15.
 */

window.funArea = {
    sidebarFunFloatAreaShow: false
};

window.searchArea = {
    sidebarSearchShow: false
};
$(window).bind('load', function () {
    $('body').bind("click", function () {
        window.funArea.sidebarFunFloatAreaShow = false;
        window.searchArea.sidebarSearchShow = false;
    });

    var toolVue = new Vue({
        data: {
            auConfig: {
                context_path: window.context_path,
            },
            bmLinks: {
                fun_query: 'sys.SYS8010.sys_function_main',
                current_company_query: 'sys.sys_current_company_query',
                total_company_query: 'sys.sys_total_company_query',
                role_company_query: 'sys.sys_user_role_groups'
            },
            svcLinks: {
                sys_lv1_fun_query: 'modules/sys/public/sys_lv1_fun_query.svc',
                role_select:'role_select.svc'
            }
        },
        methods: {
            getBmUrl: function (bmName, operation) {
                return this.auConfig.context_path + '/autocrud/' + this.bmLinks[bmName] + '/' + operation;
            },
            getSvcUrl: function (svcName) {
                return this.auConfig.context_path + '/' + this.svcLinks[svcName];
            }
        }
    });

    (function () {
        var sidebarVue = new Vue({
            el: '#page-sidebar',
            data: {
                sidebarToggleOn: true,
                searchCondition: '',
                searchResult: [],
                funArea: window.funArea,
                searchArea: window.searchArea,
                funLv3ListRange: 8,
                funLv1List: [],
                currentFunLv1Item: {},
                currentFunLv2Item: {},
                funLv3GroupList: [],
                funLv2Timeout: {}
            },
            watch: {
                searchCondition: 'searchFunction'
            },
            methods: {
                toggleSideBar: function () {
                    this.sidebarToggleOn = !this.sidebarToggleOn;
                },
                searchFunction: function (value, oldValue) {
                    if (!value || value == '' || value == null) {
                        this.hideSearch();
                    } else {
                        $A.request({
                            url: toolVue.getBmUrl('fun_query', 'query'),
                            para: {
                                function_name: value,
                                function_type: 'F',
                                _fetchall: false,
                                pagesize: 20
                            },
                            success: function (resp) {
                                if (resp && resp.result && resp.result.record) {
                                    if (resp.result.record.length) {
                                        this.searchResult = resp.result.record;
                                    } else {
                                        this.searchResult = [resp.result.record];
                                    }

                                    this.showSearch();
                                } else {
                                    this.hideSearch();
                                }
                            },
                            scope: this
                        });
                    }
                },
                showSearch: function () {
                    this.searchArea.sidebarSearchShow = true;
                },
                hideSearch: function () {
                    this.searchArea.sidebarSearchShow = false;
                },
                clearFunLv1: function () {
                    this.funLv1List.forEach(function (funLv1Item) {
                        funLv1Item.selected = false;
                        this.clearFunLv2(funLv1Item);
                    }, this);
                },
                clearFunLv2: function (funLv1Item) {
                    funLv1Item.funLv2List.forEach(function (funLv2Item) {
                        funLv2Item.selected = false;
                        this.clearFunLv3(funLv2Item);
                    }, this);

                    this.funLv3GroupList = [];
                },
                clearFunLv3: function (funLv2Item) {
                    funLv2Item.funLv3List.forEach(function (funLv3Item) {
                        funLv3Item.selected = false;
                    }, this);
                },
                generateFunLv3Group: function (funLv2Item) {
                    var funLv3GroupSize = Math.ceil(funLv2Item.funLv3List.length / this.funLv3ListRange);
                    this.funLv3GroupList = [];
                    for (var i = 0; i < funLv3GroupSize; i++) {
                        var funLv3SingleList = [];
                        for (var j = this.funLv3ListRange * i; j < this.funLv3ListRange * (i + 1); j++) {
                            if (funLv2Item.funLv3List[j]) {
                                funLv3SingleList.push(funLv2Item.funLv3List[j]);
                            }
                        }
                        this.funLv3GroupList.push(funLv3SingleList);
                    }
                },
                showFunFloatArea: function () {
                    this.funArea.sidebarFunFloatAreaShow = true;
                },
                hideFunFloatArea: function () {
                    this.funArea.sidebarFunFloatAreaShow = false;
                },
                jumpTargetFun: function (funCode, funName, url) {
                    this.hideFunFloatArea();
                    this.hideSearch();

                    window.openTab(url, funName);
                },
                funLv1Change: function (lv1FunCode) {
                    if (this.currentFunLv1Item.fun_code != lv1FunCode) {
                        this.clearFunLv1();
                    }
                    //更新Lv3列表?、Lv2列表和Lv1列表的样式
                    this.funLv1List.forEach(function (funLv1Item) {
                        if (funLv1Item.fun_code === lv1FunCode) {
                            this.currentFunLv1Item = funLv1Item;
                            funLv1Item.selected = true;
                        } else {
                            funLv1Item.selected = false;
                        }
                    }, this);

                    this.showFunFloatArea();
                },
                funLv2Change: function (lv2FunCode, event) {
                    var changeLv2Fun = function (thisData) {
                        return function () {
                            thisData.clearFunLv2(thisData.currentFunLv1Item);
                            thisData.currentFunLv1Item.funLv2List.forEach(function (funLv2Item) {
                                if (funLv2Item.fun_code === lv2FunCode) {
                                    thisData.currentFunLv2Item = funLv2Item;
                                    thisData.generateFunLv3Group(funLv2Item);
                                    funLv2Item.selected = true;
                                } else {
                                    funLv2Item.selected = false;
                                }
                            }, thisData);
                        };
                    }

                    this.funLv2Timeout[lv2FunCode] = setTimeout(changeLv2Fun(this), 150);
                },
                funLv2StopChange: function (lv2FunCode, event) {
                    clearTimeout(this.funLv2Timeout[lv2FunCode]);
                },
                funLv3Change: function (lv3FunCode) {
                    this.clearFunLv3(this.currentFunLv2Item);
                    this.currentFunLv2Item.funLv3List.forEach(function (funLv3Item) {
                        if (funLv3Item.fun_code === lv3FunCode) {
                            funLv3Item.selected = true;
                        } else {
                            funLv3Item.selected = false;
                        }
                    }, this);
                }
            },
            created: function () {
                $A.request({
                    url: toolVue.getSvcUrl('sys_lv1_fun_query'),
                    success: function (resp) {
                        if (resp && resp.result && resp.result.lv1_fun_list) {
                            this.funLv1List = JSON.parse(resp.result.lv1_fun_list);
                        }
                    },
                    scope: this
                });
            },
            updated: function () {
                //由于加载该段JS时vue尚未渲染完成，所以在sidebar的vue的updated中调用$(window).resize()重新计算宽度
                $(window).resize();
                $A.auWindow.fireEvent('resize');

                if ($(this.$el).hasClass('page-side-toggle-off')) {
                    $('[data-toggle="tooltip"]').tooltip();
                } else {
                    $('[data-toggle="tooltip"]').tooltip('destroy');
                }
            }
        });
    })();

    (function () {
        var topbarVue = new Vue({
            el: '#page-topbar',
            data: {
                currentRoleCompany: ' ',
                totalRoleCompany: []
            },
            methods: {
                logout: function () {
                    Aurora.showConfirm('提示', '确认退出系统吗?', function (cmp) {
                        Aurora.request({
                            url: 'logout.svc',
                            para: {},
                            success: function () {
                                try {
                                    if (window.opener) {
                                        opener.location.href = 'login.screen';
                                        window.close();
                                    } else {
                                        location.href = 'login.screen';
                                    }
                                } catch (e) {
                                    window.close();
                                }
                            },
                            scope: this
                        });
                    }, null, null, 85);
                },
                changeRoleCompany:function(companyId,roleId){
                    Aurora.request({
                        url: toolVue.getSvcUrl('role_select'),
                        para: {
                            company_id:companyId,
                            role_id:roleId
                        },
                        success: function(){
                            this.refreshHome();
                        },
                        scope: this
                    });
                },
                refreshHome:function(){
                    window.location.reload();
                }
            },
            created: function () {
                $A.request({
                    url: toolVue.getBmUrl('role_company_query', 'query'),
                    para: {
                        current_role_company: "true"
                    },
                    success: function (resp) {
                        if (resp && resp.result && resp.result.record) {
                            this.currentRoleCompany = resp.result.record.role_company_name;
                        }
                    },
                    scope: this
                });

                $A.request({
                    url: toolVue.getBmUrl('role_company_query', 'query'),
                    success: function (resp) {
                        if (resp && resp.result && resp.result.record) {
                            if (resp.result.record.length) {
                                this.totalRoleCompany = resp.result.record;
                            } else {
                                this.totalRoleCompany = [resp.result.record];
                            }
                        }
                    },
                    scope: this
                });
            }
        });
    })();
});


