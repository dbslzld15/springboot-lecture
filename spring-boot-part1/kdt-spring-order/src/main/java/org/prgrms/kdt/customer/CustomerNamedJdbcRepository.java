package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        Map<String, Object> paramMap = toParamMap(customer);
        int update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at) values (UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :createdAt)",
                paramMap);
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        Map<String, Object> paramMap = toParamMap(customer);
        int update = jdbcTemplate.update("update customers SET name = :name, email = :email, last_login_at = :lastLoginAt where customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                paramMap);
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", (rs, rowNum) -> rowMapperCustomer(rs));
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            Customer customer = jdbcTemplate.queryForObject(
                    "select * from customers where customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    (rs, rowNum) -> rowMapperCustomer(rs));
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try{
            Customer customer = jdbcTemplate.queryForObject(
                    "select * from customers where name = :name",
                    Collections.singletonMap("name", name),
                    (rs, rowNum) -> rowMapperCustomer(rs));
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            Customer customer = jdbcTemplate.queryForObject(
                    "select * from customers where email = :email",
                    Collections.singletonMap("email", email),
                    (rs, rowNum) -> rowMapperCustomer(rs));
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

//    public void testTransaction(Customer customer) {
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus status) {
//                jdbcTemplate.update("UPDATE customers SET name = :name where customer_id = UNHEX(REPLACE(:customerId, '-', ''))", toParamMap(customer));
//                jdbcTemplate.update("UPDATE customers SET email = :email where customer_id = UNHEX(REPLACE(:customerId, '-', ''))", toParamMap(customer));
//
//            }
//        });
//    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.EMPTY_MAP);
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Customer rowMapperCustomer(ResultSet resultSet) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
        return paramMap;
    }

}
