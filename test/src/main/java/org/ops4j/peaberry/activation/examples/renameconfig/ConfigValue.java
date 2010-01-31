package org.ops4j.peaberry.activation.examples.renameconfig;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

@Retention(RUNTIME)
@BindingAnnotation
@Target({PARAMETER, FIELD})
public @interface ConfigValue {
}
