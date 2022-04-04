package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;

import java.util.Optional;
import java.util.UUID;

public class OrderContext { // 주문에 대한 전반적인 도메인 객체에 대한 생성을 책임짐, IOC라고 부름

    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
}
