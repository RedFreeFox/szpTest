package com.szpcons.szpcons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 压缩解压文件
 * @author admin
 *
 */
public class FileToZipUtil {
	private static final Logger log = LoggerFactory
			.getLogger(FileToZipUtil.class);
    /**
     * 文件加密压缩,并输出到界面
     * @param response  
     * @param zipFileName 压缩文件名字
     * @param file  要压缩的文件
     * @param fileName 压缩的文件的名字
     * @param passWord 加密的密码
     * @throws IOException
     * @throws ZipException
     */
    public static void fileToZip(HttpServletResponse response, String zipFileName,
                                 File file, String fileName, String passWord) throws IOException, ZipException{
    	//获取输出到页面的流  
    	log.debug("调用FileToZipUtil.fileToZip()方法，文件压缩成ZIP文件 ------begin!");
    	if(StringUtils.isBlank(passWord)){
    		throw new ZipException("input password is null or empty in standard encrpyter constructor");
    	}        
        response.setContentType("application/x-msdownload;charset=utf-8");  
        //new String(zipFileName.getBytes("utf-8"), "ISO_8859_1")
        response.setHeader("Content-Disposition", "attachment;filename=\""+zipFileName+"\"");
        ZipModel modle = new ZipModel();
        modle.setFileNameCharset(InternalZipConstants.CHARSET_UTF8);
        try{
        	ZipOutputStream outputStream = new ZipOutputStream(response.getOutputStream(),modle);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);  
            parameters.setSourceExternalStream(true);  
            parameters.setFileNameInZip(fileName);
            parameters.setEncryptFiles(true);  
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);  
            parameters.setPassword(passWord);  
            outputStream.putNextEntry(null, parameters);  
            outputStream.write(getContent(file)) ;
            outputStream.flush();  
            outputStream.closeEntry();  
            outputStream.finish();  
            outputStream.close();  
            deleteFolder(file);
            if (response != null) {
                if (response.getOutputStream() != null)
                    response.getOutputStream().flush();               
            }
        }finally{
        	 if (response.getOutputStream() != null)
                 response.getOutputStream().close();
        }
        

        log.debug("调用FileToZipUtil.fileToZip()方法，文件压缩成ZIP文件 ------end!");
    }
    
    /**
     * 获取文件的字节数组
     * @param file
     * @return
     * @throws IOException
     */
    public static  byte[] getContent(File file) throws IOException {  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            log.error("文件太大了!");
            return null;  
        }  
        FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
        int offset = 0;  
        int numRead = 0;  
        while (offset < buffer.length  
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
            offset += numRead;  
        }  
        // 确保所有数据均被读取  
        if (offset != buffer.length) {  
        throw new IOException("Could not completely read file "  
                    + file.getName());  
        }  
        fi.close();  
        return buffer;  
    }  
    
    public static void deleteFolder(File file) {  
        // 判断目录或文件是否存在  
        if (file.isFile() && file.exists()) {  
        	file.delete();
        }  
    }  
    
    
    /**
     * 创建excel文件（不创建文件生成的压缩文件解压文件打不开）
     * @param wbook
     * @param wsheet
     * @param path
     * @return
     * @throws Exception
     */
    /*public static File createrFile(HSSFWorkbook wbook,String path,String Parentpath  ) throws Exception{
    	log.debug("调用FileToZipUtil.createrFile()方法,创建excel文件");
    	File file = new File(Parentpath);
    	if(!file.exists()){
    		file.mkdirs();
    	}
    	File downloadfile = new File(path);
    	if(!downloadfile.exists()){
    		downloadfile.createNewFile();
    	}
		FileOutputStream out = new FileOutputStream(downloadfile);  
		wbook.write(out);
		out.flush();  
		out.close(); 
        wbook = null;
    	return downloadfile;
    }*/
    
    /*public static HSSFWorkbook createWorkBook(List<WorkBookData> dataList) throws Exception{
    	if(null==dataList||dataList.size()<=0){
    		return null;
    	}
        HSSFWorkbook wbook = new HSSFWorkbook();
        
        for (int i = 0; i < dataList.size(); i++) {
        	String sheetName = dataList.get(i).getSheetName();
        	Map<String, String> headMap = dataList.get(i).getHeadmap();
        	List<Object> list = dataList.get(i).getContentList();
        	if(null==headMap||headMap.size()<=0){
        		continue;
        	}
        	if(StringUtils.isEmpty(sheetName)){
        		sheetName = "sheet"+(i+1);
        	}
        	HSSFSheet sheet = wbook.createSheet(sheetName);
        	ExportExcelUtil.setHeaderContent(sheet, wbook, headMap);
        	ExportExcelUtil.setContent(sheet, wbook, headMap,list);
		}
        
        return wbook;
    }*/
    
    /**
     * 导出加密压缩文件
     * @param exportFileName  加密压缩文件的名字
     * @param fileName  压缩的excel的名字
     * @param response  响应
     * @param password  加密的密码
     * @param dataList  excel数据集合
     * @throws Exception 异常
     */
   /* public static void exportEncryptZip(String exportFileName, String fileName, HttpServletRequest request,
                                        HttpServletResponse response, String password, List<WorkBookData> dataList) throws Exception{
		log.debug("调用FileToZipUtil.exportEncryptZip()方法,导出加密压缩文件");
    	String downLoadPath = request.getSession().getServletContext().getRealPath("/WEB-INF/uploadExcel/");
    	
    	File file =  createrFile(createWorkBook(dataList), downLoadPath + "test"+"_"+System.currentTimeMillis()+".xls",downLoadPath);
    	fileToZip(response,exportFileName,
        		file,fileName,password);
    }
    
    public static <T> List<Object> changeListObject(List<T> list){
    	
    	List<Object> dataList = new ArrayList<>();
    	if(list != null && list.size() > 0){
    		for (T t : list) {
    			dataList.add(t);
    		}
    	}
		return dataList;
    	
    }*/
   /* public static void main(String[] args) throws Exception {
    	List<WorkBookData> list = new ArrayList<>();
    	LinkedHashMap<String, String> head = new LinkedHashMap<>();
    	List<Object> dataList = new ArrayList<>();
    	UserInfo user1 = new UserInfo();
    	user1.setUserID(11111111111l);
    	user1.setDeptID(22222222222l);
    	
    	UserInfo user2 = new UserInfo();
    	user2.setUserID(33333333333l);
    	user2.setDeptID(44444444444l);
    	dataList.add(user1);
    	dataList.add(user2);
    	
    	head.put("userID", "用户ID");
    	head.put("deptID", "部门ID");
    	
    	WorkBookData data = new WorkBookData();
    	
    	data.setSheetName("部门信息");
    	data.setHeadmap(head);
    	data.setContentList(dataList);
    	list.add(data);
    	
	}*/
    /**
     * excel导出是超过50000行 自动分成多个sheet页
     * @param list
     * @param quantity
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List groupListByQuantity(List list, int quantity) {
		if (list == null || list.size() == 0) {
			return list;
		}

		if (quantity <= 0) {
			new IllegalArgumentException("Wrong quantity.");
		}

		List wrapList = new ArrayList();
		int count = 0;
		while (count < list.size()) {
			wrapList.add(list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity));
			count += quantity;
		}

		return wrapList;
	}
    
    
    
}
