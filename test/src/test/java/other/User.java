package other;

import org.springframework.data.annotation.Transient;

/**
 * @author : lzw
 * @date : 2022/9/15
 * @since : 1.0
 */
public class User {

    private Org org;

    @Transient
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.org.setId(id);
    }


}
