package rest.example;

public class StringUtils {
	/**
	 * 向String数组中添加一个字符串，如果传入的array为null，则返回一个新的字符串数组，并且该数组包含了给定字符串
	 * @param array
	 * @param str
	 * @return
	 * @date 2012-11-29下午1:29:01
	 */
	public static String[] addStringToArray(String[] array, String str) {
		if (array == null) {
			return new String[] { str };
		}
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}
}
