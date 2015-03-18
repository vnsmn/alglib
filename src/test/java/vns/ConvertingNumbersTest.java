package vns;

import org.junit.Test;

import vns.ConvertingNumbers;

public class ConvertingNumbersTest {

    @Test
    public void testConvert() {
        ConvertingNumbers cn = new ConvertingNumbers();
        String s = cn.convert(31, ConvertingNumbers.BaseOfPowerConst.BINARY);
        System.out.println(s);
        s = cn.convert("37", ConvertingNumbers.BaseOfPowerConst.OCTAL, ConvertingNumbers.BaseOfPowerConst.HEXADECIMAL);
        System.out.println(s);
    }
}
