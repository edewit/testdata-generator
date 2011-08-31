/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.erikjan.generators.testdata.framework.analyzer;

import java.lang.reflect.Method;

/**
 *
 * @author edewit
 */
public interface ReturnTypeAnalyzers {

    Class<?>[] findClass(Method method);
}
