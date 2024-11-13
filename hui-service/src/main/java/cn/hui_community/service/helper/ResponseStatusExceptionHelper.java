package cn.hui_community.service.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseStatusExceptionHelper {
  public static Supplier<ResponseStatusException> notFoundSupplier(String message) {
    return () -> notFound(message);
  }

  public static Supplier<ResponseStatusException> notFoundSupplier(String message, Object... args) {
    return () -> notFound(message, args);
  }

  public static ResponseStatusException notFound(String message) {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
  }

  public static ResponseStatusException notFound(String messageFormat, Object... args) {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, messageFormat.formatted(args));
  }

  public static Supplier<ResponseStatusException> badRequestSupplier(
      String message, Object... args) {
    return () -> badRequest(message, args);
  }

  public static Supplier<ResponseStatusException> badRequestSupplier(String message) {
    return () -> badRequest(message);
  }

  public static ResponseStatusException badRequest(String message) {
    return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
  }

  public static ResponseStatusException badRequest(String messageFormat, Object... args) {
    return new ResponseStatusException(HttpStatus.BAD_REQUEST, messageFormat.formatted(args));
  }

  public static ResponseStatusException internalServerError(String message) {
    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

  public static ResponseStatusException internalServerError(String messageFormat, Object... args) {
    return new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR, messageFormat.formatted(args));
  }
}
