## 2주차 과제

### API

#### 회원
POST /member/signup : 회원 가입 <br/> 
POST /member/login : 로그인

#### 게시글
GET /api/posts : 전체 게시글 최신 순 조회<br/>
GET /api/posts/{id} : id에 해당하는 게시글<br/>
POST /api/posts/ :게시글 작성<br/>
PUT /api/posts/{id} : 게시글 수정<br/>
DELETE /api/posts/{id} : 게시글 삭제

#### 댓글
GET /api/posts/{id} : id에 해당하는 게시글의 댓글<br/>
POST /api/posts/{id}/comments :게시글 작성<br/>
PUT /api/posts/{postId}/comments/{commentId} : 게시글 수정<br/>
DELETE /api/posts/{postId}/comments/{commentId} : 게시글 삭제

