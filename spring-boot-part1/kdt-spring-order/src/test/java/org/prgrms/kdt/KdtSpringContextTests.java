package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

//@ExtendWith(SpringExtension.class) //실제 스프링 컨텍스트 프레임워크를 사용할 수 있게 해줌
//@ContextConfiguration
@SpringJUnitConfig
@ActiveProfiles("test")
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt"}
    )
    static class Config {

    }
    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;
    @Autowired
    VoucherRepository voucherRepository;
    
    @Test
    @DisplayName("applciationContext가 생성되야 한다.")
    public void testApplicationContext() throws Exception {
        //given
        assertThat(context, notNullValue());
        //when
        //then
    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation() throws Exception {
        //given
        //when
        VoucherRepository voucherRepository = context.getBean(VoucherRepository.class);
        //then
        assertThat(voucherRepository, notNullValue());
    }

    @Test
    @DisplayName("OrderService를 사용해서 주문을 생성할 수 있다.")
    public void testOrderService() throws Exception {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);
        //when
        Order order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());
        //then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}
