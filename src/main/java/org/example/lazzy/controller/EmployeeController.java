package org.example.lazzy.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lazzy.common.Result;
import org.example.lazzy.pojo.Employee;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Employee login
     * @param request --> for session
     * @param employee --> the employee object encapsulated with username and password
     * @return the result
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        /**
         *  1. md5 encryption
         *  2. query employee by username
         *  3. if null return false
         *  4. compare the password, if not the same, return false
         *  5. check the status of the user, if is disabled, return the result indicate that the user is disabled
         *  6. login succeed, store the user id into session
         *
         */
        String password = employee.getPassword();
        // 1. md5 cryption
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //  2. query employee by username
        Employee user = employeeService.selectByUsername(employee.getUsername());

        // 3. if null return false
        if(user == null) {
            return Result.error("login failed");
        }
        // 4. compare the password, if not the same, return false
        if(!password.equals(user.getPassword())){
            return Result.error("login failed");
        }
        // 5. check the status of the user, if is disabled, return the result indicate that the user is disabled
        if(user.getStatus() == 0){
            return Result.error("user is disabled");
        }
        // 6. login succeed, store the user id into session
        HttpSession session = request.getSession();
        session.setAttribute("employee", user.getId());
        return Result.success(user);
    }

    /**
     * Employee logout
     * @return
     */
    @PostMapping("logout")
    public Result<String> logout(HttpServletRequest request){
        // remove id from the session
        request.getSession().removeAttribute("employee");
        return Result.success("logout succeed");
    }

    /**
     * Add employee
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee){
        log.info("current Thread id in addXxx() controller ---> {}", Thread.currentThread().getId());
        log.info("add employee. Employee info: {}", employee.toString());
        // create id
        Long id = System.currentTimeMillis();
        // set default password as MD5 ("123456")
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
        employee.setId(id);


        // create time, update time, create user, and update user are filled in Advice class.

//        LocalDateTime localDateTime = LocalDateTime.now();
//        employee.setCreateTime(localDateTime);
//        employee.setUpdateTime(localDateTime);

        // get the current operator's id
//        Long operator = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(operator);
//        employee.setUpdateUser(operator);

        // not null check
        String username = employee.getUsername().trim();
        String name = employee.getName().trim();
        String phoneNum = employee.getPhone().trim();
        String status = employee.getSex();
        String idNumber = employee.getIdNumber().trim();
        if(username.equals("") || name.equals("") || phoneNum.equals("") || status == null || idNumber.equals("")){
            return Result.error("info are required");
        }

        /**
         *
         *  do not query from the database, which can give more burden to server.
         *  Through the exception directly, then processed by the global exception handler
          */


        // check the username from the database
//        Employee emp = employeeService.selectByUsername(employee.getUsername());
//        if(emp != null){
            // username already exist,
//            return Result.error("username already exist");
//        }
        // username does not exist. Add the info into database
        employeeService.addEmployee(employee);

        return Result.success("succeed");
    }

    @GetMapping("/page")
    public Result<PageBean<Employee>> selectByPage(int page, int pageSize, String name){
        // get the result
        PageBean<Employee> pageBean = employeeService.selectByPage(page, pageSize, name);
        if(pageBean == null) {
            return Result.error("no message");
        }
        return Result.success(pageBean);
    }

    /**
     *  update status
     */
    @PutMapping
    public Result<String> updateStatus(HttpServletRequest request, @RequestBody Employee employee){

        log.info("current Thread id in updateXxx() controller ---> {}", Thread.currentThread().getId());
        // since this is an update operation, we do not need to update the creation time and user.
        // get current operator id
        Long id = (Long) request.getSession().getAttribute("employee");
        // set update user
        employee.setUpdateUser(id);
        // set update time
        employee.setUpdateTime(LocalDateTime.now());

        // check the information from the front
        if(employee.getId() == null) {
            return Result.error("update failed");
        }
        if(employee.getStatus() == null){
            return Result.error("update failed");
        }
        // update the status of the employee
        employeeService.update(employee);

        return Result.success("update successful");

    }

    /**
     *  update employee info by id
     */
    @GetMapping("/{id}")
    public Result<Employee> updateById(HttpServletRequest request, @PathVariable Long id){
        if(id == null) {
            return Result.error("update failed");
        }
        // query info with the specified id
        Employee employee = employeeService.selectById(id);
        if(employee == null) {
            // employee does not exist
            return Result.error("update failed");
        }
        // employee exists
        // return the employee
        return Result.success(employee);
    }

}
