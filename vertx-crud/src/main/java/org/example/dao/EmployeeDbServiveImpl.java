package org.example.dao;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import org.example.db_connection.DatabaseConfigLoader;
import org.example.model.Employee;

public class EmployeeDbServiveImpl extends AbstractVerticle implements EmployeeDbService {
    private final String INSERT = "insert into  employee(id,name) values (?,?)";
    private final String SELECTALL = "select * from employee";
    private final String SELECTBYID = "select * from employee where id=?";
    private final String SELECTCOUNTBYID = "select count(*) as count from employee where id=?";
    private final String UPDATE = "update employee set name=? where id=?";
    private final String DELETE = "delete from employee where id=?";
    JsonObject connPropertyJson = DatabaseConfigLoader.getDbConfig();
//            new JsonObject()
//            //.put("url", "jdbc:hsqldb:mem:employee?shutdown=true")
//            .put("url", "jdbc:mysql://localhost:3306/users")
//            //.put("driver_class", "org.hsqldb.jdbcDriver")
//            .put("driver_class", "com.mysql.jdbc.Driver")
//            .put("max_pool_size", 30)
//            .put("user", "root")
//           // .put("password", new String());
//            .put("password", "");

//    @Override
//    public void start(Promise<Void> startPromise) throws Exception {
//        final JDBCClient client = JDBCClient.createShared(vertx, connPropertyJson);
//        client.getConnection(conn -> {
//            if (conn.failed()) {
//                System.err.println(conn.cause().getMessage());
//                return;
//            }
//            final SQLConnection connection = conn.result();
//            connection.setAutoCommit(true,handler->{
//                System.out.println(handler.result());
//            });
//            connection.execute("create table employee(id int primary key, name varchar(255))", res -> {
//                if (res.failed()) {
//                    throw new RuntimeException(res.cause());
//                } else
//                    System.out.println("table created");
//            });
//
//            connection.close();
//        });
//
//    }

    @Override
    public void create() {
        System.out.println(connPropertyJson);
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        System.out.println("client created");
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }
            final SQLConnection connection = conn.result();
            connection.execute("create table employee(id int primary key, name varchar(255))", res -> {
                if (res.failed()) {
                    throw new RuntimeException(res.cause());
                } else
                    System.out.println("table created");
            });
            connection.close();
        });
    }

    @Override
    public void insert() {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }
            final SQLConnection connection = conn.result();
            connection.execute("insert into  employee values(4,'hari')", res -> {
                if (res.failed()) {
                    throw new RuntimeException(res.cause());
                }
            });
            connection.close();
        });
    }

    @Override
    public void selectAll() {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }
            final SQLConnection connection = conn.result();
            connection.query("select * from employee", res -> {
                for (JsonArray jsonArray : res.result().getResults()) {
                    System.out.println(jsonArray.encodePrettily());
                }
            });
            connection.close();
        });

    }

    @Override
    public void selectById() {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }
            final SQLConnection connection = conn.result();
            connection.query("select * from employee where id=2", res -> {
                System.out.println(res.result().getResults());
            });
            connection.close();
        });
    }

    @Override
    public void update() {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }
            final SQLConnection connection = conn.result();
            connection.query("update employee set name='sita' where id=2", res -> {
                selectAll();
            });
            connection.close();
        });
    }

    @Override
    public void delete() {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }
            final SQLConnection connection = conn.result();
            connection.query("delete from employee where id=1", res -> {
                selectAll();
            });
            connection.close();
        });
    }

    @Override
    public void inserEmptData(Employee employee, Handler<AsyncResult<Employee>> next) {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }
            client.updateWithParams(INSERT,
                    new JsonArray().add(employee.getId()).add(employee.getName()),
                    res -> {
                        if (res.failed()) {
                            throw new RuntimeException(res.cause());
                        } else
                            System.out.println("inserting data"+res.result());
                    });
            //client.close();
        });
    }

    @Override
    public void selectEmpData(Handler<AsyncResult<Employee>> next) {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }

            client.query(SELECTALL,
                    res -> {
                        if (res.failed()) {
                            throw new RuntimeException(res.cause());
                        } else
                            System.out.println("select all data: "+res.result().getResults());
                    });
            //client.close();
        });
    }

    @Override
    public void updateEmpData(Employee employee, Integer id, Handler<AsyncResult<Employee>> next) {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }

            client.updateWithParams(UPDATE, new JsonArray().add(employee.getName()).add(id),
                    res -> {
                        if (res.failed()) {
                            throw new RuntimeException(res.cause());
                        } else {
                            System.out.println("select all after update: ");
                            selectAll();
                        }
                        // System.out.println(res.result().getResults());
                    });
            //client.close();
        });
    }

    @Override
    public void deleteById(Integer id, Handler<AsyncResult<Employee>> next) {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }

            client.updateWithParams(DELETE, new JsonArray() .add(id),
                    res -> {
                        if (res.failed()) {
                            throw new RuntimeException(res.cause());
                        } else {
                            selectAll();
                            System.out.println("delete emp byid: "+res.result().toString());
                        }
                        // System.out.println(res.result().getResults());
                    });
            //client.close();
        });


    }

    @Override
    public void selectById(Integer id, Handler<AsyncResult<Employee>> next) {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }

            client.queryWithParams(SELECTCOUNTBYID, new JsonArray().add(id),
                    res -> {
                        if (res.failed()) {
                            System.out.println(res.cause());
                        } else {
                            //System.out.println("select emp count by Id"+res.result().encodePrettily());
                            System.out.println("====="+res.result().getRows().get(0));
                        }
                    });
        });


    }

    @Override
    public void selectCountById(Integer id, Handler<AsyncResult<Employee>> handler) {
        final JDBCClient client = JDBCClient.createShared(Vertx.vertx(), connPropertyJson);
        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }

            client.querySingleWithParams(SELECTCOUNTBYID, new JsonArray().add(id),
                    res -> {
                        if (res.failed()) {
                            System.out.println(res.cause());
                        } else {
                            System.out.println("select emp by Id"+res.result().encodePrettily());
                            System.out.println(res.result().toString());
                        }
                    });
        });


    }
}
