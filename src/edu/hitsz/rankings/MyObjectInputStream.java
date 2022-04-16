package edu.hitsz.rankings;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * 改写ObjectInputStream类的readStreamHeader方法，避免每次添加User都生成StreamHeader造成错误
 * @author Zhoudanni
 */
public class MyObjectInputStream extends ObjectInputStream {

    public MyObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected void readStreamHeader() {
    }

}
