package other;

import org.junit.Test;

import java.util.Random;

/**
 * @author : lzw
 * @date : 2022/6/16
 * @since : 1.0
 */
public class RandomTest {


    /**
     * 生成 5200到5100之间的随机数
     */
    @Test
    public void test01() {
        int max = 6000;
        int min = 4000;

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int res = random.nextInt(max % (max - min + 1)) + min;
            double v = (Math.random());
            double v1 = res + v;
            String format = String.format("%.1f", v1);
            System.out.println(Double.parseDouble(format));
        }

    }

}
