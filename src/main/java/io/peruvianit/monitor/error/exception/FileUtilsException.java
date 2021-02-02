package io.peruvianit.monitor.error.exception;

import io.peruvianit.monitor.error.APIException;
import io.peruvianit.monitor.error.enums.TypeError;

public class FileUtilsException extends APIException{

	private static final long serialVersionUID = -3799401312056586715L;

	private FileUtilsException(String messageNotExists) {
		super(TypeError.FOLDER_NOT_EXISTS_ERROR, messageNotExists);
	}
	
	private FileUtilsException(TypeError typeError, String messageError) {
		super(typeError, messageError);
	}

	public static FileUtilsException folderNotExists(String pathDirectory) {
		String messageNotExists = "Folder not exist : " + pathDirectory;
		
		return new FileUtilsException(messageNotExists);
	}
	
	public static FileUtilsException fileNotExists(String pathFileName) {
		String messageNotExists = "File not exist : " + pathFileName;
		
		return new FileUtilsException(messageNotExists);
	}
	
	public static FileUtilsException folderNotDirectory(String pathDirectory) {
		String messageFolderNotIsDirectory = "Not is an directory : " + pathDirectory;
		
		return new FileUtilsException(TypeError.FILE_NOT_DIRECTORY_ERROR, messageFolderNotIsDirectory);
	}
}