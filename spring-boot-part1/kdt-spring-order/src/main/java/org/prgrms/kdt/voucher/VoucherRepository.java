package org.prgrms.kdt.voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository { //DB를 통해 어떻게 보관하느냐에 따라 바뀔수 있음 (mysql, mongoDB..)
    // 그래서 레포에 대한 구현체를 별도로 가져감
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
}
