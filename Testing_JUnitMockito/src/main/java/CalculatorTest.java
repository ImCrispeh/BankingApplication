import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    @Mock
    private Calculator calc;

    @Test
    void shouldAdd() {
        //MockitoAnnotations.initMocks(this);
        //when(calc.add(1, 1)).thenReturn(2);
        //assertEquals(2, calc.add(1, 1));
        calc.add(1, 1);

        verify(calc).add(1, 1);
    }

    @Test
    void shouldSub() {
        //Calculator calc = mock(Calculator.class);

        calc.sub(2, 1);

        verify(calc).sub(anyInt(), anyInt());
    }
}