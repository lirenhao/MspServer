# mspServer

## 后台服务

- 获取用户信息

    网关将用户的orgId、userId放到了用户request的header里，可以在controller中添加下列参数获取orgId、userId
    
    ```
    @RequestHeader(value = "X-YADA-ORG-ID") String orgId
    @RequestHeader(value = "X-YADA-USER-ID") String userId
    ```

- 网关配置

    在网关的`spring.cloud.gateway.routes`下添加下列配置
    
    ```
    uri: http://localhost:3012
    predicates:
      - Svc=/api,msp
    filters:
      - AuthApi
      - RewritePath=/api/msp,/msp
    ```

## 前端应用

前端应用具体操作将在前端项目中具体说明

- 打包集成

    将前端项目打包(_前端路由只支持hashHistory方式_)放到`src/main/resources/static/app`目录下

- 网关配置

    在网关的`spring.cloud.gateway.routes`下添加下列配置

    ```
    uri: http://localhost:3012
    predicates:
      - App=/msp
    filters:
      - Auth
      - RewritePath=/msp,/msp/app/index.html
    ```
  
## 测试访问

- 后台服务测试

    - 直接访问
    
        使用http客户端(如ARC、PostMan、curl等)组装相应的http报文发送后台服务，组装http报文时需要在headers中添加下列参数
        
        ```
        X-YADA-ORG-ID: 请求用户的机构Id
           
        X-YADA-USER-ID: 请求用户的用户Id
        ```

    - 通过网关访问
        
        使用下列地址访问网关配置后台服务
        
        ```
        http://loaclhost:8080/api/msp/${res}
        ```

- 前端应用测试

    - 配置用户角色
          
        访问网关服务到权限配置管理端将，将服务端资源访问权限赋给角色问
        
    - 浏览器访问应用
    
        浏览器打开下列地址访问应用
        
        ```
        http://localhost:8080/msp
        ```

     

