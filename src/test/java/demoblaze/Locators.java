package demoblaze;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$x;

public class Locators {
    public void clickLink(String name) {
        String script = "arguments[0].click();";
        Selenide.executeJavaScript(script, $x("//*[text()=\"" + name + "\"]"));
    }
}
