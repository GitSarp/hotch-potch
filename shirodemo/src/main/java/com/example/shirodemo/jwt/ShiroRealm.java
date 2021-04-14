package com.example.shirodemo.jwt;

import com.example.shirodemo.domain.Account;
import com.example.shirodemo.service.AccountService;
import com.example.shirodemo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/9/21 3:39 PM
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("用户授权中");
        //获取当前登录的用户信息
        String username = JwtUtil.getUsernameFromToken(principalCollection.toString());
        Account account = accountService.findByUsername(username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置权限
        info.addStringPermission(account.getPerms());
        info.addRole(account.getRole());
        return info;
    }

    /**
     * 认证
     * 拿到从 executeLogin() 方法中传过来的 Token，并对 Token 检验是否有效、用户是否存在以及是否封号
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("身份认证");
        String token = (String) authenticationToken.getCredentials();
        //解密
        String username = JwtUtil.getUsernameFromToken(token);
        //从数据库汇总获取对应的用户名和面
        Account account = accountService.findByUsername(username);
        if (StringUtils.isEmpty(username) || !JwtUtil.verify(token,username)){
            log.error("token 认证失败");
            throw new AuthenticationException("token 认证失败");
        }
        if (null == account){
            log.error("账号或密码错误");
            throw new AuthenticationException("账号或密码错误");
        }
        log.info("用户{}认证成功！", account.getUsername());
        return new SimpleAuthenticationInfo(token, token, getName());
    }

//
//    /**
//     * 校验token的有效性
//     *
//     * @param token
//     */
//    public LoginUser checkUserTokenIsEffect(String token) throws AuthenticationException {
//        // 解密获得username，用于和数据库进行对比
//        String username = JwtUtil.getUsername(token);
//        if (username == null) {
//            throw new AuthenticationException("token非法无效!");
//        }
//
//        // 查询用户信息
//        log.debug("———校验token是否有效————checkUserTokenIsEffect——————— "+ token);
//        LoginUser loginUser = sysBaseAPI.getUserByName(username);
//        if (loginUser == null) {
//            throw new AuthenticationException("用户不存在!");
//        }
//        // 判断用户状态
//        if (loginUser.getStatus() != 1) {
//            throw new AuthenticationException("账号已被锁定,请联系管理员!");
//        }
//        // 校验token是否超时失效 & 或者账号密码是否错误
//        if (!jwtTokenRefresh(token, username, loginUser.getPassword())) {
//            throw new AuthenticationException("Token失效，请重新登录!");
//        }
//
//        return loginUser;
//    }
//
//    /**
//     * JWTToken刷新生命周期 （实现： 用户在线操作不掉线功能）
//     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)，缓存有效期设置为Jwt有效时间的2倍
//     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
//     * 3、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
//     * 4、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
//     * 注意： 前端请求Header中设置Authorization保持不变，校验有效性以缓存中的token为准。
//     *       用户过期时间 = Jwt有效时间 * 2。
//     *
//     * @param userName
//     * @param passWord
//     * @return
//     */
//    public boolean jwtTokenRefresh(String token, String userName, String passWord) {
//        String cacheToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
//        if (oConvertUtils.isNotEmpty(cacheToken)) {
//            // 校验token有效性
//            if (!JwtUtil.verify(cacheToken, userName, passWord)) {
//                String newAuthorization = JwtUtil.sign(userName, passWord);
//                // 设置超时时间
//                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
//                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME *2 / 1000);
//                log.info("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— "+ token);
//            }
//            return true;
//        }
//        return false;
//    }
}
