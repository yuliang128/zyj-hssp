<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:a="http://www.aurora-framework.org/application">
    <a:view>
        <style><![CDATA[
	        .flowBox{
	            background:#fff;
	            z-index:2;
	            border:#ccc 1px solid;
	            border-radius:5px;
	            box-shadow: 0 2px 6px 0 rgba(0,0,0,.5);
	            position: absolute;
	            padding:15px;
	        }
	        .flowContent{
	            overflow:auto;
	        }
	        .flowTitle{
	            height:50px;
	            color:#005a78;
	            font-weight: bold;
	            font-size: 14px;
	        }
	        .flowContent li{
	            width: 100%;
	            height:40px;
	            font-size: 12px;
	        }
	        .flowContent li a{
	            float: left;
	            margin-left: 15px;
	        }
	        .flowContent .flowProcess{
	            width:13px;
	            height:40px;
	            float:left;
	            background: url("$[/request/@context_path]/resources/images/aurora/hap/flow-line.png");
	        }
	        .flowContent .flowStart{
	            width:13px;
	            height:13px;
	            float:left;
	            background: url("$[/request/@context_path]/resources/images/aurora/hap/flow-bottom.png");
	        }
	        .flowContent .flowCurrent{
	            background: url("$[/request/@context_path]/resources/images/aurora/hap/flow-current.png");
	        }
	        .history-block .triangle{
	    		z-index:3
	    	}
	        .history-block .triangle .triangle1 {
			    border-color: transparent #ccc transparent;
			    border-width: 10px 0 10px 10px;
			    border-style: solid;
			    position: absolute;
			    top: 0px;
			    left: 0px;
			}
	        .history-block .triangle .triangle2 {
			    border-color: transparent #fff transparent;
			    border-width: 9px 0 9px 9px;
			    border-style: solid;
			    position: absolute;
			    top: 1px;
			    left: -1px;
			}
			.flowInfo{
	            vertical-align: baseline;
	        }
	        .flowInfo div{
	            margin-left: 10px;
	            max-width: 600px; /*12px * 50*/
	            overflow: hidden;
	            white-space: nowrap;
	            text-overflow: ellipsis;
	        }
	    ]]></style>
        <script><![CDATA[
            Aurora.onReady(function() {
                var flowBox = Ext.get("flowBox-$[/parameter/@documentId]"),
                    linesDom = flowBox.child("tbody");
                	//modified by tank 2018-03-01 非空情况下再进行元素获取
                if (linesDom) {
                    var first = linesDom.first().child("div.flowProcess"),
                        last = linesDom.last().child("div.flowProcess");
                }
                if (first && !first.hasClass("flowCurrent")) {
                    first.addClass('flowCurrent');
                }
                if (last && !last.hasClass("flowStart")) {
                    last.addClass('flowStart');
                }
            });
            
            function onFlowContentMouseWheel(event) {
                event.stopPropagation();
            }
        ]]></script>
        <div id="flowBox-$[/parameter/@documentId]" class="flowBox">
            <div class="flowTitle"><![CDATA[$[l:doc_following]]]></div>
            <div class="flowContent" onmousewheel="onFlowContentMouseWheel(event);" style="height:300px;width:800px">
                <table border="0" cellpadding="0" cellspacing="0" style="height:auto;width:auto;">
			    		<#if document_lines_list ??>
			    			<#list document_lines_list as lines>
			    				<tr>
			    					<td><div class="flowProcess"></div></td>
			    					<td class="flowInfo"><div>${lines.operationTimeTz?string('yyyy-MM-dd HH:mm:ss')}</div></td>
			    					<td class="flowInfo"><div>${lines.operater}</div></td>
			    					<td class="flowInfo"><div><#if lines.operation??>${lines.operation}</#if></div></td>
			    					<td class="flowInfo"><div><#if lines.description??>${lines.description}</#if></div></td>
		    					</tr>
			    			</#list>
			    		</#if>
                </table>
            </div>
        </div>
        <div class="triangle">
            <div class="triangle1"/>
            <div class="triangle2"/>
        </div>
    </a:view>
</a:screen>
