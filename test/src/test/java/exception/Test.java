package exception;

/**
 * 测试for循环中异常捕获后是否会跳出循环
 *
 * @author : lzw
 * @date : 2022/7/11
 * @since : 1.0
 */
public class Test {

    @org.junit.Test
    public void testCyclicException() {
        for (int i = 0; i < 10; i++) {
            System.out.println("aaaaa" + 1);
            if (i == 2) {
                try {
                    testZeroException();
                } catch (Exception e) {
                    System.out.println("cccc" + 1 + e.getMessage());
                }
            }
            System.out.println("bbbb" + 1);
        }
    }


    public void testZeroException() throws Exception {
        try {
            int j = 1 / 0;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
