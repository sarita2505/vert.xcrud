package org.example.dao;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.sql.SQLConnection;
import org.example.model.Employee;

public interface EmployeeDbService {
    void create();

    void insert();

    void inserEmptData(Employee employee, Handler<AsyncResult<Employee>> next);

    void selectEmpData(Handler<AsyncResult<Employee>> next);

    void update();

    void updateEmpData(Employee employee,Integer id, Handler<AsyncResult<Employee>> next);

    void selectAll();

    void selectById();
    void selectById(Integer id,Handler<AsyncResult<Employee>>handler);
    void selectCountById(Integer id,Handler<AsyncResult<Employee>>handler);


    void delete();
    void deleteById(Integer id,Handler<AsyncResult<Employee>>handler);
}
