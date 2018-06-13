package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.model.ldapsys.common.BaseResponseModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysAuthorityModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysMenuModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysRoleModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysUserModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysAuthorityListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysAuthorityNeedInfoListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysDpRoleInfoModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysMenuListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysRoleListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysUserAuthModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysUserInfoListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseTreeAuthListModel;
import com.phicomm.smarthome.model.ldapsys.response.SysUserResponseModel;

public interface LdapSysMgrService {

    SysUserResponseModel requestLdapLoginPst(SysUserResponseModel model, String reqUrl);

    BaseResponseModel requestLdapRolePst(RequestSysRoleModel model, String reqUrl);

    ResponseSysRoleListModel requestLdapQuerySysRoleInfoListPst(RequestSysRoleModel model, String reqUrl);

    ResponseTreeAuthListModel requestLdapAddRoleNeedAuthInfoPst(RequestSysRoleModel model, String reqUrl);

    ResponseSysDpRoleInfoModel requestLdapSysRolesPst(SysUserResponseModel model, String reqUrl);

    ResponseSysAuthorityNeedInfoListModel requestLdapAuthorityNeedInfoPst(RequestSysAuthorityModel model,
            String reqUrl);

    BaseResponseModel requestLdapAuthorityPst(RequestSysAuthorityModel model, String reqUrl);

    ResponseSysAuthorityListModel requestLdapAuthorityInfoListPst(RequestSysAuthorityModel model, String reqUrl);

    ResponseSysAuthorityListModel requestLdapSysAuthoritysPst(RequestSysUserModel model, String reqUrl);

    ResponseSysMenuListModel requestLdapSysMenuPst(RequestSysMenuModel model, String reqUrl);

    ResponseSysUserAuthModel requestLdapSysMenuPst(RequestSysAuthorityModel model, String reqUrl);

    ResponseSysDpRoleInfoModel requestLdapSysMenuPst(RequestSysUserModel model, String reqUrl);

    ResponseSysUserInfoListModel requestLdapSysUserInfoPst(RequestSysUserModel model, String reqUrl);

    BaseResponseModel requestLdapSysDelUserPst(RequestSysUserModel model, String reqUrl);

    ResponseSysUserAuthModel requestLdapCheckSysUserPageAuthPst(RequestSysAuthorityModel model, String reqUrl);

}
