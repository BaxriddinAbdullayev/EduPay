package uz.alifservice.config.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.exps.AppBadException;
import uz.alifservice.service.message.ResourceBundleService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private final ResourceBundleService bundleService;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String headerLang = request.getHeader("Accept-Language");
        AppLanguage lang = headerLang != null ? AppLanguage.valueOf(headerLang.toUpperCase()) : AppLanguage.UZ;

        List<String> errorFields = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorFields.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return new ResponseEntity<>(
                AppResponse.error(bundleService.getMessage("fill.input", lang) + ": " + errorFields),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse<Object>> handleEntityNotFoundException(
            EntityNotFoundException ex,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return new ResponseEntity<>(
                AppResponse.error(bundleService.getMessage("entity.not.found", lang) + " " + ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<AppResponse<Object>> handleAppBadException(AppBadException ex) {
        return new ResponseEntity<>(AppResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppResponse<Object>> handleGeneralException(
            RuntimeException ex,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                AppResponse.error(bundleService.getMessage("unexpected.error", lang)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
