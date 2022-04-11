package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll
    static void setup() {
        logger.info("@BeforeALl - 단 한번 실행");
    }

    @BeforeEach
    void init() {
        logger.info("@BeforeEach - 매 테스트마다 실행");
    }


    @Test
    public void nameAssertEqual() throws Exception {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    public void testDiscount() throws Exception {
        //given
        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 100);
        //when

        //then
        assertEquals(900, sut.discount(1000));
    }

    @Test
    @DisplayName("디스카운트된 금액은 마이너스가 될 수 없다.")
    public void testMinusDiscountAmount() throws Exception {
        //given
        FixedAmountVoucher sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        //when

        //then
        assertEquals(0, sut.discount(900));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    public void testWithMinus() throws Exception {
        //given

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    public void testVoucherCreation() throws Exception {
        //given
        assertAll("FixedAmountVoucher Creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 100000))
        );
        //when

        //then
    }

}