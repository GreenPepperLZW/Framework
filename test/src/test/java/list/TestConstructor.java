package list;

import cn.hutool.core.lang.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lzw
 * @date : 2022/4/29
 * @since : 1.0
 */
public class TestConstructor {


    @Test
    public void test1() {
        Map init = this.init();
        System.out.println(init.toString());


        try (PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("out.txt", true))))
        {
            out2.println("the text");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map init() {
        List<String> list = new ArrayList<String>(){{
            add("1");
            add("2");
            add("3");
        }};

        Map<String, List<String>> map = new HashMap<String, List<String>>(){{
            if (list.size() != 0) {
                put("aa", list);
            }
        }};
        return map;
    }



}
