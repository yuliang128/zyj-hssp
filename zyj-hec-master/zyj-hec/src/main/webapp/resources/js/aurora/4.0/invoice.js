var generateInvoice = function(invoiceHeader, invoiceLines) {
	var linesHtml = "";
	linesHtml += "<tr><td class='b3 common' style='text-align: center; color: #9E5209;'>货物或应税劳务、服务名称</td>";
	linesHtml += "<td class='b3 common' style='text-align: center; color: #9E5209;' width='100pt'>规格型号</td>";
	linesHtml += "<td class='b3 common' style='text-align: center; color: #9E5209;' width='50pt'>单位</td>";
	linesHtml += "<td class='b3 common' style='text-align: center; color: #9E5209;' width='100pt'>数量</td>";
	linesHtml += "<td class='b3 common' style='text-align: center; color: #9E5209;' width='100pt'>单价</td>";
	linesHtml += "<td class='b3 common' style='text-align: center; color: #9E5209;'width='100pt'>金额</td>";
	linesHtml += "<td class='b3 common' style='text-align: center; color: #9E5209;' width='40pt'>税率</td>";
	linesHtml += "<td class='b3 common' style='text-align: center; color: #9E5209;' width='100pt'>税 额</td></tr>";
	for (var i = 0; i < invoiceLines.length; i++) {
		var line_information = invoiceLines[i], LineHtml = "<tr></tr><td class='b3 common'style='text-align: left; '>"
				+ line_information["goods_or_taxable_service"] + "</td><td class='b3 common'style='text-align: left;'>"
				+ (line_information["specifications"] ? line_information["specifications"] : " ")
				+ "</td><td class='b3 common'style='text-align: left;'>"
				+ (line_information["unit"] ? line_information["unit"] : " ")
				+ "</td><td class='b3 common'style='text-align: right;'>"
				+ (line_information["quantity"] ? line_information["quantity"] : " ")
				+ "</td><td class='b3 common'style='text-align: right;'>"
				+ (line_information["unit_price"] ? line_information["unit_price"] : " ")
				+ "</td><td class='b3 common'style='text-align: right;'>"
				+ (line_information["without_tax_amount"] ? line_information["without_tax_amount"] : " ")
				+ "</td><td class='b3 common'style='text-align: center;'>"
				+ (line_information["tax_rate"] ? (line_information["tax_rate"] * 100 + "%") : " ")
				+ "</td><td class='b3 common'style='text-align: right;'>"
				+ (line_information["tax_amount"] ? line_information["tax_amount"] : " ") + "</td ></tr>";
		linesHtml += LineHtml;
	}
	linesHtml += "<tr><td class='b2 common' style='text-align: center; color: #9E5209;'>合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</td>";
	linesHtml += "<td class='b2 common' style='text-align: center; '></td>";
	linesHtml += "<td class='b2 common' style='text-align: center; '></td>";
	linesHtml += "<td class='b2 common' style='text-align: center; '></td>";
	linesHtml += "<td class='b2 common' style='text-align: center; '></td>";
	linesHtml += "<td class='b2 common' style='text-align: center; ' id='without_tax_amount'></td>";
	linesHtml += "<td class='b2 common' style='text-align: center; '></td>";
	linesHtml += "<td class='b2 common' style='text-align: center; ' id='tax_amount'></td></tr>";
	$("#goods").html(linesHtml);
	for (key in invoiceHeader) {
		if (key != "invoice_distrect" || key != "invoice_type") {
			$("#" + key).html((invoiceHeader[key] ? invoiceHeader[key] : " "));
		}
	}
	$("#invoice_type").html(
			(invoiceHeader["invoice_distrect"] ? invoiceHeader["invoice_distrect"] : " ")
					+ (invoiceHeader["invoice_type"] ? invoiceHeader["invoice_type"] : " "));
	close_mask();
};

function show_mask() {
	var query_mask_div = $("#query_mask_div"),
	// query_wait_div = $("#" + id),
	query_wait_img = $("#query_wait_img"), query_wait_img_width = query_wait_img.width(), query_wait_img_height = query_wait_img
			.height(), body_width = $(document.body).outerWidth(true), body_height = $(document.body).outerHeight(true), window_width = $(
			window).width(), window_height = $(window).height();
	query_mask_div.css({
		'display' : 'block',
		'width' : window_width + 'px',
		'height' : window_height + 'px'
	});
	query_wait_img.css({
		'top' : (window_height - query_wait_img_height) / 2 + 'px',
		'left' : (window_width - query_wait_img_width) / 2 + 'px',
	});
}

function close_mask() {
	$("#query_mask_div").css({
		'display' : 'none'
	});
}

function check_queryString(queryString) {
	pattern = /01,01|04|10,\d{10|12},\d{8},[1-9]\d*.\d*|0\d*[1-9]\d*,\d{4}\d{2}\d{2},\d{0|20},[A-Z0-9]{4},?/;
	return pattern.test(queryString);
}