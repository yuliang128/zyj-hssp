CREATE FUNCTION `get_payment_line_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	declare v_amount NUMERIC(20,5);

	select cprl.amount
		into v_amount
		from csh_payment_requisition_ln cprl
	 where cprl.payment_requisition_line_id = p_id;

	if v_amount is null then
	  set v_amount = 0;
	end if;

	RETURN v_amount;
END