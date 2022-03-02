package lzw.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * 使用注解的方式监听chunk方式执行step前后的监听
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
public class MyChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        System.out.println("step的名称"+chunkContext.getStepContext().getStepName()+"---before");
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        System.out.println("step的名称"+chunkContext.getStepContext().getStepName()+"---after");
    }

}
