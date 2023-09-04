# Instance

<details>
    <summary>인스턴스 생성</summary>

1. [리소스 실행] VM 인스턴스 생성 클릭
![Alt text](image/1.png)

<br/>

2. [이미지 및 구성] 이미지 변경 클릭
![Alt text](image/2.png)

<br/>

3. [이미지 선택] 서버 운영체제 고른 후 이미지 선택
![Alt text](image/3.png)

<br/>

4. [SSH키 추가] 서버에 접속할때 사용하기 위한 키 다운로드 (다시 발급받을 수 없으므로 파일을 관리해야함.)
![Alt text](image/4.png)

<br/>

5. [생성] 인스턴스 생성
![Alt text](image/5.png)

<br/>

6. [인스턴스] 기다리면 초록색으로 변경되고 구성이 완료됨.
![Alt text](image/6.png)

</details>

<details>
    <summary>인스턴스 접속</summary>

1. [모바엑스텀 설치](https://mobaxterm.mobatek.net/)
2. [세션] 새로운 세션 만들기 위해 세션 클릭
![Alt text](image2/1.png)

<br/>

3. [SSH] Remote host : 인스턴스 공용 IPv4 주소를 넣어주고, 인스턴스 생성하며 발급 받은 키 파일을 등록.
![Alt text](image2/2.png)

<br/>

4. [접속] 기본 로그인은 ubuntu. 
![Alt text](image2/3.png)

</details>

<details>
    <summary>방화벽 설정</summary>

1. 먼저 ubuntu에서 업데이트를 해줌.
    ```ubuntu
    sudo apt update
    ```

<br/>

2. ubuntu에서 특정 포트 방화벽 해제
    ```ubuntu
    ## firewall을 이용한 포트 열기
    # firewall 설치
    sudo apt install firewalld

    # 특정 포트 열기 규칙 추가
    sudo firewall-cmd --permanent --zone=public --add-port=80/tcp

    # 추가한 규칙 적용 하는 초기화
    sudo firewall-cmd --reload


    ## iptables를 이용한 포트 열기
    # 특정 포트 규칙 추가
    sudo iptables -I INPUT -p tcp -m tcp --dport 8080 -j ACCEPT

    # 특정 포트 규칙 삭제
    sudo iptables -D INPUT -p tcp -m tcp --dport 8080 -j ACCEPT

    # 특정 IP로만 특정 포트 규칙 추가
    sudo iptables -I INPUT -p tcp -s 123.123.123.123 --dport 8009 -j ACCEPT

    # 위의 규칙 삭제
    iptables -D INPUT -p tcp -s 123.123.123.123 --dport 8009 -j ACCEPT

    # 변경 사항 저장
    sudo netfilter-persistent save

    # 추가한 규칙 초기화
    sudo iptables -F

    ```

<br/>

3. 서브넷 방화벽 해제를 위해 서브넷 접속
![Alt text](image3/1.png)

<br/>

4. 보안 목록 선택
![Alt text](image3/2.png)

<br/>

4. 신규 규칙 추가
![Alt text](image3/3.png)

<br/>

5. 서버간 라우팅 허용 규칙 추가
![Alt text](image3/4.png)

</details>

<details>
    <summary>시간 설정</summary>

```ubuntu
sudo timedatectl set-timezone Asia/Seoul
```
</details>

<details>
    <summary>Java 설치</summary>

```ubuntu
# 운영체제에 기본으로 있는 jdk 설치, 또는 원하는 버전 설치 #
sudo apt install default-jdk
sudo apt-get install openjdk-11-jdk

# 설치 확인 #
java -version
javac -version

# 환경 변수 설정 #
sudo vim /etc/profile

# 맨 아래에 추가
...
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64      // 본인의 자바 설치 경로
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=$CLASSPATH:$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/tools.jar
...

#확인
source /etc/profile
echo $JAVA_HOME
```

</details>

<details>
    <summary>docker 설치</summary>

```ubuntu
# 필요한 패키지 설치
sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common

# Docker 공식 GPG키 추가
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

# Docker 공식 api 저장소 추가
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

# Docker 설치
sudo apt-get install docker-ce docker-ce-cli containerd.io

# 도커 실행상태 확인
sudo systemctl status docker

# docker 실행
sudo service docker start

# 파일의 권한을 666으로 변경하여 그룹 내 다른 사용자도 접근 가능하게 변경
sudo chmod 666 /var/run/docker.sock

# ubuntu 유저를 docker 그룹에 추가 후 재시작
sudo usermod -aG docker $USER
sudo service docker restart

# 버전 확인 
docker --version

# 현재 실행중인 도커 확인
docker ps

# Docker 삭제-1
sudo snap remove docker

# Docker 삭제-2
sudo apt-get purge -y docker-engine docker docker.io docker-ce
sudo apt-get autoremove -y --purge docker-engine docker docker.io docker-ce
sudo rm -rf /var/lib/docker /etc/docker
sudo rm /etc/apparmor.d/docker
sudo groupdel docker
sudo rm -rf /var/run/docker.sock
```
</details>

<details>
    <summary>docker 실행 시 설명</summary>

```ubuntu
docker run --name jenkins-docker -d -p 8000:8080 -p 8888:50000 -v /home/jenkins:/var/jenkins_home -u root jenkins/jenkins:lts
```

- `d` : detached mode, 백그라운드에서 컨테이너가 실행되게 한다.

- `p`: 서버의 9090포트와 컨테이너 내부 8080포트를 연결한다.

- `v`: 서버의 `/home/jenkins`경로와 컨테이너 내부 `/var/jenkins_home`경로를 마운트한다.  이것을 하는 이유는, Jenkins 설치 시 ssh 키값 생성, 저장소 참조 등을 용이하게 하기 위함입니다.

- `-name`: 실행될 컨테이너의 이름을 jenkins-docker으로 설정한다.

- `u`: 실행할 사용자를 root으로 설정한다.

- 포트는 ec2 인스턴스의 8000, 8888번 포트를 도커 컨테이너의 8080, 50000번 포트에 대응시킨다.



</details>

<details>
    <summary>docker 기타 명령어</summary>

1. [주요 명령어](https://captcha.tistory.com/49)
2. [도커 삭제 명령어](https://www.lainyzine.com/ko/article/docker-rm-removing-docker-containers/)

</details>

<details>
    <summary>docker-compose 설치</summary>

```ubuntu
#설치
sudo curl -L https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

#권환
sudo chmod +x /usr/local/bin/docker-compose

#버전확인
docker-compose --version
```

</details>

<details>
    <summary>docker-compose 명령어</summary>

1. [주요 명령어](https://kimjingo.tistory.com/108)
2. [간단 문법](https://darrengwon.tistory.com/793)

</details>


<details>
    <summary>nginx 설치</summary>

```ubuntu
# 설치
sudo apt install nginx

# 실행
sudo systemctl start nginx

# 상태 보기
sudo systemctl status nginx

# 연결 상태를 보기 위한 툴 설치
sudo apt install net-tools
netstat - lntp

# 제거
sudo apt remove nginx
sudo apt purge nginx

# docker로 설치하기
docker pull nginx

# docker로 실행하기
docker run --name 원하는이름 -v docker와 공유하려는 폴더 경로:docker 안에서 공유하려는 폴더 경로
```
</details>

<details>
    <summary>HTTPS 설정</summary>

```ubuntu

# https 설정을 위한 툴 설치
sudo apt-get install letsencrypt -y

# nginx 중단
sudo service nginx stop

# certbot 발급을 위한 80, 443 방화벽 열기
# certbot 이메일 입력, 인증서 발급 동의, 이메일 수신은 미동의
sudo certbot certonly --standalone -d 도메인(example.com)

# https 파일 설정
sudo vim /etc/nginx/sites-available/default

...
# 기본
server {
        if ($host = 도메인) {
                return 301 https://$host$request_uri;
        } # managed by Certbot

        listen 80 default_server;
        listen [::]:80 default_server;

        server_name 도메인;
        return 404;
}

server {
  index index.html index.htm index.nginx-debian.html;
  server_name 도메인; # managed by Certbot

  location / {

    try_files $uri $uri/ @router;
        }

        location @router{
            rewrite ^(.+)$ /index.html last;
        }

        ssl_certificate /etc/letsencrypt/live/도메인/fullchain.pem; # managed by Certbot
        ssl_certificate_key /etc/letsencrypt/live/도메인/privkey.pem; # managed by Certbot
  listen 443 ssl; # managed by Certbot

}



## 이 밑으로는 내가 보기위한 예시임.
# 80포트 접근 시 443 포트로 리다이렉트
server {
    if ($host = beanzido.com) {
        return 301 https://$host$request_uri;
    } # managed by Certbot

    listen 80 ;
    listen [::]:80 ;
    server_name beanzido.com;
    return 404; # managed by Certbot
}

# domain을 두개 연결해서 사용하고 싶다면 똑같은걸 만들기만 하면 된다.
server {
    if ($host = k7a206.p.ssafy.io) {
        return 301 https://$host$request_uri;
    } # managed by Certbot

    listen 80 ;
    listen [::]:80 ;
    server_name k7a206.p.ssafy.io;
    return 404; # managed by Certbot
}

server {
  index index.html index.htm index.nginx-debian.html;
  server_name beanzido.com; # managed by Certbot
  root /home/ubuntu/compose/jenkins/workspace/release/frontend/build/;
  location / {
    root /home/ubuntu/compose/jenkins/workspace/release/frontend/build/;
    try_files $uri $uri/ @router;
 	}
  location /chat-server{
    proxy_pass http://13.125.39.100:8091;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "Upgrade";
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $remote_addr;
    proxy_set_header X-Forwarded-Proto $scheme;		
 	}
  location /keyword-server{
    proxy_pass http://13.125.39.100:8092;
  }
 	location @router{
       	    rewrite ^(.+)$ /index.html last;
 	}
    
 	ssl_certificate /etc/letsencrypt/live/beanzido.com/fullchain.pem; # managed by Certbot
 	ssl_certificate_key /etc/letsencrypt/live/beanzido.com/privkey.pem; # managed by Certbot
  listen 443 ssl; # managed by Certbot
    
}

server {
  index index.html index.htm index.nginx-debian.html;
  server_name k7a206.p.ssafy.io; # managed by Certbot
  root /home/ubuntu/compose/jenkins/workspace/front/frontend/build/;
  location / {
    root /home/ubuntu/compose/jenkins/workspace/front/frontend/build/;
    try_files $uri $uri/ @router;
 	}
  location /chat-server{
    proxy_pass http://13.125.39.100:8061;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "Upgrade";
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $remote_addr;
    proxy_set_header X-Forwarded-Proto $scheme;		
 	}
  location /keyword-server{
    proxy_pass http://13.125.39.100:8062;
  }
 	location @router{
       	    rewrite ^(.+)$ /index.html last;
 	}
    
 	ssl_certificate /etc/letsencrypt/live/k7a206.p.ssafy.io/fullchain.pem; # managed by Certbot
 	ssl_certificate_key /etc/letsencrypt/live/k7a206.p.ssafy.io/privkey.pem; # managed by Certbot
  listen 443 ssl; # managed by Certbot
    
    
}
...



# nginx 제대로 실행 되는지 테스트 확인  
sudo nginx -t

# nginx 재시작
sudo service nginx restart

```

</details>

<details>
    <summary>nginx 명령어</summary>

```ubuntu
# nginx 실행
service nginx start
sudo service nginx start
sudo systemctl start nginx

# nginx 재실행
service nginx restart
sudo service nginx restart
sudo systemctl restart nginx

# nginx 중단
service nginx stop
sudo service nginx stop
sudo systemctl stop nginx

# nginx 상태 보기
service nginx status
sudo service nginx status
ps -ef | grep nginx

```

</details>

<details>
    <summary>Certbot(인증서) 명령어</summary>

```ubuntu
# 인증서 해지 명령어
sudo certbot revoke --cert-name www.domain.com

# 인증서 삭제 명령어
sudo certbot delete --cert-name www.domain.com

# 인증서 발급 명령어 (서버 소유주 인증 방식)
sudo certbot --nginx -d www.domain.com

# 인증서 발급 명령어 (nginx 웹서버 인증 방식)
sudo certbot certonly --standalone -d www.domain.com

# 인증서 발급 명령어 (도메인 소유주 방식)
sudo certbot certonly --manual --preferred-challenges dns-01 --server https://acme-v02.api.letsencrypt.org/directory -d "*.domain.com"

# 인증서 갱신 명령어
sudo certbot renew

# nginx로 받은 인증서 갱신 명령어
sudo nginx -s stop
sudo certbot renew
sudo nginx

# 만료 이메일 업데이트 (1년마다 갱싱해야함)
certbot update_account --email yourname+1@example.com
```

</details>

<details>
    <summary>CI/CD</summary>

- <details>
    <summary>github action</summary>

    1. github 프로젝트 세팅
    ![Alt text](image4/image.png)

    2. action에 들어가서 기본 파일 선택 (프로젝트랑 비슷한 것 고르면 됨. 아무거나 하고 수정해도 됨)
    ![Alt text](image4/image-1.png)

    3. 선택한 파일 안의 코드를 다음과 같이 수정
    ![Alt text](image4/image-2.png)
        - name: 파일 설명이라 보면 됨.
        - on: yml을 실행하는 시점, 실행 경로 등 기본 세팅을 설정함.
            - branches: 어떤 브랜치에 적용할 것인지 선택(다중 가능).
            - paths: test/** -> 해당 경로에 있는 모든 파일을 대상으로 하나라도 변경 된다면 yml 실행.
        - jobs: 어떤 작업을 할지 정하는 단계.
            - build: 빌드를 할때의 설정.
                - runs-on: 뭐로 할지 정하는 것.
                - defaults: build 안의 작업에 전역으로 세팅. 
                - steps: 실제 작업을 작성하는 단계.
                    - name: 별칭
                      uses: 다른 사람이 만들어 놓은 것을 사용
                      run: 명령어

    <br/>                
    
    4. yml의 소스에 맞춰 레파지토리에 변화가 생기면 action 실행 됨.
    ![Alt text](image4/image-3.png)

    5. 인스턴스 서버에 git 설치 및 github clone.
        ```
        # git 설치 및 확인
        sudo apt install git
        git --version

        # git 구성 및 확인
        git config --global user.name "Your Name"
        git config --global user.email "youremail@yourdomain.com"
        git config --list

        # 원하는 경로에서 git clone할 폴더 생성 및 clone. 
        cd /usr
        sudo mkdir github
        cd github
        sudo git clone repository주소
        ```
    <br/>

    6. repository > settings > Secrets and variables > Actions > new repository secret 을 통해 yml에서 사용할 변수 등록.
    ![Alt text](image4/image-5.png)
        - 해당 변수에 들어갈 값들은 7번 참조.
        
    <br/>

    7. 배포를 위해 oracle cloud에 접속해야함. appleboy/ssh-action@master 라이브러리를 이용하여 ssh 접속.
    ![Alt text](image4/image-4.png)
        - host: oracle cloud ip
        - username: 오라클 클라우드 홈페이지 인스턴스 정보에 들어가면 사용자 이름이 있음.
        - key: 인스턴스 생성할 때 만든 key 파일을 메모장으로 열면 값이 나옴. 전체 복사해야함. (맨위, 맨아래 ---- 부분도 복사 다해야함)  
        - port: 인스턴스 접속 포트 (기본 22)
        - script : 인스턴스 접속한 뒤 실행할 명령어. 

    8. 클라우드에 배포하기
    ![Alt text](image4/image-6.png)
        - front server 구성을 예시로 만듬.
        - 하나의 도커에 nginx와 vue 프로젝트를 구동 시킴.
        - dockerfile은 다음과 같음.
        
     

</details>

</details>
