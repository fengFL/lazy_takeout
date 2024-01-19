package org.example.lazzy.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lazzy.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 *  this class is used to manage file uploads
 *
 * requirement for file upload
 *  method = "post", enctype = "multipart/form-data", type = "file"
 *  For example
 *  <form method = "post", action="/common/upload" enctype="multipart/form-data">
 *      <input name = "myFile" type="file"/>
 *      <input type = "submit" value = "submit"/>
 *  </form>
 *
 * requirement for file download
 * <img v-if="imageUrl" :src="imageUrl" class="avatar"></img>
 *
 *  the essence of upload images to the page is first upload the image to the server, the write the image to
 *  the page from the server.
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    // the base path
    @Value("${lazzy.path}")
    private String basePath;
    /**
     *  this is used to manage file upload
     *
     * @param file must match the name attribute from the request
     */
    @PostMapping("/upload")
    public Result<String> fileUpload(MultipartFile file) {
        // file is a temporary file, we need to transfer to other place
        // otherwise, the file will be cleared automatically

        String originalFilename = file.getOriginalFilename();
        // get the extension name of the original file, including the dot(.xxx)
        if(originalFilename == null) {
            return Result.error("upload failed");
        }
        int index = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(index);
        // generate UUID for the picture name
        String fileName = UUID.randomUUID().toString() + extension;

        // create a directory
        File directory = new File(basePath);
        if(!directory.exists()){
            // the directory does not exist
            // create the directory
            directory.mkdirs();
        }

        try {
            file.transferTo(new File(  basePath + fileName ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // return the file name
        return Result.success(fileName);
    }

    /**
     *
     *
     * @param response is used to write response to the client
     * @param name is used to accept the file name from the client
     * @return success if complete
     */
    @GetMapping("/download")
    public void fileDownload(HttpServletResponse response, String name){

        try (// Get file from local directory through FileInput Stream
             FileInputStream fileInputStream = new FileInputStream(basePath + name);
             // write the file to the client page through OutputStream of response
             // set response content type
             ServletOutputStream outputStream = response.getOutputStream();){
            response.setContentType("image/jpeg");
            // read the bytes of file and write the bytes to the page
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
