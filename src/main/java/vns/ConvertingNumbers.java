package vns;

import vns.str.Alphabet;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * Converting numbers
 */
public class ConvertingNumbers {
	public String convert(long decimalNumber, BaseOfPowerConst targetBaseOfPower) {
		if (decimalNumber == 0) {
			return "0";
		}
		int[] result = getFactors(decimalNumber, targetBaseOfPower);
		StringBuilder sb = new StringBuilder(result.length * 2);
		// factor is index of symbol position in char array
		for (int factor : result) {
			sb.insert(0, targetBaseOfPower.toChar(factor));
		}
		return decimalNumber > 0 ? sb.toString() : "-" + sb.toString();
	}

	public String convert(String sNumber, BaseOfPowerConst sourceBaseOfPower, BaseOfPowerConst targetBaseOfPower) {
		String sn;
		if (StringUtils.isBlank((sn = sNumber.trim()))) {
			throw new IllegalArgumentException("sNumber is empty or null");
		}
		long value = toDecimal(sn, sourceBaseOfPower);
		return sn.startsWith("-") ? "-" + convert(value, targetBaseOfPower) : convert(value, targetBaseOfPower);
	}

	private long toDecimal(String value, BaseOfPowerConst baseOfPower) {
		long result = 0;
		char[] chars = value.toCharArray();
		int n = 1;
		for (int ind = chars.length - 1; ind >= 0; ind--) {
			int factor;
			try {
				factor = baseOfPower.toFactor(chars[ind]);
			} catch (IllegalArgumentException ex) {
				continue;
			}
			result += factor * n;
			n *= baseOfPower.BASE;
		}
		return result;
	}

	private int[] getFactors(long decimalNumber, BaseOfPowerConst baseOfPower) {
		int pos = 0;
		int kn = 1;
		long numberPrev = Math.abs(decimalNumber);
		int[] result = null;
		while (numberPrev > 0) {
			long value = numberPrev / kn;
			if (value < baseOfPower.BASE) {
				if (result == null) {
					result = new int[pos + 1];
					Arrays.fill(result, 0);
				}
				result[pos] = (int) value;
				numberPrev = numberPrev % kn;
				pos = 0;
				kn = 1;
			} else {
				pos++;
				kn *= baseOfPower.BASE;
			}
		}
		return result == null ? new int[0] : result;
	}

	public enum BaseOfPowerConst {
		BINARY(2, Alphabet.BINARY), OCTAL(8, Alphabet.OCTAL), DECIMAL(10, Alphabet.DECIMAL), HEXADECIMAL(16,
				Alphabet.HEXADECIMAL);
		final public int BASE;
		final public Alphabet alphabet;

		BaseOfPowerConst(int base, Alphabet alphabet) {
			this.BASE = base;
			this.alphabet = alphabet;
		}

		public char toChar(int value) {
			return alphabet.toChar(value);
		}

		public int toFactor(char ch) {
			return alphabet.toIndex(ch);
		}
	}
}
