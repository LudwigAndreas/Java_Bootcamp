package edu.school21.form;

import edu.school21.annotation.HtmlForm;
import edu.school21.annotation.HtmlInput;

@HtmlForm(fileName = "test_form.html", action = "/test", method = "post")
public class TestForm {
    @HtmlInput(type = "text", name = "test", placeholder = "Enter test")
    String test;
}
