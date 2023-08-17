package edu.school21.processor;

import com.google.auto.service.AutoService;
import edu.school21.annotation.HtmlForm;
import edu.school21.annotation.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.FileWriter;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("edu.school21.annotation.HtmlForm")
public class HtmlProcessor extends AbstractProcessor {
    private final static String HTML_FORM_OPEN = "<form action=\"%s\" method=\"%s\">\n";
    private final static String HTML_FORM_CLOSE = "</form>\n";
    private final static String HTML_INPUT = "<input type=\"%s\" name=\"%s\" placeholder=\"%s\">\n";
    private final static String HTML_SUBMIT = "<input type=\"submit\" value=\"Submit\">\n";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set <? extends Element> elements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : elements) {
            createHtmlFile(element);
        }

        return false;
    }

    private void createHtmlFile(Element element) {
        HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
        HtmlInput htmlInput;

        try (FileWriter fileWriter = new FileWriter("target/classes/" + htmlForm.fileName())) {
            fileWriter.write(String.format(HTML_FORM_OPEN, htmlForm.action(), htmlForm.method()));
            for (Element field : element.getEnclosedElements()) {
                htmlInput = field.getAnnotation(HtmlInput.class);
                if (htmlInput != null) {
                    fileWriter.write(String.format(HTML_INPUT, htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
                }
            }
            fileWriter.write(HTML_SUBMIT);
            fileWriter.write(HTML_FORM_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
