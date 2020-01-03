CREATE FUNCTION `get_payment_line_write_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	#Routine body goes here...
	declare v_amount NUMERIC(20,5);

	select ifnull(sum(cwo.csh_write_off_amount * ctl.exchange_rate),
						 0)
		into v_amount
		from csh_write_off           cwo,
				 csh_transaction_header cth,
				 csh_transaction_line   ctl
	 where cwo.csh_transaction_line_id in
				 (select ctl.transaction_line_id
						from csh_payment_requisition_ref ef,
								 csh_transaction_line        ctll,
								 csh_transaction_header      cthh,
								 csh_transaction_line        ctl
					 where ef.csh_transaction_line_id = ctll.transaction_line_id
								 and
								 cthh.source_payment_header_id = ctll.transaction_header_id
								 and cthh.posted_flag = 'Y'
								 and ctl.transaction_header_id = cthh.transaction_header_id
								 and ef.payment_requisition_line_id =
								 p_id)
				 and cwo.gld_interface_flag = 'P'
				 and cwo.document_source = 'EXPENSE_REPORT'
				 and cwo.write_off_type = 'PREPAYMENT_EXPENSE_REPORT'
				 and ctl.transaction_line_id = cwo.csh_transaction_line_id
				 and cth.transaction_header_id = ctl.transaction_header_id;

	if v_amount is null then
		set v_amount = 0;
	end if;

	RETURN v_amount;
END