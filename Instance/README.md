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


   ## 공통
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
    <summary>docker 이미지 빌드 및 실행 옵션</summary>

<br/>

```ubuntu
docker build -t portfolio:1.0 /home/ubuntu/about-me/
```

- `-t`: 태그를 뜻하며 이미지이름:태그 이다.

- `경로`: 이미지로 만들 Dockerfile이 있는 경로

<br/>

```ubuntu
docker run -m 512m --name jenkins-docker -d -p 8000:8080 -p 8888:50000 -v /home/jenkins:/var/jenkins_home -u root jenkins/jenkins:lts
```

- `-m`: docker에 할당할 최대 메모리를 설정한다.

- `--name`: 실행될 컨테이너의 이름을 jenkins-docker으로 설정한다.

- `d`: detached mode, 백그라운드에서 컨테이너가 실행되게 한다.

- `p`: 서버의 9090포트와 컨테이너 내부 8080포트를 연결한다.

- `v`: 서버의 `/home/jenkins`경로와 컨테이너 내부 `/var/jenkins_home`경로를 마운트한다. 이것을 하는 이유는, Jenkins 설치 시 ssh 키값 생성, 저장소 참조 등을 용이하게 하기 위함입니다.

- `u`: 실행할 사용자를 root으로 설정한다.

- 포트는 ec2 인스턴스의 8000, 8888번 포트를 도커 컨테이너의 8080, 50000번 포트에 대응시킨다.

</details>

<details>
    <summary>docker 기타 명령어</summary>

1. [주요 명령어](https://captcha.tistory.com/49)
2. [도커 삭제 명령어](https://www.lainyzine.com/ko/article/docker-rm-removing-docker-containers/)

</details>

<details>
    <summary>Dockerfile</summary>

- docker image를 만들기 위한 파일임.
- 확장자 명은 따로 없으며 Dockerfile 이라는 이름을 가짐.

```Dockerfile
# 해당 Dockerfile은 front 배포 시 사용한 예시

FROM node:14.21.3 as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

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
docker run -i -d --name 도커별칭 -p 클라우드포트번호:도커포트번호 -v 공유하려는 클라우드 폴더 경로:공유받으려는 도커의 폴더 경로
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

## Nginx front 배포 예시
server {
    if ($host = about-ljk.store) {
        return 301 https://$host$request_uri;
    } # managed by Certbot

    listen 80 default_server;
    listen [::]:80 default_server;

    server_name about-ljk.store;
    return 404;
}

server {
    root /home/ubuntu/about-me/dist/;
    index index.html index.htm index.nginx-debian.html;
    server_name about-ljk.store; # managed by Certbot

    location / {
        root /home/ubuntu/about-me/dist/;
        try_files $uri $uri/ @router;
    }

    location @router{
        rewrite ^(.+)$ /index.html last;
    }

    ssl_certificate /etc/letsencrypt/live/about-ljk.store/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/about-ljk.store/privkey.pem; # managed by Certbot
    listen 443 ssl; # managed by Certbot

}


## 이 밑에는 nginx를 API-gateway 기능도 활용한 방식
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

# ubuntu일 때, 도메인 접속 시 500 에러가 난다면 해당 파일 열어서 맨 윗줄의 user 변경
sudo vim /etc/nginx/nginx.conf
'''
user ubuntu;
'''

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
    <summary>nvm, node, npm 설치</summary>

```ubuntu
# curl 설치 (기본으로 설치되어있음)
sudo apt install build-essential curl


# nvm 설치
$ curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.36.0/install.sh | bash

# nvm 설정 리로드 (설정 파일은 여러가지 일 수 있으니 있는 파일은 다해주면 됨)(~/.bash_profile, ~/.zshrc, ~/.profile, ~/.bashrc )
source ~/.bashrc


# node 설치 (sudo는 사용하지 말것. 나중에 권한 문제 발생함.)
nvm install node

# node 특정 버전 설치
nvm install x.x.x

# node 버전 전환
nvm use x.x.x


# npm 설치 (node 설치 시 자동 설치 됨)
sudo apt install npm


# nvm 삭제
rm -rf ./nvm

# node 특정 버전삭제
nvm uninstall x.x.x

# npm 삭제
sudo apt remove npm

# npm 설정 파일까지 삭제
sudo apt purge npm



#### nvm 없이 node, npm 설치 하기 (8.x는 node 버전)
curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
sudo apt-get install -y nodejs
```

</details>

<details>
    <summary>메모리 관리</summary>

- 프리티어 사용 시 메모리가 부족할 수 있음.
- 따라서, 하드디스크를 이용하여 가상메모리를 늘리면 좋음.
- 또한, Jar 실행 시 메모리 제한을 두는 것도 하나의 방법.

  #### 스와핑

  ```linux
  ## 스왑 파일 생성.
  ## bs = 블록 크키, count = 블록 수. 블록 크기는 인스턴스에서 사용 가능한 메모리보다 작아야함.
  sudo dd if=/dev/zero of=/swapfile bs=128M count=16

  ##읽기 및 쓰기 권한 업데이트
  sudo chmod 600 /swapfile

  ## 스왑 영역 설정
  sudo mkswap /swapfile

  ## 스왑 공간에 스왑 파일 추가하여 스왑 파일 즉시 사용할 수 있도록 설정
  sudo swapon /swapfile

  ## 성공 여부 확인
  sudo swapon -s

  ## 파일 편집하여 부팅 시 스왑 파일 활성화
  sudo vi /etc/fstab
  ## 맨 아래에 추가
  /swapfile swap swap defaults 0 0
  ```

  #### jar 메모리 제한

  ```linux
  java -jar -Xms512M -Xmx512M fast-automl-0.0.1-SNAPSHOT.jar
  ```

</details>

<details>
    <summary>백그라운드 실행(로그 포함)</summary>

- 백그라운드 실행 명령어 &
- &만 사용 할 경우 사용자 세션 만료 시 백그라운드 종료(현재는 유지됨.) 로그x
- nohup 사용 시 로그 관리 가능하며 세션 만료해도 유지됨.

```linux
## 백그라운드 nohup 실행. (실행 후 문구가 뜸. 그냥 1 엔터 치고 실행 됐는지 확인. default는 표준 출력 로그 쌓임.)
nohup java -jar my-app.jar &

## 확인 방법 2개
ps auxf | grep java
bg

## 로그 조회 (nohup.out은 명령어 입력한 경로에 생성되어 있음.)
cat nohup.out

## 로그 테일링
tail -f nohup.out

## 백그라운드 종료 (ps auxf로 조회하여 나온 PID 필요함.)
kill -9 PID

## 로그를 내가 원하는 곳에 쌓고 싶은 경우
nohup java -jar my-app.jar > 경로/파일명.out &

## 표준 출력과 표준 에러를 다른 파일에 쌓고 싶은 경우
nohup java -jar my-app.jar 1 > 경로/출력_파일명.out 2 > 경로/에러_파일명.out &

## 하나의 파일에 출력과 에러를 둘 다 하나의 파일에 쌓고 싶은 경우
nohup java -jar my-app.jar > 경로/통합_파일명.out 2>&1 &

```

</details>

<details>
    <summary>CI/CD</summary>

- [CI/CD 정리](https://github.com/away0419/CI-CD)

</details>
