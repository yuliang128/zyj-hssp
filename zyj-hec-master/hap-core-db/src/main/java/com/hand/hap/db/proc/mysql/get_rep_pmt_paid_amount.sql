CREATE FUNCTION `get_rep_pmt_paid_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	declare v_paid_amount NUMERIC(20,5);

	select
		ifnull(sum(cwo.csh_write_off_amount),
		0) into
			v_paid_amount
		from
			exp_report_pmt_schedule erps,
			csh_write_off cwo
		where
			erps.payment_schedule_line_id = p_id
			and erps.payment_schedule_line_id = cwo.document_line_id
			and erps.exp_report_header_id = cwo.document_header_id
			and cwo.document_source = 'EXPENSE_REPORT';

	RETURN v_paid_amount;
END