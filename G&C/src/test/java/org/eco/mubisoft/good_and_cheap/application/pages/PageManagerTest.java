package org.eco.mubisoft.good_and_cheap.application.pages;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized.Parameters;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PageManagerTest {

    private static final int TOTAL_PAGE_COUNT = 10;

    @Parameters
    public static Stream<Arguments> values() {
        return Stream.of(
                Arguments.of(1, "next", 2),
                Arguments.of(4, "previous", 3),
                Arguments.of(10, "next", 10),
                Arguments.of(1, "previous", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("values")
    public void getPageNum(int pageNum, String direction, int result) {
        assertEquals(
                result,
                PageManager.getPageNum(
                        pageNum,
                        TOTAL_PAGE_COUNT,
                        direction
        ));
    }
}