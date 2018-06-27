package br.edu.ifpb.nutrif.util;

import java.util.List;

public class ListUtil {

	public static boolean isItemNull(List values) {

		boolean isItemNull = false;
		
		for (Object value: values) {
			if (value == null) {
				isItemNull = true;
				return isItemNull;
			}
		}
		
		return isItemNull;		
	}
}
