package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.ChallengeFile;
import cn.edu.swu.ffdy.springboot.repository.ChallengeFileRepository;
import cn.edu.swu.ffdy.springboot.repository.ChallengeRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/challengefile")
public class ChallengeFileHandler {
    private final static String basePath = "/home/ffdy/Downloads/uploads/";

    ChallengeFileRepository challengeFileRepository;

    @Autowired
    ChallengeFileHandler(ChallengeFileRepository challengeFileRepository) {
        this.challengeFileRepository = challengeFileRepository;
    }

    @GetMapping("/findAll")
    public List<ChallengeFile> findAll() {
        return challengeFileRepository.findAll();
    }

    @GetMapping("/getinfo/{challengeId}")
    public List<ChallengeFileInfo> findAllByChallengeId(@PathVariable("challengeId") Integer challengeId) {
        List<ChallengeFileInfo> challengeFileInfos = new ArrayList<>();
        List<ChallengeFile> challengeFiles = challengeFileRepository.findAllByChallengeId(challengeId);
        System.out.println(challengeFiles);
        for(ChallengeFile challengeFile : challengeFiles) {
            challengeFileInfos.add(new ChallengeFileInfo(
                    challengeFile.getName(),
                    challengeFile.getChallengeId()));
        }
        return challengeFileInfos;
    }

    @GetMapping("/getfile/{challengeId}/{fileName}")
    public String download(@PathVariable("challengeId")Integer challengeId,
                         @PathVariable("fileName")String fileName, HttpServletResponse response) {
        ChallengeFile challengeFile = challengeFileRepository.findByChallengeIdAndName(challengeId, fileName);
        if (challengeFile != null) {
            //??????????????????
            File file = new File(basePath + challengeFile.getLocation());
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// ???????????????????????????
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// ???????????????
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "????????????";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally { // ???????????????
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                return "???????????????";
            }
        }
        return "????????????";
    }

    @Getter
    @Setter
    @ToString
    private class ChallengeFileInfo {
        private String name;
        private Integer challengeId;

        public ChallengeFileInfo(String name, Integer challengeId) {
            this.name = name;
            this.challengeId = challengeId;
        }
    }
}
