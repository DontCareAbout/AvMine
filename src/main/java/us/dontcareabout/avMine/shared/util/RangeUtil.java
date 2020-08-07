package us.dontcareabout.avMine.shared.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;

public class RangeUtil {
	private static final String toChar = "-";
	private static final String splitChar = ",";

	/**
	 * 將指定格式的字串轉為 {@link Range} instance。
	 */
	public static Range<Integer> parse(String string) {
		Preconditions.checkNotNull(string);

		String[] array = string.trim().split(toChar);
		int a0 = Integer.parseInt(array[0].trim());
		int a1 = Integer.parseInt(array[1].trim());
		//有惡搞出 a2 以上的... 不理它...... XD

		try {
			return Range.closed(Math.min(a0, a1), Math.max(a0, a1));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 將指定格式的字串，轉為 type 為 {@link Range} 的 {@link List}。
	 */
	public static List<Range<Integer>> parseList(String string) {
		ArrayList<Range<Integer>> result = new ArrayList<>();

		if (string == null) { return result; }

		for (String term : string.split(splitChar)) {
			Range<Integer> r = parse(term);

			if (r != null) { result.add(parse(term)); }
		}

		return result;
	}

	public static String toString(Range<Integer> r) {
		Preconditions.checkNotNull(r);
		return r.lowerEndpoint() + toChar + r.upperEndpoint();
	}

	public static String toString(List<Range<Integer>> list) {
		Preconditions.checkNotNull(list);

		if (list.size() == 0) { return ""; }

		StringBuilder result = new StringBuilder(toString(list.get(0)));

		for (int i = 1; i < list.size(); i++) {
			result.append(splitChar);
			result.append(toString(list.get(i)));
		}

		return result.toString();
	}
}
