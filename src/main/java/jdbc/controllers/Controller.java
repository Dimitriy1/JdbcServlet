package jdbc.controllers;

import jdbc.web.Request;
import jdbc.web.ViewModel;

public interface Controller {
    ViewModel process(Request request);
}
