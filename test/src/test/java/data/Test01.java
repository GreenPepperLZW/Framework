package data;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : lzw
 * @date : 2022/5/7
 * @since : 1.0
 */
public class Test01 {

    @Test
    public void test01() {
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMDD");
        SimpleDateFormat format2 = new SimpleDateFormat("hhmmss");
        System.out.println(format.format(new Date()));
        System.out.println(format2.format(new Date()));
    }
}
