> ## Container

- 어플리케이션과 그걸 실행하기 위한 모든 환경을 하나로 묶은 실행 단위.
- 어떤 환경에서도 동일한 결과를 보장. (이식성)
- 가상머신 처럼 동작하지만, 호스트 OS 커널을 공유하여 훨씬 빠르고 효율적. (경량성)
- 각 컨테이너는 서로 독립된 프로세스로 실행. (격리성)

<br/>
<br/>

> ## Docker

- 컨테이너 기술을 이용한 컨테이너 기반의 오픈소스 가상화 플랫폼으로 컨테이너의 특성을 가짐.
- 이미지, 컨테이너, 파일로 구성되어 있음.

<br/>
<br/>

> ## Docker 구성

<details>
    <summary>Docker Image</summary>

- 컨테이너를 실행하기 위한 모든 요소를 포함한 템플릿. (컨테이너 설계도)
- 실행 가능한 애플리케이션의 설치파일, 실행파일, 설정정보, 의존 라이브러리, OS 파일 시스템 등을 모두 포함하고 있음.
- Dockerfile을 이용해 생성 가능하며 로컬에 저장됨.
- 한번 만들어진 이미지는 변경되지 않음. (불변)
- 여러 개의 계층으로 구성되어 재사용성과 캐싱 효율성을 높임. (레이어 구조)
- 이미지에 태그를 붙여 다양한 버젼 관리 가능. (버전관리)
- 어디서든 실행 가능함. (이식성)
- 이미지 저장소를 통해 공유 가능. (공유성)

</details>

<details>
    <summary>Docker Container</summary>

- 이미지를 실제로 실행한 인스턴스.
- 이미지로부터 생성되며, 실행 중인 애플리케이션의 상태를 포함.
- 각 컨테이너는 고유한 ID와 이름을 가짐.
- 컨테이너는 격리된 환경에서 실행되어 다른 컨테이너나 호스트 시스템에 영향을 주지 않음.
- 컨테이너는 시작, 중지, 재시작, 삭제 등의 생명주기 관리가 가능.
- 컨테이너 간 통신은 네트워크를 통해 이루어짐.
- 호스트 시스템의 리소스(CPU, 메모리, 디스크 등)를 제한하여 사용 가능.
- 컨테이너의 상태 변경은 컨테이너 레이어에만 저장되며, 이미지에는 영향을 주지 않음.

</details>

<details>
    <summary>Docker Registry</summary>

- Docker 이미지를 저장하고 배포하는 저장소.
- Docker Hub는 가장 대표적인 공개 레지스트리.
- 개인/기업용 프라이빗 레지스트리 구축 가능.
- 이미지의 버전 관리와 배포를 용이하게 함.
- 보안을 위한 인증/인가 기능 제공.

</details>

<details>
    <summary>Dockerfile</summary>

- Docker 이미지를 생성하기 위한 설정 파일.
- 이미지 생성에 필요한 모든 명령어를 순차적으로 기록.
- 기본 이미지 지정, 파일 복사, 명령어 실행, 환경 변수 설정 등 정의.
- 각 명령어는 새로운 레이어를 생성.
- 빌드 컨텍스트를 통해 이미지 생성에 필요한 파일들을 관리.

</details>

<br/>
<br/>

> ## Docker 심화 개념

<details>
    <summary>Docker 네트워크</summary>

- **Bridge Network**: 기본 네트워크 드라이버로, 컨테이너 간 통신을 위한 가상 네트워크

  - 기본적으로 docker0 브릿지 인터페이스 사용
  - 컨테이너는 자동으로 이 네트워크에 연결됨
  - 컨테이너 간 통신이 가능하며, 호스트와도 통신 가능
  - 포트 매핑을 통해 외부에서 접근 가능

- **Host Network**: 호스트의 네트워크를 직접 사용하는 방식

  - 컨테이너가 호스트의 네트워크 스택을 직접 사용
  - 네트워크 성능이 가장 좋음
  - 포트 매핑이 필요 없음
  - 호스트의 모든 네트워크 인터페이스에 직접 접근

- **Overlay Network**: 여러 Docker 호스트 간의 통신을 위한 네트워크

  - Docker Swarm에서 사용되는 네트워크
  - 여러 호스트에 분산된 컨테이너 간 통신 지원
  - 암호화된 통신 지원
  - 서비스 디스커버리 기능 제공
  - 실제 사용 예시:
    - 서버 A의 웹 컨테이너와 서버 B의 DB 컨테이너 간 통신
    - 여러 서버에 분산된 마이크로서비스 간 통신
    - 로드밸런서가 있는 다중 서버 환경에서의 컨테이너 통신
  - 작동 방식:
    - 각 호스트의 Docker 엔진이 VXLAN 터널을 통해 통신
    - 컨테이너는 마치 같은 네트워크에 있는 것처럼 통신
    - 호스트 간 물리적 네트워크를 추상화하여 단일 가상 네트워크 제공

- **Macvlan Network**: 컨테이너에 MAC 주소를 할당하여 물리적 네트워크와 직접 통신

  - 컨테이너가 물리적 네트워크에 직접 연결된 것처럼 동작
  - 고정 IP 할당 가능
  - 네트워크 성능이 우수
  - VLAN 지원

- **None Network**: 네트워크를 비활성화하는 옵션
  - 컨테이너가 네트워크에 접근할 수 없음
  - 완전한 격리가 필요한 경우 사용
  - 보안이 중요한 환경에서 활용

</details>

<details>
    <summary>Docker 볼륨</summary>

- **Named Volumes**: Docker가 관리하는 영구 저장소

  - Docker 엔진에 의해 관리되는 영구 저장소
  - 컨테이너가 삭제되어도 데이터 유지
  - 여러 컨테이너에서 공유 가능
  - 백업과 마이그레이션이 용이
  - 볼륨 드라이버를 통한 다양한 스토리지 옵션 지원

- **Bind Mounts**: 호스트 시스템의 특정 경로를 컨테이너에 마운트

  - 호스트의 파일시스템을 컨테이너에 직접 마운트
  - 실시간으로 파일 변경 가능
  - 개발 환경에서 코드 수정 즉시 반영
  - 호스트의 기존 디렉토리 구조 활용
  - 절대 경로나 상대 경로 사용 가능

- **tmpfs Mounts**: 메모리에 임시 파일시스템 생성

  - 컨테이너의 메모리에 임시 파일시스템 생성
  - 컨테이너가 중지되면 데이터 삭제
  - 성능이 중요한 임시 데이터 저장에 적합
  - 호스트의 파일시스템을 사용하지 않음
  - 보안이 중요한 임시 데이터 처리에 유용

- **Volume Drivers**: 다양한 스토리지 시스템 지원 (NFS, S3 등)

  - 다양한 스토리지 백엔드 지원
  - 클라우드 스토리지 통합
  - 분산 스토리지 시스템 활용
  - 고가용성 스토리지 구성
  - 스토리지 특성에 따른 최적화

- **볼륨 컨테이너**: 여러 컨테이너가 공유하는 볼륨을 관리하는 전용 컨테이너
  - 공유 볼륨을 전용 컨테이너로 관리
  - 데이터 지속성 보장
  - 볼륨 백업과 복원 용이
  - 여러 컨테이너 간 데이터 공유
  - 볼륨 라이프사이클 관리

</details>

<details>
    <summary>Docker Compose</summary>

- 여러 컨테이너를 정의하고 실행하기 위한 도구
- YAML 파일을 사용하여 서비스 구성
- 서비스 간 의존성 관리
- 환경 변수 관리
- 볼륨과 네트워크 설정
- 스케일링과 업데이트 관리

</details>

<details>
    <summary>Docker Swarm</summary>

- Docker의 네이티브 클러스터링 솔루션
- 여러 Docker 호스트를 단일 가상 호스트로 관리
- 서비스 기반 배포 및 스케일링
- 자동 로드 밸런싱
- 서비스 디스커버리
- 롤링 업데이트와 롤백

</details>

<details>
    <summary>Docker 보안</summary>

- **컨테이너 격리**: 리소스 제한과 네임스페이스 격리
- **이미지 보안**: 취약점 스캔과 신뢰할 수 있는 이미지 사용
- **시크릿 관리**: 민감한 정보의 안전한 관리
- **접근 제어**: 사용자 권한과 네트워크 정책
- **보안 감사**: 컨테이너 활동 모니터링

</details>

<details>
    <summary>Docker 최적화</summary>

- **멀티 스테이지 빌드**: 이미지 크기 최소화
- **레이어 최적화**: 캐시 활용과 레이어 수 최소화
- **리소스 제한**: CPU, 메모리, 디스크 I/O 제한
- **이미지 태그 관리**: 버전 관리와 가비지 컬렉션
- **빌드 컨텍스트 최적화**: .dockerignore 활용

</details>

<br/>
<br/>

> ## Docker 사용 시 주의사항

<details>
    <summary>보안 관련 주의사항</summary>

- **이미지 보안**

  - 공식 이미지나 신뢰할 수 있는 이미지 사용
  - 정기적인 취약점 스캔 수행
  - 최신 버전의 이미지 사용
  - 불필요한 패키지 설치 지양

- **컨테이너 보안**

  - root 사용자로 실행하지 않기
  - 필요한 최소한의 권한만 부여
  - 호스트 시스템의 민감한 디렉토리 마운트 주의
  - 컨테이너 간 격리 유지

- **네트워크 보안**
  - 필요한 포트만 노출
  - 내부 네트워크 사용 시 적절한 격리
  - 민감한 정보는 환경 변수나 시크릿으로 관리
  - 네트워크 정책 설정

</details>

<details>
    <summary>성능 관련 주의사항</summary>

- **리소스 관리**

  - CPU, 메모리 제한 설정
  - 디스크 I/O 제한 고려
  - 네트워크 대역폭 제한
  - 컨테이너 수와 호스트 리소스 밸런싱

- **이미지 최적화**

  - 멀티 스테이지 빌드 활용
  - 불필요한 레이어 최소화
  - .dockerignore 파일 사용
  - 이미지 크기 최소화

- **스토리지 관리**

  - 볼륨 사용으로 데이터 지속성 보장
  - 디스크 공간 모니터링
  - 주기적인 가비지 컬렉션
  - 백업 전략 수립
  - 컨테이너 데이터 관리:
    - 컨테이너 삭제 시 자동 정리되지 않는 데이터 주의
    - 사용하지 않는 볼륨 정기적 삭제
    - dangling 이미지 정리 (`docker image prune`)
    - 중지된 컨테이너 정리 (`docker container prune`)
    - 사용하지 않는 네트워크 정리 (`docker network prune`)
    - 전체 시스템 정리 (`docker system prune -a`)

- **디스크 공간 관리**
  - 정기적인 디스크 사용량 모니터링
  - 로그 파일 크기 제한 설정
  - 불필요한 이미지와 컨테이너 정리
  - 볼륨 사용량 모니터링
  - 디스크 공간 부족 시 대응 방안:
    - 오래된 로그 파일 정리
    - 사용하지 않는 이미지 삭제
    - dangling 이미지 정리
    - 불필요한 볼륨 삭제
    - 컨테이너 로그 로테이션 설정

</details>

<details>
    <summary>운영 관련 주의사항</summary>

- **모니터링**

  - 컨테이너 상태 모니터링
  - 리소스 사용량 추적
  - 로그 수집 및 관리
  - 알림 설정

- **업데이트 관리**

  - 정기적인 이미지 업데이트
  - 롤백 전략 수립
  - 무중단 배포 고려
  - 버전 관리 철저히

- **장애 대응**
  - 자동 복구 설정
  - 장애 전파 방지
  - 백업 및 복구 절차 수립
  - 재해 복구 계획 수립

</details>

<details>
    <summary>개발 관련 주의사항</summary>

- **개발 환경**

  - 로컬 개발 환경 표준화
  - Docker Compose 활용
  - 개발-테스트-운영 환경 일관성 유지
  - 의존성 관리

- **테스트**

  - 컨테이너 기반 테스트 환경 구축
  - 통합 테스트 자동화
  - 성능 테스트 수행
  - 보안 테스트 정기화

- **문서화**
  - Dockerfile 문서화
  - 실행 방법 명시
  - 환경 변수 설명
  - 트러블슈팅 가이드 작성

</details>

<br/>
<br/>

> ## Ubuntu에서 Docker 설치 및 실행

<details>
    <summary>Docker 설치 과정</summary>

1. **이전 버전 제거 (선택사항)**

```bash
sudo apt-get remove docker docker-engine docker.io containerd runc
```

2. **필요한 패키지 설치**

```bash
sudo apt-get update
sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
```

3. **Docker의 공식 GPG 키 추가**

```bash
sudo mkdir -m 0755 -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
```

4. **Docker 리포지토리 설정**

```bash
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

5. **Docker 엔진 설치**

```bash
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

6. **Docker 서비스 시작 및 자동 시작 설정**

```bash
sudo systemctl start docker
sudo systemctl enable docker
```

7. **현재 사용자를 docker 그룹에 추가 (선택사항)**

```bash
sudo usermod -aG docker $USER
# 변경사항 적용을 위해 재로그인 필요
```

</details>

<details>
    <summary>Docker 실행 및 테스트</summary>

1. **Docker 설치 확인**

```bash
docker --version
docker compose version
```

2. **테스트 컨테이너 실행**

```bash
sudo docker run hello-world
```

3. **기본적인 Docker 명령어**

```bash
# 이미지 목록 확인
docker images

# 실행 중인 컨테이너 확인
docker ps

# 모든 컨테이너 확인 (중지된 것 포함)
docker ps -a

# 컨테이너 중지
docker stop [컨테이너ID]

# 컨테이너 삭제
docker rm [컨테이너ID]

# 이미지 삭제
docker rmi [이미지ID]
```

</details>

<details>
    <summary>Docker Compose 사용</summary>

1. **Docker Compose 확인**

```bash
# Docker Compose 버전 확인
docker compose version
```

2. **기본적인 Docker Compose 사용**

```bash
# 서비스 시작
docker compose up -d

# 서비스 상태 확인
docker compose ps

# 서비스 중지
docker compose down
```

3. **Docker Compose 파일 예시**

```yaml
version: "3"
services:
  web:
    image: nginx:latest
    ports:
      - "80:80"
  db:
    image: mysql:latest
    environment:
      MYSQL_ROOT_PASSWORD: example
```

</details>

<details>
    <summary>문제 해결</summary>

1. **권한 문제 발생 시**

```bash
# docker 그룹에 사용자 추가
sudo usermod -aG docker $USER

# docker 서비스 재시작
sudo systemctl restart docker
```

2. **디스크 공간 부족 시**

```bash
# 사용하지 않는 리소스 정리
docker system prune -a

# 볼륨 정리
docker volume prune
```

3. **네트워크 문제 발생 시**

```bash
# Docker 네트워크 재설정
sudo systemctl restart docker
```

</details>

<br/>
<br/>
