##### Cloud Native 구성

* 마이크로서비스
* CI/CD
* DevOps (기획, 배포, 구현 반복)
* Container



##### Cloud Native 를 운영하면서 고려해야할 항목

* Codebase : 동일한 배포
* Dependencies : 수정을 하더라도 전체 시스템에 영향도가 없어야함 

* Config 동일한 환경
* Backing services 의존성 없이 동일한 환경에서 개발 가능해야함
* Build, release, run 배포 실행 등 각 환경이 엄격하게 분리
* Processes 다른마이크로 서비스와 분리되어 독립적인 프로세스를 가져야함
* Port binding 자체 포트를가지고 격리되어야함
* Concurrency 동일한 서비스에 여러 인스턴스에 올라갈 수 있어야함
* Disposability 서비스 인스턴스 삭제가 가능해야함
* Dev/prod parity 개발/운영 단계 분리
* Logs 언제든 로깅 되어야함 (애저 등을 이용)
* Admin processes 관리되는 마이크로 서비스들을 관리할 수 있는 도구가 있어야한다.

