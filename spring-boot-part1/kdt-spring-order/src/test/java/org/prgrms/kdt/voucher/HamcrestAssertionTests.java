package org.prgrms.kdt.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

public class HamcrestAssertionTests {

    @Test
    @DisplayName("여러 hamcrest matcher 테스트")
    public void hamcrestTest() throws Exception {
        //given
        assertEquals(2, 1+1);
        //when
        assertThat(1+1, is(2));
        assertThat(1+1, anyOf(is(1), is(2)));
        //then
        assertNotEquals(1, 1+1);
        assertThat(1+1, not(1));
    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    public void hamcrestListMatcherTest() throws Exception {
        //given
        List<Integer> prices = List.of(1, 2, 3);
        assertThat(prices, hasSize(3));
        assertThat(prices, everyItem(greaterThan(0)));
        assertThat(prices, containsInAnyOrder(3, 1, 2));
        assertThat(prices, hasItem(2));
        assertThat(prices, hasItem(greaterThanOrEqualTo(2)));
        //when

        //then
    }
}
