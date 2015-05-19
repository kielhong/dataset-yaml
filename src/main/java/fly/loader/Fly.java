package fly.loader;

/**
 * Created by 1001982(kielhong@sk.com)
 * Date : 15. 5. 18.
 */
public interface Fly {
    public void load();

    public void load(String... files);

    //public <T> void addProcessor(Processor<T> postProcessor);
}
