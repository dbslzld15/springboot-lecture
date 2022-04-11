package org.prgrms.kdt.order;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    public void 바우처를_통해_주문생성() throws Exception {
        Logger logger = LoggerFactory.getLogger(OrderTest.class);


        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfiguration.class);
        UUID customerId = UUID.randomUUID();

        VoucherRepository voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(ac.getBeanFactory(), VoucherRepository.class, "memoryVoucher");
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L); //고정 할인

        Voucher voucher = voucherRepository.insert(fixedAmountVoucher);

        OrderService orderService = ac.getBean(OrderService.class);
        List<OrderItem> orderItems = new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        Order order1 = orderService.createOrder(customerId, orderItems);
        assertThat(order1.totalAmount()).isEqualTo(100L);

        Order order2 = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());
        assertThat(order2.totalAmount()).isEqualTo(90L);
    }

    @Test
    public void 박우진() throws Exception {
        //given
        List<String> answer = new ArrayList<>();

        String[] dir = {"E", "S", "W", "N"};
        System.out.println(Arrays.asList(dir).indexOf("E"));

        //when

        //then
    }

}