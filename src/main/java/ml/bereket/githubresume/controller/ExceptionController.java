package ml.bereket.githubresume.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleReportServiceError(HttpServletRequest request, HttpClientErrorException e) {

        Map<String, Object> model = new HashMap<>();
        String message = e.getMessage();
        HttpStatus httpStatus = e.getStatusCode();
        if(httpStatus == HttpStatus.FORBIDDEN) message = "Public API rate limit has reached. Please use your api key to proceed.";
        model.put("errorMessage", message);
        return new ModelAndView("error", model);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleReportServiceError(HttpServletRequest request, Exception e) {

        Map<String, Object> model = new HashMap<>();
        model.put("errorMessage", "Something went wrong. Please try again later.");
        return new ModelAndView("error", model);
    }
}