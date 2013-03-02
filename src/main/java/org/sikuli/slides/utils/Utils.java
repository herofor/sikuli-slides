/**
Khalid
*/
package org.sikuli.slides.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

public class Utils {
	public final static String pptx = "pptx";
	public static final File zipDirectory = new File(Constants.zipDirectoryPath);
    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
 
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
    public static boolean createWorkingDirectory(){
    	
    	if (!zipDirectory.exists()) {
    		if (zipDirectory.mkdir()) {
    			return true;
    		}
			else 
				return false;
    	}
    	else
    		return true;
    }
    
    public static void doZipFile(File file){
       	byte[] buffer = new byte[1024];
    	try{
 
    		FileOutputStream fos = new FileOutputStream(zipDirectory.getAbsolutePath()+File.separator+file.getName()+".zip");
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(file.getName());
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(file.getAbsolutePath());
 
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
 
    		in.close();
    		zos.closeEntry();
    		zos.close();

 
    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
    }
    

    public static void doUnZipFile(File file){
    	
    	
     byte[] buffer = new byte[1024];
 
     try{
 
    	//create output directory
    	File folder = new File(zipDirectory.getAbsolutePath()+File.separator+file.getName().substring(0, file.getName().indexOf('.')));
    	// if the directory doesn't exist, create it
    	if(!folder.exists()){
    		folder.mkdir();
    	}
    	// if the directory already exists, delete it and recreate it.
    	else{
    		FileUtils.deleteDirectory(folder);
    		folder.mkdir();
    	}
    	
    	// Next, unzip it.
    	//get the zip file content
    	ZipInputStream zis = 
    		new ZipInputStream(new FileInputStream(file.getAbsoluteFile()));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	while(ze!=null){
 
    	   String fileName = ze.getName();
           File newFile = new File(folder + File.separator + fileName);
 
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
 
            FileOutputStream fos = new FileOutputStream(newFile);             
 
            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }
 
            fos.close();   
            ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();
    	
    	//delete the zip file since we no longer need it.
    	File zipFile = new File(zipDirectory.getAbsolutePath()+File.separator+file.getName()+".zip");
    	if(zipFile.delete())
    		return;
    	else
    		System.err.println("Couldn't delete zip: "+zipDirectory.getAbsolutePath()+File.separator+file.getName()+".zip");
     }
     catch(IOException ex){
       ex.printStackTrace(); 
     }
    }
    
    public static void createSikuliImagesDirectory(String projectAbsolutePath){
    	//create sikuli directory
    	File folder = new File(projectAbsolutePath+File.separator+Constants.sikuliDirectory);
    	//create sikuli images directory
    	File imagesFolder = new File(projectAbsolutePath+File.separator+Constants.sikuliDirectory+File.separator+Constants.imagesDirectory);
    	// if the directory doesn't exist, create it
    	if(!folder.exists()){
    		folder.mkdir();
    		imagesFolder.mkdir();
    	}
    }
    
}