package org.example.lazzy.service.impl;

import org.example.lazzy.mapper.EmployeeMapper;
import org.example.lazzy.pojo.Employee;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    /**
     *  query user by username
     * @param username
     * @return the employee if exists, otherwise, return null
     */
    @Override
    public Employee selectByUsername(String username){
        return employeeMapper.selectByUsername(username);
    }

    @Override
    public Employee selectById(Long id) {
        return employeeMapper.selectById(id);
    }

    /**
     *  add the employee info into employee table
     * @param employee
     */
    @Override
    public void addEmployee(Employee employee) {
        employeeMapper.addEmployee(employee);
    }

    /**
     * paging query
     * @param page
     * @param pageSize
     */
    @Override
    public PageBean<Employee> selectByPage(int page, int pageSize, String name) {
        // calculate the index
        int index= (page - 1) * pageSize;

        // process name
        // like query if name exists
        if(name != null ) {
            if(name.trim().equals("")) { // treat "" as null
                name = null;
            } else {
                name = "%" + name + "%";
            }

        }

        // query records and store the data
        List<Employee> records = employeeMapper.selectByPageAndConditions(index, pageSize, name);
        // query total
        int total = employeeMapper.totalByConditions(name);
        // encapsualte the data into PageBean object
        PageBean<Employee> pageBean = new PageBean<>();
        pageBean.setRecords(records);
        pageBean.setTotal(total);
        return pageBean;
    }

    /**
     *  update the employee info
     * @param employee
     */
    @Override
    public void update(Employee employee) {
        employeeMapper.updateInfo(employee);
    }
}
