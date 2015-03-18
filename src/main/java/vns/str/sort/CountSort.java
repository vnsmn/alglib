package vns.str.sort;

import java.util.Arrays;
import java.util.List;

import vns.str.Alphabet;

/**
 * 
 * @author vns
 *
 * Effective for sorting small keys.
 * 
 */
public class CountSort {	
	private Alphabet alphabet;
	
	public CountSort(Alphabet alphabet) {
		this.alphabet = alphabet;
	}
	
	public char[] execute(char ... sourceKeys) {
		char[] srcKeys = Arrays.copyOf(sourceKeys, sourceKeys.length);
		return executeInner(srcKeys);
	}
	
	public char[] execute(List<Character> sourceKeys) {
		char[] srcKeys = new char[sourceKeys.size()];
		int ind = 0;
		for (Character ch : srcKeys) {
			srcKeys[ind++] = ch;
		}
		return executeInner(srcKeys);
	}
	
	public char[] execute(String sourceKeys) {
		return this.execute(sourceKeys.toCharArray());
	}
	
	private char[] executeInner(char[] sourceKeys) {
		int[] count = new int[alphabet.R() + 1];
		Arrays.fill(count, 0);
		for (char ch : sourceKeys) {
			count[alphabet.toIndex(ch)]++;
		}
		for (int ind = count.length - 1; ind > 0; ind--) {
			count[ind - 1] += count[ind];
		}
		char[] sortedKeys = new char[sourceKeys.length];
		for (char ch : sourceKeys) {
			int index = alphabet.toIndex(ch);
			sortedKeys[sourceKeys.length - count[index]] = ch;
			count[index]--;
		}		
		return sortedKeys;
	}
}
