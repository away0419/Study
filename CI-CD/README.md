### CI/CD 란

- 애플리케이션 개발 단계 자동화이며 지속적인 통합, 서비스 제공, 배포를 통해 개발 및 운영팀에서 발생하는 문제를 해결하기 위한 솔루션.
- CI (Continuous Delivery) : 지속적인 통합
  - 코드의 변경 사항을 통합하고 빌드 및 테스트 과정까지 자동으로 진행하고 지속적으로 통합하여 코드 충돌을 방어하고 효율을 늘리는 방법.
- CD (Continuous Delivery OR Continuous Deployment) : 지속적인 서비스 제공, 지속적인 배포
  - CI 이후의 코드를 자동으로 배포.
  - 지속적으로 배포하여 서비스가 지속적으로 제공 될 수 있도록 함.

<br/>

### 가장 많이 사용 되는 도구

- Jenkins
- CircleCI
- TravisCI
- Github Actions

<br/>

### CI/CD 과정 (feat. GitHub Action)

1. GitHub 소스 Push. (CI)
2. GitHub Action Server 실행. (CI)
3. Build Test 진행. (CI)
4. Build 완료 된 파일 실제 서버에 전송. (CD)
5. 해당 서버에서 프로젝트 실행. (CD)

<br/>

### 실제 서버에 Build 완료 된 파일 전송하는 방법

- 실제 서버에 직접 전송 : SSH-SCP-SSH ([사용법](https://github.com/away0419/Study-2023/tree/main/Linux/Ubuntu/#명령어))
- 중간 서버를 거쳐 전송 : AWS S3, Oracle Cloud Bucket
- Docker 이용한 전송 : Docker Compose, Docker Hub, Docker image, Dokcerfile
