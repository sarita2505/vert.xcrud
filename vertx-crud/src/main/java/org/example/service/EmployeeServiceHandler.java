package org.example.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.sql.SQLConnection;
import org.example.dao.EmployeeDbServiveImpl;
import org.example.model.Employee;

public class EmployeeServiceHandler {
    private Employee employee=new Employee();
    private EmployeeDbServiveImpl dao =new EmployeeDbServiveImpl();
    private  SQLConnection connection;
    public void createTable(){
        try {
            dao.create();
        } catch (Exception e) {
            System.out.println("table creation failed: "+e);
        }
    }
    public void insertData(){
        employee.setId(6);
        employee.setName("rupal");
        Handler<AsyncResult<Employee>> handler=event -> {
            if (event.failed()){
                System.out.println(event.cause());
            }else {
                Employee e = event.result();
                System.out.println(e);
            }
        };
        try {
            dao.inserEmptData(employee,handler);
        } catch (Exception e) {
            System.out.println("not able to insert the data: "+e);
        }
    }
    public void insertEmplData(Employee employee,Handler<AsyncResult<Employee>> resultHandler){
        try {
            dao.inserEmptData(employee,resultHandler);
        } catch (Exception e) {
            System.out.println("unable to insert: "+e);
        }
    }
    public void selectAll(Handler<AsyncResult<Employee>> resultHandler){
        try {
            dao.selectEmpData(resultHandler);
        } catch (Exception e) {
            System.out.println("data is not available: "+e);
        }
    }

    public void updateEmp(Employee emp1,Integer id,Handler<AsyncResult<Employee>> resultHandler){
        try {
            dao.updateEmpData(emp1,2,resultHandler);
        } catch (Exception e) {
            System.out.println("not able to update: "+e);
        }
    }

    public void selectByEmpId(int id, Handler<AsyncResult<Employee>> resultHandler){

        try {
            dao.selectById(2,resultHandler);
        } catch (Exception e) {
            System.out.println("not able to update: "+e);
        }
    }
    public void selectCountByEmpId(int id, Handler<AsyncResult<Employee>> resultHandler){

        try {
            dao.selectById(id,resultHandler);
        } catch (Exception e) {
            System.out.println("not able to update: "+e);
        }
    }

    public void deleteEmpById(int id,Handler<AsyncResult<Employee>> resultHandler){
        try {
            dao.deleteById(id,resultHandler);
        } catch (Exception e) {
            System.out.println("not able to update: "+e);
        }
    }
}
