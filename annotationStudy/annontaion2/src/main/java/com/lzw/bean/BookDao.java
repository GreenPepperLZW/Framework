package com.lzw.bean;

import org.springframework.stereotype.Repository;

/**
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
@Repository
public class BookDao {

    private int label = 1;

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label=" + label +
                '}';
    }
}
