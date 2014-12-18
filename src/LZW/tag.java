package LZW;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class tag {
	public int num;
	public static ArrayList<Boolean> binaryTag = new ArrayList<Boolean>(32);
	public static ArrayList<Integer> binaryNum = new ArrayList<Integer>(32);
	public static int numberTages;

	tag() {
		num = 0;
	}

	tag(int y) {
		num = y;
	}

	public String toString() {
		return num + "";
	}

	public void prepareTag(binNum poi) {
		ArrayList<Boolean> arPoi = poi.toBinary(num);

		for (int i = 0; i < arPoi.size(); i++) {
			binaryTag.add(arPoi.get(i));
		}
	}

	public static void genrate() {
		int num = 0;
		for (int i = 0, j = 1; i < binaryTag.size(); i++, j++) {

			if (j % 32 == 0) {
				j = 1;
				binaryNum.add(num);
				num = (int) ((binaryTag.get(i) ? 1 : 0) * Math.pow(2, j - 1));
			} else
				num += (binaryTag.get(i) ? 1 : 0) * Math.pow(2, j - 1);
		}
		if (num > 0)
			binaryNum.add(num);
	}

	public static void createBinaryNum() {
		binaryTag = new ArrayList<Boolean>(32);
		int num = 0;
		for (int i = 0; i < binaryNum.size(); i++) {
			num = binaryNum.get(i);
			for (int j2 = 0; j2 < 31; j2++) {
				binaryTag.add((num % 2 == 1 ? true : false));
				num /= 2;
			}
		}
	}

	public static ArrayList<tag> deGenrate(binNum poi) {

		createBinaryNum();

		ArrayList<tag> list = new ArrayList<tag>();
		int tagSize = poi.binLen;
		for (int i = 0, j2 = 0; i < binaryTag.size() && j2 < numberTages ; i += tagSize, j2++) {

			tag temp = new tag();

			ArrayList<Boolean> po = new ArrayList<Boolean>();

			for (int j = i; j < i + tagSize && j < binaryTag.size() ; j++) {
				po.add(binaryTag.get(j));
			}

			temp.num = poi.toDecimal(po);

			list.add(temp);

		}

		return list;
	}

	public static void saveTag(DataOutputStream out, binNum poi)
			throws IOException {
		out.writeShort(poi.binLen);
		genrate();
		out.writeShort(binaryTag.size() / (poi.binLen));
		for (int i = 0; i < binaryNum.size(); i++) {
			out.writeInt(binaryNum.get(i));
		}

	}

	public static void readTag(DataInputStream in, binNum poi) {
		binaryNum = new ArrayList<Integer>(32);
		try {
			poi.SetMaxBinLen(in.readShort());
			numberTages = in.readShort();
			for (int i = 0; true; i++) {
				binaryNum.add(in.readInt());
			}
		} catch (Exception e) {
			return;
		}
	}

}

