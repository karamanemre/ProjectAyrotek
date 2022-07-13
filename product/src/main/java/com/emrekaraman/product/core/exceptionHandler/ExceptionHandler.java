package com.emrekaraman.product.core.exceptionHandler;

import com.emrekaraman.product.core.constants.Messages;
import com.emrekaraman.product.core.utilities.ErrorDataResult;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Configuration
public class ExceptionHandler implements ErrorController {


    private final ErrorAttributes errorAttributes;

    @Lazy
    public ExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ErrorDataResult<Object> handleError(WebRequest webRequest){

        HashMap<String,String> error = new HashMap<>();
        Map<String, Object> attributes = this.errorAttributes
                .getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE,ErrorAttributeOptions.Include.BINDING_ERRORS));

        error.put("message", (String) attributes.get("message"));
        error.put("path",(String) attributes.get("path"));
        int status = (int) attributes.get("status");
        error.put("status",Integer.toString(status));

        if (attributes.containsKey("errors")){
            List<FieldError> fieldErrors = (List<FieldError>) attributes.get("errors");
            for (FieldError fieldError : fieldErrors) {
                error.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }

        return new ErrorDataResult(error, Messages.VALIDATION_EROR_MESSAGE);
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions includeStackTrace) {

                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

                Throwable error = getError(webRequest);

                if (error instanceof ApplicationException) {
                    ApplicationException myException = (ApplicationException) error;
                    errorAttributes.put("errorCode", myException.getId());
                    errorAttributes.put("message", myException.getMessage());
                    errorAttributes.put("error", myException.getLocalizedMessage());
                }
                return errorAttributes;
            }
        };
    }

}
