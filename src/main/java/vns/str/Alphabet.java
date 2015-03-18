package vns.str;

import java.util.HashMap;

public class Alphabet {
	public static final Alphabet BINARY = new Alphabet("01");
	public static final Alphabet OCTAL = new Alphabet("01234567");
	public static final Alphabet DECIMAL = new Alphabet("0123456789");
	public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF");
	public static final Alphabet DNA = new Alphabet("ACTG");
	public static final Alphabet LOWERCASE = new Alphabet(
			"abcdefghijklmnopqrstuvwxyz");
	public static final Alphabet UPPERCASE = new Alphabet(
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	public static final Alphabet PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY");
	public static final Alphabet BASE64 = new Alphabet(
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
	public static final Alphabet ASCII = new Alphabet(128);
	public static final Alphabet EXTENDED_ASCII = new Alphabet(256);
	public static final Alphabet UNICODE16 = new Alphabet(65536);

	private char[] alphabet; // the characters in the alphabet
	private int lgR;
	private HashMap<Character, Integer> charUniqMap;

	// Create a new Alphabet of Unicode chars 0 to 255 (extended ASCII)
	public Alphabet() {
		this(256);
	}

	// Create a new Alphabet of Unicode chars 0 to R-1
	private Alphabet(int R) {
		alphabet = new char[R];
		charUniqMap = new HashMap<Character, Integer>();
		for (int i = 0; i < R; i++) {
			alphabet[i] = (char) i;
			charUniqMap.put((char) i, i);
		}
		lgR = (int) Math.log(R());
	}

	// Create a new Alphabet from sequence of characters in string.
	public Alphabet(String alpha) {
		alphabet = alpha.toCharArray();
		charUniqMap = new HashMap<Character, Integer>();
		int i = 0;
		for (char c : alphabet) {
			if (charUniqMap.containsKey(c)) {
				throw new IllegalArgumentException(
						"Illegal alphabet: repeated character = '" + c + "'");
			}
			charUniqMap.put(c, i++);
		}
		lgR = (int) Math.log(R());
	}

	// is character c in the alphabet?
	public boolean contains(char c) {
		return charUniqMap.containsKey(c);
	}

	// return number of bits to represent an index
	public int lgR() {
		return this.lgR;
	}

	// return radix R
	public int R() {
		return alphabet.length;
	}

	// convert an index between 0 and R-1 into a char over this alphabet
	public char toChar(int index) {
		if (index < 0 || index >= R()) {
			throw new IndexOutOfBoundsException("Alphabet index out of bounds");
		}
		return alphabet[index];
	}

	// Convert base-R integer into a String over this alphabet
	public String toChars(int[] indices) {
		StringBuilder s = new StringBuilder(indices.length);
		for (int i = 0; i < indices.length; i++) {
			s.append(toChar(indices[i]));
		}
		return s.toString();
	}

	// convert c to index between 0 and R-1.
	public int toIndex(char c) {
		if (!charUniqMap.containsKey(c)) {
			throw new IllegalArgumentException("Character " + c
					+ " not in alphabet");
		}
		return charUniqMap.get(c);
	}

	// convert String s over this alphabet into a base-R integer
	public int[] toIndices(String s) {
		int[] target = new int[s.length()];
		int i = 0;
		for (char c : s.toCharArray()) {
			target[i++] = toIndex(c);
		}
		return target;
	}
}
