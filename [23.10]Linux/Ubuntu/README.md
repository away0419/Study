> ## Ubuntu

- Linux 배포판.
- GUI (그래픽 사용자 인터페이스)와 CLI (명령 행 인터페이스)가 있음.
- APT 기반 패키지 관리 도구 추가 가능.

<br>
<br>

> ## 설치 & 명령어

<details>
  <summary>사용자</summary>

- 사용자 추가

  ```ubuntu
  # 이후 나오는 질문은 엔터로 넘어가도 무방
  # 사용자 추가하면 /home/사용자이름 폴더가 생성됨.
  sudo adduser 사용자이름
  ```

- sudo 권한 부여

  ```ubuntu
  sudo usermod -aG sudo 사용자이름
  ```

- 사용자 확인

  ```ubuntu
  id 사용자이름
  ```

- 사용자 로그인

  ```ubuntu
  su 사용자이름
  ```

- 사용자 로그인 시 사용될 key 등록

  ```ubuntu
  # 사용자 로그인 후 해당 홈 폴더에 .ssh 폴더 생성
  mkdir ~/.ssh

  # 사용자 홈 폴더에 .ssh 폴더 권한 설정
  chmod 700  ~/.ssh

  # 프라이빗키로 퍼블릭키 생성
  ssh-keygen -y -f 프라이빗키.pem > 퍼블릭키.pub

  # 사용자 홈 폴더에 .ssh 폴더 내에 authorized_keys 파일 생성

  # 퍼블릭키 내용을 authorized_keys 파일 생성 후 내용 추가 (nano 에디터 또는 vim 에디터 사용)
  nano ~/.ssh/authorized_keys

  # authorized_keys 파일 권한 설정
  chmod 600 ~/.ssh/authorized_keys

  # 소유권 변경 (만약 다른 사용자로 진행한 경우)
  sudo chown -R 사용자이름:사용자이름 home/사용자이름/.ssh
  ```

- 비밀번호 없이 sudo 사용 설정 방법

  ```ubuntu
  # 먼저 sudo 권한이 있는 사용자로 로그인
  sudo su ubuntu

  # sudoers 파일 수정
  sudo visudo

  # 맨 아래 내용 추가
  username ALL=(ALL) NOPASSWD: ALL

  # username 로그인 후 확인
  su username
  sudo ls /root
  ```

</details>

<details>
    <summary>패키지 다운로드</summary>

- 최신 패키지를 다운 받기 위해 APT 업데이트.

  ```ubuntu
  sudo apt update
  ```

- Ubuntu 버전에 따라 APT 명령어 차이 있음.
  ```ubuntu
  apt 패키지명
  apt-get 패키지명
  ```

</details>

<details>
    <summary>방화벽</summary>

- 여러 방법이 존재함. Ubuntu 버전에 따라 적용이 안될 수 있음.
- 만약 AWS, Oracle Cloud 등 클라우드 서비스를 이용하는 경우 클라우드 서비스 내에서 방화벽 설정이 가능하며, 리눅스 명려어로 방화벽을 설정해도 클라우드 서비스 내에서 방화벽 설정이 우선됨.

<br/>

- ufw (간단한 방화벽 설정)

  ```ubuntu
  # 상태 확인. ufw는 설치되어 있을 확률이 큼.
  sudo ufw status

  # ufw 활성화. 활성화 후 모든 인바운드 트래픽 차단하고 아웃 바운드 트래픽은 허용함.
  sudo ufw enable

  # 기본 정책 설정. 인바운드 트래픽 차단.
  sudo ufw default deny incoming

  # 기본 정책 설정. 아웃 바운드 트래픽 허용.
  sudo ufw default allow outgoing

  # 특정 포트 열기. 포트 22, 80, 443 포트는 프로토콜 생략 가능.
  sudo ufw allow 포트번호/프로토콜

  # 특정 포트 닫기. 포트 22, 80, 443 포트는 프로토콜 생략 가능.
  sudo ufw deny 포트번호/프로토콜

  # 특정 IP 특정 포트만 접근 허용. to any port 포트번호 생략 시 모든 포트 허용.
  sudo ufw allow from IP주소 to any port 포트번호

  # 특정 IP 특정 포트만 접근 차단. to any port 포트번호 생략 시 모든 포트 차단.
  sudo ufw deny from IP주소 to any port 포트번호

  # IP 포트 허용 범위 지정.
  sudo ufw allow from IP주소 to any port 포트번호:포트번호/프로토콜

  # IP 포트 차단 범위 지정.
  sudo ufw deny from IP주소 to any port 포트번호:포트번호/프로토콜

  # 방화벽 로그 활성화
  sudo ufw logging on

  # 포트 규칙 삭제
  sudo ufw delete allow 포트번호/프로토콜

  # 규칙 초기화
  sudo ufw reset

  # 현재 규칙 확인
  sudo ufw status verbose

  # 방화벽 비활성화
  sudo ufw disable

  ```

- firewall (복잡한 방화벽 설정). 영역 개념을 중심으로 방화벽 관리.

  ```ubuntu
  # 영역: public, trusted, internal, external, dmz
  # 서비스: 미리 정의된 포트 집합. ex) http, ssh, mysql 등
  # firewall 설치
  # 영구 설정과 임시 설정 분리 관리함.

  # 설치
  sudo apt install firewalld

  # 상태 확인
  sudo firewall-cmd --state

  # firewall 시작/중지/재시작
  sudo systemctl start firewalld
  sudo systemctl stop firewalld
  sudo systemctl restart firewalld

  # 사용 가능한 영역 목록 확인
  firewall-cmd --get-zones

  # 현재 영역 확인
  firewall-cmd --get-active-zones

  # 현재 영역 변경
  sudo firewall-cmd --set-default-zone=public

  # 서비스 목록 확인
  firewall-cmd --get-services

  # 모든 포트 닫기 (기본설정)
  sudo firewall-cmd --zone=public --set-target=DROP

  # 특정 포트 열기 규칙 추가.
  sudo firewall-cmd --permanent --zone=public --add-port=80/tcp

  # 특정 포트 닫기 규칙 추가
  sudo firewall-cmd --permanent --zone=public --remove-port=80/tcp

  # 특정 서비스 열기 규칙 추가
  sudo firewall-cmd --permanent --zone=public --add-service=http

  # 특정 서비스 닫기 규칙 추가
  sudo firewall-cmd --permanent --zone=public --remove-service=http

  # 추가한 규칙 적용 하는 초기화. 영구 설정(--permanent) 반영시 리로드 필요
  sudo firewall-cmd --reload

  # 현재 규칙 확인
  sudo firewall-cmd --list-all

  # 특정 영역 규칙 확인
  sudo firewall-cmd --zone=public --list-all

  # 설정 초기화 (임시 설정만)
  sudo firewall-cmd --complete-reload

  # 설정 초기화 (영구 설정 포함)
  sudo rm -rf /etc/firewalld/zones/*
  sudo firewall-cmd --reload

  ```

</details>

<details>
    <summary>시간</summary>

- 클라우드 사용시 해당 서버의 지역이 우리나라와 다를 경우 설정 필요함.

  ```ubuntu
  # 현재 시간 확인
  timedatectl

  # 서울로 변경
  sudo timedatectl set-timezone Asia/Seoul
  ```

</details>

<details>
    <summary>자바</summary>

- JDK 설치

  ```ubuntu
  # 운영체제에 기본으로 있는 jdk 설치, 또는 원하는 버전 설치
  sudo apt install default-jdk
  sudo apt-get install openjdk-11-jdk

  # 설치 확인
  java -version
  javac -version
  ```

  <br/>

- 환경 변수 설정

  ```ubuntu
  # 환경 변수 설정
  sudo vim /etc/profile

  # 맨 아래에 추가
  ...
  export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64      // 본인의 자바 설치 경로
  export PATH=$JAVA_HOME/bin:$PATH
  export CLASSPATH=$CLASSPATH:$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/tools.jar
  ...

  # 확인
  source /etc/profile
  echo $JAVA_HOME
  ```

  </details>

<details>
    <summary>Docker</summary>

- Docker 설치 및 실행

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
  ```

<br/>

- Docker 삭제

  ```ubuntu
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

<br/>

- Docker Image Build
  ```ubuntu
  docker build -t portfolio:1.0 /home/ubuntu/about-me/
  ```
  - `-t`: 태그를 뜻하며 이미지이름:태그 이다.
  - `경로`: 이미지로 만들 Dockerfile이 있는 경로

<br/>

- Docker Container 실행
  ```ubuntu
  docker run -m 512m --name jenkins-docker -d -p 8000:8080 -p 8888:50000 -v /home/jenkins:/var/jenkins_home -u root jenkins/jenkins:lts
  ```
  - `-m`: docker에 할당할 최대 메모리를 설정한다.
  - `--name`: 실행될 컨테이너의 이름을 jenkins-docker으로 설정한다.
  - `d`: detached mode, 백그라운드에서 컨테이너가 실행되게 한다.
  - `p`: 서버의 8000포트와 컨테이너 내부 8080포트를 연결한다.
  - `v`: 서버의 `/home/jenkins`경로와 컨테이너 내부 `/var/jenkins_home`경로를 마운트한다. 이것을 하는 이유는, Jenkins 설치 시 ssh 키값 생성, 저장소 참조 등을 용이하게 하기 위함입니다.
  - `u`: 실행할 사용자를 root으로 설정한다.

<br/>

- 기타 명령어
  1. [주요 명령어](https://captcha.tistory.com/49)
  2. [도커 삭제 명령어](https://www.lainyzine.com/ko/article/docker-rm-removing-docker-containers/)

<br/>

- Dockerfile

  - docker image를 만들기 위한 파일.
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

<br/>

- Docker-Compose 설치

  ```ubuntu
  #설치
  sudo curl -L https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

  #권환
  sudo chmod +x /usr/local/bin/docker-compose

  #버전확인
  docker-compose --version
  ```

<br/>

- Docker-Compose 명령어
  1. [주요 명령어](https://kimjingo.tistory.com/108)
  2. [간단 문법](https://darrengwon.tistory.com/793)

</details>

<details>
    <summary>Nginx</summary>

- nginx 설치 및 실행

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

  # docker로 설치하기
  docker pull nginx

  # docker로 실행하기
  docker run -i -d --name 도커별칭 -p 클라우드포트번호:도커포트번호 -v 공유하려는 클라우드 폴더 경로:공유받으려는 도커의 폴더 경로
  ```

<br/>

- nginx 제거
  ```ubuntu
  # 제거
  sudo apt remove nginx
  sudo apt purge nginx
  ```

<br/>

- nginx 명령어

  ```ubuntu
  ubuntu # nginx 실행
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
    <summary>Letsencrypt</summary>

- HTTPS 사용을 위한 letsencrypt 설치
  ```ubuntu
  # https 설정을 위한 툴 설치
  sudo apt-get install letsencrypt -y
  ```

<br/>

- 인증서 발급

  ```ubuntu
  # nginx 중단
  sudo service nginx stop

  # certbot 발급을 위한 80, 443 방화벽 열기
  # certbot 이메일 입력, 인증서 발급 동의, 이메일 수신은 미동의
  sudo certbot certonly --standalone -d 도메인(example.com)
  ```

  <br/>

- Nginx 인증서 설정

  ```ubuntu
  # nginx 설정 파일 오픈
  sudo vim /etc/nginx/sites-available/default

  ...
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
      root 기본 index가 있는 경로;

      location / {
          root index가 있는 경로;
          try_files $uri $uri/ @router;
      }

      location @router{
          rewrite ^(.+)$ /index.html last;
      }

      location /api{
          proxy_pass http://13.125.39.100:8061;
          proxy_http_version 1.1;
          proxy_set_header Upgrade $http_upgrade;
          proxy_set_header Connection "Upgrade";
          proxy_set_header Host $host;
          proxy_set_header X-Forwarded-For $remote_addr;
          proxy_set_header X-Forwarded-Proto $scheme;
      }

      location /chat{
          proxy_pass http://13.125.39.100:8011;
      }

      ssl_certificate /etc/letsencrypt/live/도메인/fullchain.pem; # managed by Certbot
      ssl_certificate_key /etc/letsencrypt/live/도메인/privkey.pem; # managed by Certbot
      listen 443 ssl; # managed by Certbot
  }
  ...

  # nginx 설정 제대로 되었는지 테스트
  sudo nginx -t

  # nginx 재시작
  sudo service nginx restart

  # ubuntu일 때, 도메인 접속 시 500 에러가 난다면 해당 파일 열어서 맨 윗줄의 user 변경
  sudo vim /etc/nginx/nginx.conf

  '''
  user ubuntu;
  '''
  ```

<br>

- 실제 설정 예시

  ```ubuntu
  ...
  # Nginx front 배포 실제 예시
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


  # API-gateway 기능 추가 활용한 방식
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

  ```

<br>

- certbot 명령어

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
    <summary>nvm, node, npm</summary>

- nvm 설치

  ```ubuntu
  # curl 설치 (기본으로 설치되어있음)
  sudo apt install build-essential curl

  # nvm 설치
  $ curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.36.0/install.sh | bash

  # nvm 설정 리로드 (설정 파일은 여러가지 일 수 있으니 있는 파일은 다해주면 됨)(~/.bash_profile, ~/.zshrc, ~/.profile, ~/.bashrc )
  source ~/.bashrc
  ```

<br/>

- node 설치

  ```ubuntu
  # node 설치 (sudo는 사용하지 말것. 나중에 권한 문제 발생함.)
  nvm install node

  # node 특정 버전 설치
  nvm install x.x.x

  # node 버전 전환
  nvm use x.x.x

  #### nvm 없이 node, npm 설치 하기 (8.x는 node 버전)
  curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
  sudo apt-get install -y nodejs
  ```

<br/>

- npm 설치

  ```ubuntu
  # npm 설치 (node 설치 시 자동 설치 됨)
  sudo apt install npm

  ```

<br/>

- 삭제

  ```ubuntu
  # nvm 삭제
  rm -rf ./nvm

  # node 특정 버전삭제
  nvm uninstall x.x.x

  # npm 삭제
  sudo apt remove npm

  # npm 설정 파일까지 삭제
  sudo apt purge npm
  ```

  </details>

<details>
    <summary>메모리 관리</summary>

- 프리티어 사용 시 메모리가 부족할 수 있음.
- 따라서, 하드디스크를 이용하여 가상메모리를 늘리면 좋음.
- 또한, Jar 실행 시 메모리 제한을 두는 것도 하나의 방법.

<br/>

- 스와핑

  ```ubuntu
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

<br/>

- jar 메모리 제한
  ```ubuntu
  java -jar -Xms512M -Xmx512M fast-automl-0.0.1-SNAPSHOT.jar
  ```

</details>

<details>
    <summary>백그라운드 실행(로그 포함)</summary>

- 백그라운드 실행 명령어 &
- &만 사용 할 경우 사용자 세션 만료 시 백그라운드 종료(현재는 유지됨.) 로그x
- nohup 사용 시 로그 관리 가능하며 세션 만료해도 유지됨.

  ```ubuntu
  # 백그라운드 nohup 실행. (실행 후 문구가 뜸. 그냥 1 엔터 치고 실행 됐는지 확인. default는 표준 출력 로그 쌓임.)
  nohup java -jar my-app.jar &

  # 확인 방법 2개
  ps auxf | grep java
  bg

  # 로그 조회 (nohup.out은 명령어 입력한 경로에 생성되어 있음.)
  cat nohup.out

  # 로그 테일링
  tail -f nohup.out

  # 백그라운드 종료 (ps auxf로 조회하여 나온 PID 필요함.)
  kill -9 PID

  # 로그를 내가 원하는 곳에 쌓고 싶은 경우
  nohup java -jar my-app.jar > 경로/파일명.out &

  # 표준 출력과 표준 에러를 다른 파일에 쌓고 싶은 경우
  nohup java -jar my-app.jar 1 > 경로/출력_파일명.out 2 > 경로/에러_파일명.out &

  # 하나의 파일에 출력과 에러를 둘 다 하나의 파일에 쌓고 싶은 경우
  nohup java -jar my-app.jar > 경로/통합_파일명.out 2>&1 &
  ```

</details>

<details>
    <summary>Git</summary>

- git 설치

  ```linux
  # git 설치 및 확인
  sudo apt install git
  git --version

  # git 구성 및 확인
  git config --global user.name "Your Name"
  git config --global user.email "youremail@yourdomain.com"
  git config --list
  ```

<br/>

- github clone (private)

  ```ubuntu
  # github에서 personal access token 생성하고 이를 이용하여 clone (선택1)
  git clone https://access*token@repository*주소

  # Credential 정보 저장 (선택2)
  git config credential.helper store --global

  # Credential 캐시 저장 (선택3)
  git config credential.helper 'cache --timeout=3600'
  ```

  </details>

<details>
  <summary>SCP</summary>

- SSH 간 파일 통신
- 배포할 때 사용 가능

<br/>

- SCP 설치

  ```ubuntu
  # 파일 복사
  scp /home/banana/test.txt lee@192.168.1.19:/home/lee/test.txt

  # 폴더 복사
  scp -r /home/banana lee@192.168.1.19:/home/lee
  ```

<br/>

- SCP 옵션

  ```ubuntu
  scp <source_path> <username>@<ip>:<dest_path>
  ```

  - source_path : 복사하고자 하는 파일 경로.
  - username : 목적지 서버 사용자 계정 아이디.
  - ip : 목적지 서버 IP주소 또는 도메인 이름.
  - dest_path : 목적지 파일 저장 경로.

</details>
