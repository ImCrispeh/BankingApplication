package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class CalculatorTest {

    @Test
    void shouldAdd() {
        Calculator calc = mock(Calculator.class);

        when(calc.add(1, 1)).thenReturn(2);

        assertEquals(2, calc.add(1, 1));

        verify(calc).add(1, 1);
        verifyNoMoreInteractions(calc);
    }

    @Test
    void shouldSub() {
        Calculator calc = mock(Calculator.class);

        assertEquals(2, calc.sub(4, 2));
        assertEquals(5, calc.sub(10, 5));
    }
}