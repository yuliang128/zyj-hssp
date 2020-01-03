package com.hand.hec.wfl.service.impl;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.hr.dto.Employee;
import com.hand.hap.hr.service.IEmployeeService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hec.wfl.dto.WflInsTaskReceiver;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/28 \* Time: 16:43 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverAppointedEmployeeServiceImpl extends WflReceiverBaseServiceImpl {

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IUserService userService;

    /**
     * 生成接收者
     * <p>
     * 指定员工： 参数1：员工的代码和ID 参数2：空 参数3：空 参数4：空
     *
     * @param context 运行上下文
     * @param instance 工作流实例
     * @param receiver 接收者
     * @param insTaskReceiver 工作流实例接收者
     */
    @Override
    public void generateReceiver(IRequest context, WflInstance instance, WflVerTaskReceiver receiver,
                    WflInsTaskReceiver insTaskReceiver) {
        Employee employee = new Employee();
        employee.setEmployeeCode(receiver.getReceiverParam1Code());
        employee.setEmployeeId(receiver.getReceiverParam1Id());
        List<Employee> employeeList = employeeService.select(context, employee, 0, 0);
        if (employeeList.size() != 1) {
            throw new RuntimeException("指定员工无法找到对应的员工信息！employee_code:" + receiver.getReceiverParam1Code());
        }

        employee = employeeList.get(0);

        User queryUser = new User();
        queryUser.setEmployeeId(employee.getEmployeeId());
        List<User> userList = userService.selectOptions(context, queryUser, new Criteria(queryUser));

        for (User user : userList) {
            insertReceiverHierarchy(context, instance, insTaskReceiver, user);
        }
    }
}
