package lzw.joblauncher;


import javax.batch.api.chunk.listener.ItemProcessListener;

/**
 * @author : lzw
 * @date : 2022/6/2
 * @since : 1.0
 */
public class MyItemProcessorLister implements ItemProcessListener {

    @Override
    public void beforeProcess(Object o) throws Exception {
        System.out.println("beforeProcess");
    }

    @Override
    public void afterProcess(Object o, Object o1) throws Exception {
        System.out.println("afterProcess");
    }

    @Override
    public void onProcessError(Object o, Exception e) throws Exception {

    }
}
