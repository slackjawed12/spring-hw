## 입문주차 과제

### API

#### GET / : 전체 게시글 최신 순 조회
response : title, writer, contents, createdAt
#### GET /api/posts/{id} : id에 해당하는 게시글 조회
response : title, writer, contents, createdAt
#### POST /api/posts/ :게시글 작성
request : title, writer, contents, password
response : title, writer, contents, createdAt
#### PUT /api/posts/{id} : 게시글 수정
request : title, writer, contents, password
response : id
#### DELETE /api/posts/{id} : 게시글 삭제
request : password
