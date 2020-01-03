CREATE FUNCTION `get_payment_line_hk_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	#Routine body goes here...
	declare v_amount NUMERIC(20,5);

	select ifnull(sum(crl.amount),
						 0)
		into v_amount
		from csh_repayment_register_hd  crh,
				 csh_repayment_register_ln  crl,
				 csh_payment_requisition_ln cprl
	 where crl.register_hds_id = crh.register_hds_id
				 and crh.repayment_status in
				 ('CASHIER_CONFIRM',
							'ACCOUNTING_CONFIRM',
							'COMPLETELY_CONFIRM')
				 and
				 crl.payment_requisition_line_id = cprl.payment_requisition_line_id
				 and
				 cprl.payment_requisition_line_id = p_id;

  if v_amount is null then
	  set v_amount = 0;
	end if;

	RETURN v_amount;
END