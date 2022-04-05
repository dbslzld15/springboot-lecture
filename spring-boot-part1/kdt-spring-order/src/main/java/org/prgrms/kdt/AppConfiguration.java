package org.prgrms.kdt;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderRepository;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan
public class AppConfiguration { // 주문에 대한 전반적인 도메인 객체에 대한 생성을 책임짐, IOC라고 부름

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }
}
