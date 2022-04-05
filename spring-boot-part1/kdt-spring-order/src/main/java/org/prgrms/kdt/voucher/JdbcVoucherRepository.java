package org.prgrms.kdt.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("memory") // 조회 대상 빈이 2개일 경우 @Qualifier, @Primary 사용
public class JdbcVoucherRepository implements VoucherRepository{
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }
}
