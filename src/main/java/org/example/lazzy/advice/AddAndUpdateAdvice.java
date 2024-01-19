package org.example.lazzy.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.lazzy.common.BaseContext;
import org.example.lazzy.pojo.Employee;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class AddAndUpdateAdvice {
    // all methods that start with add will be reinforced
    @Pointcut("execution(* org.example.lazzy.service.*.add*(..))")
    private void pointCutForAdd(){}

    // all methods that start with update will be reinforced
    @Pointcut("execution(* org.example.lazzy.service.*.update*(..))")
    private void pointCutForUpdate(){}


    @Before("pointCutForAdd()")
    // what we want to do
    public void addReinforcement(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // get current operator's id Through ThreadLocal. This id is the creation user and updating user
        Long currentId = BaseContext.getCurrentId();
        log.info("Before method addXx() ... current Thread id in Advice --> {}", Thread.currentThread().getId());

        // signature is the method that caught
//        System.out.println(joinPoint.getSignature());
        // joinPoint.getArgs() get the parameter array. In this case, it is one-element array.

        // get class
        Object object = joinPoint.getArgs()[0];
//        System.out.println(object);
        // get class bytes
        Class<?> objectClass = joinPoint.getArgs()[0].getClass();
        // get the setCreateTime method
        Method setCreateTime = objectClass.getMethod("setCreateTime", LocalDateTime.class);
        // get the setCreateTime method
        Method setUpdateTime = objectClass.getMethod("setUpdateTime", LocalDateTime.class);
        // get the setCreateTime method
        Method setCreateUser = objectClass.getMethod("setCreateUser", Long.class);
        // get the setCreateTime method
        Method setUpdateUser = objectClass.getMethod("setUpdateUser", Long.class);




        // set the value

        setCreateTime.invoke(object, LocalDateTime.now());
        setUpdateTime.invoke(object, LocalDateTime.now());


        setCreateUser.invoke(object, currentId);
        setUpdateUser.invoke(object, currentId);

//        System.out.println(object);


        // getTaget() and getThis() will get the service object.
//        Object target = joinPoint.getTarget();
//        Object aThis = joinPoint.getThis();




    }

    @Before("pointCutForUpdate()")
    public void updateReinforcement(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // get current operator's id through ThreadLocal
        Long currentId = BaseContext.getCurrentId();


        // get original class, the first parameter need to be one pojo class with setUpdateTime and setUpdateUser methods
        Object object = joinPoint.getArgs()[0];
        // get the class bytes
        Class<?> objectClass = object.getClass();
        // get the setUpdateTime and setUpdateUser methods
        Method setUpdateTime = objectClass.getMethod("setUpdateTime", LocalDateTime.class);
        Method setUpdateUser = objectClass.getMethod("setUpdateUser", Long.class);

        // invoke the  setUpdateTime and  setUpdateUser methods

        setUpdateTime.invoke(object, LocalDateTime.now());
        setUpdateUser.invoke(object, currentId);

    }
}
