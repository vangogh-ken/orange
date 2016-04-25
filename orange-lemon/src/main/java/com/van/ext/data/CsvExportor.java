package com.van.ext.data;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.van.halley.core.util.ServletUtils;


public class CsvExportor implements Exportor {
    public void doExport(HttpServletRequest request,HttpServletResponse response, ExportDataModel exportDataModel)
            throws IOException {
    	//response.setCharacterEncoding("UTF-8");
        response.setContentType(ServletUtils.STREAM_TYPE);
        ServletUtils.setFileDownloadHeader(request, response, exportDataModel.getFileName() + ".csv");
        response.getOutputStream().write(exportDataModel.toCsv().getBytes("GBK"));
        //response.getOutputStream().write(exportDataModel.toCsv().getBytes());
        response.getOutputStream().flush();
    }
}
