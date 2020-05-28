## springboot 项目部署到 nginx 使用 https 环境的 demo

```
项目运行步骤

1 环境配置
    1 nginx 的配置
    2 项目的 application.properties 的配置
    3 操作系统的 host 文件配置
2 运行项目
    1 项目使用 springboot + gradle构建工具
    2 进入项目根目录，执行 gradle clean bootJar 生成 jar 文件运行
    3 浏览器打开 https://webapi.com/swagger-ui.hmtl 检验项目是否能正常访问
    4 访问 webapi 的接口，看负载均衡是否正常
```



- nginx 的配置
    ```
    需要在 nginx/conf 目录下增加 vhost 目录存放 manager.conf, webapi-balance.conf 文件
    ```
    - manager对应域名的配置
        - 对应配置文件 manager.conf
        
        ```
        server {
        	
        	listen       80;
        	server_name  manager.com;
        	return   301 https://$server_name$request_uri;
        }
        
        server {
        	
        	listen       443 ssl http2;
        	server_name  manager.com;
        
        	ssl_certificate      $ssl_cert_file;
        	ssl_certificate_key  $ssl_cert_key;
        	ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        	ssl_session_cache    shared:SSL:1m;
        	ssl_session_timeout  5m;
        	ssl_ciphers  HIGH:!aNULL:!MD5;
        	ssl_prefer_server_ciphers  on;
        
        	location / {
        		proxy_pass http://localhost:8083;
        		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        		proxy_set_header X-Forwarded-Proto $scheme;
        		proxy_set_header X-Forwarded-Port $server_port;
        		proxy_set_header X-Real-IP $remote_addr;
        		proxy_set_header Host $http_host;
        		proxy_set_header X-NginX-Proxy true;
        	}
        }

        ```

    - webapi 对应域名的配置
        - 对应配置文件 webapi-balance.conf
        
        ```
        # 这里使用了 nginx 的负载均衡，在生产上是很常见的
        upstream webapi_list{
        	server localhost:8081;
        	server localhost:8082;
        }
        	
        # webapi.com
        server {
        
        	listen       80;
        	server_name  webapi.com;
        	return   301 https://$server_name$request_uri;
        }
        
        server {
        
        	listen       443 ssl http2;
        	server_name  webapi.com;
        
        	ssl_certificate      $ssl_cert_file;
        	ssl_certificate_key  $ssl_cert_key;
        	ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
        	ssl_session_cache    shared:SSL:1m;
        	ssl_session_timeout  5m;
        	ssl_ciphers  HIGH:!aNULL:!MD5;
        	ssl_prefer_server_ciphers  on;
        
        	location / {
        		proxy_pass http://webapi_list;
        		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        		proxy_set_header X-Forwarded-Proto $scheme;
        		proxy_set_header X-Forwarded-Port $server_port;
        		proxy_set_header X-Real-IP $remote_addr;
        		proxy_set_header Host $http_host;
        		proxy_set_header X-NginX-Proxy true;
        	}
        }
        ```

    - nginx.conf 的配置
    
        ```
        #user  nobody;
        worker_processes  1;
        error_log  logs/error.log;
        #pid        logs/nginx.pid;
        
        events {
            worker_connections  1024;
        }
        
        
        http {
            include       mime.types;
            default_type  application/octet-stream;
        
            log_format  main  '$remote_addr - $time_local - $status $body_bytes_sent';
            access_log  logs/access.log;
            sendfile        on;
            keepalive_timeout  65;
            
            server {
                listen   80 default;
                server_name  _;
                return 403;
            }
        
            server_names_hash_bucket_size 64; 
        
            geo $ssl_cert_file {
                default "D:/dev/app/nginx/SSL/nginx/create/zhengshu.pem";
            }
            
            geo $ssl_cert_key {
                default "D:/dev/app/nginx/SSL/nginx/create/zhengshu.key";
            }
            # 如果证书能支持多级域名的话，可以用这 2 个 geo 的全局证书配置
            # vhost 每个文件的 ssl_certificate 直接用变量 $ssl_cert_file 配置
            
            include vhost/*.conf;
        }
        ```

- 项目的配置文件要添加这些

    ```
    server.tomcat.remote_ip_header=x-forwarded-for
    server.tomcat.protocol_header=x-forwarded-proto
    server.tomcat.port-header=X-Forwarded-Port
    server.use-forward-headers=true
    ```

- 操作系统的 host 文件配置
    ```
    127.0.0.1 webapi.com
    127.0.0.1 manager.com
    ```
