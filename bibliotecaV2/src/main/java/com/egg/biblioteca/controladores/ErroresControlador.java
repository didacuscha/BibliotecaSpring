
package com.egg.biblioteca.controladores;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class ErroresControlador implements ErrorController {
    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST})
    public String renderErrorPage(Model model, HttpServletResponse httpResponse) throws IOException {
        String errorMsg = "";
        int httpErrorCode = httpResponse.getStatus();

        switch (httpErrorCode) {
            case 400:
                errorMsg = "El recurso solicitado no existe.";
                break;
            case 403:
                errorMsg = "No tiene permisos para acceder al recurso.";
                break;
            case 401:
                errorMsg = "No se encuentra autorizado.";
                break;
            case 404:
                errorMsg = "El recurso solicitado no fue encontrado.";
                break;
            case 500:
                errorMsg = "Ocurrió un error interno. El servidor no pudo realizar la petición con éxito";
                break;
            default:
        }
        model.addAttribute("codigo", httpErrorCode);
        model.addAttribute("mensaje", errorMsg);
        return "error";
    }






/*
@Controller
public class ErroresControlador implements ErrorController {


    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");

        String errorMsg = "";

        Integer httpErrorCode = getErrorCode(httpRequest);
        System.out.println(httpRequest);
        System.out.println(httpErrorCode);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "El recurso solicitado no existe.";
                break;
            }
            case 403: {
                errorMsg = "No tiene permisos para acceder al recurso.";
                break;
            }
            case 401: {
                errorMsg = "No se encuentra autorizado.";
                break;
            }
            case 404: {
                errorMsg = "El recurso solicitado no fue encontrado.";
                break;
            }
            case 500: {
                errorMsg = "Ocurrió un error interno.";
                break;
            }
        }

        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }


    private int getErrorCode(HttpServletRequest httpRequest) {
        String statusCodeString = (String) httpRequest.getAttribute("javax.servlet.error.status_code");

        //if (statusCodeString != null) {
            try {
                return Integer.parseInt(statusCodeString);
            } catch (NumberFormatException e) {
                // Handle the exception or log a message if conversion fails
                e.printStackTrace();
            }
        //}

        return 500; // Default to 500 if status code is null or cannot be parsed
    }

    public String getErrorPath() {
        return "/error.html";
    }


     */

}
