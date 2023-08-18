package classes;

import interfaces.Printer;
import interfaces.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        renderer.render(
            String.format("%s %s", LocalDateTime.now(), message)
        );
    }
}
