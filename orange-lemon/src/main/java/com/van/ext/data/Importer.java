package com.van.ext.data;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface Importer {
	public void doImport(MultipartHttpServletRequest request, String clsId);
}
