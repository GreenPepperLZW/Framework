package list;

import list.pojo.Aclass;
import list.pojo.AclassVo;
import list.pojo.Bclass;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 两个对象集合，A，B
 *
 * 根据B集合中的number字段对A集合进行排序
 *
 * A，B中的对象的id字段相等
 *
 * @author : lzw
 * @date : 2022/4/19
 * @since : 1.0
 */
public class TestSort {


    @Test
    public void test01() {

        ArrayList<Aclass> aclasses = new ArrayList<Aclass>(){
            {
                add(new Aclass(1,"张三"));
                add(new Aclass(2,"张2"));
                add(new Aclass(3,"张3"));
                add(new Aclass(4,"张4"));
                add(new Aclass(5,"张5"));
                add(new Aclass(6,"张6"));
            }
        };

        ArrayList<Bclass> bclasses = new ArrayList<Bclass>() {
            {
                add(new Bclass(1,5));
                add(new Bclass(2,3));
                add(new Bclass(3,14));
                add(new Bclass(4,54));
                add(new Bclass(5,2));
                add(new Bclass(6,9));
            }
        };

        List<AclassVo> collect = aclasses.stream().map(aclass -> {
            AclassVo vo = new AclassVo();
            // 根据条件筛选出对象集合中的目标对象
            Bclass cart = bclasses.stream()
                    .filter(item -> item.getId() == aclass.getId()).findFirst().get();
            vo.setNumber(cart.getNumber());
            BeanUtils.copyProperties(aclass, vo);
            return vo;
        }).collect(Collectors.toList())
                // 根据对象集合中对象的某一个属性进行排序
                .stream().sorted(Comparator.comparing(AclassVo::getNumber)).collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

}
