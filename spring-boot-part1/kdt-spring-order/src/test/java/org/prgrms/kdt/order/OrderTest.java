package org.prgrms.kdt.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.FixedAmountVoucher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    public void 바우처를_통해_주문생성() throws Exception {
        UUID customerId = UUID.randomUUID();
        OrderContext orderContext = new OrderContext();
        OrderService orderService = orderContext.orderService();
        List<OrderItem> orderItems = new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        Order order1 = orderService.createOrder(customerId, orderItems);
        assertThat(order1.totalAmount()).isEqualTo(100L);

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L); //고정 할인
        Order order2 = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);
        assertThat(order2.totalAmount()).isEqualTo(90L);
    }

}