package nl.openvalue.joosten.rosanne.kotlininput.data

import java.net.URI
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject

class JavaSourceFromString(name: String, code: String)
    : SimpleJavaFileObject(URI.create("string:///" + name.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
        JavaFileObject.Kind.SOURCE) {
    var code: String? = code

    override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence? {
        return code
    }
}
