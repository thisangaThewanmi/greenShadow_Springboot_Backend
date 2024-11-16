package lk.ijse.greenshadow_springboot.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to enforce a specific content type for request part parameters.
 */
@Target(ElementType.PARAMETER)  // This annotation will be used on method parameters.
@Retention(RetentionPolicy.RUNTIME)  // It will be available at runtime for reflection.
public @interface RequestPartContentType {
    String value();  // The content type value (e.g., application/json).
}
