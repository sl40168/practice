package sl40168.list;

import java.util.LinkedList;
import java.util.List;

public class KMP {
	int[] next;
	char[] contents;

	public static void main(String[] args) {
		String content = "abcabd";
		String full = "abcabcddfrs";
		KMP kmp = new KMP(content);
		for (int i = 0; i < kmp.next.length; i++)
			System.out.print(kmp.next[i] + " ");
		System.out.println("\r\n");
		System.out.println(kmp.kmpIn(full));
	}

	public KMP(String content) {
		this.contents = content.toCharArray();
		this.next = new int[this.contents.length];
		this.prepareNext(this.contents, this.next);
	}

	void prepareNext(char[] contents, int[] next) {
		next[0] = 0;
		for (int i = 0, j = 1; i < contents.length - 2 && j < contents.length - 1;) {
			char a = contents[i], b = contents[j];
			if (a == b) {
				i++;
				j++;
				next[j] = i;
			} else if (i == 0) {
				j++;
			} else {
				i = 0;
			}
		}
	}

	List<Integer> kmpIn(String full) {
		List<Integer> result = new LinkedList<Integer>();
		int j = 0;
		for (int i = 0; i < full.length();) {
			System.out.println("comparing with :" + full);
			String input = "";
			for (int c = 0; c < i - j; c++)
				input += " ";
			System.out.println("input is       :" + input + new String(contents));
			for (; j < this.contents.length && i < full.length();) {
				char a = full.charAt(i);
				char b = this.contents[j];
				if (a == b) {
					j++;
					i++;
					continue;
				} else if (j == 0) {
					i++;
				} else {
					j = this.next[j];
				}
				break;
			}
			if (j == this.contents.length) {
				result.add(i - j);
				j = 0;
				continue;
			}
		}
		return result;
	}
}
