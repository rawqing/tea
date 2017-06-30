package utils

import constant.Common
import org.omg.CORBA.Request

/**
 * Created by king on 17/5/2.
 */
class FileRW {

    /**
     * 将上传的文件写入临时目录
     * @param uploadedFile class org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile
     * @return
     */
    def writeTempFile(uploadedFile){
        return saveFile(uploadedFile ,createTempFilePath(uploadedFile.originalFilename))
    }

    def saveFile(uploadedFile ,String path) {
        File f = new File(path)
        uploadedFile.transferTo(f)
        return f.getAbsolutePath()
    }
    def createDir(String path){
        File file = new File(path);
        return createDir(file)
    }
    def createDir(File file){

        if(!file.isDirectory()){
            println("目录不存在！将进行创建");
            file.mkdir()
        }
        return file.getAbsolutePath()
    }

    def createTempFilePath(fileName){
        def time = new Date().getTime()
        return createDir(Common.TEMP_DIR) + File.separator + time + fileName
    }
}
