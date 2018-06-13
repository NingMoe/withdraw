package com.phicomm.smarthome.ssp.server.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phicomm.smarthome.ssp.server.dao.UseHelpMapper;
import com.phicomm.smarthome.ssp.server.model.UseHelpModel;
import com.phicomm.smarthome.ssp.server.model.request.MoveReqModel;
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.ssp.server.service.UseHelpService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import com.phicomm.smarthome.ssp.server.util.MyListUtils;
import com.phicomm.smarthome.util.OptDateUtil;

@Service
public class UseHelpServiceImp implements UseHelpService {
    
    private static final Logger LOGGER = LogManager.getLogger(UseHelpServiceImp.class);

    /** 图片类型名 */
    private static final String IMG_TYPE_NAME = "pic";

    /** http://字符串中的双斜杠的下标值以上 */
    private static final int DOUBLE_SLASH_SUBSCRIPT_VALUE = 7;

    /** 更新记录数为0 */
    private static final int UPDATE_RECORD_COUNT = 0;

    /** 添加操作参数id为0 */
    private static final int ADD_OPERAR_PARAM_ID = 0;

    /** 是否被删除的状态,0为未删除，-1为删除*/
    private static final byte VOICE_CMDS_DELETE_STATUS = -1;
    private static final byte VOICE_CMDS_STATUS = 0;

    @Autowired
    private UseHelpMapper useHelpMapper;

    @Autowired
    private FtpOptService ftpOptService;

    @Value("${FTP_IMG_BASE_PATH}")
    private String FtpImgBasePath;
    
    /** 帮助内容sequenceInit初始值为1 */
    private static final int SEQUENCE_INIT_VALUE = 1;

    @Transactional
    @Override
    public int addUseHelp(UseHelpModel model) {
        int addCount = UPDATE_RECORD_COUNT;
        try {
            // 插入主表一条记录
            model.setUpdateTime(OptDateUtil.getNowTimeLong());
            if (model.getId() != ADD_OPERAR_PARAM_ID ) { // 编辑操作
                int editCount = useHelpMapper.editUseHelp(model);
                if (editCount > 0) {
                    addCount = useHelpMapper.deleteUseHelpContentUrl(model);
                }
            } else {
                // 添加操作
                // 查询最大的顺序号+1
                Integer maxSequence = useHelpMapper.getMaxSequence();
                maxSequence = maxSequence == null? SEQUENCE_INIT_VALUE: ++maxSequence;
                model.setSequence(maxSequence);
                model.setStatus(VOICE_CMDS_STATUS);
                model.setCreateTime(OptDateUtil.getNowTimeLong());
                addCount = useHelpMapper.addUseHelp(model);
            }
            // 插入附表多条记录
            if (addCount > 0) {
                Integer sequence = SEQUENCE_INIT_VALUE;
                synchronized(sequence){
                    for (UseHelpModel.HelpContent helpContent : model.getHelpContentList()) {
                        if (helpContent.getType().equals("pic")) {
                            helpContent.setContent(helpContent.getContent().substring(helpContent.getContent().indexOf("/images/sharedwifi-backend")));
                        }
                        helpContent.setSequence(sequence);
                        helpContent.setUseHelpId(model.getId());
                        helpContent.setStatus(VOICE_CMDS_STATUS);
                        ++sequence;
                    }
                }
                return useHelpMapper.addContentUrlList(model.getHelpContentList());
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return UPDATE_RECORD_COUNT;
    }

    @Transactional
    @Override
    public int deleteUseHelp(UseHelpModel model) {
        try {
            // 删除主表记录
            model.setUpdateTime(OptDateUtil.getNowTimeLong());
            int deleteHelpCount = useHelpMapper.deleteUseHelp(model);
            List<UseHelpModel.HelpContent> listHelpContent = useHelpMapper.getHelpContentList();
            // 删除附表记录
            int deleteContent = 0;
            if (deleteHelpCount > 0) {
                deleteContent = useHelpMapper.deleteUseHelpContentUrl(model);
            }
            // 删除图片文件
            if (!MyListUtils.isEmpty(listHelpContent) && deleteContent > 0) {
                deleteImgFile(listHelpContent ,model.getId());
            }
            return deleteContent;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return UPDATE_RECORD_COUNT;
    }

    /** 删除图片文件 */
    private void deleteImgFile(List<UseHelpModel.HelpContent> listHelpContent, int id) {
        for (UseHelpModel.HelpContent helpContent : listHelpContent) {
            if (helpContent.getUseHelpId() == id && helpContent.getType().toLowerCase().equals(IMG_TYPE_NAME.toLowerCase())) {
                // 去掉域名前部分
                String fileName = helpContent.getContent().substring(helpContent.getContent().indexOf("/",DOUBLE_SLASH_SUBSCRIPT_VALUE));
                // 删除图片文件
                ftpOptService.removeFile(fileName);
            }
        }
    }

    @Transactional
    @Override
    public int moveUseHelp(MoveReqModel model) {
        try {
            // 移动记录
            int upMoveCount = useHelpMapper.moveUseHelp(model.getUpId(), model.getDownSequence(), model.getLastOptUid(), model.getRealName());
            if (upMoveCount > UPDATE_RECORD_COUNT) {
               return useHelpMapper.moveUseHelp(model.getDownId(), model.getUpSequence(), model.getLastOptUid(), model.getRealName());
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return UPDATE_RECORD_COUNT;

    }

    @Override
    public List<UseHelpModel> getUseHelpList(String client, int startPage, int endPage) {
        List<UseHelpModel> list = new ArrayList<>();
        List<UseHelpModel.HelpContent> helpContentList = new ArrayList<>();

        try {
            // 获取使用帮助列表
            list = useHelpMapper.getUseHelpList(client, startPage, endPage);
            // 获取帮助内容
            helpContentList = useHelpMapper.getHelpContentList();
            for (UseHelpModel model : list) {
                List<UseHelpModel.HelpContent> newContentList = new ArrayList<>();
                for (UseHelpModel.HelpContent helpModel : helpContentList) {
                    // 给每个使用帮助添加上帮助内容
                    if (model.getId() == helpModel.getUseHelpId()) {
                        newContentList.add(helpModel);
                    }
                    if (helpModel.getType().equals("pic")) {
                        helpModel.setContent(FtpImgBasePath + helpModel.getContent());
                    }
                }
                model.setHelpContentList(newContentList);
                model.setCreateTimeStr(CommonUtils.stampToDateTimeStr(model.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                model.setUpdateTimeStr(CommonUtils.stampToDateTimeStr(model.getUpdateTime(), "yyyy/MM/dd HH:mm:ss"));
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return list;
    }

    @Override
    public int getUseHelpListCount() {
        return useHelpMapper.getUseHelpListCount();
    }

    
    @Override
    public int countUseHelp(String client) {
        int count = 0;
        try {
           count = useHelpMapper.countUseHelpList(client);
            
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return count;
    }
}
