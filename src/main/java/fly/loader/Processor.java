package fly.loader;

import java.util.Queue;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public abstract class Processor<T> {
    Queue<Object> processQueue;
    private final Class<? super T> type;

    public Processor(Class<? super T> type) {
        this.type = type;
    }

    public abstract void process(T entity);

    protected void addEntity(Object entity) {
        processQueue.add(entity);
    }

    public Class<? super T> getType() {
        return type;
    }

}
