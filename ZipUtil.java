package com.pt.theme;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.annotation.processing.FilerException;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * Created on 16/6/28.
 */
public class ZipUtil {

    /*
    * @param zipFilePath name of zip file
    * @param outPathString path to be unZIP
    * */
    public static void UnZipFile(String zipFilePath, String outPutPath) throws Exception{
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        UnZipFile(zipInputStream, outPutPath);
    }

    /*
    * @param inZip ZipInputStream of zip file
    * @param outPathString path to be unZIP
    * */
    public static void UnZipFile(ZipInputStream inZip, String outPutPath) throws Exception{
        File file = new File(outPutPath);
        if (!file.exists()) {
        	file.mkdirs();
        }
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null && !zipEntry.isDirectory()){
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()){
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPutPath + File.separator + szName);
                folder.mkdirs();
            }else{
            	file = new File(outPutPath + File.separator + szName);
                if (!file.getParentFile().exists()) {
                	file.getParentFile().mkdirs();
				}
                file.createNewFile();
                OutputStream outStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int byteread = 0;
                while ((byteread = inZip.read(buffer)) != -1){
                    outStream.write(buffer, 0, byteread);
                }
                outStream.flush();
                outStream.close();
            }
        }
        inZip.close();
    }
    
    /** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @throws FilerException 
     */  
    public static void fileToZip(String sourceFilePath, String zipFilePath) throws FilerException {
    	Project project = new Project();
    	Zip zip = new Zip();
    	zip.setProject(project);
    	zip.setDestFile(new File(zipFilePath));
    	FileSet fileSet = new FileSet();
    	fileSet.setProject(project);
    	fileSet.setDir(new File(sourceFilePath));
    	zip.addFileset(fileSet);
    	zip.execute();
//    	FileOutputStream outputStream = null;
//    	ZipOutputStream zipOutputStream = null;
//    	try {
//    		outputStream = new FileOutputStream(zipFilePath);
//    		zipOutputStream = new ZipOutputStream(outputStream);
//    		fileToZip(new File(sourceFilePath), zipOutputStream, "");
//		} catch (Exception e) {
//			throw new FilerException("创建压缩文件失败！");
//		}finally {
//			try {
//				if (zipOutputStream!=null) {
//					zipOutputStream.close();
//				}
//				if (outputStream!=null) {
//					outputStream.close();
//				}
//			} catch (Exception e2) {
//			}
//		}
	}
    
//    public static void fileToZip(File sourceFile,ZipOutputStream zipFile, String pathPrefix) throws Exception{  
//    	if (!sourceFile.exists()) {
//			throw new FileNotFoundException(sourceFile.getAbsolutePath() + "not found!");
//		}
//    	String[] filenames = sourceFile.list();
//    	for (int i = 0; i < filenames.length; i++) {
//			String filename = filenames[i];
//			File file = new File(sourceFile, filename);
//			String path = pathPrefix + file.getName();
//			if (file.isDirectory()) {
//				path += "/";
//				fileToZip(file, zipFile, path);
//			}else{
//				ZipEntry zipEntry = new ZipEntry(path);  
//				zipFile.putNextEntry(zipEntry);
//				FileInputStream inputStream = new FileInputStream(file);
//				BufferedInputStream bis = new BufferedInputStream(inputStream, 1024);
//				byte[] bufs = new byte[1024];
//				int read = 0;  
//                while((read=bis.read(bufs, 0, 1024)) != -1){  
//                	zipFile.write(bufs,0,read);  
//                }
//                bis.close();
//                inputStream.close();
//			}
//		}
//    }  

}
