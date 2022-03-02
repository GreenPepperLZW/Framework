package lzw.itemreader;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * 具体的ItemReader实现
 *
 * @author : lzw
 * @date : 2022/2/28
 * @since : 1.0
 */
public class MyReader implements ItemReader<String> {

    private Iterator<String> iterator;

    /**
     * 读取数据
     * @return
     * @throws Exception
     * @throws UnexpectedInputException
     * @throws ParseException
     * @throws NonTransientResourceException
     */
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 一个数据一个数据的读取
        if (iterator.hasNext()) {
            return this.iterator.next();
        }
        return null;
    }

    public MyReader(List<String> data) {
        this.iterator = data.iterator();
    }
}
