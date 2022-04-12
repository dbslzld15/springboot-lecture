package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_BY_NAME_SQL = "select * from customers where name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) values (UUID_TO_BIN(?), ?, ?)";
    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";
    private final String DELETE_ALL_SQL = "delete from customers";

    public List<String> findAllNameByName(String name) {
        List<String> names = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "park", "1234");
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        }catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return names;
    }

    public List<String> findAllName() {
        List<String> names = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "park", "1234");
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                names.add(customerName);
            }
        }catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return names;
    }

    public List<UUID> findAllIds() {
        List<UUID> uuids = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "park", "1234");
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                UUID customerId = toUUID(resultSet.getBytes("customer_id"));
                logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                uuids.add(customerId);
            }
        }catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return uuids;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "park", "1234");
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL)
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    public int updateCustomerName(UUID customerId, String name) {
        try(
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "park", "1234");
                PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL)
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    public int deleteAllCustomers() {
        try(
           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "park", "1234");
           PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL)
        ) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
        }
        return 0;
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }


    public static void main(String[] args){
        JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository();
        int count = jdbcCustomerRepository.deleteAllCustomers();
        logger.info("deleted count {}", count);


        UUID customerId = UUID.randomUUID();
        logger.info("created customerId -> {}", customerId);
        jdbcCustomerRepository.insertCustomer(customerId, "new-user2", "dbslzdl153@naver.com");
        jdbcCustomerRepository.findAllIds().forEach(v -> logger.info("Found name : {}", v));;

    }
}
