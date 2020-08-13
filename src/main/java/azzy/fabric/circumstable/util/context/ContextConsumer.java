package azzy.fabric.circumstable.util.context;

import java.util.function.Consumer;

public class ContextConsumer<T, K extends Enum> {

    private Consumer<T> consumer;
    private K context;

    private ContextConsumer(Consumer<T> consumer, K context){
        this.consumer = consumer;
        this.context = context;
    }

    public static  <T, K extends Enum> ContextConsumer of(Consumer<T> consumer, K context){
        return new ContextConsumer<>(consumer, context);
    }

    public void accept(T t){
        consumer.accept(t);
    }

    public K getContext(){
        return context;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }
}
