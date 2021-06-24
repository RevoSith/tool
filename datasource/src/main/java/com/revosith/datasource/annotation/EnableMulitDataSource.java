package com.revosith.datasource.annotation;

import com.revosith.datasource.base.InitDataSource;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Revosith
 * @description
 * @date 2020/12/2.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({InitDataSource.class})
@Documented
public @interface EnableMulitDataSource {
}
