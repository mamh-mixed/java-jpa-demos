package com.mamh.ssh.demo.actions;

import com.mamh.ssh.demo.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.RequestAware;

import javax.xml.ws.RequestWrapper;
import java.util.Map;

public class EmployeeAction extends ActionSupport implements RequestAware {

    private EmployeeService employeeService;
    private Map<String, Object> request;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public String list() {
        System.out.println("list..............");
        request.put("employees", employeeService.getAll());
        return "list";
    }

    @Override
    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }
}
