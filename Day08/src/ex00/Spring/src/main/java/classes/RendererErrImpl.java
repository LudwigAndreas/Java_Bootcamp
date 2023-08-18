package classes;

import interfaces.PreProcessor;
import interfaces.Renderer;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor){
        this.preProcessor = preProcessor;
    }

    @Override
    public void render(String s) {
        System.err.println(
            preProcessor.process(s)
        );
    }
}
