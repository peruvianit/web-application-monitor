package io.peruvianit.monitor.util;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.FileSystemResource;

import io.peruvianit.monitor.error.exception.FileUtilsException;

public class FileUtils {

	public static List<File> listFile(final String pathDirectory) throws FileUtilsException {
	    File folder = new File(pathDirectory);
	    
	    if (folder.exists()) {
	    	
	    	if (!folder.isDirectory()) {
	    		throw FileUtilsException.folderNotDirectory(pathDirectory);
	    	}
	    	
		    return (List<File>)Stream.<File>of(folder.listFiles()).sorted(new Comparator<File>() {
		          public int compare(File o1, File o2) {
		            return Long.valueOf(o2.lastModified()).compareTo(Long.valueOf(o1.lastModified()));
		          }
		        }).collect(Collectors.toList());
	    }else {
	    	throw FileUtilsException.folderNotExists(pathDirectory);
	    }
	    
	}
	
	public static FileSystemResource file(final String pathFileName) throws FileUtilsException {
		File file = new File(pathFileName);
		
		if (file.exists()) {
			return new FileSystemResource(file);
	    }else {
	    	throw FileUtilsException.fileNotExists(pathFileName);
	    }
	    
	}
}
