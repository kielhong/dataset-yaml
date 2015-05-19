package fly.loader;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.extensions.compactnotation.CompactConstructor;
import org.yaml.snakeyaml.extensions.compactnotation.CompactData;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.*;


class ConstructImport extends AbstractConstruct {
    private final FlyImpl fly;
    private final List<String> importedPackages = new ArrayList<>();

    public ConstructImport(FlyImpl fly) {
        this.fly = fly;
    }

    @Override
    public Object construct(Node node) {
        String location = ((ScalarNode) node).getValue();
        if (!importedPackages.contains(location)) {
            importedPackages.add(location);
            fly.loadEntities(location);
        }
        return null;
    }
}

class ConstructPackage extends AbstractConstruct {
    private final FlyImpl fly;

    public ConstructPackage(FlyImpl fly) {
        this.fly = fly;
    }

    @Override
    public Object construct(Node node) {
        String packageName = ((ScalarNode) node).getValue();
        fly.setPackage(packageName);
        return "";
    }
}

/**
 * FlyImpl allows you to create Java classes from YAML markup.
 *
 */
public final class FlyImpl extends CompactConstructor implements Fly {
    Logger logger = LoggerFactory.getLogger(FlyImpl.class);

    private final Map<String, Object> entityCache = new LinkedHashMap<>();
//    private final Multimap<Class<?>, Processor<? super Object>> postProcessors = HashMultimap.create();
    private final Persister persister;
    private final String defaultPackage;
    private final BeanAccess beanAccess;
    private final String file;
    private String packageName;

    public FlyImpl(Persister persister) {
        this(persister, "");
    }

    public FlyImpl(Persister persister, String defaultPackage) {
        this(persister, defaultPackage, null, BeanAccess.DEFAULT);
    }

    public FlyImpl(Persister persister, String defaultPackage, String file, BeanAccess beanAccess) {
        this.yamlConstructors.put(new Tag("!import"), new ConstructImport(this));
        this.yamlConstructors.put(new Tag("!package"), new ConstructPackage(this));
        this.defaultPackage = defaultPackage;
        this.packageName = defaultPackage;
        this.file = file;
        this.persister = persister;
        this.beanAccess = beanAccess;
    }

    @Override
    protected Class<?> getClassForName(String name) throws ClassNotFoundException {
        if(!Strings.isNullOrEmpty(packageName)) {
            try {
                return super.getClassForName(packageName + "." + name);
            } catch (ClassNotFoundException ignored) { }
        }
        ClassNotFoundException exceptionToThrow;
        try {
            return super.getClassForName(name);
        } catch (ClassNotFoundException e) {
            exceptionToThrow = e;
        }
        try {
            return super.getClassForName("java.lang." + name);
        } catch (ClassNotFoundException ignored) { }
        throw exceptionToThrow;
    }

    @Override
    protected Object createInstance(ScalarNode node, CompactData data) throws Exception {
        if (!entityCache.containsKey(node.getValue())) {
            data.getArguments().clear();
            Object entity = super.createInstance(node, data);
            entityCache.put(node.getValue(), entity);
        }
        return entityCache.get(node.getValue());
    }

    void loadEntities(String... files) {
        Yaml yaml = new Yaml(this);
        yaml.setBeanAccess(beanAccess);
        for(String file : files) {
            if(!file.startsWith("/")) {
                file = "/" + file;
            }
            String origPackage = this.packageName;
            this.packageName = this.defaultPackage;
            System.out.println("FlyImpl.load() - file:" + file);
            System.out.println("FlyImpl.load() - package:" + this.packageName);
            yaml.load(getClass().getResourceAsStream(file));
            this.packageName = origPackage;
        }
    }

    void persistEntities() {
        Queue<Object> processQueue = new LinkedList<Object>(entityCache.values());
        while (!processQueue.isEmpty()) {
            Object entity = processQueue.remove();
            System.out.println("entity:" + entity);
//            for (Map.Entry<Class<?>, Processor<? super Object>> entry : postProcessors.entries()) {
//                if (entity.getClass().isAssignableFrom(entry.getKey())) {
//                    Processor<? super Object> postProcessor = entry.getValue();
//                    postProcessor.processQueue = processQueue;
//                    postProcessor.process(entity);
//                }
//            }
            persister.persisit(entity);
        }
    }

    public void load() {
        load(this.file);
    }

    public void load(String... files) {
        loadEntities(files);
        persistEntities();
    }

//    public <T> void addProcessor(Processor<T> postProcessor) {
//        @SuppressWarnings("unchecked")
//        Processor<? super Object> casted = (Processor<? super Object>) postProcessor;
//        postProcessors.put(postProcessor.getType(), casted);
//    }

    void setPackage(String packageName) {
        this.packageName = packageName;
    }
}