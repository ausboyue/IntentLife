package cn.icheny.intentlife;

import com.squareup.javapoet.JavaFile;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;

/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.06.15
 *     @desc   : Generator.
 * </pre>
 */
final class JavaFileGenerator {
    /**
     * Generate java file.
     */
    static void generate(Filer filer, JavaFile file) {
        if (filer == null || file == null) {
            return;
        }
        try {
            file.writeTo(filer);
        } catch (FilerException e) {
            //    e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
