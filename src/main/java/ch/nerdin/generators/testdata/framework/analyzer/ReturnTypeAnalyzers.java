/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.nerdin.generators.testdata.framework.analyzer;

import java.lang.reflect.Method;

/**
 *
 * @author edewit
 */
public interface ReturnTypeAnalyzers {

    Class<?>[] findClass(Method method);
}
