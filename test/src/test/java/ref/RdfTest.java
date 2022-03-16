package ref;

import com.alipay.rdf.file.interfaces.FileFactory;
import com.alipay.rdf.file.interfaces.FileReader;
import com.alipay.rdf.file.model.FileConfig;
import com.alipay.rdf.file.model.StorageConfig;
import com.alipay.rdf.file.model.Summary;
import com.alipay.rdf.file.model.SummaryPair;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : lzw
 * @date : 2022/3/16
 * @since : 1.0
 */
public class RdfTest {


    /**
     * 使用rdf-file-corejar包处理模板化文件，读取文件过程累加配置汇总字段， 一般用于校验文件总笔数，总金额是否正确
     */
    @Test
    public void test1() {
        FileConfig config = new FileConfig("D:\\Code\\framework\\test\\src\\main\\resources\\file\\file.txt", "/rdf/template_1.json", new StorageConfig("nas"));
        config.setSummaryEnable(true); // 开启汇总字段汇总功能
        FileReader fileReader = FileFactory.createReader(config);
        try {
            Map<String, Object> head = fileReader.readHead(HashMap.class);
            BigDecimal totalAmount = (BigDecimal)head.get("totalAmount");
            System.out.println();
            Map<String, Object> row1= fileReader.readRow(HashMap.class);
            Map<String, Object> tail = fileReader.readTail(HashMap.class);
            Map<String, Object> row = null;
            while (null != (row = fileReader.readRow(HashMap.class))) {
                // 处理业务
            }
            Summary summary = fileReader.getSummary();
            for (SummaryPair pair : summary.getHeadSummaryPairs()) {
                BigDecimal  summaryValue = (BigDecimal) pair.getSummaryValue(); //数据字段汇总后的值
                BigDecimal headValue = (BigDecimal) pair.getHeadValue(); //文件头中的汇总值
                boolean summaryEquals = pair.isSummaryEquals();// 汇总的值是否一致
                System.out.println("summaryValue"+summaryValue);
                System.out.println("headValue"+headValue);
                System.out.println("汇总的值是否一致"+summaryEquals);
            }

        } finally {
            fileReader.close();
        }
    }
}
