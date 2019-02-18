package jdbc.web;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModel {
    private final String view;
    private final Map<String, Object> model = new HashMap<>();
    private  final List<Cookie> cookies = new ArrayList<>();


    public ViewModel(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public static ViewModel of(String view) {
        return new ViewModel(view);
    }

    public void addCookie(Cookie cookie){
        cookies.add(cookie);
    }

    public void addAttribute(String name, Object object) {
        model.put(name,object);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}
