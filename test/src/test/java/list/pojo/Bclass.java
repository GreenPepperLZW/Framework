package list.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lzw
 * @date : 2022/4/19
 * @since : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bclass {

    /**
     * A、B类id一致
     */
    private int id;

    /**
     * 根据B类的number对A类进行排序
     */
    private int number;
}
