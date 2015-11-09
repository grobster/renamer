package com.grobster.zip;

import java.text.*;
import java.util.*;

public class FileNamer {

	public static String nameFile() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	}
}