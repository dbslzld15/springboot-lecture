package org.prgrms.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount < 0){
            throw new IllegalArgumentException("Should me positive value");
        }
        if(amount == 0) {
            throw new IllegalArgumentException("Amount should not be zero");
        }
        if(amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        long discountedPrice = beforeDiscount - amount;
        return discountedPrice < 0 ? 0 : discountedPrice;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
