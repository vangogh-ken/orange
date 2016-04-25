package com.van.ext.data;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Exportor {
    public void doExport(HttpServletRequest request, HttpServletResponse response, ExportDataModel exportDataModel)throws IOException;
}
