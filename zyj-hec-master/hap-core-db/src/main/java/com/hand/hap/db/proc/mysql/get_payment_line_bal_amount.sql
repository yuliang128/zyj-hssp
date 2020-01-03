CREATE FUNCTION `get_payment_line_bal_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	#Routine body goes here...
	declare v_amount NUMERIC(20,5);

	set v_amount = get_payment_line_paid_amount(p_id) - get_payment_line_write_amount(p_id) - get_payment_line_hk_amount(p_id);

	if v_amount is null then
		set v_amount = 0;
	end if;

	RETURN 0;
END