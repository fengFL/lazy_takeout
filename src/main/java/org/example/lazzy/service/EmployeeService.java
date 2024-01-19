package org.example.lazzy.service;

import org.example.lazzy.pojo.Employee;
import org.example.lazzy.pojo.PageBean;

public interface EmployeeService {
    /**
     *  query database by username
     * @param username
     * @return
     */
    Employee selectByUsername(String username);

    /**
     * query employee info by id
     */
    Employee selectById(Long id);
    /**
     *  add employee into database
     * @param employee
     */
    void addEmployee(Employee employee);

    /**
     * query by page, page size, and username
     * @param page
     * @param pageSize
     */
    PageBean<Employee> selectByPage(int page, int pageSize, String username);

    /**
     *  update the employee info
     */
    void update(Employee employee);

}
