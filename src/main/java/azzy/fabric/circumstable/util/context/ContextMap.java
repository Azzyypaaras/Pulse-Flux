package azzy.fabric.circumstable.util.context;

import java.util.HashMap;
import java.util.function.Consumer;

public class ContextMap<T, K extends Enum<K>> {

    private final HashMap<Enum<K>, Consumer<T>> CONSUMERS;

    private ContextMap(){
        CONSUMERS = new HashMap<>();
    }

    public static ContextMap construct(ContextConsumer[] consumers){
        ContextMap map = new ContextMap();
        if(consumers == null)
            return map;

        for (ContextConsumer consumer : consumers) {
            map.CONSUMERS.put(consumer.getContext(), consumer.getConsumer());
        }
        return map;
    }

    //Returns true if execution was successful
    public boolean execute(T consumerPackage, K context){
        Consumer<T> consumer = CONSUMERS.get(context);
        if(consumer != null){
            consumer.accept(consumerPackage);
            return true;
        }
        return false;
    }

    public void executeWithFallback(T consumerPackage, K context, Consumer<T> fallback){
        if (!execute(consumerPackage, context)) {
            fallback.accept(consumerPackage);
        }
    }
}
