package sl40168.sorting;

public class Sorting {
	static int[] popup(int[] input) {
		for (int i = 0; i < input.length - 1; i++) {
			for (int j = i + 1; j < input.length; j++) {
				if (input[i] > input[j])
					swap(input, i, j);
			}
		}
		return input;
	}

	static int[] simpleSelect(int[] input) {
		for (int i = 0; i < input.length - 1; i++) {
			int min = i;
			for (int j = i; j < input.length; j++) {
				if (input[j] < input[min])
					min = j;
			}
			swap(input, i, min);
		}
		return input;
	}

	static int[] insert(int[] input) {
		for (int i = 1; i < input.length; i++) {
			if (input[i] < input[i - 1]) {
				int temp = input[i];
				int j = i - 1;
				for (; input[j] > temp; j--)
					input[j + 1] = input[j];
				input[j + 1] = temp;
			}
		}
		return input;
	}

	static int[] quick(int[] input) {
		quick(input, 0, input.length - 1);
		return input;
	}

	static void quick(int[] input, int low, int high) {
		int pivotIndex;
		if (low < high) {
			pivotIndex = partion(input, low, high);
			quick(input, low, pivotIndex - 1);
			quick(input, pivotIndex + 1, high);
		}

	}

	static int partion(int[] input, int low, int high) {
		int pivot = input[low];
		while (low < high) {
			while (low < high && input[high] >= pivot)
				high--;
			swap(input, low, high);
			while (low < high && input[low] <= pivot)
				low++;
			swap(input, low, high);
		}
		return low;
	}

	static void insert(int[] input, int i1, int i2) {
		int ele = input[i2];
		for (int i = i2; i > i1; i--)
			swap(input, i - 1, i);
		input[i1] = ele;
	}

	public static void main(String[] args) {
		int[] input = new int[] { 2, 8, 9, 10, 3, 2, 6, 8, 3, 4, 5, 70, 6 };
		int[] output = new int[input.length];
		// input = quick(input);
		split(input, output, 0, input.length - 1);
		for (int i = 0; i < output.length; i++)
			System.out.println(output[i] + ",");
	}

	static void swap(int[] input, int i1, int i2) {
		int temp = input[i1];
		input[i1] = input[i2];
		input[i2] = temp;
	}

	static void split(int[] source, int[] target, int start, int end) {
		int middle = (end - start) / 2 + start;
		int[] target2 = new int[source.length];
		if (start == end)
			target[start] = source[start];
		else {
			split(source, target2, start, middle);
			split(source, target2, middle + 1, end);
			merge(target2, target, start, middle, end);
		}

	}

	static void merge(int[] source, int[] target, int start, int middle, int end) {
		int i = start, j = middle + 1, k = start;
		for (; i <= middle && j <= end; k++) {
			if (source[i] < source[j]) {
				target[k] = source[i];
				i++;
			} else {
				target[k] = source[j];
				j++;
			}
		}
		for (; i <= middle; i++, k++)
			target[k] = source[i];
		for (; j <= end; j++, k++)
			target[k] = source[j];
	}
}
