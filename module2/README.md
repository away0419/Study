# 계정 보안

## 보안 추체 및 자격 증명
<details>
    <summary>AWS 계정 루트 사용자</summary>

![Alt text](image/image.png)
- AWS 계정을 처음 생성할 때는 루트 사용자로 시작.
- 루트 사용자는 모든 AWS 서비스 및 리소스에 대한 전체 액세스 권한을 가짐.
- 일상적인 상호 작용에는 루트 계정 보안 인증 정보를 사용하지 않아야 함.
- 일상 태스크를 위한 사용자를 생성해야 함.
- 먼저 관리자에 해당하는 사용자를 생성 한 뒤, 이후 사용자는 관리자로 관리해야 함.

</details>

<details>
    <summary>AWS Identity and Access Management (IAM)</summary>

![Alt text](image/image-1.png)
- AWS 리소스에 대한 액세스를 안정하게 제어하는데 도움이 되는 웹 서비스
- IAM 사용하여 리소스를 사용하도록 인증 및 권한 부여된 대상을 제어함.
- 리소스를 기반으로 하며 누가 어떤 API 호출에 대한 권한이 있는지 정의하는데 도움이 됨.

</details>

<details>
    <summary>보안 주체</summary>

![Alt text](image/image-2.png)
- AWS 리소스에 대한 작업 또는 운영을 요청할 수 있는 엔터티.
- AWS 서비스, Security Assertion Markup Language 2.0(SAML 2.0) 제공업체, ID제공업체(idP) 등.

</details>

<details>
    <summary>IAM 사용자</summary>

![Alt text](image/image-3.png)
![Alt text](image/image-5.png)
- 새로운 IAM 사용자는 작업 수행 권한이 없음.
- 개별 IAM 사용자를 생성하는 경우 권한을 부여해야 함.
- 사용자에게 필요한 자격 증명만 생성해야함.

</details>

<details>
    <summary>프로그래밍 방식 액세스</summary>

![Alt text](image/image-4.png)
- AWS CLI 또는 AWS SDK에서 API 호출을 수행하는데 필요한 자격 증명을 IAM 사용자에게 제공 가능.
- AWS는 Java, Python, .Net 등의 프로그래밍 언어용 SDK 제공.
- 권한을 부여할 경우 액세스 키 ID 및 비밀 액세스 키로 구성된 고유한 키 페어가 생성됨. 이를 사용하여 AWS CLI를 구성하거나 AWS SDK를 통해 API 호출 가능.
- AWS CLI를 설정하는데 필요한 4가지 요소.
    - AWS 액세스 키 ID
    - AWS 비밀 액세스 키
    - 기본 리전 이름
    - 기본 출력 형식(json, yaml, text, table)

</details>

<details>
    <summary>IAM 사용자 그룹</summary>

![Alt text](image/image-6.png)
- IAM 사용자 그룹은 IAM 사용자의 모음.
- 사용자 그룹에서는 한번에 여러 사용자의 권한을 지정할 수 있음.
- 하나의 사용자가 다수 그룹의 구성원이 될 수 있으며 두 그룹의 권한이 모두 부여됨.

</details>

<details>
    <summary>IAM 역할</summary>

![Alt text](image/image-7.png)
- IAM 역할은 임시 AWS 자격 증명이 제공.
- IAM 역할을 수임하는 IAM 사용자는 기존 그룹의 권한을 잃어버리고 IAM 역할이 제공하는 권한만 제공됨.
- IAM 역할을 사용하는 방법
    - 교차 계정 액세스
    - 임시 계정 액세스
    - 최소 권한
    - 감사
    - AWS 서비스 액세스
    - Amazon EC2용 IAM 역할
    - SAML 페더레이션

</details>

<details>
    <summary>역할 수임</summary>

![Alt text](image/image-8.png)
- 신뢰할 수 있는 엔터티를 사용하여 역할 수임.
- IAM 사용자, AWS 서비스는 AssumeRole API를 사용하여 AWS 관리 콘솔 또는 AWS CLI에서 역할 수임.
- 페더레이션 사용자는 AssumeRoleWithSAML 또는 AssumeRoleWithWebIdentity API를 사용함.
- AWS STS는 사용자에게 제한된 권한의 임시 자격 증명을 제공.
- 이를 이용하여 사용자는 AWS 리소스에 액세스.

</details>

<details>
    <summary>IAM 정책 배정</summary>

![Alt text](image/image-9.png)
- IAM 모든 정책 유형을 생성하고 관리할 수 있는 도구를 제공함.
- IAM 자격 증명에 권한 추가를 위해, 정책을 생성하고 정책 유효성을 검사한 뒤 자격 증명에 연결.
- 자격 증명 하나에 여러 정책 연결 가능하며, 정책은 여러 권한 포함 가능.

</details>

## 보안 정책

<details>
    <summary>보안 정책 범주</summary>

![Alt text](image/image-10.png)
- 자격 증명이나 리소스에 연결되어 해당 권한을 정의함.
- 사용자와 같은 보안 주체가 요청할 때 이러한 정책을 평가함.
- AWS에서 사용 가능한 정책
    - 자격 증명 기반 정책
        - IAM 자격 증명에 관리형 정책과 인라인 정책을 연결.
        - 사용자, 사용자가 속한 그룹 및 역할 포함.
    - 리소스 기반 정책
        - 리소스에 인란인 정책을 연결.
        - Amazone s3 버킷 정책 및  IAM 역할 신뢰 정책임.
    - AWS Organizations 서비스 제어 정책(SCP)
        - 조직 또는 단위의 계정 구성원에 대한 최대 권한을 정의
    - IAM 권한 경계
        - AWS에서는 IAM엔터티의 권한 경계를 지원함.
        - IAM 권한 경계를 사용하여 IAM 엔터티가 수행할 수 있는 최대 권한을 설정함.


</details>

<details>
    <summary>권한 부여</summary>

![Alt text](image/image-11.png)


</details>