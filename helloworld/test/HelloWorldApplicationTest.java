import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * HelloWorldApplication 单元测试。
 *
 * <p>覆盖正常路径和输出校验。
 */
class HelloWorldApplicationTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("getGreeting 应返回 Hello, World!")
    void shouldReturnGreeting_whenCalled() {
        String greeting = HelloWorldApplication.getGreeting();
        assertEquals("Hello, World!", greeting);
    }

    @Test
    @DisplayName("main 应输出 Hello, World!")
    void shouldPrintGreeting_whenMainInvoked() {
        HelloWorldApplication.main(new String[]{});
        String output = outputStream.toString().trim();
        assertEquals("Hello, World!", output);
    }

    @Test
    @DisplayName("printGreeting 应输出 Hello, World!")
    void shouldPrintGreeting_whenPrintGreetingInvoked() {
        HelloWorldApplication.printGreeting();
        String output = outputStream.toString().trim();
        assertEquals("Hello, World!", output);
    }
}