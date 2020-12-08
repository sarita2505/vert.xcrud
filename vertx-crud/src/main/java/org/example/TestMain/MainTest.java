package org.example.TestMain;

import org.example.dao.EmployeeDbService;
import org.example.dao.EmployeeDbServiveImpl;
import org.example.model.Employee;
import org.example.service.EmployeeServiceHandler;

public class MainTest {
    public static void main(String[] args) {
        // Vertx vertx = Vertx.vertx();
        //vertx.deployVerticle(new EmployeeDbServiveImpl());
        //EmployeeDbService service=new EmployeeDbServiveImpl();
        //service.insert();
        //service.selectAll();
        //service.selectById();
        // service.update();
        //service.delete();
        EmployeeServiceHandler handler = new EmployeeServiceHandler();
        //inser data
//        Employee employee = new Employee();
//        employee.setId(9);
//        employee.setName("addya");
//        handler.insertEmplData(employee, event -> {
//            if (event.failed()) {
//                System.out.println(event.cause());
//            } else
//                System.out.println("insert data:" + event.result());
//        });
        //update data
//        Employee employee1 = new Employee();
//        employee1.setName("leela");
//        handler.updateEmp(employee1, 2, event -> {
//            if (event.failed()) {
//                System.out.println(event.cause());
//            } else
//                System.out.println("update employee with" + employee.getId() + event.result());
//        });
       // select all
        handler.selectAll(event -> {
            if (event.failed()) {
                System.out.println(event.cause());
            } else
                System.out.println("selecting all data" + event.result());
        });
        //select by emp id
//        handler.selectByEmpId(2, event -> {
//            if (event.failed()) {
//                System.out.println(event.cause());
//            } else
//                System.out.println(event.result());
//        });
        //delete emp
//        handler.deleteEmpById(2, event -> {
//            if (event.failed()) {
//                System.out.println(event.cause());
//            } else
//                System.out.println(event.result());
//        });

        handler.selectCountByEmpId(2, event -> {
            if (event.failed()) {
                System.out.println(event.cause());
            } else
                System.out.println("--------"+event.result());
        });
    }
}
