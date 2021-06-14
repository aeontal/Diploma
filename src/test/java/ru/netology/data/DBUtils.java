package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.models.CreditEntity;
import ru.netology.data.models.OrderEntity;
import ru.netology.data.models.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static Connection conn;
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.login");
    private static final String password = System.getProperty("db.pass");

    private static final String creditSQLQuery = "SELECT * FROM credit_request_entity WHERE created IN (SELECT max(created) " +
            "FROM credit_request_entity);";
    private static final String orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) " +
            "FROM order_entity);";
    private static final String paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) " +
            "FROM payment_entity);";


    // установка соединения

    @SneakyThrows
    private static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //очистка всех данных из таблиц

    @SneakyThrows
    public static void clearDBTables() {
        val runner = new QueryRunner();
        runner.update(getConnection(), "DELETE FROM credit_request_entity;");
        runner.update(getConnection(), "DELETE FROM payment_entity;");
        runner.update(getConnection(), "DELETE FROM order_entity;");
    }

    // получение информации о последнем платеже

    @SneakyThrows
    public static PaymentEntity getLastPayment() {
        val runner = new QueryRunner();
        return runner.query(getConnection(), paymentSQLQuery, new BeanHandler<>(PaymentEntity.class));
    }

    //получение информации о последнем кредите

    @SneakyThrows
    public static CreditEntity getLastCredit() {
        val runner = new QueryRunner();
        return runner.query(getConnection(), creditSQLQuery, new BeanHandler<>(CreditEntity.class));
    }

    //получение информации о последнем заказе

    @SneakyThrows
    public static OrderEntity getLastOrder() {
        val runner = new QueryRunner();
        return runner.query(getConnection(), orderSQLQuery, new BeanHandler<>(OrderEntity.class));
    }
}