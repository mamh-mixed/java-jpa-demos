package com.mamh.spring.demo.test;

import com.mamh.spring.demo.aop.byxml.ArithmeticCalculator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ArithmeticCalculatorTest {


    @Test
    public void test() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        ArithmeticCalculator arithmeticCalculatorImpl = ctx.getBean(ArithmeticCalculator.class);
        System.out.println("int test() " + arithmeticCalculatorImpl.add(3, 0));
        System.out.println("\n");

        System.out.println("int test() " + arithmeticCalculatorImpl.div(3, 1));
    }

    @Test
    public void testByxml() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContextByxml.xml");

        ArithmeticCalculator arithmeticCalculatorImpl = (ArithmeticCalculator) ctx.getBean("arithmeticCalculator");
        System.out.println("int test() " + arithmeticCalculatorImpl.add(3, 0));
        System.out.println("\n");

        System.out.println("int test() " + arithmeticCalculatorImpl.div(3, 1));
    }
}