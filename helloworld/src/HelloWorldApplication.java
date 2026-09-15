/**
 * HelloWorld 应用程序入口类。
 *
 * <p>提供标准的控制台 HelloWorld 输出，遵循数科 Java 编码规范。
 *
 * @author AiWork
 * @version 1.0.0
 */
public class HelloWorldApplication {

    /** 问候语常量 */
    private static final String GREETING = "Hello, World!";

    /**
     * 程序主入口。
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        printGreeting();
    }

    /**
     * 向控制台输出问候语。
     */
    public static void printGreeting() {
        System.out.println(GREETING);
    }

    /**
     * 获取问候语内容。
     *
     * @return 问候语字符串
     */
    public static String getGreeting() {
        return GREETING;
    }
}