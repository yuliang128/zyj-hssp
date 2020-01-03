CREATE FUNCTION `get_payment_line_paid_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	declare v_amount NUMERIC(20,5);

	select ifnull(sum(cwo.csh_write_off_amount),
					 0)
	into v_amount
	from csh_write_off cwo
 where cwo.csh_transaction_line_id in
			 (select cprr.csh_transaction_line_id
					from csh_payment_requisition_ref cprr
				 where cprr.payment_requisition_line_id =
							 p_id)
			 and cwo.document_source = 'PAYMENT_REQUISITION'
			 and cwo.document_line_id = p_id;

	RETURN v_amount;
END