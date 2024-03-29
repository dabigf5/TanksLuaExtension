package tools.important.tankslua;


import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that tells you that the LuaValue being annotated can possibly be the lua constant nil
 */
@Target(ElementType.FIELD)
@SupportedAnnotationTypes({"LuaValue"})
@Retention(RetentionPolicy.SOURCE)
public @interface LuaNillable {
}
