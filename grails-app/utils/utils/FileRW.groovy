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
        def time = new Date().getTime()
        String fileName = time + uploadedFile.originalFilename
        File file = new File(Common.TEMP_DIR );
        if(!file.isDirectory()){
            println("目录不存在！将进行创建");
            file.mkdir()
        }
        File f = new File(Common.TEMP_DIR + File.separator + fileName)
        uploadedFile.transferTo(f)
        return f.getAbsolutePath()
    }
}
