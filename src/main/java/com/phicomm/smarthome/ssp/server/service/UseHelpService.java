package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.UseHelpModel;
import com.phicomm.smarthome.ssp.server.model.request.MoveReqModel;

import java.util.List;

public interface UseHelpService {

    int addUseHelp(UseHelpModel model);

    int deleteUseHelp(UseHelpModel model);

    int moveUseHelp(MoveReqModel model);

    List<UseHelpModel> getUseHelpList(String client, int startPage, int endPage);

    int getUseHelpListCount();

    int countUseHelp(String client);

}
