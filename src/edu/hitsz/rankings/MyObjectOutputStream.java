package edu.hitsz.rankings;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 改写ObjectOutputStream类的writeStreamHeader方法，避免读Header
 * @author Zhoudanni
 */
public class MyObjectOutputStream extends ObjectOutputStream {

    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        super.reset();
    }

}
