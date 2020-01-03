CREATE FUNCTION `get_rep_pmt_frozen_amount`(`p_id` int) RETURNS decimal(20,5)
BEGIN
	#Routine body goes here...
	declare v_amount NUMERIC(20,5);
	select ifnull(sum(ard.amount),
					 0)
					 into v_amount
	from acp_requisition_dtl ard,
			 acp_requisition_ln arl,
			 acp_requisition_hd arh
 where ard.ref_document_type = 'REPORT'
			 and exists
 (select 1
					from exp_report_pmt_schedule erps
				 where erps.payment_schedule_line_id = 1
							 and erps.frozen_flag = 'Y'
							 and
							 erps.payment_schedule_line_id = ard.ref_document_line_id
							 and erps.exp_report_header_id = ard.ref_document_line_id)
			 and ard.requisition_lns_id = arl.requisition_lns_id
			 and arl.requisition_hds_id = arh.requisition_hds_id
			 and arh.status = 'COMPLETELY_APPROVED'
			 and arh.audit_flag = 'Y';
	RETURN v_amount;
END