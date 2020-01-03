(function() {
	var POW = Math.pow;
	// 乘法
	mul = function(a, b) {
		var m = 0, s1 = String(a), s2 = String(b), l1 = s1.indexOf('.'), l2 = s2
				.indexOf('.'), e1 = s1.indexOf('e'), e2 = s2.indexOf('e');
		if (e1 != -1) {
			m -= Number(s1.substr(e1 + 1));
			s1 = s1.substr(0, e1);
		}
		if (e2 != -1) {
			m -= Number(s2.substr(e2 + 1));
			s2 = s2.substr(0, e2);
		}
		if (l1 != -1)
			m += s1.length - l1 - 1;
		if (l2 != -1)
			m += s2.length - l2 - 1;
		return Number(s1.replace('.', '')) * Number(s2.replace('.', ''))
				/ POW(10, m);
	}

	// 除法
	div = function(a, b) {
		var re = String(a / b), i = re.indexOf('.');
		if (i != -1) {
			re = Number(re).toFixed(16 - i - 1)
		}
		return Number(re);
	}

	// 加法
	plus = function(a, b) {
		var m1 = 0, m2 = 0, m3, s1 = String(a), s2 = String(b), l1 = s1
				.indexOf('.'), l2 = s2.indexOf('.'), e1 = s1.indexOf('e'), e2 = s2
				.indexOf('e');
		if (e1 != -1) {
			m1 -= Number(s1.substr(e1 + 1));
			s1 = s1.substr(0, e1);
		}
		if (e2 != -1) {
			m2 -= Number(s2.substr(e2 + 1));
			s2 = s2.substr(0, e2);
		}
		if (l1 != -1)
			m1 += s1.length - l1 - 1;
		if (l2 != -1)
			m2 += s2.length - l2 - 1;
		if (m2 > m1) {
			m3 = m2;
			m1 = m2 - m1;
			m2 = 0;
		} else if (m1 > m2) {
			m3 = m1;
			m2 = m1 - m2;
			m1 = 0;
		} else {
			m3 = m1;
			m1 = m2 = 0;
		}
		return (Number(s1.replace('.', '')) * POW(10, m1) + Number(s2.replace(
				'.', ''))
				* POW(10, m2))
				/ POW(10, m3);
	}

	// 减法
	minus = function(a, b) {
		return plus(a, -b);
	}

	pow = function(a, b) {
		var re = String(POW(a, b)), i = re.indexOf('.');
		if (i != -1) {
			re = Number(re).toFixed(16 - i - 1)
		}
		return Number(re);
	}
})();

function HecUtil() {

};

HecUtil.prototype.calAmount = function(price, quantity, precision) {
	var amount;
	var calPrecision = (isNaN(precision) || precision == 0) ? 2 : precision;
	amount = mul(price, quantity);
	return !isNaN(amount) ? amount.toFixed(calPrecision) : null;
}

HecUtil.prototype.calExchangeAmount = function(amount, rate, precision) {
	var exchAmount;
	var calPrecision = (isNaN(precision) || precision == 0) ? 2 : precision;
	exchAmount = mul(amount, rate);
	return !isNaN(exchAmount) ? exchAmount.toFixed(calPrecision) : null;
};

HecUtil.prototype.calTaxAmount = function(amount, taxRate, taxCategory,
		precision) {
	var taxAmount;
	var calPrecision = (isNaN(precision) || precision == 0) ? 2 : precision;
	if (taxCategory == 'TAX_EXCLUSIVE_PRICE') {
		// 价外税 税款=（含税价格/（1+税率））*税率=不含税价格*税率
		taxAmount = mul(div(amount, 1 + taxRate), taxRate);
	} else if (taxCategory == 'TAX_INCLUSIVE_PRICE') {
		// 价内税 税款=含税价格*税率
		taxAmount = mul(amount, taxRate);
	}
	return !isNaN(taxAmount) ? taxAmount.toFixed(calPrecision) : null;
};

HecUtil.prototype.calWithOutTaxAmount = function(withoutTaxAmount, taxRate,
		taxCategory, precision) {
	var taxAmount;
	var calPrecision = (isNaN(precision) || precision == 0) ? 2 : precision;
	if (taxCategory == 'TAX_EXCLUSIVE_PRICE') {
		// 价外税 税额=不含税金额*税率
		taxAmount = mul(withoutTaxAmount, taxRate);
	} else if (taxCategory == 'TAX_INCLUSIVE_PRICE') {
		// 价内税 税额=不含税金额/（1-税率）*税率
		taxAmount = mul(div(withoutTaxAmount, 1 - taxRate), taxRate);
	}
	return !isNaN(taxAmount) ? taxAmount.toFixed(calPrecision) : null;
};

// options的ds根据value找name
HecUtil.prototype.getNameByValue = function(optionsDs, valueFieldName,
		nameFieldName, value) {
	var records = optionsDs.getAll();
	for (var i = 0; i < records.length; i++) {
		if (records[i].get(valueFieldName) == value) {
			return records[i].get(nameFieldName);
		}
	}
	return '';
}