package nl.openvalue.joosten.rosanne.kotlininput.data

import java.net.URI
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject

class JavaSourceFromString : SimpleJavaFileObject {
    var code: String? = null

    constructor (name: String, code: String) :
            super(URI.create("string:///" + name.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
                    JavaFileObject.Kind.SOURCE) {
        this.code = code
    }

    override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence? {
        return code
    }
}
