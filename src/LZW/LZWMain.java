package LZW;

import java.util.ArrayList;
import java.util.HashMap;

public class LZWMain {

	public static ArrayList<tag> compress(String data, binNum bin) {
		ArrayList<tag> compresed = new ArrayList<tag>();
		String temp = "";
		char ch;
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int index = 0;
		for (; index < 128; index++)
			map.put(new Character((char) index) + "", index);

		for (int i = 0; i < data.length(); i++) {

			ch = data.charAt(i);

			if (!map.containsKey(temp + ch)) {
				// System.out.println(temp + ch + " " + index);
				map.put(temp + ch, index);
				index++;
				compresed.add(new tag(map.get(temp)));
				bin.maxNum(map.get(temp));
				//bin.SetMaxBinLen(map.get(temp));
				i--;
				temp = "";
			} else
				temp += ch;

		}
		if (!temp.equals("")) {
			compresed.add(new tag(map.get(temp)));
			bin.maxNum(map.get(temp));
		}
		// System.out.println(compresed);
		return compresed;
	}

	public static String deCompress(ArrayList<tag> compresed) {
		String deCompressed = "", temp = "";
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		int index = 0;
		for (; index < 128; index++)
			map.put(index, new Character((char) index) + "");

		deCompressed += map.get(compresed.get(0).num);

		for (int i = 1; i < compresed.size(); i++, index++) {

			if (compresed.get(i).num != index) {
				map.put(index,
						map.get(compresed.get(i - 1).num)
								+ map.get(compresed.get(i).num).charAt(0));
			} else {
				map.put(index,
						map.get(compresed.get(i - 1).num)
								+ map.get(compresed.get(i - 1).num).charAt(0));
			}

			deCompressed += map.get(compresed.get(i).num);

		}

		return deCompressed;
	}

	public static void main(String[] args) {

	//	new LZWAPP();

		 binNum bin = new binNum();
		 String data = "mohamed fouad";
		 ArrayList<tag> compresed = compress(data, bin);
		 System.out.println(compresed.toString());
		 String result = deCompress(compresed);
		 System.out.println(data + "\n" + result);
	}

}
