package rdf;

import cn.hutool.core.date.DateUtil;
import com.alipay.rdf.file.interfaces.FileFactory;
import com.alipay.rdf.file.interfaces.FileReader;
import com.alipay.rdf.file.interfaces.FileWriter;
import com.alipay.rdf.file.model.FileConfig;
import com.alipay.rdf.file.model.StorageConfig;
import com.alipay.rdf.file.model.Summary;
import com.alipay.rdf.file.model.SummaryPair;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
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
     * 根据模板读取文件
     * 文档：https://github.com/alipay/rdf-file/wiki
     */
    @Test
    public void read() {
        FileConfig config = new FileConfig("D:\\Code\\framework\\test\\src\\main\\resources\\file\\file.txt", "/rdf/template_1.json", new StorageConfig("nas"));
        config.setSummaryEnable(true); // 开启汇总字段汇总功能
        config.setColumnSplit("|");
        FileReader fileReader = FileFactory.createReader(config);
        try {
            Map<String, Object> head = fileReader.readHead(HashMap.class);
            // 总金额
            BigDecimal totalAmount = (BigDecimal) head.get("totalAmount");
            // 总笔数
            BigDecimal totalCount = (BigDecimal) head.get("totalAmount");
            System.out.println();
            // 获取总比数
            Map<String, Object> row1 = fileReader.readRow(HashMap.class);
            System.out.println(row1.size());

            Map<String, Object> tail = fileReader.readTail(HashMap.class);
            Map<String, Object> row = null;
            while (null != (row = fileReader.readRow(HashMap.class))) {
                System.out.println(row.get("acctNo"));
            }
            Summary summary = fileReader.getSummary();
            for (SummaryPair pair : summary.getHeadSummaryPairs()) {
                BigDecimal summaryValue = (BigDecimal) pair.getSummaryValue(); //数据字段汇总后的值
                BigDecimal headValue = (BigDecimal) pair.getHeadValue(); //文件头中的汇总值
                // 汇总的值是否一致，一致时返回true
                boolean summaryEquals = pair.isSummaryEquals();
                System.out.println("summaryValue" + summaryValue);
                System.out.println("headValue" + headValue);
                System.out.println("汇总的值是否一致" + summaryEquals);
            }

        } finally {
            fileReader.close();
        }
    }

    /**
     * 写文件之汇总写
     * 文档：https://github.com/alipay/rdf-file/wiki/%E5%86%99%E6%96%87%E4%BB%B6%E4%B9%8B%E6%B1%87%E6%80%BB%E5%86%99#%E4%B8%80%E6%95%B0%E6%8D%AE%E5%AE%9A%E4%B9%89%E6%A8%A1%E6%9D%BF
     */
    @Test
    public void write() {
        String filePath = "D:\\Code\\framework\\test\\src\\main\\resources\\file";
        FileConfig config = new FileConfig(new File(filePath, "test.txt").getAbsolutePath(), "/rdf/templaate_write.json", new StorageConfig("nas"));
        config.setSummaryEnable(true); //启动汇总功能
        config.setColumnSplit("|");
        FileWriter fileWriter = FileFactory.createWriter(config);
        try {
            // 头使用数据定义模板的常量
            Map<String, Object> head = new HashMap<String, Object>();
            fileWriter.writeHead(head);
            // 写入两条数据
            Map<String, Object> body = new HashMap<String, Object>();
            Date testDate = DateUtil.parse("2017-01-03 12:22:33", "yyyy-MM-dd HH:mm:ss");

            body.put("seq", "seq12345");
            body.put("instSeq", "303");
            body.put("gmtApply", testDate);
            body.put("date", testDate);
            body.put("dateTime", testDate);
            body.put("applyNumber", 12);
            body.put("amount", new BigDecimal("1.22"));
            body.put("age", new Integer(33));
            body.put("longN", new Long(33));
            body.put("bol", true);
            body.put("memo", "memo1");
//            body.put("spec", "|");
            fileWriter.writeRow(body);
            testDate = DateUtil.parse("2016-02-03 12:22:33", "yyyy-MM-dd HH:mm:ss");
            body.put("seq", "seq234567");
            body.put("instSeq", "505");
            body.put("gmtApply", testDate);
            body.put("date", testDate);
            body.put("dateTime", testDate);
            body.put("applyNumber", 12);
            body.put("amount", new BigDecimal("1.09"));
            body.put("age", 66);
            body.put("longN", 125);
            body.put("bol", false);
            body.put("memo", "memo2");
//            body.put("spec", "|");
            fileWriter.writeRow(body);

            // 根据汇总信息写入尾部
            Map<String, Object> stringObjectMap = fileWriter.getSummary().summaryTailToMap();
            System.out.println(stringObjectMap.toString());
            fileWriter.writeTail(fileWriter.getSummary().summaryTailToMap());
            fileWriter.writeHead(fileWriter.getSummary().summaryTailToMap());
        } finally {
            fileWriter.close();
        }
    }
}
