package com.dtask.common.config;

import com.dtask.DTask.accountModule.dao.AccountDao;
import com.dtask.DTask.userModule.bo.PermissionBo;
import com.dtask.DTask.userModule.bo.RoleBo;
import com.dtask.DTask.accountModule.bo.AccountBo;
import com.dtask.DTask.userModule.dao.RoleDao;
import com.dtask.DTask.userModule.dao.UserDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现AuthorizingRealm接口用户用户认证
 * @author Louis
 * @date Jun 20, 2019
 */
public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	AccountDao accountDao;

	@Autowired
	RoleDao roleDao;

	@Autowired
	UserDao userDao;

	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {

		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		// 获取用户信息
		String username = authenticationToken.getPrincipal().toString();
		AccountBo accountBo = accountDao.findUserIDByName(username);

		if(accountBo == null){
			return null;
		}

		if(accountBo.getStatus() != 1)
		{
			throw new DisabledAccountException();
		}

		// 这里验证authenticationToken和simpleAuthenticationInfo的信息
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,
				accountBo.getPwd(), getName());

		return simpleAuthenticationInfo;

	}

	/**
	 * 角色权限和对应权限添加
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 获取登录用户名
		String username = (String) principalCollection.getPrimaryPrincipal();
		// 获取用户id
		int userID = accountDao.findUserIDByName(username).getId();

		// 获取用户角色
		RoleBo roleBo = roleDao.getUserRole(userID);

		// 添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();


		// 添加角色
		simpleAuthorizationInfo.addRole(roleBo.getRoleName());

		for (PermissionBo permissionBo : roleDao.getRolePermission(roleBo.getRoleID())) {
			// 添加权限
			simpleAuthorizationInfo.addStringPermission(permissionBo.getPCategory() + ":"
					+ permissionBo.getPObject() + ":"
					+ permissionBo.getPOperate());
		}
		return simpleAuthorizationInfo;
	}

}