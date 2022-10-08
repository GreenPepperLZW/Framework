package com.lzw.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义逻辑返回需要导入的组件
 *
 * @author : lzw
 * @date : 2022/5/11
 * @since : 1.0
 */
public class MyImportSelector implements ImportSelector {

    /**
     * @param importingClassMetadata 当前标注了@Import注解的类{@link com.lzw.config.MainConfig2}的所有注解信息
     * @return 返回值就是要导入到容器中的组件全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 不能返回null值，否则会报错空指针异常，可以返回空数组
        return new String[]{"com.lzw.bean.Blue", "com.lzw.bean.Yellow"};
    }
}
