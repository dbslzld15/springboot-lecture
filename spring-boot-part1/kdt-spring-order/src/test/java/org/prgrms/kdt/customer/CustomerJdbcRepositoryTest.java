package org.prgrms.kdt.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.customer"}
    )
    static class config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("park")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
//            dataSource.setMaximumPoolSize(100);
//            dataSource.setMinimumIdle(100);
            return dataSource;
        }
    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setUp() {
        customerJdbcRepository.deleteAll();
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now());

    }

    @Test
    @Order(1)
    public void HikariDataSource() throws Exception {
        //given
        //when
        //then
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("????????? ????????? ??? ??????.")
    public void testInsert() throws Exception {
        customerJdbcRepository.insert(newCustomer);
        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(newCustomer.getCustomerId(), is(retrievedCustomer.get().getCustomerId()));
    }

    @Test
    @Order(3)
    @DisplayName("?????? ????????? ????????? ??? ??????.")
    public void testFindAll() throws Exception {
        //given
        List<Customer> customers = customerJdbcRepository.findAll();
        //when
        System.out.println(customers);
        //then
        Thread.sleep(5000);
    }

    @Test
    @Order(4)
    @DisplayName("???????????? ????????? ????????? ??? ??????.")
    public void testFindByName() throws Exception {
        //given
        Optional<Customer> customer = customerJdbcRepository.findByName(newCustomer.getName());
        Optional<Customer> unknown = customerJdbcRepository.findByName("unknown");
        //when
        //then
        assertThat(customer.isEmpty(), is(false));
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("???????????? ????????? ????????? ??? ??????.")
    public void testFindByEmail() throws Exception {
        //given
        Optional<Customer> email = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        Optional<Customer> unknown = customerJdbcRepository.findByEmail("unknown");
        //when
        //then
        assertThat(email.isEmpty(), is(false));
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("????????? ????????? ??? ??????.")
    public void testUpdateCustomer() throws Exception {
        newCustomer.changeName("updated-user");
        customerJdbcRepository.update(newCustomer);

        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers, hasSize(1));

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
    }

}