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
        File f = new File(createTempFilePath(uploadedFile.originalFilename))
        uploadedFile.transferTo(f)
        return f.getAbsolutePath()
    }

    def createTempDir(){
        File file = new File(Common.TEMP_DIR );
        if(!file.isDirectory()){
            println("目录不存在！将进行创建");
            file.mkdir()
        }
        return file.getAbsolutePath()
    }

    def createTempFilePath(fileName){
        def time = new Date().getTime()
        return createTempDir() + File.separator + time + fileName
    }
}
