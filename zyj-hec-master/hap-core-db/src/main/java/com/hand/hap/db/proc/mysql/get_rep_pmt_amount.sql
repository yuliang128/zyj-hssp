CREATE FUNCTION `get_rep_pmt_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	#Routine body goes here...
	declare v_amt NUMERIC(20,5);
	select s.DUE_AMOUNT into v_amt
		from exp_report_pmt_schedule s
		where s.PAYMENT_SCHEDULE_LINE_ID = p_id;

	if v_amt is null then
		set v_amt = 0;
	end if;

	RETURN v_amt;
END