package com.van.ext.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class CsvNormalEntityImporter implements Importer {
	private static Logger logger = LoggerFactory.getLogger(CsvNormalEntityImporter.class);

	@Override
	public void doImport(MultipartHttpServletRequest request, String clsId) {
		/*Map<String, String> map = FileUtil.upload("muiltFile", request);
		File file = new File(FileUtil.realPath, map.get("fileData"));
		try {
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			String[] attrs = lines.get(0).split(",");
			
			BusinessAttribute filter = new BusinessAttribute();
			filter.setBusinessClass(businessClassService.getById(clsId));
			List<BusinessAttribute> attributes = businessAttributeService.queryForList(filter);
			Map<String, String> attributeMap = new HashMap<String, String>();
			for(BusinessAttribute attribute : attributes){
				attributeMap.put(attribute.getName(), attribute.getColumnName());
			}
			
			for(int i=1, len = lines.size(); i<len; i++){
				Map<String, Object> entityValues = new HashMap<String, Object>();
				String[] values = lines.get(i).split(",");
				
				if(values.length != attrs.length){
					logger.info("该行数据与数据头不一致 {}", values.toString());
					continue;
				}
				for(int j=0, l=values.length; j<l; j++){
					entityValues.put(attributeMap.get(attrs[j]), values[j]);
				}
				normalEntityService.add(clsId, entityValues);
			}
		} catch (IOException e) {
			logger.error("DoImport error {}", e);
		}*/
	}

}
